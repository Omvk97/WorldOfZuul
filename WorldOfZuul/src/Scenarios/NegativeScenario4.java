package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class NegativeScenario4 extends Scenario {

    public NegativeScenario4(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "Parts of the village have left due to lacking ressources, the remainders\n"
                    + "chase you out of the village with guns \n"
                    + "In the fight you get a black eye and 5 broken fingers";
            } else {
                return "Large parts of the world are flooded."
                    + "The ice caps are gone\n"
                    + "Global Temperature: 28 degrees Celsius\n"
                    + "Water level: Risen 15 meters\n";
            }
        }
        return "";
    }

}
