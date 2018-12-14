package domain_layer.game_elements;

/**
 * This class represents everything that a tree is and what a tree can do. A tree has life, climate
 * points and a sell price.
 *
 * @author oliver co-author: michael
 */
public abstract class Tree {

    private final int MAX_TREEHEALTH = 12;
    protected int treeHealth;
    protected int numPlanksWorth;
    protected final int TREE_CLIMATE_POINTS;
    protected final double TREE_SELL_PRICE_PR_HEALTH;

    public Tree(int treeHealth, int treeClimatePoints, double treeSellPrice) {
        this.treeHealth = treeHealth;
        this.TREE_CLIMATE_POINTS = treeClimatePoints;
        this.TREE_SELL_PRICE_PR_HEALTH = treeSellPrice;
        numPlanksWorth = treeHealth;
    }

    /**
     * @return amount of climate points the tree gives.
     */
    public int getTreeClimatePoints() {
        return TREE_CLIMATE_POINTS;
    }

    /**
     * Returns the price of the current tree based on the highest treeHealth the tree had before the
     * player started chopping it down.
     *
     * @return amount worth of tree. Converted to int
     */
    public int getTreePrice() {
        return (int) (TREE_SELL_PRICE_PR_HEALTH * numPlanksWorth);
    }

    /**
     * Reducing the tree health by the amount damaged by the player
     *
     * @param reduceAmount how much damage the tree has taken
     */
    public void reduceTreeHealth(double reduceAmount) {
        this.treeHealth -= reduceAmount;
    }

    public int getTreeHealth() {
        return this.treeHealth;
    }

    /**
     * After the player sleeps the tree has to grow in size, this methods makes sure of that. It
     * also makes sure that the tree never grows bigger than MAX_TREEHEALTH.
     *
     * @param amountOfTreeGrowth how much the tree should grow.
     */
    public void treeGrowth(int amountOfTreeGrowth) {
        if (amountOfTreeGrowth + treeHealth < MAX_TREEHEALTH) {
            treeHealth += amountOfTreeGrowth;
        } else {
            treeHealth = MAX_TREEHEALTH;
        }
        numPlanksWorth = treeHealth;
    }
}
