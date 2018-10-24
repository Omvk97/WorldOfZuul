package gameFunctionality;

import Locations.*;

public class Game {

    private final Parser parser;
    private final Player humanPlayer = new Player();
    private final Room trailer = new Trailer("inside your trailer", humanPlayer);
    private final Room certifiedForest = new CertifiedForest("in a certified forest", humanPlayer);
    private final Room nonCertificedForest = new NonCertifiedForest("in a non certified forest", humanPlayer);
    private final Room localVillage = new LocalVillage("in a local community", humanPlayer, (Trailer) trailer);
    private final Room weatherCenter = new Room("in a weather report center from around the world", humanPlayer);
    private final Room store = new Store("in the LumberJack shop", humanPlayer, (Trailer) trailer);

    public Game() {
        setExitsForRooms();
        parser = new Parser();
    }

    private void setExitsForRooms() {

        trailer.setExit("east", localVillage);
        trailer.setExit("south", certifiedForest);
        trailer.setExit("west", weatherCenter);
        trailer.setExit("north", nonCertificedForest);
        trailer.setExit("southwest", store);

        certifiedForest.setExit("east", localVillage);
        certifiedForest.setExit("north", trailer);

        nonCertificedForest.setExit("south", trailer);
        nonCertificedForest.setExit("east", localVillage);

        localVillage.setExit("west", trailer);
        localVillage.setExit("north", nonCertificedForest);
        localVillage.setExit("south", certifiedForest);

        weatherCenter.setExit("east", trailer);

        store.setExit("northeast", trailer);
    }

    public void play() {
        /**
         * Der bliver her tilføjet meget samme funktion som der var før, men i stedet for at game klassen holder øje med
         * hvilket rum spilleren er i, så er det nu 'Player' klassen som holder øje med dette. Det betyder at spilleren
         * faktisk bevæger sig rundt og ikke spillet der bevæger sig rundt om spilleren.
         */
        humanPlayer.setCurrentRoom(trailer);

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
        System.out.println(humanPlayer.getCurrentRoom().getLongDescription());
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
            System.out.println(humanPlayer.getCurrentRoom().getExitString());
        }
        return wantToQuit;
    }

    private void printHelp() {
        System.out.println("You are a lumberjack, your job is to cut down trees!");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = humanPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no road!");
        } else {
            humanPlayer.setCurrentRoom(nextRoom);
            System.out.println(humanPlayer.getCurrentRoom().getLongDescription());
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
                humanPlayer.getCurrentRoom().option1();
                break;
            case "2":
                humanPlayer.getCurrentRoom().option2();
                break;
            case "3":
                humanPlayer.getCurrentRoom().option3();
                break;
            case "666":
                System.out.println("THE DEVIL REWARDS YOU FOR YOUR CURIOSITY");
                humanPlayer.addMoney(9999);
                System.out.println(9999 + " HAS BEEN ADDED TO YOU WALLET MUAHAHA");
                break;
            default:
                System.out.println("I do not know that option");
        }
    }
}
