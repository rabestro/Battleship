package battleship.domain;

import java.util.Optional;
import java.util.function.IntUnaryOperator;

public class ShipCoordinates {
    private final Coordinates bow;
    private final Coordinates stern;

    private ShipCoordinates(Coordinates x, Coordinates y) {
        final var isOrdered = x.getIndex() < y.getIndex();
        bow = isOrdered ? x : y;
        stern = isOrdered ? y : x;
    }

    public static Optional<ShipCoordinates> of(String input) {
        final var coordinates = input.split(" ");
        final var isValid = coordinates.length == 2
                && Coordinates.isValid(coordinates[0])
                && Coordinates.isValid(coordinates[1]);
        if (!isValid) {
            return Optional.empty();
        }
        final var bow = new Coordinates(coordinates[0]);
        final var stern = new Coordinates(coordinates[1]);

        if (bow.getCol() != stern.getCol() && bow.getRow() != stern.getRow()) {
            return Optional.empty();
        }
        return Optional.of(new ShipCoordinates(bow, stern));
    }

    boolean isHorizontal() {
        return bow.getRow() == stern.getRow();
    }

    public int length() {
        return isHorizontal()
                ? stern.getCol() - bow.getCol() + 1
                : stern.getRow() - bow.getRow() + 1;
    }

    public IntUnaryOperator getIndexes() {
        return i -> isHorizontal()
                ? bow.getIndex() + i
                : bow.getIndex() + BattleField.WIDTH * i;
    }
}
