package gameFunctionality;

import java.util.ArrayList;

public class Player {

    private static ArrayList<Tree> amountOfLogsCarrying; // Denne skal kunne resettes af Trailer class, den er static da den bliver tilgået af flere klasser
    private int money; // Skal bruges til at sælge træer, skal tilgås af flere klasser muligvis derfor static
    private static final int MAX_TREECARRY = 5; // skal sammenlignes med denne variabel altid
    private static int climatePoints;
    
    public Player() {
        Player.amountOfLogsCarrying = new ArrayList<>();
        this.money = 0;
        Player.climatePoints = 0;
    }

    public static int getAmountOfLogsCarrying() {
        return amountOfLogsCarrying.size();
    }
    
    public static Tree getTreeType(int treePosition) {
        return amountOfLogsCarrying.get(treePosition);
    }

    public void increaseAmountOfTreeCarrying(Tree tree) {
        if (tree instanceof CertifiedTree) {
            Player.amountOfLogsCarrying.add(new CertifiedTree());
        } else {
            Player.amountOfLogsCarrying.add(new NonCertifiedTree());
        }
    }
    
    public void loadLogsToStorage() {
        Player.amountOfLogsCarrying = new ArrayList<>();
    }

    public int getMoney() {
        return money;
    }
    
    public void addMoney(int treeSellPrice) {
        this.money += treeSellPrice;
    }
    
    public static int getClimatePoints() {
        return Player.climatePoints;
    }
    
    public static void addClimatePoints(int treeClimatePoints) {
        Player.climatePoints += treeClimatePoints;
        System.out.println(Player.climatePoints);
    }

    public static int getMAX_TREECARRY() {
        return MAX_TREECARRY;
    }
}
