package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.ShipCoordinates;
import battleship.domain.ShipType;

import java.util.Scanner;

import static java.util.Arrays.stream;

public class ManualShipArranger implements ShipArranger {
    private static final Scanner scanner = new Scanner(System.in);

    private BattleField battleField;

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

            if (ship.isFit()) {
                battleField.addShip(ship);
                return;
            }
            System.out.println("Error! You placed it too close to another one. Try again:");
        }
    }

    private ShipCoordinates getShipCoordinates() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            final var coordinates = ShipCoordinates.of(input);
            if (coordinates.isPresent()) {
                return coordinates.get();
            }
            System.out.println("Error! Wrong ship location! Try again:");
        }
    }

}
