package domain_layer.game_functionality;

import domain_layer.game_elements.Axe;
import domain_layer.game_elements.BackPack;
import domain_layer.game_elements.BackPackFactory;
import domain_layer.game_elements.Tree;
import domain_layer.game_locations.Trailer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This class represents everything that a player is and handles what a player can do.
 *
 * @author oliver co-author: michael, steffen & daniel
 */
public class Player {

    private final SimpleIntegerProperty money = new SimpleIntegerProperty();
    private final SimpleIntegerProperty climatePoints = new SimpleIntegerProperty();
    private final SimpleIntegerProperty saplingsCarrying = new SimpleIntegerProperty();
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    public Player(Trailer trailer) {
        this.equippedBackPack = BackPackFactory.createStarterBackPack();
        playerInteraction.setPreviousRoom(trailer);
    }

    /**
     * Calculates the players highscore based on their items value, money and climatePoints.
     *
     * @return
     */
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

    /**
     * Calculates how big of a percentage the currently equipped axe durability is at.
     *
     * @return fraction between 0.0 and 1 of the axe durability percentage state.
     */
    public double getAxeDurabilityPercentage() {
        return 1 - (((double) equippedAxe.getStartDurability() - equippedAxe.getDurability())
            / equippedAxe.getStartDurability());
    }

    /**
     * Used to add listeners to the players money for the Top Menu
     *
     * @return the players money integer property.
     */
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
     * Sets equippedAxe to the new axe the player just got. It also updates the listener so that it
     * knows that the players axe has changed so the Top Menu icon can be updated to the correct
     * icon
     *
     * @param newAxe The new Axe that is to be equipped
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money.setValue(money.getValue() - newAxe.getPrice());
        int newAxeChangeValue = playerInteraction.getEquippedAxeChange().getValue() + 1;
        playerInteraction.getEquippedAxeChange().setValue(newAxeChangeValue);
    }

    public void grindedAxe(int cost) {
        money.setValue(money.getValue() - cost);
    }

    /**
     * Used to reduce durability on the players currently equipped Axe and to destroy the axe if
     * it's durability ever reaches 0. And notifies the listener in Top menu that the players axe
     * has changed
     *
     * @return if the axe was destroyed
     */
    public boolean useAxe() {
        equippedAxe.reduceDurability();
        if (equippedAxe.getDurability() == 0) {
            equippedAxe = null;
            int newAxeChangeValue = playerInteraction.getEquippedAxeChange().getValue() + 1;
            playerInteraction.getEquippedAxeChange().setValue(newAxeChangeValue);
            return true;
        }
        return false;
    }

    public Axe getEquippedAxe() {
        return equippedAxe;
    }

    public boolean playerHasAnAxe() {
        return equippedAxe != null;
    }

    public BackPack getEquippedBackPack() {
        return equippedBackPack;
    }

    /**
     * Sets equippedBackPack to the new backpack the player just got. It also updates the listener
     * so that Top Menu axe icon can be updated to the correct. It also checks if the player has
     * enough money to buy the backpack that the player wants.
     *
     * @param newBackPack The new BackPack that is to be equipped
     * @return whether or not the player has enough money to buy the backpack.
     */
    public boolean buyBackPack(BackPack newBackPack) {
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

    /**
     * Checks if player has enough money to buy the sapling they want and adds it their inventory if
     * they do.
     *
     * @param saplingAmount how many sapling the player wants to buy.
     * @param saplingCost what the cost of each sapling is.
     * @return whether or not the player has enough money to buy the saplings.
     */
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

    /**
     * Checks how many trees has been felled without planting trees first, and for each tree felled
     * a new one will be planted. It also checks first if you have enough saplings to keep planting
     * trees.
     *
     * @return how many new tress the player had money to plant.
     */
    public int plantSeeds() {
        int startAmountOfChoppedTrees = playerInteraction.getNumChoppedTreesWithoutPlantingSaplings();
        int saplingsPlanted = 0;
        for (int i = 0; i < startAmountOfChoppedTrees; i++) {
            if (saplingsCarrying.getValue() > 0) {
                saplingsCarrying.setValue(saplingsCarrying.getValue() - 1);
                playerInteraction.reduceChoppedTreesInCertifiedForest();
                saplingsPlanted++;
            }
        }
        return saplingsPlanted;
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
        playerInteraction.setNumChoppedTreesWithoutPlantingSaplings(0);
        playerInteraction.setGiftHasBeenGivenToday(false);
        playerInteraction.setSlept(true);
    }

    /**
     * Determines what damage the player can do.
     *
     * @return 1 if no axe is equipped. Otherwise returns equippedAxe's damage.
     */
    public double getDamage() {
        if (equippedAxe != null) {
            return equippedAxe.getDamage();
        } else {
            return 1;
        }
    }

    public boolean canCarryMoreTrees() {
        return equippedBackPack.getAmountOfLogsInBackPack()
            < equippedBackPack.getBackpackCapacity();
    }

}
