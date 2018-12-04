package game_functionality;

import game_locations.*;
import javafx.scene.layout.AnchorPane;

public class Game {

    private final Trailer trailer = new Trailer();
    private final Player humanPlayer = new Player(trailer);
    private final Forest certifiedForest = new CertifiedForest();
    private final Forest nonCertificedForest = new NonCertifiedForest();
    private final Room localVillage = new LocalVillage();
    private final Room store = new Store();
    private final Room tutorialRoom = new TutorialRoom();
    private final Room blacksmith = new BlackSmith();
    private final Room library = new Library();
    private static final Game instance = new Game();

    private Game() {
        setExitsForRooms();
        humanPlayer.setCurrentRoom(trailer);
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
            if (humanPlayer.getPreviousRoom() != null) {
                humanPlayer.setCurrentRoom(humanPlayer.getPreviousRoom());
                anchorPane.getScene().setRoot(humanPlayer.getCurrentRoom().getRoomFXML());
                return;
            }
        }

        Room nextRoom = humanPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no road!");
        } else {
            humanPlayer.setCurrentRoom(nextRoom);
        }
        anchorPane.getScene().setRoot(humanPlayer.getCurrentRoom().getRoomFXML());
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

    public Room getTutorialRoom() {
        return tutorialRoom;
    }

    public Room getBlacksmith() {
        return blacksmith;
    }

    public Room getLibrary() {
        return library;
    }

}
