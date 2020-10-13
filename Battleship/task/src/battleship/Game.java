package battleship;

import battleship.domain.BattleField;
import battleship.domain.ShipType;
import battleship.domain.ShotStatus;
import battleship.services.HumanPlayer;
import battleship.services.ManualShipArranger;
import battleship.services.Player;
import battleship.services.ShipArranger;

import java.util.Scanner;

import static battleship.domain.ShipType.*;
import static java.util.stream.IntStream.range;

public class Game implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipType[] SHIPS_SET = new ShipType[]
            {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};
    private static final ShipArranger SHIP_ARRANGER = new ManualShipArranger();

    private BattleField[] fields = new BattleField[2];
    private String[] playerName = new String[]{"Player 1", "Player 2"};
    private int currentPlayer;
    private Player[] players;

    public Game() {
        players = new Player[]{
                new HumanPlayer("Player1"),
                new HumanPlayer("Player2")
        };
        players[0].setFoe(players[1]);
        players[1].setFoe(players[0]);
        currentPlayer = 0;
    }

    @Override
    public void run() {
        currentPlayer().placeShips(SHIPS_SET);
        switchPlayer();
        currentPlayer().placeShips(SHIPS_SET);
        startGame();
    }

    private Player currentPlayer() {
        return players[currentPlayer];
    }

    private void switchPlayer() {
        currentPlayer = 1 - currentPlayer;
        System.out.println("Press Enter and pass the move to " + currentPlayer());
        scanner.nextLine();
        clearScreen();
    }

    private void clearScreen() {
        range(0, 15).forEach(System.out::println);
    }

    private void startGame() {
        System.out.println("The game starts!");
        ShotStatus shotStatus;
        do {
            switchPlayer();
            final int index = currentPlayer().getShotIndex();
            shotStatus = currentPlayer().getFoe().shot(index);
            System.out.println(shotStatus);

        } while (shotStatus != ShotStatus.ALL);

        System.out.println(currentPlayer() + " won the battle!");
    }

}
