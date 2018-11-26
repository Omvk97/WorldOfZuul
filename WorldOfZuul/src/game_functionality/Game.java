package game_functionality;

import game_locations.*;
import javafx.scene.control.Label;
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
    private static final Game self = new Game();

    private Game() {
        setExitsForRooms();
        setOptionsForRooms();
        humanPlayer.setCurrentRoom(trailer);
    }

    public static Game getInstanceOfSelf() {
        return self;
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

    private void setOptionsForRooms() {
        // certifiedForest
        certifiedForest.setOptions("choptree", "1");
        certifiedForest.setOptions("chop", "1");
        certifiedForest.setOptions("chopwood", "1");
        certifiedForest.setOptions("treeinfo", "2");
        certifiedForest.setOptions("info", "2");
        certifiedForest.setOptions("tree", "2");
        certifiedForest.setOptions("replanttrees", "3");
        certifiedForest.setOptions("replant", "3");
        //Trailer
        trailer.setOptions("storelogs", "1");
        trailer.setOptions("store", "1");
        trailer.setOptions("checkwallet", "2");
        trailer.setOptions("check", "2");
        trailer.setOptions("sleep", "3");
        trailer.setOptions("pickupaxe", "4");
        trailer.setOptions("axe", "4");
        //NonCertifiedForest
        nonCertificedForest.setOptions("choptree", "1");
        nonCertificedForest.setOptions("chop", "1");
        nonCertificedForest.setOptions("chopwood", "1");
        nonCertificedForest.setOptions("treeinfo", "2");
        nonCertificedForest.setOptions("tree", "2");
        nonCertificedForest.setOptions("info", "2");
        //Store
        store.setOptions("selllogs", "1");
        store.setOptions("sell", "1");
        store.setOptions("buyitems", "2");
        store.setOptions("buy", "2");
        //Blacksmith
        blacksmith.setOptions("repair", "1");
        blacksmith.setOptions("repairaxe", "1");
        blacksmith.setOptions("buy", "2");
        blacksmith.setOptions("buyaxe", "2");
        // library
        library.setOptions("readbook1", "1");
        library.setOptions("book1", "1");
        library.setOptions("readbook2", "2");
        library.setOptions("book2", "2");
        library.setOptions("readbook3", "3");
        library.setOptions("book3", "3");
    }

    public void goRoom(Command command, AnchorPane anchorPane) {
//        if (!command.hasSecondWord()) {
//            return "Go where?";
//        }
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

//        if (nextRoom == null) {
//            return "There is no road!";
//        } else {
        humanPlayer.setCurrentRoom(nextRoom);
//        }
        anchorPane.getScene().setRoot(humanPlayer.getCurrentRoom().getRoomFXML());
    }

    public void printHelp(Label text) {
        text.setText("You can type 'exits' to get the directions you can go\n"
            + "2. You can type 'go back' to go to the place you came from previously\n"
            + "3. You can type 'quit' to quit the game");
    }

    public boolean quit(Command command, Label text) {
        if (command.hasSecondWord()) {
            text.setText("Quit what?");
            return false;
        } else {
            text.setText("Your score is: " + humanPlayer.getHighScore());
            System.exit(0);
            return true;
        }
    }

//    public void doOption(Command command, AnchorPane anchorPane) {
//        if (!command.hasSecondWord()) {
//            text.setText("Do what?");
//            return;
//        }
//        String optionNumber = command.getSecondWord();
//        switch (optionNumber) {
//            case "1":
//                humanPlayer.getCurrentRoom().option1(humanPlayer);
//                break;
//            case "2":
//                humanPlayer.getCurrentRoom().option2(humanPlayer);
//                break;
//            case "3":
//                humanPlayer.getCurrentRoom().option3(humanPlayer);
//                break;
//            case "4":
//                humanPlayer.getCurrentRoom().option4(humanPlayer);
//                break;
//            case "666":
//                text.setText("THE DEVIL REWARDS YOU FOR YOUR CURIOSITY");
//                humanPlayer.addMoney(9999);
//                text.setText(9999 + " HAS BEEN ADDED TO YOUR WALLET");
//                break;
//            default:
//                text.setText("I do not know that option");
//        }
//
//    }
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
