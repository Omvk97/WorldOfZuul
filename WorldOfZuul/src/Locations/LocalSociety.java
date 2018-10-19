package Locations;

import gameFunctionality.Player;

public class LocalSociety extends Room {

    private final int CLIMATESCENARIO1 = -50;
    private final int CLIMATESCENARIO2 = -100;
    private final int CLIMATESCENARIO3 = -150;
    private final int CLIMATESCENARIO4 = -200;
    private final int CLIMATESCENARIO5 = -250;

    private int monkeys;
    private int birds;
    private int humans;
    private int children;

    public LocalSociety(String description) {
        super(description);
        this.monkeys = 20;
        this.birds = 30;
        this.humans = 15;
        this.children = 5;
    }

    public void setValues() {
        int climatePoints = Player.getClimatePoints();
        this.monkeys = 20;
        this.birds = 30;
        this.humans = 15;
        this.children = 5;

        if (climatePoints > CLIMATESCENARIO1 && climatePoints < 0) {
            this.monkeys = 16;
            this.birds = 24;
            this.humans = 12;
            this.children = 4;
        } else if (climatePoints > CLIMATESCENARIO2 && climatePoints < CLIMATESCENARIO1) {
            this.monkeys = 12;
            this.birds = 18;
            this.humans = 9;
            this.children = 3;
        } else if (climatePoints > CLIMATESCENARIO3 && climatePoints < CLIMATESCENARIO2) {
            this.monkeys = 8;
            this.birds = 12;
            this.humans = 6;
            this.children = 2;
        } else if (climatePoints > CLIMATESCENARIO4 && climatePoints < CLIMATESCENARIO3) {
            this.monkeys = 4;
            this.birds = 6;
            this.humans = 3;
            this.children = 1;
        } else if (climatePoints > CLIMATESCENARIO5 && climatePoints < CLIMATESCENARIO4) {
            this.monkeys = 0;
            this.birds = 0;
            this.humans = 0;
            this.children = 0;
        }
    }

    @Override
    public String getLongDescription() {
        setValues();
        return "You are standing " + getShortDescription() + "!\n"
            + "You are greeted by " + this.humans + " humans, they have " + this.children + " children.\n"
            + "In the distance you see " + this.monkeys + " monkeys and " + this.birds + " birds \n"
            + getExitString();
    }

}
