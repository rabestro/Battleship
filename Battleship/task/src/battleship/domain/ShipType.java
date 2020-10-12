package battleship.domain;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public enum ShipType {
    AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
    BATTLESHIP(4, "Battleship"),
    SUBMARINE(3, "Submarine"),
    CRUISER(3, "Cruiser"),
    DESTROYER(2, "Destroyer");

    private final int length;
    private final String name;

    ShipType(final int length, final String name) {
        this.length = length;
        this.name = name;
    }

    public int length() {
        return length;
    }

    public IntStream getRange() {
        return IntStream.range(0, length);
    }

    public IntStream getIndexes(IntUnaryOperator getIndex) {
        return IntStream.range(0, length).map(getIndex);
    }

    @Override
    public String toString() {
        return name;
    }
}
