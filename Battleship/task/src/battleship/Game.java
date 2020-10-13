package battleship;

import battleship.domain.ShipType;
import battleship.domain.ShotStatus;
import battleship.services.HumanPlayer;
import battleship.services.LazyHumanPlayer;
import battleship.services.Player;

import java.util.Scanner;

import static battleship.domain.ShipType.*;
import static java.util.stream.IntStream.range;

public class Game implements Runnable {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipType[] SHIPS_SET = new ShipType[]
            {AIRCRAFT_CARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};

    private int currentPlayer;
    private Player[] players;

    public Game() {
        players = new Player[]{
                new HumanPlayer("Player1"),
                new LazyHumanPlayer("Player2")
        };
        players[0].setFoe(players[1]);
        players[1].setFoe(players[0]);
        currentPlayer = 0;
    }

    @Override
    public void run() {
        currentPlayer()
                .placeShips(SHIPS_SET);
        switchPlayer();
        currentPlayer()
                .placeShips(SHIPS_SET);
        startGame();
    }

    private void startGame() {
        System.out.println("The game starts!");
        ShotStatus shotStatus;
        do {
            switchPlayer();
            shotStatus = currentPlayer().makeShot();
            System.out.println(shotStatus);
        } while (shotStatus != ShotStatus.ALL);

        System.out.println(currentPlayer() + " won the battle!");
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
        range(0, 15).forEach(i -> System.out.println());
    }

}
