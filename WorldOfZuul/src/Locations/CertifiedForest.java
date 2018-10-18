package Locations;

import gameFunctionality.Tree;
import java.util.ArrayList;
import java.util.List;

public class CertifiedForest extends Room {

    final int MAX_AMOUNTOFTREESINFOREST = 100;
    final int MIN_AMOUNTOFTREESINFOREST = 70;
    static List<Tree> trees;

    public CertifiedForest(String description) {
        super(description);
        this.trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new Tree());
        }
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This forest will slowly regrow, there are " + trees.size() + " trees" + "\n"
            + "Your options are: \n"
            + "1 - Cut down a tree and bring it with you \n"
            + "2 - See how many trees are left in the forest \n"
            + getExitString();
    }

    private boolean playerCanCarryMoreTree() {
        return player.getAmountOfLogsCarrying() + 1 <= player.getMAX_TREECARRY();
    }

    private boolean thereIsMoreTreesToCut() {
        return trees.size() - 1 >= MIN_AMOUNTOFTREESINFOREST; 
    }

    @Override
    public void option1() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
            player.increaseAmountOfTreeCarrying();
            trees.remove(trees.size() - 1);
            System.out.println("You have cut down a tree! You are now carrying "
                + player.getAmountOfLogsCarrying() + (player.getAmountOfLogsCarrying() > 1 ? " logs" : " log"));
        } else {
            if (playerCanCarryMoreTree() && !thereIsMoreTreesToCut()) {
                System.out.println("You have cut too much wood!! Wait for the trees to regrow!"); // Her skal vi overveje hvad der skal stå
            } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree()) {
                System.out.println("You are carrying too much wood! Go back to your trailer and sell or store your logs!");
            }
        }
    }

    @Override
    public void option2() {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }

}
