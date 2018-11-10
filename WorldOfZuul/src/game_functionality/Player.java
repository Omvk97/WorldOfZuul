package game_functionality;

import game_elements.BackPack;
import game_elements.Axe;
import game_locations.Room;
import game_locations.Trailer;

public class Player {

    private final static int MIN_CLIMATEPOINTS = -250;

    private int money;
    private int climatePoints;
    private boolean giftHasBeenGivenToday;
    private Room currentRoom = null;
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private int saplingBundleAmount;
    private boolean saplingsPlanted;
    private boolean hasChoppedTrees;
    private final Trailer trailer;
    private Room previousRoom;

    public Player(BackPack starterBackPack, Trailer trailer) {
        this.equippedBackPack = starterBackPack;
        this.trailer = trailer;
        this.previousRoom = trailer;
    }

    /**
     * Used to determine whether or not the game should end. If the climatepoints
     * goes over this threshold, the game is over, the world is destroyed
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
     * Used to move the player around in the rooms
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
     * Gives the player a new axe if the player buys a new one in Store
     *
     * @param newAxe The new Axe that is to be equipped
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money -= newAxe.getPrice();
    }
    
    public void pickedUpAxe(Axe axe) {
        equippedAxe = axe;
    }

    public Trailer getTrailer() {
        return trailer;
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
     * Used to get access to currently equipped Axe and it's methods.
     *
     * @return Axe that is equipped
     */
    public Axe getAxe() {
        return equippedAxe;
    }

    /**
     * Used to get access to currently equipped BackPack and it's methods.
     *
     * @return BackPack that is currently equipped
     */
    public BackPack backPack() {
        return equippedBackPack;
    }

    public void boughtBackPack(BackPack newBackPack) {
        equippedBackPack = newBackPack;
    }

    public int getSaplingAmount() {
        return saplingBundleAmount;
    }

    public boolean buySaplingBundle(int saplingBundleAmount, int saplingCost) {
        if (saplingCost <= money) {
            money -= saplingCost;
            this.saplingBundleAmount += saplingBundleAmount;
            return true;
        } else {
            return false;
        }
    }

    public void plantSeeds() {
        saplingsPlanted = true;
        saplingBundleAmount--;
    }

    public void setHasChoppedTreesInCertifiedForest() {
        hasChoppedTrees = true;
    }

    /**
     * Resets all the things that the player can interact with during a day. And also checks
     * for if the player has choppedTrees without replanting, if this is the case the player will
     * recieve a fine.
     */
    public boolean sleep() {
        if (!saplingsPlanted && hasChoppedTrees) {
            System.out.println("YOU DIDN'T REPLANT TREES HERE IS A FINE OF 200 GOLD COINS");
            money -= 200;
            return false;
        }
        saplingsPlanted = false;
        hasChoppedTrees = false;
        giftHasBeenGivenToday = false;
        return true;
    }

    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        giftHasBeenGivenToday = true;
    }
}
