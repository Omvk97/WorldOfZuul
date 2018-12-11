package game_locations;

import game_elements.Radio;
import game_elements.Axe;
import game_elements.AxeFactory;
import game_elements.Tree;
import controllers.HighScoreGraphics;
import game_functionality.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Trailer extends Room {

    private final static int NUM_PLAY_DAYS = 5;
    private final static int MAX_TREESTORAGEAMOUNT = 30;
    private final ArrayList<Tree> logsInStorage;
    private Axe starterAxe = AxeFactory.createStarterAxe();
    private final Radio radio = new Radio();
    private SimpleIntegerProperty numOfDaysGoneBy = new SimpleIntegerProperty();

    private final HighScoreGraphics highScoreGraphics = new HighScoreGraphics();

    public Trailer() {
        this.logsInStorage = new ArrayList<>();
        numOfDaysGoneBy.setValue(1);
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return "You stand in your trailer, it is your home\n";
//            + "---------------------------------------------\n"
//            + "○ Store Logs   ➤ Store logs you are carrying\n"
//            + "○ Check Wallet ➤ See how much money you have\n"
//            + "○ Sleep\n"
//            + (starterAxe != null ? "○ Pick up Axe\n" : "")
//            + "---------------------------------------------";
    }

    /**
     * @return arrayList with the trees in storage
     */
    public ArrayList<Tree> getLogsInStorage() {
        return this.logsInStorage;
    }

    /**
     * when the trees are sold at the store, the storage has to be emptied
     */
    public void loadOffLogsInStorage() {
        this.logsInStorage.clear();
    }

    /**
     * @return true if there is no more space left for trees in storage
     */
    public boolean isStorageFull() {
        return getLogsInStorage().size() == MAX_TREESTORAGEAMOUNT;
    }

    public int getNUM_PLAY_DAYS() {
        return NUM_PLAY_DAYS;
    }

    public SimpleIntegerProperty getNumOfDaysGoneBy() {
        return numOfDaysGoneBy;
    }

    public int getNumOfDaysGoneByValue() {
        return numOfDaysGoneBy.getValue();
    }

    @Override
    public String option1(Player humanPlayer) {
        if (humanPlayer.getBackPack().getAmountOfLogsInBackPack() == 0) {
            return "You are not carrying any logs!";
        }
        /**
         * Copies all the elements from the backpack
         */
        ArrayList<Tree> copyAmountOflogsCarrying = new ArrayList<>();
        for (Tree tree : humanPlayer.getBackPack().getLogsInBackPack()) {
            copyAmountOflogsCarrying.add(tree);
        }
        /**
         * Adds trees to storage and removes tree from backpack one by one.
         */
        for (Tree tree : copyAmountOflogsCarrying) {
            if (getLogsInStorage().size() < MAX_TREESTORAGEAMOUNT) {
                getLogsInStorage().add(tree);
                humanPlayer.getBackPack().removeLogFromBackpack();
            }
        }
        humanPlayer.updateLogsInBackPackAndStorage();

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
     * @param humanPlayer the user.
     */
    @Override
    public String option3(Player humanPlayer) {
//        int daysleft = NUM_PLAY_DAYS - numOfDaysGoneBy;
//        int fineAmount = 0;
//        if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() != 0) {
//            fineAmount = givePlayerFine(humanPlayer);
//            System.out.println("Your fine amounts to " + fineAmount + " gold coins!");
//        }
//        humanPlayer.sleep(fineAmount);
//        if (numOfDaysGoneBy++ >= NUM_PLAY_DAYS) {
//            highScoreGraphics.closeGame();
//            System.exit(0);
//        }
        Random globalOrLocal = new Random();
        if (globalOrLocal.nextBoolean()) {
            return radio.globalNews(humanPlayer);
        } else {
            return radio.localNews(humanPlayer);
        }
    }

    /**
     * If the player hasn't picked up the starterAxe they will be prompted with the option to pick it up
     *
     * @param humanPlayer user that picks up the starter axe
     */
    @Override
    public String option4(Player humanPlayer) {
        if (starterAxe != null) {
            humanPlayer.boughtAxe(starterAxe);
            starterAxe = null;
            return "You equipped an axe!";
        } else {
            return "I don't know what you mean";
        }
    }



    public boolean answerValidation(String userAnswer, String correctAnswer) {
        if (userAnswer.contains(correctAnswer)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/Trailer.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

    public HighScoreGraphics getHighScoreGraphics() {
        return highScoreGraphics;
    }
    

}
