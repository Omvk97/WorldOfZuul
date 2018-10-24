package Locations;

import gameFunctionality.CertifiedTree;
import gameFunctionality.Player;
import gameFunctionality.Tree;
import java.util.ArrayList;
import java.util.List;

public class CertifiedForest extends Room {

    private final static int MAX_AMOUNTOFTREESINFOREST = 100;
    private final static int MIN_AMOUNTOFTREESINFOREST = 70;
    private static List<Tree> trees;
    private final static int FOREST_REGROW_RATE = 3;

    public CertifiedForest(String description, Player player) {
        super(description, player);
        CertifiedForest.trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new CertifiedTree());
        }
    }

    public static void regrowTrees() {
        int counter = 0;
        while (trees.size() < MAX_AMOUNTOFTREESINFOREST) {
            trees.add(new CertifiedTree());
            counter++;
            if (counter >= FOREST_REGROW_RATE) {
                break;
            }
        }
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This forest will slowly regrow, there are " + trees.size() + " trees" + "\n"
            + "Your options are: \n"
            + "Option 1 - Cut down a tree and bring it with you \n"
            + "Option 2 - See how many trees are left in the forest \n"
            + getExitString();
    }

    private boolean playerCanCarryMoreTree() {
        return humanPlayer.getAmountOfLogsCarrying() < Player.getMAX_TREECARRY();
    }

    private boolean thereIsMoreTreesToCut() {
        return trees.size() > MIN_AMOUNTOFTREESINFOREST;
    }

    @Override
    public void option1() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
            humanPlayer.increaseAmountOfTreeCarrying(trees.get(0));
            humanPlayer.addClimatePoints(trees.get(0).getTreeClimatePoints());
            trees.remove(trees.size() - 1);
            System.out.println("You have cut down a tree! You are now carrying "
                + humanPlayer.getAmountOfLogsCarrying() + (humanPlayer.getAmountOfLogsCarrying() > 1 ? " logs" : " log"));
        } else {
            if (playerCanCarryMoreTree() && !thereIsMoreTreesToCut()) {
                System.out.println("You have cut too much wood!! Wait for the trees to regrow!"); // Her skal vi overveje hvad der skal stå
            } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree()) {
                System.out.println("You are carrying too much wood! "
                    + "Go back to your trailer and sell or store your logs!");
            }
        }
    }

    @Override
    public void option2() {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }

}
