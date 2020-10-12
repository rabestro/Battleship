package battleship.domain;

import java.util.regex.Pattern;

import static java.text.MessageFormat.format;

public class Coordinates {
    private static final String ROW_PATTERN = format("[A-{0}]", (char) ('A' + BattleField.HEIGHT - 1));
    private static final String COL_PATTERN = "[1-9]|10";
    private static final Pattern PATTERN_COORDINATES = Pattern.compile(
            format("({0})({1}) \\1({1})|{0}({1}) {0}\\4", ROW_PATTERN, COL_PATTERN));
    private static final Pattern PATTERN_COORDINATE = Pattern.compile(
            format("{0}({1})", ROW_PATTERN, COL_PATTERN));

    private final int row;
    private final int col;

    public Coordinates(String coordinates) {
        row = coordinates.charAt(0) - 'A';
        col = Integer.parseInt(coordinates.substring(1)) - 1;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getIndex() {
        return BattleField.WIDTH * row + col;
    }

    public static boolean isCorrect(String coordinates) {
        return PATTERN_COORDINATE.matcher(coordinates).matches();
    }

}
