package Locations;

import gameFunctionality.Axe;
import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;

public class Trailer extends Room {

    private final static int NUM_PLAY_DAYS = 5;
    private final static int MAX_TREESTORAGEAMOUNT = 30;

    private int numOfDaysGoneBy;
    private ArrayList<Tree> logsInStorage;
    private Axe starterAxe = new Axe("axe", 0, 10, 3);

    public Trailer(String description, Player player) {
        super(description, player);
        this.numOfDaysGoneBy = 1;
        this.logsInStorage = new ArrayList();
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This is your home, you have " + humanPlayer.getClimatePoints() + " climate points,"
            + " your choices are: \n"
            + "----------------------------------\n"
            + "Store Logs\n"
            + "Check Wallet\n"
            + "Sleep";
            + (starterAxe != null ? "\nOption 4 - Pick up Axe" : "");
    }

    /**
     * Denne metode er til for at printe ud når spillet starter hvor mange dage der er i alt. Den bliver brugt i 'game'
     * klassen.
     *
     * @return mængden af dage spilleren har.
     */
    public static int getNumPlayDays() {
        return Trailer.NUM_PLAY_DAYS;
    }

    /**
     * Denne metode benyttes til at få information omkring oplagring af træerne. Den benyttes i 'Local Village' og
     * 'Store'.
     *
     * @return arrayList som indeholder både certificeret og ikke certificeret træer i storage space.
     */
    public ArrayList<Tree> getLogsInStorage() {
        return this.logsInStorage;
    }

    public void loadOffLogsInStorage() {
        this.logsInStorage = new ArrayList();
    }

    /**
     * Denne metode er til for at se om storage med træer er fyldt med træer, Den bruges i LocalVillage
     *
     * @return
     */
    public boolean isStorageFull() {
        return getLogsInStorage().size() == MAX_TREESTORAGEAMOUNT;
    }

    @Override
    public void option1() {
        if (humanPlayer.backPack().getAmountOfLogsInBackPack() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }
        /**
         * Kopier alle elementerne fra den oprindelige arraylist med de logs spilleren bærer når spilleren skal til at
         * lagre logs.
         */
        ArrayList<Tree> copyAmountOflogsCarrying = new ArrayList();
        for (Tree tree : humanPlayer.backPack().getLogsInBackPack()) {
            copyAmountOflogsCarrying.add(tree);
        }

        /**
         * Adder så mange logs som muligt fra den kopierede arraylist ovenover Fjerner logs fra den oprindelige
         * arraylist. Dette er for at undgå at man både adder og fjerner fra samme arrayList. Dette betyder at selvom
         * spilleren har flere logs end der kan være i storage arealet så kan spilleren stadig tilføje så mange som
         * muligt og så bære rundt på resten.
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
    public void option2() {
        if (humanPlayer.getMoney() == 0) {
            System.out.println("Your wallet is empty! What a shame!");
        } else {
            System.out.println("You wallet holds " + humanPlayer.getMoney() + " gold coins");
        }
    }

    /**
     * Sørger for at alle ting som spilleren skal gøre
     */
    @Override
    public void option3() {
        int daysleft = NUM_PLAY_DAYS - numOfDaysGoneBy;
        if (numOfDaysGoneBy++ >= NUM_PLAY_DAYS) {
            System.out.println("THERE IS NO MORE DAYS, YOUR HIGHSCORE IS: "
                + humanPlayer.getHighScore());
            System.exit(0);
        }
        System.out.println("The sun goes down and you sleep tight \n"
            + "ZzzzZzzzZzzzZzzz");
        System.out.println("The sun rises and you are ready to tackle the day! \n"
            + (daysleft > 1 ? "There are " + daysleft + " days left!"
                : "This is your last day as a lumberjack!"));
        humanPlayer.sleep();
    }

    /**
     * If the player hasn't picked up the starterAxe before they will be prompted with the option to pick one up
     */
    @Override
    public void option4() {
        if (starterAxe != null) {
            humanPlayer.pickedUpAxe(starterAxe);
            starterAxe = null;
            System.out.println("You equipped an axe!");
        } else {
            System.out.println("I don't know what you mean");
        }
    }

}
