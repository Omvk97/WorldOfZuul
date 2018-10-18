package gameFunctionality;

public class Player {

    int amountOfTreeCarrying; // Denne skal kunne resettes af Trailer class
    int moneyInPocket;
    final int MAX_TREECARRY = 5;

    public Player() {
        this.amountOfTreeCarrying = 0;
        this.moneyInPocket = 0;
    }

    public int getAmountOfTreeCarrying() {
        return amountOfTreeCarrying;
    }

    public void setAmountOfTreeCarrying(int amountOfTreeCarrying) {
        this.amountOfTreeCarrying = amountOfTreeCarrying;
    }

    public int getMoneyInPocket() {
        return moneyInPocket;
    }

    public void setMoneyInPocket(int moneyInPocket) {
        this.moneyInPocket = moneyInPocket;
    }

    public int getMAX_TREECARRY() {
        return MAX_TREECARRY;
    }
}
