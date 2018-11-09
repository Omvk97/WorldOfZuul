package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class StandardScenario extends Scenario {

    public StandardScenario(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "The local people from the village greet you a kind welcome\nand you observe a "
                    + "healthy and vibrant wildlife";
            } else {
                return "Global atmospheric conditions are normal\n"
                    + "Global Temperature: 21 degrees Celsius\n"
                    + "Water level: Stable\n";
            }
        }
        return "";
    }
}
