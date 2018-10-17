package Locations;

import gameFunctionality.Tree;
import java.util.ArrayList;

public class NonCertifiedForest extends Room {

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
            + "This forest will not regrow, there are " + trees.size() + " trees left \n"
            + "Your options are: \n"
            + "1 - Cut down a tree and bring it with you \n"
            + "2 - See how many trees are left in the forest \n"
            + "3 - Put on a JetPack \n"
            + getExitString();
    }

    @Override
    public void option1() {
        trees.remove(trees.size() - 1);
        System.out.println("You have succesfully cut down a tree!");
    }

    @Override
    public void option2() {
        System.out.println(trees.size());
    }

    @Override
    public void option3() {
        System.out.println("A Jetpack has been put on your back, now FLY");
    }

}
