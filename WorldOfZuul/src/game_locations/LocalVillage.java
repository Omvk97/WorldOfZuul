package game_locations;

import game_elements.NonCertifiedTree;
import game_functionality.Player;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LocalVillage extends Room {

    public LocalVillage(String description) {
        super(description);
    }

    @Override
    public String getLongDescription(Player humanPlayer) {
        return "You are standing " + getShortDescription() + "!\n"
            + getScenario(humanPlayer);
    }

    public String getScenario(Player humanPlayer) {
        LinkedHashMap<Integer, String> scenarios = new LinkedHashMap();
        scenarios.put(29, "The local people from the village are happy about your\n"
            + "environmental considerations and wildlife is flourishing");
        scenarios.put(19, "The local people from the village greet you a kind welcome \n"
            + "and you observe a healthy and vibrant wildlife");
        scenarios.put(-19, "The local people from the village greet you welcome"
            + "\nand you observe animals starving");
        scenarios.put(-29, "The local people from the village stopped giving you"
            + "\nhospitality and the wildlife is suffering heavily");
        scenarios.put(-39, "You cut too much wood! The local people from the village are enraged!\n"
            + "Spitting and throwing rocks after you\n "
            + "wildlife is decimated");
        scenarios.put(-49, "Parts of the village have left due to lacking ressources, the remainders"
            + "\n chase you with guns");
        scenarios.put(-59, "The village has been forsaken and the wildlife is completely gone\n"
            + "Why did you do this? You mindlessly chopped down trees and destroyed this village");

        int climatePoints = humanPlayer.getClimatePoints();
        List<Integer> keySet = new ArrayList(scenarios.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            if (climatePoints > keySet.get(0)) {
                return giftScenario(humanPlayer);
            } else if ( climatePoints < keySet.get(keySet.size() - 1)) {
                return scenarios.get(keySet.get(keySet.size() - 1));
            } else if (climatePoints < keySet.get(i) && climatePoints > keySet.get(i + 1)) {
                return scenarios.get(keySet.get(i));
            }
        }
        
        return "You successfully broke the game";
    }

    public String giftScenario(Player humanPlayer) {
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
    }
}
