package battleship.services;

import battleship.domain.Coordinates;
import battleship.domain.ShipType;
import battleship.domain.ShotStatus;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ShipArranger SHIP_ARRANGER = new ManualShipArranger();

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public void placeShips(ShipType[] ships) {
        System.out.println(name + ", place your ships on the game field");
        battleField = SHIP_ARRANGER.placeShips(ships);
    }

    @Override
    public ShotStatus makeShot() {
        System.out.println(foe.getFoggedField());
        System.out.println("---------------------");
        System.out.println(battleField);
        System.out.println(name + ", it's your turn:");
        return foe.shot(getCoordinates().getIndex());
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
