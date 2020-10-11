package battleship.ui;

import battleship.domain.BattleField;
import battleship.domain.CellType;
import battleship.domain.ShipType;

import java.util.Scanner;
import java.util.regex.Pattern;

import static battleship.domain.ShipType.*;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

public class Game implements Runnable {
    private static final Pattern PATTERN_COORDINATES = Pattern.compile(
            "([A-J])([1-9]|10) \\1([1-9]|10)|[A-J]([1-9]|10) [A-J]\\4");
    private static final Pattern PATTERN_CELL = Pattern.compile("[A-J]([1-9]|10)");
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipType[] SHIPS_SET = new ShipType[]
            {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};

    private final BattleField battleField = new BattleField();

    @Override
    public void run() {
        placeShips();
        startGame();
    }

    private void placeShips() {
        System.out.println(battleField);

        for (var ship : SHIPS_SET) {
            while (!tryAdd(ship)) ;
        }
    }

    private void startGame() {
        System.out.println("The game starts!");
        System.out.println(battleField.getField(true));
        System.out.println("Take a shot!");

        boolean isAllSank;
        do {
            final int index = getIndex();
            final var isHit = battleField.isHit(index);
            battleField.setCell(index, isHit ? CellType.HIT : CellType.MISS);
            System.out.println(battleField.getField(true));

            final var isSank = isHit && battleField.getShip(index).orElseThrow().isSank();
            isAllSank = isSank && battleField.isAllSank();

            System.out.println(isHit ? isSank ? isAllSank ?
                    "You sank the last ship. You won. Congratulations!" :
                    "You sank a ship! Specify a new target:" :
                    "You hit a ship! Try again:" :
                    "You missed. Try again:");

        } while (!isAllSank);

        System.out.println("Bye!");
    }

    private int getIndex() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            if (PATTERN_CELL.matcher(input).matches()) {
                return BattleField.getIndex(input);
            }
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }
    }


    private boolean tryAdd(ShipType ship) {
        System.out.println("Enter the coordinates of the " + ship + " (" + ship.getLength() + " cells):");
        final var input = scanner.nextLine().toUpperCase();
        if (!PATTERN_COORDINATES.matcher(input).matches()) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        final var coordinates = input.split(" ");

        final var isHorizontal = coordinates[0].charAt(0) == coordinates[1].charAt(0);
        final var length = isHorizontal
                ? parseInt(coordinates[1].substring(1)) - parseInt(coordinates[0].substring(1))
                : coordinates[1].charAt(0) - coordinates[0].charAt(0);
        if (abs(length) != ship.getLength() - 1) {
            System.out.println("Error! Wrong length of the " + ship + "! Try again:");
            return false;
        }
        final var bowIndex = BattleField.getIndex(coordinates[length > 0 ? 0 : 1]);
        final var isAdded = battleField.addShip(ship, bowIndex, isHorizontal);
        if (!isAdded) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }

        System.out.println(battleField);
        return true;
    }
}
