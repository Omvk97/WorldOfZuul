package gameFunctionality;

public class NonCertifiedTree implements Tree {
    private final int TREE_CLIMATE_POINTS = -10;
    private final int TREE_SELL_PRICE = 10;
    
    @Override
    public int getTreeClimatePoints() {
        return TREE_CLIMATE_POINTS;
    }

    @Override
    public int getTreePrice() {
        return TREE_SELL_PRICE;
    }
}