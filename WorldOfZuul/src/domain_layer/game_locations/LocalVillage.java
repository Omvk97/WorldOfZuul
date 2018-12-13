package domain_layer.game_locations;

import domain_layer.game_elements.NonCertifiedTree;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * This room is where the player will be met with some consequences of their actions. The room
 * functions as a stop between other rooms and gives different scenarios depending on the players
 * action
 *
 * @author oliver co-author: daniel
 */
public class LocalVillage extends Room {

    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    public LocalVillage() {
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return "You are standing in the local village! \n"
            + getScenario(humanPlayer) + "\n";
    }

    /**
     * This method is supposed to return a given scenario based off the players climate points.
     *
     * @param humanPlayer the player that enters the village.
     * @return what the villagers think about the player and how the village is doing.
     */
    public String getScenario(Player humanPlayer) {
        LinkedHashMap<Integer, String> scenarios = new LinkedHashMap<>();
        scenarios.put(99, "The local people are happy about your\n"
            + "environmental considerations and \nwildlife is flourishing");
        scenarios.put(49, "The local people greet you a kind welcome \n"
            + "and you observe a healthy and vibrant wildlife");
        scenarios.put(-49, "The local people from the village greet you welcome"
            + "\nand you observe animals starving");
        scenarios.put(-99, "The local people stopped giving you"
            + "\nhospitality and the wildlife is suffering");
        scenarios.put(-149, "You go and talk to some of the locals...\n"
            + "They scream and shout at you...\n"
            + "They are apparently lacking ressources from the forest...\n "
            + "wildlife is decimated");
        scenarios.put(-199, "The village is silent and only a couple of humans is around\n"
            + "You talk to some of them...\n"
            + "They spit on you... Not very friendly...\n"
            + "You observe dead animals in the street...");
        scenarios.put(-249, "The village has been forsaken and wildlife is nowhere to be seen...\n"
            + "You see a sign \"Death to all lumberjacks\"...");

        int climatePoints = humanPlayer.getClimatePointsValue();
        List<Integer> keySet = new ArrayList<>(scenarios.keySet());
        for (int i = 0; i < keySet.size(); i++) {
            if (climatePoints > keySet.get(0)) {
                if (!playerInteraction.isGiftHasBeenGivenToday()) {
                    return giftScenario(humanPlayer);
                } else {
                    return scenarios.get(keySet.get(i));
                }
            } else if (climatePoints < keySet.get(keySet.size() - 1)) {
                return scenarios.get(keySet.get(keySet.size() - 1));
            } else if (climatePoints < keySet.get(i) && climatePoints > keySet.get(i + 1)) {
                return scenarios.get(keySet.get(i));
            }
        }
        return "You successfully broke the game";
    }

    /**
     * This method is supposed to reward the user with a gift if they have enough climate points If
     * the players storage is full the player will recieve a gold coin reward and otherwise the
     * player will recieve 1 tree and some gold.
     *
     * @param humanPlayer The object that functions as the player for the game interacting with
     * aspects of the game
     * @return text associated with getting a gift
     */
    public String giftScenario(Player humanPlayer) {
        final Trailer trailer = Game.getInstanceOfSelf().getTrailer();
        if (!playerInteraction.isGiftHasBeenGivenToday()) {
            if (trailer.isStorageFull()) {
                trailer.getLogsInStorage().add(new NonCertifiedTree(12));
                int moneyAmountGiven = (int) (Math.random() * 50) + 50;
                humanPlayer.addMoney(moneyAmountGiven);
                playerInteraction.setGiftHasBeenGivenToday(true);

                return "The villagers are very happy\n about your enviromental efforts\n"
                    + "and offer to donate " + moneyAmountGiven + " gold coins and 1 tree to you";
            } else {
                int moneyAmountGiven = (int) (Math.random() * 50) + 50;
                humanPlayer.addMoney(moneyAmountGiven);
                playerInteraction.setGiftHasBeenGivenToday(true);
                return "The villagers are very happy about your enviromental efforts\n"
                    + "and offer to donate " + moneyAmountGiven + " gold coins to you";
            }
        } else {
            return "The villagers are very happy\n about your envriomental efforts\n"
                + "but they don't have any more gifts for you today";
        }
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/LocalVillage.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
