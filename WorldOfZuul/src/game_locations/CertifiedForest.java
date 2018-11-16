package game_locations;

import game_elements.CertifiedTree;
import game_elements.Tree;
import game_functionality.Player;

public class CertifiedForest extends Forest {

    public CertifiedForest() {
        for (int i = 0; i < 15; i++) {
            trees.add(new CertifiedTree((int) (Math.random() * 2) + 10));
        }
        while (trees.size() < MAX_AMOUNTOFTREESINFOREST) {
            trees.add(new CertifiedTree((int) (Math.random() * 6) + 1));
        }
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        if (humanPlayer.isHasSlept()) {
            treeGrowth();
            humanPlayer.setHasSlept(false);
            plantNewTrees(MAX_AMOUNTOFTREESINFOREST);
        }
        moveChoppableTreesUp();
        return "You are standing in a certified forest!\n"
            + "In this forest you can plant new trees, but you are only allowed to \n"
            + "fell large trees! Also if you don't seed the forest after felling\n"
            + "you will be fined the next day! \n"
            + "There currently are " + trees.size() + " trees" + "\n"
            + "-----------------------------------------------------------\n"
            + "○ Chop Tree ➤ Cut down a tree and bring it with you \n"
            + "○ Tree info ➤ Trees left in the forest big enough to chop \n"
            + "○ Replant   ➤ Replant trees that you have cut down\n"
            + "-----------------------------------------------------------";
    }

    private int numberOfTreesBigEnoughToChop() {
        int counter = 0;
        for (Tree tree : trees) {
            if (tree.getTreeHealth() >= LARGE_TREE_SIZE) {
                counter++;
            }
        }
        return counter;
    }
    
    @Override
    protected boolean thereIsMoreTreesToCut() {
        return numberOfTreesBigEnoughToChop() > 0 && trees.size() > 0;
    }

    @Override
    public void option1(Player humanPlayer) {
        if (chopWood(humanPlayer)) {
            humanPlayer.addChoppedTreesInCertifiedForest();
        }
    }

    @Override
    public void option2(Player humanPlayer) {
        System.out.println("There are " + numberOfTreesBigEnoughToChop() + " trees ready to be felled!");
    }

    @Override
    public void option3(Player humanPlayer) {
        if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() > 0) {
            int amountOfSeedsPlanted = humanPlayer.plantSeeds();
            if (amountOfSeedsPlanted > 0) {
                plantNewTrees(amountOfSeedsPlanted);
                System.out.println("You just planted " + (amountOfSeedsPlanted > 1
                    ? amountOfSeedsPlanted + " saplings!" : "1 sapling!"));
            } else {
                System.out.println("You don't have any saplings, go buy some!");
            }
        } else {
            System.out.println("You haven't chopped any trees today!");
        }
    }

    /**
     * This forest always needs to have exactly 10 trees in it, either the player needs to plant
     * new trees or the player will receive a fine and then the "government" will plant the trees instead.
     * @param numOfTreesToBeAdded how many new trees that has to be added
     */
    public void plantNewTrees(int numOfTreesToBeAdded) {
        for (int i = 0; i < numOfTreesToBeAdded && trees.size() < numOfTreesToBeAdded; i++) {
            trees.add(new CertifiedTree((int) (Math.random() * 2) + 1));
        }
    }
}
