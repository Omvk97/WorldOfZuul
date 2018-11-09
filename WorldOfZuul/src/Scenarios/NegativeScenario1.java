package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class NegativeScenario1 extends Scenario {

    public NegativeScenario1(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "The local people from the village greet you welcome\nand you observe "
                    + "the wildlife steadily decaying";
            } else {
                return "The world is experiencing a global heat increase\n"
                    + "Global Temperature: 23 degrees Celsius\n"
                    + "Water level: Risen 1 meter\n";
            }
        }
        return "";
    }

}
