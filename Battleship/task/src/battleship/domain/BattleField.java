package battleship.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.fill;
import static java.util.stream.IntStream.range;

public class BattleField {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static final Map<IntPredicate, Integer> CHECK_CELLS = Map.of(
            i -> true, 0,
            i -> i / 10 > 0 && i % 10 > 0, -11,
            i -> i / 10 > 0, -10,
            i -> i / 10 > 0 && i % 10 < 9, -9,
            i -> i % 10 > 0, -1,
            i -> i % 10 < 9, 1,
            i -> i / 10 < 9 && i % 10 > 0, 9,
            i -> i / 10 < 9, 10,
            i -> i / 10 < 9 && i % 10 < 9, 11);

    private final CellType[] field;
    private final List<Ship> ships;

    public BattleField() {
        field = new CellType[WIDTH * HEIGHT];
        fill(field, CellType.EMPTY);
        ships = new ArrayList<>();
    }

    public boolean addShip(ShipType type, int bow, boolean isHorizontal) {
        final var ship = new Ship(type, i -> bow + (isHorizontal ? i : WIDTH * i));
        final var isOk = ship.getIndexes().allMatch(this::isOpen);
        if (isOk) {
            ship.getIndexes().forEach(this::setShip);
            ships.add(ship);
        }
        return isOk;
    }

    public void setCell(int index, CellType type) {
        field[index] = type;
    }

    private void setShip(int index) {
        field[index] = CellType.SHIP;
    }

    private boolean isOpen(int index) {
        return CHECK_CELLS.entrySet().stream()
                .filter(e -> e.getKey().test(index))
                .allMatch(e -> field[index + e.getValue()] == CellType.EMPTY);
    }

    public static int getIndex(String coordinates) {
        return WIDTH * (coordinates.charAt(0) - 'A') + Integer.parseInt(coordinates.substring(1)) - 1;
    }

    public CellType getCell(int index) {
        return field[index];
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
    }
}