package game_locations;

import game_elements.Radio;
import game_elements.Axe;
import game_elements.AxeFactory;
import game_elements.Tree;
import game_functionality.Player;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class Trailer extends Room {
    private final static int NUM_PLAY_DAYS = 20;
    private final static int MAX_TREESTORAGEAMOUNT = 30;
    private final ArrayList<Tree> logsInStorage;
    private Axe starterAxe = AxeFactory.createStarterAxe();
    private final Radio radio = new Radio();
    private int numOfDaysGoneBy;


    public Trailer() {
        this.logsInStorage = new ArrayList<>();
        numOfDaysGoneBy = 1;
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return "You stand in your trailer, it is your home\n"
            + "You have " + humanPlayer.getClimatePoints() + " climate points\n"
            + "---------------------------------------------\n"
            + "○ Store Logs   ➤ Store logs you are carrying\n"
            + "○ Check Wallet ➤ See how much money you have\n"
            + "○ Sleep\n"
            + (starterAxe != null ? "○ Pick up Axe\n" : "")
            + "---------------------------------------------";
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

    @Override
    public void option1(Player humanPlayer, Label textArea) {
        if (humanPlayer.backPack().getAmountOfLogsInBackPack() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }
        /**
         * Copies all the elements from the backpack
         */
        ArrayList<Tree> copyAmountOflogsCarrying = new ArrayList<>();
        for (Tree tree : humanPlayer.backPack().getLogsInBackPack()) {
            copyAmountOflogsCarrying.add(tree);
        }
        /**
         * Adds trees to storage and removes tree from backpack one by one.
         */
        for (Tree tree : copyAmountOflogsCarrying) {
            if (getLogsInStorage().size() < MAX_TREESTORAGEAMOUNT) {
                getLogsInStorage().add(tree);
                humanPlayer.backPack().removeLogFromBackpack();
            } else {
                System.out.println("You carry too many logs to store!");
                break;
            }
        }
        if (isStorageFull()) {
            System.out.println("Your storage contains " + getLogsInStorage().size() + " logs "
                + "and is now full! \n"
                + "Sell your logs in the store or upgrade storage space!");
        } else {
            System.out.println("You now have " + getLogsInStorage().size()
                + (getLogsInStorage().size() > 1 ? " logs" : " log") + " stored!");
        }
    }

    @Override
    public void option2(Player humanPlayer, Label textArea) {
        if (humanPlayer.getMoney() == 0) {
            System.out.println("Your wallet is empty! What a shame!");
        } else {
            System.out.println("You wallet holds " + humanPlayer.getMoney() + " gold coins");
        }
    }

    /**
     * @param humanPlayer the user.
     */
    @Override
    public void option3(Player humanPlayer, Label textArea) {
        int daysleft = NUM_PLAY_DAYS - numOfDaysGoneBy;
        int fineAmount = 0;
        if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() != 0) {
            fineAmount = givePlayerFine(humanPlayer);
            System.out.println("Your fine amounts to " + fineAmount + " gold coins!");
        }
        humanPlayer.sleep(fineAmount);
        if (numOfDaysGoneBy++ >= NUM_PLAY_DAYS) {
            System.out.println("THERE IS NO MORE DAYS, YOUR HIGHSCORE IS: "
                + humanPlayer.getHighScore());
            System.exit(0);
        }
        System.out.println("The sun goes down and you sleep tight \n"
            + "ZzzzZzzzZzzzZzzz");
        System.out.println("The sun rises and you are ready to tackle the day! \n"
            + (daysleft > 1 ? "It's day " + numOfDaysGoneBy + " and there is " + daysleft + " days left"
                : "This is your last day as a lumberjack!"));
        Random globalOrLocal = new Random();
        if (globalOrLocal.nextBoolean()) {
            radio.globalNews(humanPlayer);
        } else {
            radio.localNews(humanPlayer);
        }
    }

    /**
     * If the player hasn't picked up the starterAxe they will be prompted with the option to pick
     * it up
     *
     * @param humanPlayer user that picks up the starter axe
     */
    @Override
    public void option4(Player humanPlayer, Label textArea) {
        if (starterAxe != null) {
            humanPlayer.boughtAxe(starterAxe);
            starterAxe = null;
            textArea.setText("You equipped an axe!");
        } else {
            textArea.setText("I don't know what you mean");
        }
    }

    private int givePlayerFine(Player humanPlayer) {
        Boolean correctAnswer = true;
        Scanner questionAnswer = new Scanner(System.in);
        String questionOne = "How many million hectare forest area disappear each year?";
        String questionTwo = "How many million hectare forest area does FSC cover over?";
        String questionThree = "How many million hectare forest area does PEFC cover over?";
        System.out.println("You didn't replant all the trees in the ceritifed forest!\n"
            + "Here is a chance to redeem yourself");
        int randomNum = (int) (Math.random() * 3) + 1;
        switch (randomNum) {
            case 1:
                System.out.println(questionOne);
                correctAnswer = answerValidation(questionAnswer.nextLine(), "7");
                break;
            case 2:
                System.out.println(questionTwo);
                correctAnswer = answerValidation(questionAnswer.nextLine(), "200");
                break;
            case 3:
                System.out.println(questionThree);
                correctAnswer = answerValidation(questionAnswer.nextLine(), "300");
                break;
        }
        if (!correctAnswer) {
            System.out.println("WRONG, study in the library!");
            return (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200);
        }
        return (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 +100);
    }

    private boolean answerValidation(String userAnswer, String correctAnswer) {
        if (userAnswer.contains(correctAnswer)) {
            System.out.println("Correct! Your fine has been cut in half! We also need you\n"
                + "to cover the cost of planting the trees that you forgot!\n");
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Trailer.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
