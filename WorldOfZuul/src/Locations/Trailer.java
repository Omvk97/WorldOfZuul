package Locations;

import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;

public class Trailer extends Room {

    private final static int NUM_PLAY_DAYS = 5;
    private final static int MAX_TREESTORAGEAMOUNT = 15;
    
    private int numOfDaysGoneBy;
    private ArrayList<Tree> amountOfLogsInStorage;
    private boolean giftCanBeGiven;

    public Trailer(String description, Player player) {
        super(description, player);
        this.amountOfLogsInStorage = new ArrayList();
        this.numOfDaysGoneBy = 1;
        this.giftCanBeGiven = true;
    }

    public static int getNumPlayDays() {
        return Trailer.NUM_PLAY_DAYS;
    }
    
    public ArrayList<Tree> getStorage() {
        return this.amountOfLogsInStorage;
    }

    public boolean isStorageFull() {
        return amountOfLogsInStorage.size() == MAX_TREESTORAGEAMOUNT;
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This is your home, you have " + humanPlayer.getClimatePoints() + " climate points,"
            + " your options are: \n"
            + "Option 1 - Load off logs you are carrying \n"
            + "Option 2 - Look in your wallet \n"
            + "Option 3 - Sleep";
    }

    public void loadOffStorage() {
        amountOfLogsInStorage = new ArrayList();
    }

    @Override
    public void option1() {
        if (humanPlayer.getAmountOfLogsCarrying() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }
        /**
         * Kopier alle elementerne fra den oprindelige arraylist med de logs spilleren bærer når spilleren skal til at
         * lagre logs.
         */
        ArrayList<Tree> startAmountOflogsCarrying = new ArrayList();
        for (Tree tree : humanPlayer.getLogsCarrying()) {
            startAmountOflogsCarrying.add(tree);
        }

        /**
         * Adder så mange logs som muligt fra den kopierede arraylist ovenover Fjerner logs fra den oprindelige
         * arraylist. Dette er for at undgå at man både adder og fjerner fra samme arrayList. Dette betyder at selvom
         * spilleren har flere logs end der kan være i storage arealet så kan spilleren stadig tilføje så mange som
         * muligt og så bære rundt på resten.
         */
        for (Tree tree : startAmountOflogsCarrying) {
            if (this.amountOfLogsInStorage.size() < MAX_TREESTORAGEAMOUNT) {
                this.amountOfLogsInStorage.add(tree);
                humanPlayer.decreaseAmountOfTreeCarrying();
            } else {
                System.out.println("You carry too many logs to store!");
                break;
            }
        }
        if (this.amountOfLogsInStorage.size() == MAX_TREESTORAGEAMOUNT) {
            System.out.println("Your storage contains " + this.amountOfLogsInStorage.size() + " logs "
                + "and is now full! \n"
                + "Sell your logs in the store or upgrade storage space!");
        } else {
            System.out.println("You now have " + this.amountOfLogsInStorage.size()
                + (this.amountOfLogsInStorage.size() > 1 ? " logs" : " log") + " stored!");
        }
    }

    @Override
    public void option2() {
        if (humanPlayer.getMoney() == 0) {
            System.out.println("Your wallet is empty! What a shame!");
        } else {
            System.out.println("You wallet holds " + humanPlayer.getMoney() + " gold coins");
        }
    }

    @Override
    public void option3() {
        /**
         * This option is for sleeping, this is where the player will rest when there is no more activities left
         * to do, so that is when he can't cut more wood cuz then the game will go down and there is no more trees
         * left in the certified forest to cut, so he has to sleep so that there will grow new trees and so he will
         * be able to get more gifts from the villagers.
         */
        int daysleft = NUM_PLAY_DAYS - numOfDaysGoneBy;
        if (numOfDaysGoneBy++ >= NUM_PLAY_DAYS) {
            System.out.println("THERE IS NO MORE DAYS, YOUR HIGHSCORE IS: " + humanPlayer.getHighScore());
            System.exit(0);
        }
        System.out.println("The sun goes down and you sleep tight \n"
            + "ZzzzZzzzZzzzZzzz");
        System.out.println("The sun rises and you are ready to tackle the day! \n"
            + (daysleft > 1 ? "There are " + daysleft + " days left!" : "This is your last day as a lumberjack!"));
        CertifiedForest.regrowTrees();
        resetGift();
    }

    public boolean giftCanBeGiven() {
        return giftCanBeGiven;
    }
    
    public void giftHasBeenGiven() {
        this.giftCanBeGiven = false;
    }
    
    private void resetGift() {
        this.giftCanBeGiven = true;
    }

}
