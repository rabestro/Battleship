package battleship.domain;

public enum ShotStatus {
    MISS("You missed. Try again:"),
    HIT("You hit a ship! Try again:"),
    SUNK("You sank a ship! Specify a new target:"),
    ALL("You sank the last ship. You won. Congratulations!");
    private final String description;

    ShotStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
