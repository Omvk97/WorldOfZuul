package gameFunctionality;

public class Player {

    private static int amountOfLogsCarrying; // Denne skal kunne resettes af Trailer class, den er static da den bliver tilgået af flere klasser
    private int money; // Skal bruges til at sælge træer, skal tilgås af flere klasser muligvis derfor static
    final int MAX_TREECARRY = 5; // amountOfLogsCarrying skal sammenlignes med denne variabel altid
    final int TREE_PRICE = 10;

    public Player() {
        Player.amountOfLogsCarrying = 0;
        this.money = 0;
    }

    public int getAmountOfLogsCarrying() {
        return Player.amountOfLogsCarrying;
    }

    public void increaseAmountOfTreeCarrying() {
        Player.amountOfLogsCarrying++;
    }
    
    public void loadLogsToStorage() {
        Player.amountOfLogsCarrying = 0;
    }

    public int getMoney() {
        return money;
    }

    public void sellLog() {
        this.money += TREE_PRICE;
    }

    public int getMAX_TREECARRY() {
        return MAX_TREECARRY;
    }
}
