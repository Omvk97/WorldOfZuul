package gameFunctionality;

import Locations.Room;
import Locations.NonCertifiedForest;

public class Game {

    private final Parser parser;
    private Room currentRoom;

    public Game() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room trailer, certificeretSkov, ikkeCertificeretSkov, lokaltSamfund, vejrRapportCenter;

        trailer = new Room("inside your trailer");
        certificeretSkov = new Room("in a certified forest");
        ikkeCertificeretSkov = new NonCertifiedForest("in a non certified forest");
        lokaltSamfund = new Room("in a local community");
        vejrRapportCenter = new Room("in a weather report center from around the world");

        trailer.setExit("east", lokaltSamfund);
        trailer.setExit("south", certificeretSkov);
        trailer.setExit("west", vejrRapportCenter);
        trailer.setExit("north", ikkeCertificeretSkov);

        certificeretSkov.setExit("east", lokaltSamfund);
        certificeretSkov.setExit("north", trailer);

        ikkeCertificeretSkov.setExit("south", trailer);
        ikkeCertificeretSkov.setExit("east", lokaltSamfund);

        lokaltSamfund.setExit("west", trailer);

        vejrRapportCenter.setExit("east", trailer);

        currentRoom = trailer;
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        System.out.println("Welcome to 'The LumberJack'! \n"
            + "Your job as a lumberjack is to cut down trees without destroying the earth!");
        System.out.println("Type '" + CommandWord.HELP + "' if you ever need help. \n");
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are a lumberjack, your job is to cut down trees! GO DO IT");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no road!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }
}
