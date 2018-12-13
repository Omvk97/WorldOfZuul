package domain_layer.game_locations;

import domain_layer.game_elements.Radio;
import domain_layer.game_elements.Axe;
import domain_layer.game_elements.AxeFactory;
import domain_layer.game_elements.Tree;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * This is the players home, it has a storage of trees. It is also where the player sleeps and hears
 * the weather report.
 *
 * @author oliver co-author: daniel
 */
public class Trailer extends Room {

    private final static int NUM_PLAY_DAYS = 15;
    private final static int MAX_TREESTORAGEAMOUNT = 30;
    private final ArrayList<Tree> logsInStorage;
    private Axe starterAxe = AxeFactory.createStarterAxe();
    private final Radio radio = new Radio();
    private int numOfDaysGoneBy = 0;
    private final SimpleIntegerProperty logsInStorageProperty = new SimpleIntegerProperty();

    public Trailer() {
        logsInStorage = new ArrayList<>();
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return "You stand in your trailer, it is your home\n";
    }

    /**
     * @return arrayList with the trees in storage
     */
    public ArrayList<Tree> getLogsInStorage() {
        return logsInStorage;
    }

    /**
     * when the trees are sold at the store, the storage has to be emptied
     */
    public void loadOffLogsInStorage() {
        logsInStorage.clear();
        updateLogsInStorage();
    }

    /**
     * @return true if there is no more space left in storage
     */
    public boolean isStorageFull() {
        return getLogsInStorage().size() == MAX_TREESTORAGEAMOUNT;
    }

    public int getNUM_PLAY_DAYS() {
        return NUM_PLAY_DAYS;
    }

    /**
     * This method has the responsibillity of storing the logs in the trailer.
     *
     * @param humanPlayer the player that has to store logs
     * @return the string associated with what has been done.
     */
    public String storeLogs(Player humanPlayer) {
        if (humanPlayer.getEquippedBackPack().getAmountOfLogsInBackPack() == 0) {
            return "You are not carrying any logs!";
        }

//        Copies all the elements from the backpack
        ArrayList<Tree> copyAmountOflogsCarrying = new ArrayList<>();
        for (Tree tree : humanPlayer.getEquippedBackPack().getLogsInBackPack()) {
            copyAmountOflogsCarrying.add(tree);
        }
//         Adds trees to storage and removes tree from backpack one by one.
        for (Tree tree : copyAmountOflogsCarrying) {
            if (getLogsInStorage().size() < MAX_TREESTORAGEAMOUNT) {
                getLogsInStorage().add(tree);
                humanPlayer.getEquippedBackPack().removeLogFromBackpack();
            }
        }
        PlayerInteraction.getInstanceOfSelf().updateLogsInBackPack(humanPlayer.getEquippedBackPack());
        updateLogsInStorage();

        if (isStorageFull()) {
            return "Your storage contains " + getLogsInStorage().size() + " logs "
                + "and is now full! \n"
                + "Sell your logs in the store or upgrade storage space!";
        } else {
            return "You now have " + getLogsInStorage().size()
                + (getLogsInStorage().size() > 1 ? " logs" : " log") + " stored!";
        }
    }

    /**
     * This method handles when the user sleeps and adds a day and prints out the news.
     *
     * @param humanPlayer the user.
     * @return the news which can be either
     */
    public String sleep(Player humanPlayer) {
        numOfDaysGoneBy++;
        Random globalOrLocal = new Random();
        if (globalOrLocal.nextBoolean()) {
            return radio.globalNews(humanPlayer);
        } else {
            return radio.localNews(humanPlayer);
        }
    }

    /**
     * If the player hasn't picked up the starterAxe they will be prompted with the option to pick
     * it up
     *
     * @param humanPlayer the player that picks up the starter axe
     * @return text representation of what has happend.
     */
    public String pickUpStarterAxe(Player humanPlayer) {
        if (starterAxe != null) {
            humanPlayer.boughtAxe(starterAxe);
            starterAxe = null;
            return "You equipped an axe!";
        } else {
            return "I don't know what you mean";
        }
    }

    /**
     * Checks to see if the answer on a fine input is the correct answer to the question.
     *
     * @param playerAnswer what the player has answered
     * @param correctAnswer
     * @return true if the answer is correct.
     */
    public boolean answerValidation(String playerAnswer, String correctAnswer) {
        return playerAnswer.contains(correctAnswer);
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/Trailer.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

    public int getNumOfDaysgoneBy() {
        return numOfDaysGoneBy;
    }

    public int getNumOfDaysLeft() {
        return NUM_PLAY_DAYS - numOfDaysGoneBy;
    }

    /**
     * notifies the listener in the topMenu whenever logsInStorage has changed.
     */
    private void updateLogsInStorage() {
        logsInStorageProperty.setValue(logsInStorage.size());
    }

    public SimpleIntegerProperty getLogsInStorageProperty() {
        return logsInStorageProperty;
    }
}
