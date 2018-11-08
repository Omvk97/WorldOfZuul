package gameFunctionality;

import Locations.Room;
import Locations.Trailer;

/**
 * Indeholder en masse information som andre klasser benytter sig af for at bestemme hvad
 * de skal gøre
 * @author olive
 */
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
    private final Trailer trailer;
    private Room previousRoom;

    public Player(Axe starterAxe, BackPack starterBackPack, Trailer trailer) {
        this.equippedAxe = starterAxe;
        this.equippedBackPack = starterBackPack;
        this.trailer = trailer;
        this.previousRoom = trailer;
    }

    /**
     * Metoden benyttes til at checke forskellige steder i spillet om spilleren har nået MAX
     * klimapoints Hvis dette er tilfældet skal spillet slutte og der skal være Game Over.
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
        return climatePoints;
    }

    public void addClimatePoints(int treeClimatePoints) {
        climatePoints += treeClimatePoints;
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
        if (currentRoom != null) {
            previousRoom = currentRoom;
        }
        currentRoom = newRoom;
    }

    public void throwPlayerBack() {
        currentRoom = previousRoom;
    }

    /**
     * Denne metode returnere en meget simpel udregning på et eksempel af hvordan highscore kan
     * udregnes benyttes alle steder hvor spillet skal lukke ned.
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

    public Trailer getTrailer() {
        return trailer;
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
     *
     * @return øksen som er equipped
     */
    public Axe axe() {
        return equippedAxe;
    }

    /**
     * Metoden her er til for at kunne tilgå metoderne fra den rygsæk som spilleren bruger
     *
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

    public void setHasChoppedTreesInCertifiedForest() {
        hasChoppedTrees = true;
    }

    /**
     * hvis der er blevet fældet træer i den certificerede skov men ikke plantet nye så får
     * spilleren en bøde. Ellers resetter dagen og spilleren kan modtage gave igen.
     * @return om spilleren har sovet og har plantet træer eller ej. 
     */
    public boolean sleep() {
        if (!saplingsPlanted && hasChoppedTrees) {
            System.out.println("YOU DIDN'T REPLANT TREES HERE IS A FINE OF 200 GOLD COINS");
            money -= 200;
            return false;
        }
        saplingsPlanted = false;
        hasChoppedTrees = false;
        this.giftHasBeenGivenToday = false;
        return true;
    }

    public boolean isGiftHasBeenGivenToday() {
        return this.giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        this.giftHasBeenGivenToday = true;
    }
}
