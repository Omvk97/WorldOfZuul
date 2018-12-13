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
 *
 * @author oliver
 * co-author: michael, steffen & daniel
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

    public void goRoom(Command command, AnchorPane anchorPane) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
        }
        String direction = command.getSecondWord();

        // The player can write "go back" to get back to the room they were in before
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

    public PlayerInteraction getPlayerInteraction() {
        return playerInteraction;
    }

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

    public String getPlayerDirectionInWorld() {
        return playerInteraction.getPlayerDirectionInWorld();
    }

    public void setPlayerDirectionInWorld(String direction) {
        playerInteraction.setPlayerDirectionInWorld(direction);
    }

    public HighScore getHighScoreData() {
        return highScoreData;
    }
}
