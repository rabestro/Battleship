package battleship.services;

import battleship.domain.BattleField;
import battleship.domain.ShipType;
import battleship.domain.ShotStatus;

public abstract class Player {
    protected final String name;
    protected BattleField battleField;
    protected Player foe;

    protected Player(String name) {
        this.name = name;
    }

    public abstract void placeShips(ShipType[] ships);

    public void setFoe(Player foe) {
        this.foe = foe;
    }

    public String getFoggedField() {
        return battleField.getFoggy();
    }

    public abstract ShotStatus makeShot();

    public ShotStatus shot(int index) {
        return battleField.shot(index);
    }

    @Override
    public String toString() {
        return name;
    }
}
