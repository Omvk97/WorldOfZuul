package gameFunctionality;

import Locations.CertifiedForest;
import Locations.Room;
import Locations.NonCertifiedForest;
import Locations.Trailer;

public class Game {

    private final Parser parser;
    private Room currentRoom;

    public Game() {
        createRooms();
        parser = new Parser();
    }

    private void createRooms() {
        Room trailer, certifiedForest, nonCertificedForest, localsoicety, weatherCenter;

        trailer = new Trailer("inside your trailer");
        certifiedForest = new CertifiedForest("in a certified forest");
        nonCertificedForest = new NonCertifiedForest("in a non certified forest");
        localsoicety = new Room("in a local community");
        weatherCenter = new Room("in a weather report center from around the world");

        trailer.setExit("east", localsoicety);
        trailer.setExit("south", certifiedForest);
        trailer.setExit("west", weatherCenter);
        trailer.setExit("north", nonCertificedForest);

        certifiedForest.setExit("east", localsoicety);
        certifiedForest.setExit("north", trailer);

        nonCertificedForest.setExit("south", trailer);
        nonCertificedForest.setExit("east", localsoicety);

        localsoicety.setExit("west", trailer);
        localsoicety.setExit("north", nonCertificedForest);
        localsoicety.setExit("south", certifiedForest);

        weatherCenter.setExit("east", trailer);

        currentRoom = trailer;
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing The LumberJack. Goodbye.");
    }

    private void printWelcome() {
        System.out.println("Welcome to 'The LumberJack'! \n"
            + "Your job as a lumberjack, is to cut down trees without \n"
            + "destroying the earth!");
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
        } else if (commandWord == CommandWord.OPTION) {
            doOption(command);
        } else if (commandWord == CommandWord.EXITS) {
            System.out.println(currentRoom.getExitString());
        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are a lumberjack, your job is to cut down trees! GO DO THAT");
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

    private void doOption(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Do what?");
            return;
        }

        String optionNumber = command.getSecondWord();
        switch (optionNumber) {
            case "1":
                currentRoom.option1();
                break;
            case "2":
                currentRoom.option2();
                break;
            case "3":
                currentRoom.option3();
                break;
        }
    }
}
