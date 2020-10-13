package battleship.domain;

public enum ShotStatus {
    MISS("You missed."),
    HIT("You hit a ship!"),
    SUNK("You sank a ship!"),
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
