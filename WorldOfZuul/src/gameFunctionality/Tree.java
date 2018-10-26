package gameFunctionality;

public abstract class Tree {
    protected int treeHealth;

    public Tree() {
        this.treeHealth = 12;
    }
    
    /**
     * @return mængden af klima points som det pågældende træ har.
     */
    abstract public int getTreeClimatePoints();
    
    /**
     * Bruges så store kan vide hvor mange penge træerne giver.
     * @return mængden af penge et træ giver
     */
    abstract public int getTreePrice();
    
    /**
     * Denne metode er til for at træerne kan have liv så spilleren skal slå flere gange
     * afhængig af spillerens økse.
     * @param reduceAmount er hvor meget spilleren skal "skade".
     */
    abstract public void reduceTreeHealth(int reduceAmount);
    
    /**
     * Bruges til at finde ud af om træet er "dødt" altså at treeHealth er 0.
     * @return træets liv.
     */
    abstract public int getTreeHealth();

}
