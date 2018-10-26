package gameFunctionality;

import Locations.Room;
import java.util.ArrayList;

public class Player {

    private final static int MAX_TREECARRY = 5;
    private final static int MIN_CLIMATEPOINTS = -250;

    private ArrayList<Tree> amountOfLogsCarrying;
    private int money;
    private int climatePoints;
    private boolean giftHasBeenGivenToday;
    private Room currentRoom = null;
    private Axe equippedAxe;

    public Player(Axe axe) {
        this.amountOfLogsCarrying = new ArrayList();
        this.money = 0;
        this.climatePoints = 0;
        this.giftHasBeenGivenToday = false;
        this.equippedAxe = axe;
    }

    public int getAmountOfLogsCarrying() {
        return this.amountOfLogsCarrying.size();
    }

    public ArrayList<Tree> getLogsCarrying() {
        return this.amountOfLogsCarrying;
    }

    /**
     * Denne metode benyttes til at få information om hvilken træ type som spilleren bærer rundt på.
     *
     * @param treePosition det er indexet i arrayListen med alle træerne
     * @return arraylist med træer som spilleren bærer rundt på.
     */
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

    public static int getMAX_TREECARRY() {
        return MAX_TREECARRY;
    }

    /**
     * Metoden benyttes til at checke forskellige steder i spillet om spilleren har nået MAX klimapoints Hvis dette er
     * tilfældet skal spillet slutte og der skal være Game Over.
     *
     * @return int max_climatepoints
     */
    public static int getMIN_CLIMATEPOINTS() {
        return MIN_CLIMATEPOINTS;
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

    /**
     * @return rummet som spilleren nuværende står i
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Bruges til at bevæge spilleren rundt i rummene
     *
     * @param newRoom: Rummet som spilleren skal bevæge sig til.
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    /**
     * Denne metode returnere en meget simpel udregning på et eksempel af hvordan highscore kan udregnes benyttes alle
     * steder hvor spillet skal lukke ned.
     *
     * @return int værdi som nu højere nu bedre for spilleren.
     */
    public int getHighScore() {
        return money + climatePoints;
    }

    public String getEquippedAxeDescription() {
        return equippedAxe.getDescription();
    }

    public int getAxeDamage() {
        return equippedAxe.getDamage();
    }

    /**
     * Bruges af Store hvis spilleren køber en ny økse
     *
     * @param newAxe den nye økse der kan købes i Store.
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money -= newAxe.getPrice();
    }
//
//    public boolean canUseAxe() {
//        if (equippedAxe != null) {
//            if (equippedAxe.getDurability() != 0)
//            return true;
//        } else {
//            return false;
//        }
//    }

    /**
     * Denne metode returnerer true hvis spillerens equipped axe rent faktisk kan bruges.
     *
     * @return true hvis øksen kan bruges og false hvis spilleren ikke har en økse.
     */
    public void useAxe() {
        if (equippedAxe != null) {
            equippedAxe.reduceDurability();
            if (equippedAxe.getDurability() == (equippedAxe.getStartDurability() * 0.5)) {
                System.out.println("Your axe is at half durability");
            } else if (equippedAxe.getDurability() == 0) {
                System.out.println("Your axe broke, gosh dangit");
                equippedAxe = null;
            }
        } else {
        }
    }

    /**
     * Alle nedenstående metoder arbejde med en gave som Local villagers kan give.
     *
     * @return en boolean som fortæller om gaven har været givet på den pågældene dag
     */
    public boolean isGiftHasBeenGivenToday() {
        return this.giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        this.giftHasBeenGivenToday = true;
    }

    public void resetGift() {
        this.giftHasBeenGivenToday = false;
    }
}
