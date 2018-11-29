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

    public boolean playerCanCarryMoreTree(Player humanPlayer) {
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
     * Chops wood if the player has an Axe equipped. And adds all the things that are associated with choppping down a
     * tree
     *
     * @param humanPlayer chopping a tree
     * @return if the tree cutting was succesfull.
     */
    private int chopWoodWithAxe(Player humanPlayer) {
        int numOfChops = 0;
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
        return numOfChops;
    }

    /**
     * If player doesn't have an Axe equipped they can instead use their hands to chop down a tree with a damage of 2
     *
     * @param humanPlayer chopping the trees
     * @return if the tree cutting was succesfull.
     */
    private int chopWoodWithHands(Player humanPlayer) {
        int numOfPunches = 0;
            while (lastTreeInArray().getTreeHealth() - 2 >= 0) {
                lastTreeInArray().reduceTreeHealth(2);
                numOfPunches++;
            }
            humanPlayer.backPack().addTreeToBackpack(lastTreeInArray());
            humanPlayer.addClimatePoints(lastTreeInArray().getTreeClimatePoints());
            trees.remove(lastTreeInArray());
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

    public int countSmallTrees() {
        int count = 0;
        for (Tree tree : trees) {
            if (tree.getTreeHealth() < MEDIUM_TREE_SIZE) {
                count++;
            }
        }
        return count;
    }

    public int countMediumTrees() {
        int count = 0;
        for (Tree tree : trees) {
            if (tree.getTreeHealth() >= MEDIUM_TREE_SIZE && tree.getTreeHealth() < LARGE_TREE_SIZE) {
                count++;
            }
        }
        return count;
    }

    public int countLargeTrees() {
        int count = 0;
        for (Tree tree : trees) {
            if (tree.getTreeHealth() >= LARGE_TREE_SIZE) {
                count++;
            }
        }
        return count;
    }

    public int getMEDIUM_TREE_SIZE() {
        return MEDIUM_TREE_SIZE;
    }

    public int getLARGE_TREE_SIZE() {
        return LARGE_TREE_SIZE;
    }
}
