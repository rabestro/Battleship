package battleship.domain;

public enum ShotStatus {
    START("The game starts!"),
    MISS("You missed. Try again:"),
    HIT("You hit a ship! Try again:"),
    SUNK("You sank a ship! Specify a new target:"),
    ALL("You sank the last ship. You won. Congratulations!");
    private final String description;

    ShotStatus(String description) {
        this.description = description;
    }
}
