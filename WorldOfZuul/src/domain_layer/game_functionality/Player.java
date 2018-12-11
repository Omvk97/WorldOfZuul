package domain_layer.game_functionality;

import domain_layer.game_elements.Axe;
import domain_layer.game_elements.BackPack;
import domain_layer.game_elements.BackPackFactory;
import domain_layer.game_elements.Tree;
import domain_layer.game_locations.Room;
import domain_layer.game_locations.Trailer;
import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author oliver co-author: michael, steffen & daniel
 */
public class Player {

    private final static int MIN_CLIMATEPOINTS = -250;
    private boolean giftHasBeenGivenToday, axePickedUp;
    private final SimpleIntegerProperty money = new SimpleIntegerProperty();
    private final SimpleIntegerProperty climatePoints = new SimpleIntegerProperty();
    private final SimpleIntegerProperty logsInBackPack = new SimpleIntegerProperty();
    private final SimpleIntegerProperty logsInStorageProperty = new SimpleIntegerProperty();
    private final SimpleIntegerProperty saplingsCarrying = new SimpleIntegerProperty();
    private final SimpleIntegerProperty equippedAxeChange = new SimpleIntegerProperty();
    private Room currentRoom = null;
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private int numChoppedTreesWithoutPlantingSaplings;
    private final Trailer trailer;
    private Room previousRoom;
    private boolean slept;
    private String direction;

    private final File baseModelFile = new File("src/pictures/baseCharacter.png");
    private final File baseModelRightFile = new File("src/pictures/baseCharacterRight.png");

    private final File modelStarterAxeFile = new File("src/pictures/characterWithStarterAxe.png");
    private final File modelStarterAxeRightFile = new File("src/pictures/characterWithStarterAxeRight.png");
    private File characterModel;

    private final File modelIronAxeFile = new File("src/pictures/characterWithIronAxe.png");
    private final File modelIronAxeRightFile = new File("src/pictures/characterWithIronAxeRight.png");

    private final File modelSteelAxeFile = new File("src/pictures/characterWithSteelAxe.png");
    private final File modelSteelAxeRightFile = new File("src/pictures/characterWithSteelAxeRight.png");

    private final File modelDiamondAxeFile = new File("src/pictures/characterWithDiamondAxe.png");
    private final File modelDiamondAxeRightFile = new File("src/pictures/characterWithDiamondAxeRight.png");

    private final File modelFireAxeFile = new File("src/pictures/characterWithFireAxe.png");
    private final File modelFireAxeRightFile = new File("src/pictures/characterWithFireAxeRight.png");

    public Player(Trailer trailer) {
        this.equippedBackPack = BackPackFactory.createStarterBackPack();
        this.trailer = trailer;
        this.previousRoom = trailer;
        characterModel = baseModelFile;
        climatePoints.setValue(-10);
    }

    public File getCharacterModel() {
        return characterModel;
    }

    public void setCharacterModel(boolean characterGoingRight) {
        if (!characterGoingRight) {
            if (equippedAxe == null) {
                characterModel = baseModelFile;
            } else if (equippedAxe.getDescription().equals("starter axe")) {
                characterModel = modelStarterAxeFile;
            } else if (equippedAxe.getDescription().equals("iron axe")) {
                characterModel = modelIronAxeFile;
            } else if (equippedAxe.getDescription().equals("steel axe")) {
                characterModel = modelSteelAxeFile;
            } else if (equippedAxe.getDescription().equals("diamond axe")) {
                characterModel = modelDiamondAxeFile;
            } else if (equippedAxe.getDescription().equals("fire axe")) {
                characterModel = modelFireAxeFile;
            }
        } else {
            if (equippedAxe == null) {
                characterModel = baseModelRightFile;
            } else if (equippedAxe.getDescription().equals("starter axe")) {
                characterModel = modelStarterAxeRightFile;
            } else if (equippedAxe.getDescription().equals("iron axe")) {
                characterModel = modelIronAxeRightFile;
            } else if (equippedAxe.getDescription().equals("steel axe")) {
                characterModel = modelSteelAxeRightFile;
            } else if (equippedAxe.getDescription().equals("diamond axe")) {
                characterModel = modelDiamondAxeRightFile;
            } else if (equippedAxe.getDescription().equals("fire axe")) {
                characterModel = modelFireAxeRightFile;
            }
        }
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

    public int getHighScore() {
        int totalValueOfItems = money.getValue() + equippedBackPack.getPrice();
        for (Tree tree : equippedBackPack.getLogsInBackPack()) {
            totalValueOfItems += tree.getTreePrice();
        }
        if (playerHasAnAxe()) {
            totalValueOfItems += equippedAxe.getPrice();
        }

        return totalValueOfItems + climatePoints.getValue();
    }

    public double getAxeDurabilityPercentage() {
        return 1 - (((double) equippedAxe.getStartDurability() - equippedAxe.getDurability())
            / equippedAxe.getStartDurability());
    }

    public SimpleIntegerProperty getMoney() {
        return money;
    }

    public int getMoneyValue() {
        return money.getValue();
    }

    public void addMoney(int moneyAmount) {
        money.setValue(money.getValue() + moneyAmount);
    }

    public SimpleIntegerProperty getClimatePoints() {
        return climatePoints;
    }

    public int getClimatePointsValue() {
        return climatePoints.getValue();
    }

    public void addClimatePoints(int treeClimatePoints) {
        climatePoints.setValue(climatePoints.getValue() + treeClimatePoints);
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
     * Gives the player a new axe if the player buys a new one at the blacksmith
     *
     * @param newAxe The new Axe that is to be equipped
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money.setValue(money.getValue() - newAxe.getPrice());
        equippedAxeChange.setValue(equippedAxeChange.getValue() + 1);
    }

    public SimpleIntegerProperty getEquippedAxeChange() {
        return equippedAxeChange;
    }

    public void grindedAxe(int cost) {
        money.setValue(money.getValue() - cost);
    }

    public ArrayList<Tree> getLogsInStorage() {
        return trailer.getLogsInStorage();
    }

    public SimpleIntegerProperty getNumOfDaysgoneBy() {
        return trailer.getNumOfDaysGoneBy();
    }

    public int getNumOfDaysLeft() {
        return trailer.getNUM_PLAY_DAYS() - trailer.getNumOfDaysGoneByValue();
    }

    /**
     * when the trees are sold at the store, the storage has to be emptied
     */
    public void loadOffLogsInStorage() {
        trailer.getLogsInStorage().clear();
        updateLogsInBackPackAndStorage();
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
     * Used to reduce durability on the players currently equipped Axe and to destroy the axe
     * if it's durability ever reaches 0.
     *
     * @return if the axe was destroyed
     */
    public boolean useAxe() {
        equippedAxe.reduceDurability();
        if (equippedAxe.getDurability() == 0) {
            equippedAxe = null;
            return true;
        }
        return false;
    }

    /**
     * Used to get access to currently equipped Axe.
     *
     * @return Axe that is equipped
     */
    public Axe getEquippedAxe() {
        return equippedAxe;
    }

    public boolean playerHasAnAxe() {
        return equippedAxe != null;
    }

    /**
     * Used to get access to currently equipped BackPack.
     *
     * @return BackPack that is currently equipped
     */
    public BackPack getBackPack() {
        return equippedBackPack;
    }

    public void updateLogsInBackPackAndStorage() {
        logsInBackPack.setValue(equippedBackPack.getLogsInBackPack().size());
        logsInStorageProperty.setValue(trailer.getLogsInStorage().size());
    }

    public SimpleIntegerProperty getLogsInStorageProperty() {
        return logsInStorageProperty;
    }

    public SimpleIntegerProperty getLogsInBackPack() {
        return logsInBackPack;
    }

    public boolean boughtBackPack(BackPack newBackPack) {
        if (newBackPack.getPrice() <= money.getValue()) {
            money.setValue(money.getValue() - newBackPack.getPrice());
            equippedBackPack = newBackPack;
            return true;
        } else {
            return false;
        }
    }

    public IntegerProperty getSaplingsCarrying() {
        return saplingsCarrying;
    }

    public int getSaplingsCarryingValue() {
        return saplingsCarrying.getValue();
    }

    public boolean buySapling(int saplingAmount, int saplingCost) {
        int totalSaplingCost = saplingAmount * saplingCost;
        if (totalSaplingCost <= money.getValue()) {
            money.setValue(money.getValue() - totalSaplingCost);
            saplingsCarrying.setValue(saplingsCarrying.getValue() + saplingAmount);
            return true;
        } else {
            return false;
        }
    }

    public int plantSeeds() {
        int startAmountOfChoppedTrees = numChoppedTreesWithoutPlantingSaplings;
        int saplingsPlanted = 0;
        for (int i = 0; i < startAmountOfChoppedTrees; i++) {
            if (saplingsCarrying.getValue() > 0) {
                saplingsCarrying.setValue(saplingsCarrying.getValue() - 1);
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
     *
     * @param fineAmount how much the fine will cost the player, if any
     */
    public void sleep(int fineAmount) {
        money.setValue(money.getValue() - fineAmount);
        numChoppedTreesWithoutPlantingSaplings = 0;
        giftHasBeenGivenToday = false;
        slept = true;
    }

    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        giftHasBeenGivenToday = true;
    }

    public boolean isSlept() {
        return slept;
    }

    public void setSlept(boolean slept) {
        this.slept = slept;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getDamage() {
        if (equippedAxe != null) {
            return equippedAxe.getDamage();
        } else {
            return 1;
        }
    }

    public boolean isAxePickedUp() {
        return axePickedUp;
    }

    public void setAxePickedUp(boolean axePickedUp) {
        this.axePickedUp = axePickedUp;
    }

}
