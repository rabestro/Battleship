package battleship.ui;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Coordinates {
    private static final Pattern PATTERN_COORDINATES = Pattern.compile(
            "([A-J])([1-9]|10) \\1([1-9]|10)|[A-J]([1-9]|10) [A-J]\\4");
    private static final Pattern PATTERN_COORDINATE = Pattern.compile("[A-J]([1-9]|10)");
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean isCorrect(String coordinate) {
        return PATTERN_COORDINATE.matcher(coordinate).matches();
    }
    public static int toIndex(String coordinates) {
        return 10 * (coordinates.charAt(0) - 'A') + Integer.parseInt(coordinates.substring(1)) - 1;
    }

    public static int getIndex() {
        while (true) {
            final var input = scanner.nextLine().toUpperCase();
            if (isCorrect(input)) {
                return toIndex(input);
            }
            System.out.println("Error! You entered the wrong coordinates! Try again:");
        }
    }

}
