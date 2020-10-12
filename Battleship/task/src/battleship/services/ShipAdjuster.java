package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.ShipType;

@FunctionalInterface
public interface ShipAdjuster {
    BattleField placeShips(ShipType[] ships);
}
