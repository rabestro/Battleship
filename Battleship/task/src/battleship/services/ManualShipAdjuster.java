package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.ShipType;

import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.util.Arrays.stream;

public class ManualShipAdjuster implements ShipAdjuster {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Pattern PATTERN_COORDINATES =
            Pattern.compile("([A-J])([1-9]|10) \\1([1-9]|10)|[A-J]([1-9]|10) [A-J]\\4");

    private final BattleField battleField;

    public ManualShipAdjuster() {
        this.battleField = new BattleField();
    }

    @Override
    public BattleField placeShips(ShipType[] ships) {
        stream(ships).forEach(this::placeShip);
        System.out.println(battleField);
        return battleField;
    }

    private void placeShip(ShipType ship) {
        boolean isAdded = false;
        System.out.println(battleField);
        do {
            System.out.println("Enter the coordinates of the " + ship + " (" + ship.getLength() + " cells):");
            final var coordinates = getCoordinates();
            final var isHorizontal = coordinates[0].charAt(0) == coordinates[1].charAt(0);
            final var length = isHorizontal
                    ? parseInt(coordinates[1].substring(1)) - parseInt(coordinates[0].substring(1))
                    : coordinates[1].charAt(0) - coordinates[0].charAt(0);
            if (abs(length) != ship.getLength() - 1) {
                System.out.println("Error! Wrong length of the " + ship + "! Try again:");
                continue;
            }
            final var bowIndex = BattleField.getIndex(coordinates[length > 0 ? 0 : 1]);
            isAdded = battleField.addShip(ship, bowIndex, isHorizontal);
            if (!isAdded) {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }
        } while (!isAdded);
    }

    private String[] getCoordinates() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            if (PATTERN_COORDINATES.matcher(input).matches()) {
                return input.split(" ");
            }
            System.out.println("Error! Wrong ship location! Try again:");
        }
    }
}
