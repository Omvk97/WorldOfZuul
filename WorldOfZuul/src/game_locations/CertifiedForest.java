package game_locations;

import game_elements.CertifiedTree;
import game_elements.Tree;
import game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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
            + "Remember to replant trees!";
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
    public boolean thereIsMoreTreesToCut() {
        return numberOfTreesBigEnoughToChop() > 0 && trees.size() > 0;
    }

    @Override
    public String option1(Player humanPlayer) {
        humanPlayer.addChoppedTreesInCertifiedForest();
        return Integer.toString(chopWood(humanPlayer));
    }

    @Override
    public String option2(Player humanPlayer) {
        return "There are " + numberOfTreesBigEnoughToChop() + " trees ready to be felled!";
    }

    @Override
    public String option3(Player humanPlayer) {
        if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() > 0) {
            int amountOfSeedsPlanted = humanPlayer.plantSeeds();
            if (amountOfSeedsPlanted > 0) {
                plantNewTrees(amountOfSeedsPlanted);
                return "You just planted " + (amountOfSeedsPlanted > 1
                    ? amountOfSeedsPlanted + " saplings!" : "1 sapling!");
            } else {
                return "You don't have any saplings, go buy some!";
            }
        } else {
            return "You haven't chopped any trees today!";
        }
    }

    /**
     * This forest always needs to have exactly 100 trees in it, either the player needs to plant new
     * trees or the player will receive a fine and then the "government" will plant the trees
     * instead.
     *
     * @param numOfTreesToBeAdded how many new trees that has to be added
     */
    public void plantNewTrees(int numOfTreesToBeAdded) {
        for (int i = 0; i < numOfTreesToBeAdded && trees.size() < numOfTreesToBeAdded; i++) {
            trees.add(new CertifiedTree((int) (Math.random() * 2) + 1));
        }
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/CertifiedForest.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
