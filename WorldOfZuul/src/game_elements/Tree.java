package game_elements;

public abstract class Tree {

    protected int treeHealth;
    protected final int TREE_CLIMATE_POINTS;
    protected final int TREE_SELL_PRICE;

    public Tree(int treeClimatePoints, int treeSellPrice) {
        this.treeHealth = 12;
        this.TREE_CLIMATE_POINTS = treeClimatePoints;
        this.TREE_SELL_PRICE = treeSellPrice;
    }

    /**
     * @return amount of climate points the tree gives.
     */
    public int getTreeClimatePoints() {
        return TREE_CLIMATE_POINTS;
    }

    /**
     * Returns the price of the current tree.
     *
     * @return amount worth of tree.
     */
    public int getTreePrice() {
        return TREE_SELL_PRICE;
    }

    /**
     * Reducing the tree health by the amount damaged by the player
     *
     * @param reduceAmount how much damage the tree has taken
     */
    public void reduceTreeHealth(int reduceAmount) {
        this.treeHealth -= reduceAmount;
    }

    /**
     * Information about tree health
     *
     * @return The tree's life
     */
    public int getTreeHealth() {
        return this.treeHealth;
    }

}
