package Locations;

import gameFunctionality.Tree;
import java.util.ArrayList;

public class NonCertifiedForest extends Room {

    final int MAX_TREECARRY = 5; // amountOfTreeCutInForest skal sammenlignes med denne variabel altid
    int amountOfTreeCutInForest = 0; // Denne skal kunne resettes af Trailer class
    boolean playerCanCutTrees = true;
    ArrayList<Tree> trees;

    public NonCertifiedForest(String description) {
        super(description);
        this.trees = new ArrayList(100);
        for (int i = 0; i < 100; i++) {
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

    @Override
    public void option1() {
        if (amountOfTreeCutInForest + 1 <= MAX_TREECARRY) {
            amountOfTreeCutInForest++;
            trees.remove(trees.size() - 1);
            System.out.println("You have succesfully cut down a tree! You are now carrying "
                + amountOfTreeCutInForest + (amountOfTreeCutInForest > 1 ? " logs" : " log"));
        } else {
            System.out.println("You are carrying too much wood! Go back to your trailer and sell your logs!");
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
