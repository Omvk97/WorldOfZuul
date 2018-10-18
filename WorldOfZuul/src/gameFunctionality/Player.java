package gameFunctionality;

public class Player {

    static int amountOfLogsCarrying; // Denne skal kunne resettes af Trailer class, den er static da den bliver tilgået af flere klasser
    static int money; // Skal bruges til at sælge træer, skal tilgås af flere klasser muligvis derfor static
    final int MAX_TREECARRY = 5; // amountOfLogsCarrying skal sammenlignes med denne variabel altid

    public Player() {
        Player.amountOfLogsCarrying = 0;
        Player.money = 0;
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

    public void setMoney(int money) {
        Player.money = money;
    }

    public int getMAX_TREECARRY() {
        return MAX_TREECARRY;
    }
}
