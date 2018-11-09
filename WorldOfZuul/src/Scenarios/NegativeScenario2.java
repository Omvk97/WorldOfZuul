package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class NegativeScenario2 extends Scenario {

    public NegativeScenario2(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "The local people from the village stopped giving you\nhospitality "
                    + "and the wildlife is suffering visibly";
            } else {
                return "The polar ice caps are starting to melt\n"
                    + "Global Temperature: 26 degrees Celsius\n"
                    + "Water level: Risen 4 meters\n";
            }
        }
        return "";
    }

}
