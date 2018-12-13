package domain_layer.game_locations;

import domain_layer.game_elements.NonCertifiedTree;
import domain_layer.game_elements.Tree;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * At start this forest will contain 100 trees. But trees will not regrow in this forest. * The
 * player can chop trees in this forest as long as they are medium size or bigger.
 *
 * @author oliver
 */
public class NonCertifiedForest extends Forest {

    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    public NonCertifiedForest() {
        for (int i = 0; i < 15; i++) {
            trees.add(new NonCertifiedTree((int) (Math.random() * 5) + 7));
        }
        while (trees.size() < MAX_AMOUNTOFTREESINFOREST) {
            trees.add(new NonCertifiedTree((int) (Math.random() * 6) + 1));
        }
    }

    /**
     * Makes the tree grow when the player enters the forest if they have slept.
     *
     * @param humanPlayer the humanPlayer entering the Room.
     * @return the description of the room.
     */
    @Override
    public String roomEntrance(Player humanPlayer) {
        if (playerInteraction.isSlept()) {
            treeGrowth();
            playerInteraction.setSlept(false);
        }
        moveChoppableTreesUp();
        return "You are standing in a non certified forest! \n"
            + "This forest will not regrow";
    }

    /**
     * @return if there is trees bigger or equal to a medium size tree.
     */
    @Override
    public boolean thereIsMoreTreesToCut() {
        return (lastTreeInArray().getTreeHealth() >= MEDIUM_TREE_SIZE
            && trees.size() > 0);
    }

    /**
     * counts how many trees that can be cut in the forest, so the player can use option2 to get
     * this information.
     *
     * @return how many fellable trees there is.
     */
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
