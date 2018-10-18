package Locations;

import gameFunctionality.Tree;
import java.util.ArrayList;
import java.util.List;

public class NonCertifiedForest extends Room {

    final int MAX_AMOUNTOFTREESINFOREST = 100;
    final int MAX_TREECARRY = 5; // amountOfTreeCarrying skal sammenlignes med denne variabel altid
    int amountOfTreeCarrying = 0; // Denne skal kunne resettes af Trailer class
    List<Tree> trees;

    public NonCertifiedForest(String description) {
        super(description);
        this.trees = new ArrayList(MAX_AMOUNTOFTREESINFOREST);
        for (int i = 0; i < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new Tree());
        }
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This forest will not regrow, there are " + trees.size() + "\n"
            + "Your options are: \n"
            + "1 - Cut down a tree and bring it with you \n"
            + "2 - See how many trees are left in the forest \n"
            + "3 - Put on jetPack \n"
            + getExitString();
    }

    private boolean playerCanCarryMoreTree() {
        return amountOfTreeCarrying + 1 <= MAX_TREECARRY;
    }

    private boolean thereIsMoreTreesToCut() {
        return trees.size() - 1 >= 0;
    }

    @Override
    public void option1() {
        if (playerCanCarryMoreTree() && thereIsMoreTreesToCut()) {
            amountOfTreeCarrying++;
            trees.remove(trees.size() - 1);
            System.out.println("You have cut down a tree! You are now carrying "
                + amountOfTreeCarrying + (amountOfTreeCarrying > 1 ? " logs" : " log"));
        } else {
            if (playerCanCarryMoreTree() && !thereIsMoreTreesToCut()) {
                System.out.println("You have cut too much wood!! The forest has no more trees!"); // Her skal vi finde ud af om der skal stå sleep eller hvad der skal stå
            } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree()) {
                System.out.println("You are carrying too much wood! Go back to your trailer and sell or store your logs!");
            }
        }
    }

    @Override
    public void option2() {
        System.out.println("There are " + trees.size() + " trees left in the forest");
    }

    @Override
    public void option3() {
        System.out.println("You now have a JetPack Equipped");
    }

}
