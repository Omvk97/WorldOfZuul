package game_functionality;

import game_elements.Axe;
import game_elements.BackPack;
import game_elements.BackPackFactory;
import game_elements.Tree;
import game_locations.Room;
import game_locations.Trailer;
import java.util.ArrayList;

public class Player {

    private final static int MIN_CLIMATEPOINTS = -250;

    private int money;
    private int climatePoints;
    private boolean giftHasBeenGivenToday;
    private Room currentRoom = null;
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private int amountOfSaplingsCarrying;
    private int numChoppedTreesWithoutPlantingSaplings;
    private final Trailer trailer;
    private Room previousRoom;
    private boolean hasSlept;

    public Player(Trailer trailer) {
        this.equippedBackPack = BackPackFactory.createStarterBackPack();
        this.trailer = trailer;
        this.previousRoom = trailer;
    }

    /**
     * Used to determine whether or not the game should end. If the climatepoints goes over this
     * threshold, the game is over, the world has been destroyed.
     *
     * @return int max_climatepoints
     */
    public static int getMIN_CLIMATEPOINTS() {
        return MIN_CLIMATEPOINTS;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int moneyAmount) {
        this.money += moneyAmount;
    }

    public int getClimatePoints() {
        return climatePoints;
    }

    public void addClimatePoints(int treeClimatePoints) {
        climatePoints += treeClimatePoints;
    }

    /**
     * @return The room that the player currently resides in
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Used to move the player around the rooms.
     *
     * @param newRoom: The room that the player is moving to
     */
    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null) {
            previousRoom = currentRoom;
        }
        currentRoom = newRoom;
    }

    public Room getPreviousRoom() {
        return previousRoom;
    }

    /**
     * A simple method of calculating highScore, not finally implemented yet
     *
     * @return int value that is money added with climatepoints
     */
    public int getHighScore() {
        return money + climatePoints;
    }

    /**
     * Gives the player a new axe if the player buys a new one at the blacksmith
     *
     * @param newAxe The new Axe that is to be equipped
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money -= newAxe.getPrice();
    }

    public void grindedAxe(int cost) {
        money -= cost;
    }

    public ArrayList<Tree> getLogsInStorage() {
        return trailer.getLogsInStorage();
    }

    /**
     * when the trees are sold at the store, the storage has to be emptied
     */
    public void loadOffLogsInStorage() {
        trailer.getLogsInStorage().clear();
    }
    
    public boolean isStorageFull() {
        return trailer.isStorageFull();
    }
    
    public String putPlayerInTrailer() {
        setCurrentRoom(trailer);
        return currentRoom.roomEntrance(this);
    }
    /**
     * @return boolean whether or not the player has an axe equipped
     */
    public boolean canUseAxe() {
        return equippedAxe != null;
    }

    /**
     * Used to reduce durability on the players currently equipped Axe
     */
    public void useAxe() {
        equippedAxe.reduceDurability();
        if (equippedAxe.getDurability() == (equippedAxe.getStartDurability() / 2)) {
            System.out.println("Your axe is at half durability");
        } else if (equippedAxe.getDurability() == 0) {
            System.out.println("Your axe broke, gosh dangit");
            equippedAxe = null;
        }
    }

    /**
     * Used to get access to currently equipped Axe.
     *
     * @return Axe that is equipped
     */
    public Axe getAxe() {
        return equippedAxe;
    }

    /**
     * Used to get access to currently equipped BackPack.
     *
     * @return BackPack that is currently equipped
     */
    public BackPack backPack() {
        return equippedBackPack;
    }

    public void boughtBackPack(BackPack newBackPack) {
        equippedBackPack = newBackPack;
    }

    public int getAmountOfSaplingsCarrying() {
        return amountOfSaplingsCarrying;
    }

    public boolean buySaplingBundle(int saplingBundleAmount, int saplingCost) {
        if (saplingCost <= money) {
            money -= saplingCost;
            this.amountOfSaplingsCarrying += saplingBundleAmount;
            return true;
        } else {
            return false;
        }
    }

    public int plantSeeds() {
        int startAmountOfChoppedTrees = numChoppedTreesWithoutPlantingSaplings;
        int saplingsPlanted = 0;
        for (int i = 0; i < startAmountOfChoppedTrees; i++) {
            if (amountOfSaplingsCarrying > 0) {
                amountOfSaplingsCarrying--;
                numChoppedTreesWithoutPlantingSaplings--;
                saplingsPlanted++;
            }
        }
        return saplingsPlanted;
    }

    public void addChoppedTreesInCertifiedForest() {
        numChoppedTreesWithoutPlantingSaplings++;
    }

    public int getNumChoppedTreesWithoutPlantingSaplings() {
        return numChoppedTreesWithoutPlantingSaplings;
    }

    /**
     * Resets all the things that the player can interact with during a day. Also checks if the
     * player has choppedTrees without replanting, if this is the case the player will recieve a
     * fine and a quiz to reduce the fine amount.
     * @param fineAmount how much the fine will cost the player, if any
     */
    public void sleep(int fineAmount) {
        money -= fineAmount;
        numChoppedTreesWithoutPlantingSaplings = 0;
        giftHasBeenGivenToday = false;
        hasSlept = true;
    }

    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        giftHasBeenGivenToday = true;
    }

    public boolean isHasSlept() {
        return hasSlept;
    }

    public void setHasSlept(boolean hasSlept) {
        this.hasSlept = hasSlept;
    }
}
