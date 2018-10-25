package gameFunctionality;

public class CertifiedTree extends Tree {
    
    private final int TREE_CLIMATE_POINTS = 5;
    private final int TREE_SELL_PRICE = 15;
    
    @Override
    public int getTreeClimatePoints() {
        return TREE_CLIMATE_POINTS;
    }

    @Override
    public int getTreePrice() {
        return TREE_SELL_PRICE;
    }

    @Override
    public void reduceTreeHealth(int reduceAmount) {
        this.treeHealth -= reduceAmount;
    }

    @Override
    public int getTreeHealth() {
        return this.treeHealth;
    }
    
    
    
    
}