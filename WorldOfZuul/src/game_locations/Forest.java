package game_locations;

import game_functionality.Player;
import game_elements.Tree;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

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

    protected Tree lastTreeInArray() {
        return trees.get(trees.size() - 1);
    }

    protected boolean chopWood(Player humanPlayer, Label textArea) {
        if (humanPlayer.getAxe() != null) {
            return chopWoodWithAxe(humanPlayer, textArea);
        } else {
            return chopWoodWithHands(humanPlayer, textArea);
        }
    }

    private String treeSize(Tree tree) {
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
    private boolean chopWoodWithAxe(Player humanPlayer, Label textArea) {
        if (playerCanCarryMoreTree(humanPlayer) && thereIsMoreTreesToCut()) {
            textArea.setText("You swing your " + humanPlayer.getAxe().getDescription()
                + treeSize(lastTreeInArray()));

            while (lastTreeInArray().getTreeHealth() - humanPlayer.getAxe().getDamage() >= 0) {
                lastTreeInArray().reduceTreeHealth(humanPlayer.getAxe().getDamage());
                textArea.setText("**CHOP**");
                sleepPause();
            }
            humanPlayer.backPack().addTreeToBackpack(lastTreeInArray());
            humanPlayer.addClimatePoints(lastTreeInArray().getTreeClimatePoints());
            trees.remove(lastTreeInArray());
            textArea.setText("**CHOP**");
            sleepPause();
            humanPlayer.useAxe();
            textArea.setText("You felled a tree! You are now carrying "
                + humanPlayer.backPack().getAmountOfLogsInBackPack()
                + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " trees" : " tree"));
            return true;
        } else if (playerCanCarryMoreTree(humanPlayer) && !thereIsMoreTreesToCut()) {
            textArea.setText("There is no more trees to fell right now!"
                + (this instanceof CertifiedForest ? "\nYou have to wait for the forest to regrow!"
                    : ""));
        } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree(humanPlayer)) {
            textArea.setText("You are carrying too much wood!\n"
                + "Sell or store your logs!");
        } else {
            textArea.setText("There is no trees to fell and your backpack is full!");
        }
        return false;
    }

    /**
     * If player doesn't have an Axe equipped they can instead use their hands to chop down a tree with a damage of 2
     *
     * @param humanPlayer chopping the trees
     * @return if the tree cutting was succesfull.
     */
    private boolean chopWoodWithHands(Player humanPlayer, Label textArea) {
        if (playerCanCarryMoreTree(humanPlayer) && thereIsMoreTreesToCut()) {
            textArea.setText("You throw a punch" + treeSize(lastTreeInArray()));
            while (lastTreeInArray().getTreeHealth() - 2 >= 0) {
                lastTreeInArray().reduceTreeHealth(2);
                textArea.setText("**POW**");
                sleepPause();
            }
            humanPlayer.backPack().addTreeToBackpack(lastTreeInArray());
            humanPlayer.addClimatePoints(lastTreeInArray().getTreeClimatePoints());
            trees.remove(lastTreeInArray());
            textArea.setText("**POW**");
            sleepPause();
            textArea.setText("You have punched down a tree! You are now carrying "
                + humanPlayer.backPack().getAmountOfLogsInBackPack()
                + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
            return true;
        } else if (playerCanCarryMoreTree(humanPlayer) && !thereIsMoreTreesToCut()) {
            textArea.setText("There is no more trees to chop down right now!"
                + (this instanceof CertifiedForest ? "\nYou have to wait for the forest to regrow!"
                    : ""));
        } else if (thereIsMoreTreesToCut() && !playerCanCarryMoreTree(humanPlayer)) {
            textArea.setText("You are carrying too much wood!\n"
                + "Sell or store your logs!");
        } else {
            textArea.setText("There is no trees to fell and your backpack is full!");
        }
        return false;
    }

    private void sleepPause() {
        try {
            TimeUnit.SECONDS.sleep(1);

        } catch (InterruptedException ex) {
            Logger.getLogger(NonCertifiedForest.class
                .getName()).log(Level.SEVERE, null, ex);
        }
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
