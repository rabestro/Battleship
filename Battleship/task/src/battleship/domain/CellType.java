package battleship.domain;

public enum CellType {
    EMPTY('~'),
    SHIP('O'),
    HIT('X'),
    MISS('M');

    private final char symbol;

    CellType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

}