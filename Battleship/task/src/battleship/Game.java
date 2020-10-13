package battleship;

import battleship.domain.BattleField;
import battleship.domain.Coordinates;
import battleship.domain.ShipType;
import battleship.domain.ShotStatus;
import battleship.services.ManualShipArranger;

import java.util.Scanner;

import static battleship.domain.ShipType.*;

public class Game implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipType[] SHIPS_SET = new ShipType[]
            {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};

    private BattleField fieldOne;
    private BattleField fieldTwo;

    @Override
    public void run() {
        placeShips();
        startGame();
    }

    private void placeShips() {
        final var adjuster = new ManualShipArranger();

        System.out.println("Player 1, place your ships on the game field");
        fieldOne = adjuster.placeShips(SHIPS_SET);
        switchPlayer();
        System.out.println("Player 2, place your ships on the game field");
        fieldTwo = adjuster.placeShips(SHIPS_SET);
        switchPlayer();
    }

    private void switchPlayer() {
        System.out.println("Press Enter and pass the move to another player");
        scanner.nextLine();
        // clear screen
    }

    private void startGame() {
        System.out.println("The game starts!");
        System.out.println(fieldTwo.getFoggy());
        System.out.println("---------------------");
        System.out.println(fieldOne);
        System.out.println("Take a shot!");

        ShotStatus shotStatus;
        do {
            final int index = getCoordinates().getIndex();
            shotStatus = fieldOne.shot(index);
            System.out.println(fieldOne.getFoggy());
            System.out.println(shotStatus);
        } while (shotStatus != ShotStatus.ALL);

        System.out.println("Bye!");
    }

    private Coordinates getCoordinates() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            if (Coordinates.isCorrect(input)) {
                return new Coordinates(input);
            }
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }
    }

}
