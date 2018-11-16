package game_locations;

import game_elements.Radio;
import game_elements.Axe;
import game_elements.AxeFactory;
import game_elements.Tree;
import game_functionality.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class Trailer extends Room {
    private final String weatherReporter = "Jensen: ";
    private final static int MAX_TREESTORAGEAMOUNT = 30;
    private final ArrayList<Tree> logsInStorage;
    private Axe starterAxe = AxeFactory.createStarterAxe();
    private final Radio radio = new Radio();

    public Trailer() {
        this.logsInStorage = new ArrayList();
    }

    @Override
    public String getLongDescription(Player humanPlayer) {
        return "This is your trailer and your home\n"
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

    @Override
    public void option1(Player humanPlayer) {
        if (humanPlayer.backPack().getAmountOfLogsInBackPack() == 0) {
            System.out.println("You are not carrying any logs!");
            return;
        }
        /**
         * Copies all the elements from the backpack
         */
        ArrayList<Tree> copyAmountOflogsCarrying = new ArrayList();
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
    public void option2(Player humanPlayer) {
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
    public void option3(Player humanPlayer) {
        humanPlayer.dayCounter(humanPlayer);
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
    public void option4(Player humanPlayer) {
        if (starterAxe != null) {
            humanPlayer.pickedUpAxe(starterAxe);
            starterAxe = null;
            System.out.println("You equipped an axe!");
        } else {
            System.out.println("I don't know what you mean");
        }
    }
}
