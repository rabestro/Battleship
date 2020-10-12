package battleship;

import battleship.domain.BattleField;
import battleship.domain.CellType;
import battleship.domain.ShipType;
import battleship.services.ManualShipAdjuster;

import java.util.Scanner;
import java.util.regex.Pattern;

import static battleship.domain.ShipType.*;

public class Game implements Runnable {
    private static final Pattern PATTERN_CELL = Pattern.compile("[A-J]([1-9]|10)");
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipType[] SHIPS_SET = new ShipType[]
            {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};

    private BattleField battleField;

    @Override
    public void run() {
        final var adjuster = new ManualShipAdjuster();
        battleField = adjuster.placeShips(SHIPS_SET);
        startGame();
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

}
