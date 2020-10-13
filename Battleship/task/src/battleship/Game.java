package battleship;

import battleship.domain.BattleField;
import battleship.domain.Coordinates;
import battleship.domain.ShipType;
import battleship.domain.ShotStatus;
import battleship.services.ManualShipArranger;
import battleship.services.ShipArranger;

import java.util.Scanner;
import java.util.stream.IntStream;

import static battleship.domain.ShipType.*;

public class Game implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipType[] SHIPS_SET = new ShipType[]
            {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};
    private static final ShipArranger SHIP_ARRANGER = new ManualShipArranger();

    private BattleField fieldOne;
    private BattleField fieldTwo;
    private BattleField[] fields = new BattleField[2];
    private String[] playerName = new String[]{"Player 1", "Player 2"};
    private int currentPlayer = 0;

    @Override
    public void run() {
        placeShips();
        switchPlayer();
        placeShips();
        startGame();
    }

    private void placeShips() {
            System.out.println(playerName[currentPlayer] + ", place your ships on the game field");
            fields[currentPlayer] = SHIP_ARRANGER.placeShips(SHIPS_SET);
    }

    private void switchPlayer() {
        currentPlayer = 1 - currentPlayer;
        System.out.println("Press Enter and pass the move to " + playerName[currentPlayer]);
        scanner.nextLine();
        // clear screen
    }

    private void startGame() {
        System.out.println("The game starts!");
        ShotStatus shotStatus;
        do {
            switchPlayer();
            System.out.println(fields[1 - currentPlayer].getFoggy());
            System.out.println("---------------------");
            System.out.println(fields[currentPlayer]);
            System.out.println(playerName[currentPlayer] + ", it's your turn:");

            final int index = getCoordinates().getIndex();
            shotStatus = fields[1 - currentPlayer].shot(index);
            System.out.println(shotStatus);
        } while (shotStatus != ShotStatus.ALL);

        System.out.println(fields[currentPlayer] + " won the battle!");
    }

    private Coordinates getCoordinates() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            if (Coordinates.isValid(input)) {
                return new Coordinates(input);
            }
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }
    }

}
