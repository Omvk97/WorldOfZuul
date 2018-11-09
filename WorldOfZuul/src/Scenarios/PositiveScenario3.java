package Scenarios;

import game_functionality.Player;
import game_locations.LocalVillage;

public class PositiveScenario3 extends Scenario {

    public PositiveScenario3(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                if (!humanPlayer.isGiftHasBeenGivenToday()) {
                        int moneyAmountGiven = (int) (Math.random() * 10) + 1;
                        humanPlayer.addMoney(moneyAmountGiven);
                        humanPlayer.giftHasBeenGiven();
                        return "The villagers gather around you as you enter the town\n"
                            + "they bring you a gift of " + moneyAmountGiven + "gold coins";
                    }
            } else {
                return "Forest areas are steadily increasing\n"
                    + "Global Temperature: 21 degrees Celsius\n"
                    + "Water Level: Stable\n";
            }
        }
        return "";
    }
}
