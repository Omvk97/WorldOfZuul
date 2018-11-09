package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class PositiveScenario2 extends Scenario {

    public PositiveScenario2(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "The villagers are starting to like you and offer you\n"
                    + "to join them over a cup of tee";
            } else {
                return "Air pollution is starting to thin out globally\n"
                    + "Global Temperature: 21 degrees Celsius\n"
                    + "Water Level: Stable\n";
            }
        }
        return "";
    }
}
