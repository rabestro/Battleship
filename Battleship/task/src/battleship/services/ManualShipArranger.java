package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.Coordinates;
import battleship.domain.ShipType;

import java.util.Scanner;
import java.util.function.IntUnaryOperator;
import java.util.regex.Pattern;

import static java.util.Arrays.stream;

public class ManualShipArranger implements ShipArranger {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern PATTERN_COORDINATES =
            Pattern.compile("([A-J])([1-9]|10) \\1([1-9]|10)|[A-J]([1-9]|10) [A-J]\\4");

    private BattleField battleField;

    public ManualShipArranger() {
    }

    @Override
    public BattleField placeShips(ShipType[] ships) {
        battleField = new BattleField();
        stream(ships).forEach(this::placeShip);
        System.out.println(battleField);
        return battleField;
    }

    private void placeShip(ShipType shipType) {
        System.out.println(battleField);
        while (true) {
            System.out.println("Enter the coordinates of the " + shipType + " (" + shipType.length() + " cells):");

            final var shipCoordinates = getShipCoordinates();

            if (shipCoordinates.length() != shipType.length()) {
                System.out.println("Error! Wrong length of the " + shipType + "! Try again:");
                continue;
            }

            final var ship = battleField.new Ship(shipType, shipCoordinates.getIndexes());

            if (battleField.isShipFit(ship)) {
                battleField.addShip(ship);
                return;
            }
            System.out.println("Error! You placed it too close to another one. Try again:");
        }
    }

    private ShipCoordinates getShipCoordinates() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            if (PATTERN_COORDINATES.matcher(input).matches()) {
                final var coordinate = input.split(" ");
                final var x = new Coordinates(coordinate[0]);
                final var y = new Coordinates(coordinate[1]);
                return new ShipCoordinates(x, y);
            }
            System.out.println("Error! Wrong ship location! Try again:");
        }
    }

    private static class ShipCoordinates {
        private final Coordinates bow;
        private final Coordinates stern;

        ShipCoordinates(Coordinates x, Coordinates y) {
            final var isOrdered = x.getIndex() < y.getIndex();
            bow = isOrdered ? x : y;
            stern = isOrdered ? y : x;
        }

        boolean isHorizontal() {
            return bow.getRow() == stern.getRow();
        }

        int length() {
            return isHorizontal()
                    ? stern.getCol() - bow.getCol() + 1
                    : stern.getRow() - bow.getRow() + 1;
        }

        IntUnaryOperator getIndexes() {
            return i -> isHorizontal()
                    ? bow.getIndex() + i
                    : bow.getIndex() + BattleField.WIDTH * i;
        }
    }
}
