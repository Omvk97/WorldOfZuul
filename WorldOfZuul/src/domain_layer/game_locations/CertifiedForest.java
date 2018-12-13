package domain_layer.game_locations;

import domain_layer.game_elements.CertifiedTree;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * This forest is made up of CertifiedTrees, and it will always contain 100 trees after the player
 * has slept. The player can only chop large trees in this forest.
 *
 * @author oliver
 */
public class CertifiedForest extends Forest {

    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    public CertifiedForest() {
        for (int i = 0; i < 15; i++) {
            trees.add(new CertifiedTree((int) (Math.random() * 2) + 10));
        }
        while (trees.size() < MAX_AMOUNTOFTREESINFOREST) {
            trees.add(new CertifiedTree((int) (Math.random() * 6) + 1));
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
            plantNewTrees(MAX_AMOUNTOFTREESINFOREST);
        }
        moveChoppableTreesUp();
        return "You are standing in a certified forest!\n"
            + "Remember to replant trees!\n"
            + "In this forest you can only chop large trees";
    }

    /**
     * @return whether or not there is more trees for the player to cut or if the player has to go
     * Sleep before being able to cut more trees.
     */
    @Override
    public boolean thereIsMoreTreesToCut() {
        return countLargeTrees() > 0 && trees.size() > 0;
    }

    /**
     * Calculates first how many seeds the player can count, thereafter it plants that amount of
     * trees.
     *
     * @param humanPlayer that wants to plant trees.
     * @return how many trees has been planted. If the player couldn't plant any trees it will
     * return 0.
     */
    public int replantTrees(Player humanPlayer) {
        int amountOfSeedsPlanted = humanPlayer.plantSeeds();
        if (amountOfSeedsPlanted > 0) {
            plantNewTrees(amountOfSeedsPlanted);
            return amountOfSeedsPlanted;
        } else {
            return 0;
        }
    }

    /**
     * This is where the new trees are actually added It makes sure that the trees in forest never
     * go over the max amount of allowed trees in the forest.
     *
     * @param numOfTreesToBeAdded how many new trees that has to be added
     */
    private void plantNewTrees(int numOfTreesToBeAdded) {
        for (int i = 0; i < numOfTreesToBeAdded && trees.size() < MAX_AMOUNTOFTREESINFOREST; i++) {
            trees.add(new CertifiedTree((int) (Math.random() * 2) + 1));
        }
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/CertifiedForest.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
