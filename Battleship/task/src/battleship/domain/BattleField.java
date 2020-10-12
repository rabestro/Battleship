package battleship.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.fill;
import static java.util.stream.IntStream.range;

public class BattleField {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    private static final Map<IntPredicate, Integer> CHECK_CELLS = Map.of(
            i -> true, 0,
            i -> i / WIDTH > 0 && i % WIDTH > 0, -WIDTH - 1,
            i -> i / WIDTH > 0, -WIDTH,
            i -> i / WIDTH > 0 && i % WIDTH < WIDTH - 1, -WIDTH + 1,
            i -> i % WIDTH > 0, -1,
            i -> i % WIDTH < WIDTH - 1, 1,
            i -> i / WIDTH < WIDTH - 1 && i % WIDTH > 0, WIDTH - 1,
            i -> i / WIDTH < WIDTH - 1, WIDTH,
            i -> i / WIDTH < WIDTH - 1 && i % WIDTH < WIDTH - 1, WIDTH + 1);

    private final CellType[] field;
    private final List<Ship> ships;

    public BattleField() {
        field = new CellType[WIDTH * HEIGHT];
        fill(field, CellType.EMPTY);
        ships = new ArrayList<>();
    }

    public boolean isShipFit(Ship ship) {
        return ship.getIndexes().allMatch(this::isShipCanPlaced);
    }

    public void addShip(Ship ship) {
        ship.getIndexes().forEach(i -> field[i] = CellType.SHIP);
        ships.add(ship);
    }

    public void setCell(int index, CellType type) {
        field[index] = type;
    }

    private boolean isShipCanPlaced(int index) {
        return CHECK_CELLS.entrySet().stream()
                .filter(e -> e.getKey().test(index))
                .allMatch(e -> field[index + e.getValue()] == CellType.EMPTY);
    }

    public static int getIndex(String coordinate) {
        return WIDTH * (coordinate.charAt(0) - 'A') + Integer.parseInt(coordinate.substring(1)) - 1;
    }

    public boolean isHit(int index) {
        return field[index] == CellType.SHIP || field[index] == CellType.HIT;
    }

    public Optional<Ship> getShip(int index) {
        return ships.stream().filter(ship -> ship.getIndexes().anyMatch(i -> i == index)).findAny();
    }

    public boolean isAllSank() {
        return ships.stream().allMatch(Ship::isSank);
    }

    @Override
    public String toString() {
        return getField(false);
    }

    public String getField(boolean isFog) {
        return "  1 2 3 4 5 6 7 8 9 10" + range(0, field.length)
                .mapToObj(i -> String.format(i % 10 > 0 ? " %2$c" : "%n%c %c", 'A' + i / 10,
                        isFog && field[i] == CellType.SHIP ? CellType.EMPTY.getSymbol() : field[i].getSymbol()))
                .collect(Collectors.joining());
    }

    public class Ship {
        private final ShipType type;
        private final IntUnaryOperator getIndex;

        public Ship(ShipType type, IntUnaryOperator getIndex) {
            this.type = type;
            this.getIndex = getIndex;
        }

        public IntStream getIndexes() {
            return range(0, type.getLength()).map(getIndex);
        }

        public boolean isSank() {
            return getIndexes().allMatch(i -> field[i] == CellType.HIT);
        }
    }
}