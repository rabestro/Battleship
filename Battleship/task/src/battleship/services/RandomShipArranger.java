package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.Coordinates;
import battleship.domain.ShipType;

import java.util.Random;

import static java.util.Arrays.stream;

public class RandomShipArranger implements ShipArranger {
    private static final Random random = new Random();
    private BattleField battleField;

    @Override
    public BattleField placeShips(ShipType[] ships) {
        battleField = new BattleField();
        stream(ships).forEach(this::placeShip);
        return battleField;
    }

    private void placeShip(ShipType shipType) {
        BattleField.Ship ship;
        do {
            final var isHorizontal = random.nextBoolean();
            final var row = random.nextInt(BattleField.HEIGHT - (isHorizontal ? 0 : shipType.length() - 1));
            final var col = random.nextInt(BattleField.WIDTH - (isHorizontal ? shipType.length() - 1 : 0));
            final var bow = new Coordinates(row, col);
            ship = battleField.new Ship(shipType, bow.getIndexes(isHorizontal));
        } while (!ship.isFit());
        battleField.addShip(ship);
    }
}
