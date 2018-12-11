package domain_layer.game_locations;

import domain_layer.game_elements.NonCertifiedTree;
import domain_layer.game_elements.Tree;
import domain_layer.game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author oliver
 */
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
        if (humanPlayer.isSlept()) {
            treeGrowth();
            humanPlayer.setSlept(false);
        }
        moveChoppableTreesUp();
        return "You are standing in a non certified forest! \n"
            + "This forest will not regrow";
    }

    @Override
    public boolean thereIsMoreTreesToCut() {
        return (lastTreeInArray().getTreeHealth() >= MEDIUM_TREE_SIZE
            && trees.size() > 0);
    }

    public int countFellableTrees() {
        int counter = 0;
        for (Tree tree : trees) {
            if (tree.getTreeHealth() >= MEDIUM_TREE_SIZE) {
                counter++;
            }
        }
        return counter;
    }
    
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/NonCertifiedForest.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
