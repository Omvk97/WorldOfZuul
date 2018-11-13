package game_functionality;

import game_elements.Axe;
import game_elements.BackPack;
import game_locations.Room;
import game_locations.Trailer;
import java.util.Scanner;

public class Player {

    private final static int NUM_PLAY_DAYS = 5;
    private int numOfDaysGoneBy;
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

    public Player(BackPack starterBackPack, Trailer trailer) {
        this.equippedBackPack = starterBackPack;
        this.trailer = trailer;
        this.previousRoom = trailer;
        this.numOfDaysGoneBy = 1;
    }

    /**
     * Used to determine whether or not the game should end. If the climatepoints
     * goes over this threshold, the game is over, the world is destroyed
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
     * @return The room that the player currently resides in
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Used to move the player around in the rooms
     *
     * @param newRoom: The room that the player is moving to
     */
    public void setCurrentRoom(Room newRoom) {
        if (currentRoom != null) {
            previousRoom = currentRoom;
        }
        currentRoom = newRoom;
    }
    
    public Room getPreviousRoom() {
        return previousRoom;
    }

    /**
     * A simple method of calculating highScore, not finally implemented yet
     *
     * @return int value that is money added with climatepoints
     */
    public int getHighScore() {
        return money + climatePoints;
    }

    /**
     * Gives the player a new axe if the player buys a new one at the blacksmith
     *
     * @param newAxe The new Axe that is to be equipped
     */
    public void boughtAxe(Axe newAxe) {
        equippedAxe = newAxe;
        money -= newAxe.getPrice();
    }

    public void pickedUpAxe(Axe axe) {
        equippedAxe = axe;
    }
    public void grindedAxe(int cost){
        money -= cost;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    /**
     * @return boolean whether or not the player has an axe equipped
     */
    public boolean canUseAxe() {
        return equippedAxe != null;
    }

    /**
     * Used to reduce durability on the players currently equipped Axe
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
     * Used to get access to currently equipped Axe and it's methods.
     *
     * @return Axe that is equipped
     */
    public Axe getAxe() {
        return equippedAxe;
    }

    /**
     * Used to get access to currently equipped BackPack and it's methods.
     *
     * @return BackPack that is currently equipped
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
     * Resets all the things that the player can interact with during a day. And also checks
     * for if the player has choppedTrees without replanting, if this is the case the player will
     * recieve a fine.
     */
    public boolean sleep() {
        Boolean correctAnswer = true;
        Scanner questionAnswer = new Scanner(System.in);
        String questionOne = "How many million hectare forest area disappear each year?";
        String questionTwo = "How many million hectare forest area does FSC cover over?";
        String questionThree = "How many million hectare forest area does PEFC cover over?";
        if (!saplingsPlanted && hasChoppedTrees) {
            System.out.println("You didn't replant trees in the ceritifed forest\n"
                + "here is a chance to redeem yourself");
            int randomNum = (int) (Math.random() * 3) + 1;
            if (randomNum == 1) {
                System.out.println(questionOne);
                String userAnswer = questionAnswer.nextLine();
                if (userAnswer.equals("7")) {
                    System.out.println("Oh okay you will only receive a fine of 100 instead");
                    money -= 100;
                } else {
                    correctAnswer = false;
                }
            } else if (randomNum == 2) {
                System.out.println(questionTwo);
                String userAnswer = questionAnswer.nextLine();
                if (userAnswer.equals("200")) {
                    System.out.println("Oh okay you will only receive a fine of 100 instead");
                    money -= 100;
                } else {
                    correctAnswer = false;
                }
            } else {
                System.out.println(questionThree);
                String userAnswer = questionAnswer.nextLine();
                if (userAnswer.equals("300")) {
                    System.out.println("Oh okay you will only receive a fine of 100 instead");
                    money -= 100;
                } else {
                    correctAnswer = false;
                }
            }
        }
        if (!correctAnswer) {
            money -= 200;
            System.out.println("WRONG ANSWER, YOU HAVE BEEN FINED 200 GOLD COINS. GO BACK TO SCHOOL");
        }
        saplingsPlanted = false;
        hasChoppedTrees = false;
        giftHasBeenGivenToday = false;
        return true;
    }

    public boolean isGiftHasBeenGivenToday() {
        return giftHasBeenGivenToday;
    }

    public void giftHasBeenGiven() {
        giftHasBeenGivenToday = true;
    }

    /**
     * Denne metode er til for at printe ud når spillet starter hvor mange dage der er i alt. Den
     * bliver brugt i 'game' klassen.
     *
     * @return mængden af dage spilleren har.
     */
    public static int getNumPlayDays() {
        return Player.NUM_PLAY_DAYS;
    }

    public void dayCounter(Player humanPlayer) {
        int daysleft = NUM_PLAY_DAYS - numOfDaysGoneBy;
        humanPlayer.sleep();
        if (numOfDaysGoneBy++ >= NUM_PLAY_DAYS) {
            System.out.println("THERE IS NO MORE DAYS, YOUR HIGHSCORE IS: "
                + humanPlayer.getHighScore());
            System.exit(0);
        }
        System.out.println("The sun goes down and you sleep tight \n"
            + "ZzzzZzzzZzzzZzzz");
        System.out.println("The sun rises and you are ready to tackle the day! \n"
            + (daysleft > 1 ? "There are " + daysleft + " days left!"
                : "This is your last day as a lumberjack!"));
    }
}
