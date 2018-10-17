package Locations;

import gameFunctionality.Tree;

public class CertifiedForest extends Room {

    Tree[] trees;

    public CertifiedForest(String description) {
        super(description);
        this.trees = new Tree[100];
    }

    @Override
    public String getLongDescription() {
        return "You are standing " + getShortDescription() + "!\n"
            + "This forest will regrow, there are " + trees.length + " trees left \n"
            + "Your options are: \n"
            + "1 - Cut down a tree and bring it with you \n"
            + "2 - See how many trees are left in the forest \n"
            + getExitString();
    }
}
