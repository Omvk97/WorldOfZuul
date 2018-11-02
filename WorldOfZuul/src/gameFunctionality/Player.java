package gameFunctionality;

import Locations.Room;

public class Player {

    private final static int MIN_CLIMATEPOINTS = -250;

    private int money;
    private int climatePoints;
    private boolean giftHasBeenGivenToday;
    private Room currentRoom = null;
    private Axe equippedAxe;
    private BackPack equippedBackPack;
    private int saplingBundleAmount;
    private boolean saplingsPlanted;
    private boolean hasChoppedTrees;

    public Player(Axe starterAxe, BackPack starterBackPack) {
        this.money = 0;
        this.climatePoints = 0;
        this.giftHasBeenGivenToday = false;
        this.equippedAxe = starterAxe;
        this.equippedBackPack = starterBackPack;
        this.saplingBundleAmount = 0;
        this.hasChoppedTrees = false;
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

    /**
     * Bruges af Store hvis spilleren køber en ny økse
     *
     * @param newAxe den nye økse der kan købes i Store.
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money -= newAxe.getPrice();
    }

    /**
     * @return om spilleren har en økse equipped.
     */
    public boolean canUseAxe() {
        return equippedAxe != null;
    }

    /**
     * Denne metode bruges til at reducerer durability.
     */
    public void useAxe() {
        equippedAxe.reduceDurability();
        if (equippedAxe.getDurability() == (equippedAxe.getStartDurability() / 2)) {
            System.out.println("Your axe is at half durability");
        } else if (equippedAxe.getDurability() == 0) {
            System.out.println("Your axe broke, gosh dangit");
            equippedAxe = null;
        }
    }
    
    /**
     * Metoden her er til for at kunne tilgå metoderne fra den økse som spilleren bruger
     * @return øksen som er equipped
     */
    public Axe axe() {
        return equippedAxe;
    }

    /**
     * Metoden her er til for at kunne tilgå metoderne fra den rygsæk som spilleren bruger
     * @return rygsækken som er equipped
     */
    public BackPack backPack() {
        return equippedBackPack;
    }
    
    public void boughtBackPack(BackPack newBackPack) {
        equippedBackPack = newBackPack;
    }

    public int getSaplingAmount() {
        return saplingBundleAmount;
    }

    public boolean buySaplingBundle(int saplingBundleAmount, int saplingCost) {
        if (saplingCost <= money) {
            money -= saplingCost;
            this.saplingBundleAmount += saplingBundleAmount;
            return true;
        } else {
            return false;
        }
    }

    public void plantSeeds() {
        saplingsPlanted = true;
        saplingBundleAmount--;
    }

    public boolean hasPlantedSeeds() {
        return saplingsPlanted;
    }

    public boolean getHasChoppedTrees() {
        return this.hasChoppedTrees;
    }

    public void setHasChoppedTreesInCertifiedForest() {
        hasChoppedTrees = true;
    }

    public void givePlayerFine() {
        money -= 200;
    }

    /**
     * Metoden her resetter alle de booleans vi bruger til at tjekke forskellige conditions. For eksempel
     * om spilleren skal have en bøde, det skal ikke carry over til næste dage. Og gifthasbeengiventoday er for at
     * sikre at spilleren ikke kan udnytte local village gaver.
     */
    public void newDay() {
        saplingsPlanted = false;
        hasChoppedTrees = false;
        this.giftHasBeenGivenToday = false;

    }

    /**
     * Alle nedenstående metoder arbejde med en gave som Local villagers kan give.
     *
     * @return en boolean som fortæller om gaven har været givet på den pågældene dag
     */
    public boolean hasGiftBeenGivenToday() {
        return this.giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        this.giftHasBeenGivenToday = true;
    }
}
