package gameFunctionality;

import Locations.Room;
import java.util.ArrayList;

public class Player {

    private final static int MAX_TREECARRY = 5; // skal sammenlignes med denne variabel altid

    private ArrayList<Tree> amountOfLogsCarrying; // Denne skal kunne resettes af Trailer class, den er static da den bliver tilgået af flere klasser
    private int money; // Skal bruges til at sælge træer, skal tilgås af flere klasser muligvis derfor static
    private int climatePoints;
    private Room currentRoom = null;
    private int highScore;

    public Player() {
        this.amountOfLogsCarrying = new ArrayList<>();
        this.money = 0;
        this.climatePoints = 0;
        this.highScore = 5;
    }

    public int getAmountOfLogsCarrying() {
        return this.amountOfLogsCarrying.size();
    }

    public ArrayList<Tree> getLogsCarrying() {
        return this.amountOfLogsCarrying;
    }

    public Tree getTreeType(int treePosition) {
        return this.amountOfLogsCarrying.get(treePosition);
    }

    public void increaseAmountOfTreeCarrying(Tree tree) {
        if (tree instanceof CertifiedTree) {
            this.amountOfLogsCarrying.add(new CertifiedTree());
        } else {
            this.amountOfLogsCarrying.add(new NonCertifiedTree());
        }
    }

    public void loadOfLogs() {
        this.amountOfLogsCarrying = new ArrayList();
    }

    public void decreaseAmountOfTreeCarrying() {
        this.amountOfLogsCarrying.remove(0);
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int moneyAmount) {
        this.money += moneyAmount;
    }

    public int getClimatePoints() {
        return this.climatePoints;
    }

    public void addClimatePoints(int treeClimatePoints) {
        this.climatePoints += treeClimatePoints;
    }

    public static int getMAX_TREECARRY() {
        return MAX_TREECARRY;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    public int getHighScore() {
        return this.highScore;
    }
}
