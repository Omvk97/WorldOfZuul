package Scenarios;

import game_elements.NonCertifiedTree;
import game_functionality.Player;
import game_locations.LocalVillage;
import game_locations.Trailer;

public class PositiveScenario4 extends Scenario {

    public PositiveScenario4(int UPPER_BOUNDARY, int LOWER_BOUNDARY) {
        super(UPPER_BOUNDARY, LOWER_BOUNDARY);
    }

    @Override
    public String scenario(Player humanPlayer) {
        int climatePoints = humanPlayer.getClimatePoints();
        
        if (climatePoints < UPPER_BOUNDARY && climatePoints > LOWER_BOUNDARY) {
            if (humanPlayer.getCurrentRoom() instanceof LocalVillage) {
                Trailer trailer = humanPlayer.getTrailer();
                if (!humanPlayer.isGiftHasBeenGivenToday()) {
                    if (trailer.isStorageFull()) {
                        trailer.getLogsInStorage().add(new NonCertifiedTree());
                        int moneyAmountGiven = (int) (Math.random() * 10) + 1;
                        humanPlayer.addMoney(moneyAmountGiven);
                        humanPlayer.giftHasBeenGiven();

                        return "The villagers are very happy about your enviromental efforts\n"
                            + "and offer to donate " + moneyAmountGiven + " gold coins and 1 tree to you";
                    } else {
                        int moneyAmountGiven = (int) (Math.random() * 10) + 1;
                        humanPlayer.addMoney(moneyAmountGiven);
                        humanPlayer.giftHasBeenGiven();
                        return "The villagers are very happy about your enviromental efforts\n"
                            + "and offer to donate " + moneyAmountGiven + " gold coins to you";
                    }
                } else {
                    return "The villagers are very happy about your envriomental efforts\n"
                        + "but they don't have any more gifts for you today";
                }
            } else {
                return "The atmosphere is very stable and optimal\n"
                    + "Global Temperature: 21 degrees Celsius\n"
                    + "Water Level: Stable\n";
            }
        }
        return "";
    }
}
