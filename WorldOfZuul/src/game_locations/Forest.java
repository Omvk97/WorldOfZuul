package game_locations;

import game_functionality.Player;
import game_elements.Tree;
import java.util.ArrayList;
import java.util.List;

public abstract class Forest extends Room {

    protected final int MEDIUM_TREE_SIZE = 7;
    protected final int LARGE_TREE_SIZE = 10;
    protected final int MAX_AMOUNTOFTREESINFOREST = 100;
    protected List<Tree> trees;

    public Forest() {
        trees = new ArrayList<>();
    }

    private boolean playerCanCarryMoreTree(Player humanPlayer) {
        return humanPlayer.backPack().getAmountOfLogsInBackPack()
            < humanPlayer.backPack().getBackpackCapacity();
    }

    abstract protected boolean thereIsMoreTreesToCut();

    public Tree lastTreeInArray() {
        return trees.get(trees.size() - 1);
    }

    protected int chopWood(Player humanPlayer) {
        if (humanPlayer.getAxe() != null) {
            return chopWoodWithAxe(humanPlayer);
        } else {
            return chopWoodWithHands(humanPlayer);
        }
    }

    public String treeSize(Tree tree) {
        if (tree.getTreeHealth() > MEDIUM_TREE_SIZE && tree.getTreeHealth()
            < LARGE_TREE_SIZE) {
            return " at a medium sized tree!";
        } else {
            return " at a large tree!";
        }
    }

    public void treeGrowth() {
        for (Tree tree : trees) {
            tree.treeGrowth((int) (Math.random() * 2) + 1);
        }
    }

    /**
     * Chops wood if the player has an Axe equipped. And adds all the things that are associated
     * with choppping down a tree
     *
     * @param humanPlayer chopping a tree
     * @return if the tree cutting was succesfull.
     */
    private int chopWoodWithAxe(Player humanPlayer) {
        int numOfChops = 0;
        if (playerCanCarryMoreTree(humanPlayer) && thereIsMoreTreesToCut()) {

            while (lastTreeInArray().getTreeHealth() - humanPlayer.getAxe().getDamage() >= 0) {
                lastTreeInArray().reduceTreeHealth(humanPlayer.getAxe().getDamage());
                numOfChops++;
            }
            humanPlayer.backPack().addTreeToBackpack(lastTreeInArray());
            humanPlayer.addClimatePoints(lastTreeInArray().getTreeClimatePoints());
            trees.remove(lastTreeInArray());
            humanPlayer.useAxe();
            if (humanPlayer.getCurrentRoom() instanceof CertifiedForest) {
                humanPlayer.addChoppedTreesInCertifiedForest();
            }

        } else if (playerCanCarryMoreTree(humanPlayer) && !thereIsMoreTreesToCut()) {
            System.out.println("There is no more trees to fell right now!"
                + (this instanceof CertifiedForest ? "\nYou have to wait for the forest to regrow!"
                    : ""));
        } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree(humanPlayer)) {
            System.out.println("You are carrying too much wood!\n"
                + "Sell or store your logs!");
        } else {
            System.out.println("There is no trees to fell and your backpack is full!");
        }
        return numOfChops;
    }

    /**
     * If player doesn't have an Axe equipped they can instead use their hands to chop down a tree
     * with a damage of 2
     *
     * @param humanPlayer chopping the trees
     * @return if the tree cutting was succesfull.
     */
    private int chopWoodWithHands(Player humanPlayer) {
        int numOfPunches = 0;
        if (playerCanCarryMoreTree(humanPlayer) && thereIsMoreTreesToCut()) {
            while (lastTreeInArray().getTreeHealth() - 2 >= 0) {
                lastTreeInArray().reduceTreeHealth(2);
                numOfPunches++;
            }
            humanPlayer.backPack().addTreeToBackpack(lastTreeInArray());
            humanPlayer.addClimatePoints(lastTreeInArray().getTreeClimatePoints());
            trees.remove(lastTreeInArray());
            
        } else if (playerCanCarryMoreTree(humanPlayer) && !thereIsMoreTreesToCut()) {
            System.out.println("There is no more trees to chop down right now!"
                + (this instanceof CertifiedForest ? "\nYou have to wait for the forest to regrow!"
                    : ""));
        } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree(humanPlayer)) {
            System.out.println("You are carrying too much wood!\n"
                + "Sell or store your logs!");
        } else {
            System.out.println("There is no trees to fell and your backpack is full!");
        }
        return numOfPunches;
    }

    public void moveChoppableTreesUp() {
        ArrayList<Tree> cloneOfTrees = new ArrayList<>();
        for (Tree tree : trees) {
            cloneOfTrees.add(tree);
        }
        for (int i = 0; i < cloneOfTrees.size(); i++) {
            Tree treeToBeMoved = cloneOfTrees.get(i);
            if (treeToBeMoved.getTreeHealth() >= (this instanceof CertifiedForest ? LARGE_TREE_SIZE : MEDIUM_TREE_SIZE)) {
                trees.remove(treeToBeMoved);
                trees.add(treeToBeMoved);
            }
        }
    }
}
