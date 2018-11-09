package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class NegativeScenario5 extends Scenario {

    public NegativeScenario5(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints > UPPER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "The village has been forsaken and the wildlife is completely gone.\n"
                    + "Why did you do this? You mindlessly chopped down trees\n"
                    + "and destroyed this village";
            } else {
                return "The world is in anarchy. Civil unrest is at its most violent\n"
                    + "Global Temperature: 31 degrees Celsius\n"
                    + "Water Level: Risen 20 meters\n";
            }
        }
        return "";
    }

}
