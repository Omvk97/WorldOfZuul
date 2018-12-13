package domain_layer.game_functionality;

import domain_layer.game_elements.Axe;
import domain_layer.game_elements.BackPack;
import domain_layer.game_elements.BackPackFactory;
import domain_layer.game_elements.Tree;
import domain_layer.game_locations.Trailer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * this class represents everything that a player is and handles what a player can do.
 * @author oliver 
 * co-author: michael, steffen & daniel
 */
public class Player {

    private final SimpleIntegerProperty money = new SimpleIntegerProperty();
    private final SimpleIntegerProperty climatePoints = new SimpleIntegerProperty();
    private final SimpleIntegerProperty logsInBackPack = new SimpleIntegerProperty();
    private final SimpleIntegerProperty saplingsCarrying = new SimpleIntegerProperty();
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    public Player(Trailer trailer) {
        this.equippedBackPack = BackPackFactory.createStarterBackPack();
        playerInteraction.setPreviousRoom(trailer);
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
     * Gives the player a new axe if the player buys a new one at the blacksmith
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
     * it's durability ever reaches 0.
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

    public void updateLogsInBackPack() {
        logsInBackPack.setValue(equippedBackPack.getLogsInBackPack().size());
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

    public double getDamage() {
        if (equippedAxe != null) {
            return equippedAxe.getDamage();
        } else {
            return 1;
        }
    }

}
