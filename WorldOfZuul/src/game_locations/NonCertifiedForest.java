package game_locations;

import game_elements.NonCertifiedTree;
import game_elements.Tree;
import game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class NonCertifiedForest extends Forest {

    public NonCertifiedForest() {
        for (int i = 0; i < 15; i++) {
            trees.add(new NonCertifiedTree((int) (Math.random() * 5) + 7));
        }
        while (trees.size() < MAX_AMOUNTOFTREESINFOREST) {
            trees.add(new NonCertifiedTree((int) (Math.random() * 6) + 1));
        }
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        if (humanPlayer.isHasSlept()) {
            treeGrowth();
            humanPlayer.setHasSlept(false);
        }
        moveChoppableTreesUp();
        return "You are standing in a non certified forest! \n"
            + "This forest will not regrow, there are " + trees.size() + " trees" + "\n"
            + "Your options are: \n"
            + "-----------------------------------------------------------\n"
            + "○ Chop Tree ➤ Cut down a tree and bring it with you \n"
            + "○ Tree info ➤ Trees left in the forest big enough to chop \n"
            + "-----------------------------------------------------------";
    }

    @Override
    protected boolean thereIsMoreTreesToCut() {
        return (lastTreeInArray().getTreeHealth() >= MEDIUM_TREE_SIZE
            && trees.size() > 0);
    }

    @Override
    public void option1(Player humanPlayer, Label textArea) {
        this.chopWood(humanPlayer);
        if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
            System.out.println("YOU DESTROYED THE EARTH, YOU HAVE CUT WAY TOO MUCH \n"
                + "NON CERTIFIED WOOD.");
            System.exit(0);
        }
    }

    @Override
    public void option2(Player humanPlayer, Label textArea) {
        int counter = 0;
        for (Tree tree : trees) {
            if (tree.getTreeHealth() >= MEDIUM_TREE_SIZE) {
                counter++;
            }
        }
        System.out.println("There are " + counter + " trees ready to be felled!");
    }
    
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("NonCertifiedForest.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
