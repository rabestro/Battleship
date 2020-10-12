package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.ShipType;

@FunctionalInterface
public interface ShipArranger {
    BattleField placeShips(ShipType[] ships);
}
