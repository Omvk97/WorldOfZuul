package domain_layer.game_functionality;

import data_access_layer.HighScore;
import domain_layer.game_locations.BlackSmith;
import domain_layer.game_locations.Forest;
import domain_layer.game_locations.Trailer;
import domain_layer.game_locations.Room;
import domain_layer.game_locations.Store;
import domain_layer.game_locations.LocalVillage;
import domain_layer.game_locations.NonCertifiedForest;
import domain_layer.game_locations.CertifiedForest;
import domain_layer.game_locations.Library;
import javafx.scene.layout.AnchorPane;

/**
 * This class is a singleton, which makes sure that when the game is instantiated, all interactions
 * made by the player with the world will be interactions with the same object in memory. The class
 * responsibility is to make all the rooms and all exits between the rooms. And also to communicate
 * between data_access_layer and view_layer.
 *
 * @author oliver co-author: michael, steffen & daniel
 */
public class Game {

    private static final Game instance = new Game();
    private final Trailer trailer = new Trailer();
    private final Player humanPlayer = new Player(trailer);
    private final Forest certifiedForest = new CertifiedForest();
    private final Forest nonCertificedForest = new NonCertifiedForest();
    private final Room localVillage = new LocalVillage();
    private final Room store = new Store();
    private final Room blacksmith = new BlackSmith();
    private final Room library = new Library();
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();
    private final HighScore highScoreData = new HighScore();

    private Game() {
        setExitsForRooms();
        playerInteraction.setCurrentRoom(trailer);
    }

    public static Game getInstanceOfSelf() {
        return instance;
    }

    /**
     * Sets the valid exits for all the rooms in the game world.
     */
    private void setExitsForRooms() {
        trailer.setExit("village", localVillage);
        trailer.setExit("south", certifiedForest);
        trailer.setExit("north", nonCertificedForest);

        certifiedForest.setExit("trailer", trailer);

        nonCertificedForest.setExit("trailer", trailer);

        localVillage.setExit("trailer", trailer);
        localVillage.setExit("store", store);
        localVillage.setExit("blacksmith", blacksmith);
        localVillage.setExit("library", library);

        blacksmith.setExit("village", localVillage);

        library.setExit("village", localVillage);
    }

    /**
     * It changes the players placement in the game world. So that the player can move around.
     *
     * @param command The direction that the player is going to.
     * @param anchorPane The current Anchorpane the player resides in so that the scene can be
     * accessed.
     */
    public void goRoom(Command command, AnchorPane anchorPane) {
        String direction = command.getSecondWord();

        // The player can go back to the room they came from.
        if (direction.equals("back")) {
            if (playerInteraction.getPreviousRoom() != null) {
                playerInteraction.setCurrentRoom(playerInteraction.getPreviousRoom());
                anchorPane.getScene().setRoot(playerInteraction.getCurrentRoom().getRoomFXML());
                return;
            }
        }

        Room nextRoom = playerInteraction.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no road!");
        } else {
            playerInteraction.setCurrentRoom(nextRoom);
        }
        anchorPane.getScene().setRoot(playerInteraction.getCurrentRoom().getRoomFXML());
    }

    /**
     * All the following methods is to access the rooms and the humanplayer that is interacting with
     * the rooms
     *
     * @return the attribute player
     */
    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Trailer getTrailer() {
        return trailer;
    }

    public Forest getCertifiedForest() {
        return certifiedForest;
    }

    public Forest getNonCertificedForest() {
        return nonCertificedForest;
    }

    public Room getLocalVillage() {
        return localVillage;
    }

    public Room getStore() {
        return store;
    }

    public Room getBlacksmith() {
        return blacksmith;
    }

    public Room getLibrary() {
        return library;
    }

    /**
     * To access the data_access_layer
     *
     * @return the highScore data.
     */
    public HighScore getHighScoreData() {
        return highScoreData;
    }
}
