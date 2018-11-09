package Scenarios;

import game_functionality.Player;

public abstract class Scenario {

    protected final int UPPER_BOUNDARY;
    protected final int LOWER_BOUNDARY;

    public Scenario(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        this.UPPER_BOUNDARY = UPPER_BOUNDARY;
        this.LOWER_BOUNDARY = LOWER_BOUNDARY;
    }

    abstract public String scenario(Player humanPlayer);
}
