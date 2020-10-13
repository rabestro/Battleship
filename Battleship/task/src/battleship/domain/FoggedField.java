package battleship.domain;

public class FoggedField {
    private final BattleField battleField;

    public FoggedField(BattleField battleField) {
        this.battleField = battleField;
    }

    @Override
    public String toString() {
        return battleField.getFoggy();
    }
}
