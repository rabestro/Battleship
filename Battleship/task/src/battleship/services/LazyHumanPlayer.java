package battleship.services;

import battleship.domain.ShipType;

public class LazyHumanPlayer extends HumanPlayer {
    public LazyHumanPlayer(String name) {
        super(name);
    }

    @Override
    public void placeShips(ShipType[] ships) {
        battleField = new RandomShipArranger().placeShips(ships);
    }

}
