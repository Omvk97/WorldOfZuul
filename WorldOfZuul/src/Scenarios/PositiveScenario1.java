package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class PositiveScenario1 extends Scenario {

    public PositiveScenario1(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "The local people from the village are happy about your"
                    + " environmental considerations\nand wildlife is flourishing";
            } else {
                return "The CO2 atmospheric concentration is stagnating\n"
                    + "Global Temperature: 21 degrees Celsius\n"
                    + "Water Level: Stable\n";
            }
        }
        return "";
    }
}
