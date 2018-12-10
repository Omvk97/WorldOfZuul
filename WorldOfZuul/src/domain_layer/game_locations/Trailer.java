package domain_layer.game_locations;

import domain_layer.game_elements.Radio;
import domain_layer.game_elements.Axe;
import domain_layer.game_elements.AxeFactory;
import domain_layer.game_elements.Tree;
import domain_layer.game_functionality.Player;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author olive
 * co-author: daniel
 */
public class Trailer extends Room {

    private final static int NUM_PLAY_DAYS = 5;
    private final static int MAX_TREESTORAGEAMOUNT = 30;
    private final ArrayList<Tree> logsInStorage;
    private Axe starterAxe = AxeFactory.createStarterAxe();
    private final Radio radio = new Radio();
    private SimpleIntegerProperty numOfDaysGoneBy = new SimpleIntegerProperty();

    public Trailer() {
        this.logsInStorage = new ArrayList<>();
        numOfDaysGoneBy.setValue(1);
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return "You stand in your trailer, it is your home\n";
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

    public String option2(Player humanPlayer) {
        if (humanPlayer.getMoneyValue() == 0) {
            return "Your wallet is empty! What a shame!";
        } else {
            return "You wallet holds " + humanPlayer.getMoneyValue() + " gold coins";
        }
    }

    /**
     * @param humanPlayer the user.
     */
    public String option3(Player humanPlayer) {
        numOfDaysGoneBy.setValue(numOfDaysGoneBy.getValue() + 1);
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
    public String option4(Player humanPlayer) {
        if (starterAxe != null) {
            humanPlayer.boughtAxe(starterAxe);
            starterAxe = null;
            return "You equipped an axe!";
        } else {
            return "I don't know what you mean";
        }
    }

//    public int givePlayerFine(Player humanPlayer) {
//        Boolean correctAnswer = true;
//        Scanner questionAnswer = new Scanner(System.in);
//        String questionOne = "How many million hectare forest area disappear each year?";
//        String questionTwo = "How many million hectare forest area does FSC cover over?";
//        String questionThree = "How many million hectare forest area does PEFC cover over?";
//        System.out.println("You didn't replant all the trees in the ceritifed forest!\n"
//            + "Here is a chance to redeem yourself");
//        int randomNum = (int) (Math.random() * 3) + 1;
//        switch (randomNum) {
//            case 1:
//                System.out.println(questionOne);
//                correctAnswer = answerValidation(questionAnswer.nextLine(), "7");
//                break;
//            case 2:
//                System.out.println(questionTwo);
//                correctAnswer = answerValidation(questionAnswer.nextLine(), "200");
//                break;
//            case 3:
//                System.out.println(questionThree);
//                correctAnswer = answerValidation(questionAnswer.nextLine(), "300");
//                break;
//        }
//        if (!correctAnswer) {
//            System.out.println("WRONG, study in the library!");
//            return (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200);
//        }
//        return (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100);
//    }

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
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/Trailer.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
