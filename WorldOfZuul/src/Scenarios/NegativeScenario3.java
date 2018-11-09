package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class NegativeScenario3 extends Scenario {

    public NegativeScenario3(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                return "You cut too much wood! The local people from the village are enraged \n"
                    + "and chase you out of the village, spitting and throwing rocks after you\n"
                    + "wildlife is decimated \n"
                    + "As you fight you get a black eye";
            } else {
                return "Tropical storms are becoming more commonplace\n"
                    + "Global Temperature: 26 degrees Celsius\n"
                    + "Water level: Risen 6 meters\n";
            }
        }
        return "";
    }

}
