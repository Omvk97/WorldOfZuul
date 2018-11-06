package gameFunctionality;

import Locations.*;

public class Game {

    private final Parser parser;
    private final BackPack starterBackPack = new BackPack("Starter Backpack", 0, 5);
    private final Player humanPlayer = new Player(starterBackPack);
    private final Room trailer = new Trailer("inside your trailer", humanPlayer);
    private final Room certifiedForest = new CertifiedForest("in a certified forest", humanPlayer);
    private final Room nonCertificedForest = new NonCertifiedForest("in a non certified forest", humanPlayer);
    private final Room localVillage = new LocalVillage("in a local village", humanPlayer, (Trailer) trailer);
    private final Room weatherCenter = new WeatherReportCenter("in a weather report center from around the world", humanPlayer);
    private final Room store = new Store("in the LumberJack shop", humanPlayer, (Trailer) trailer);
    private final Room tutorialRoom = new TutorialRoom("the tutorial room", humanPlayer, (Trailer) trailer);

    public Game() {
        setExitsForRooms();
        setOptionsForRooms();
        parser = new Parser(humanPlayer);
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

    private void setOptionsForRooms() {
        trailer.setOptions("store logs", "1");
        trailer.setOptions("look wallet", "2");
        trailer.setOptions("sleep", "3");
        weatherCenter.setOptions("global news", "1");
        weatherCenter.setOptions("local news", "2");
    }

    public void play() {
        /**
         * Der bliver her tilføjet meget samme funktion som der var før, men i stedet for at game
         * klassen holder øje med hvilket rum spilleren er i, så er det nu 'Player' klassen som
         * holder øje med dette. Det betyder at spilleren faktisk bevæger sig rundt og ikke spillet
         * der bevæger sig rundt om spilleren.
         */
        humanPlayer.setCurrentRoom(tutorialRoom);

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
            + "Your job as a lumberjack, is to cut down trees. \n"
            + "You have " + Trailer.getNumPlayDays() + " days playtime to earn as much money as you can\n"
            + "without destroying the earth!\n");
        System.out.println(humanPlayer.getCurrentRoom().getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (null != commandWord) {
            switch (commandWord) {
                case HELP:
                    printHelp();
                    break;
                case GO:
                    goRoom(command);
                    break;
                case QUIT:
                    wantToQuit = quit(command);
                    break;
                case OPTION:
                    doOption(command);
                    break;
                case EXITS:
                    System.out.println(humanPlayer.getCurrentRoom().getExitString());
                    break;
                default:
                    break;
            }
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
            case "4":
                humanPlayer.getCurrentRoom().option4();
                break;
            case "666":
                System.out.println("THE DEVIL REWARDS YOU FOR YOUR CURIOSITY");
                humanPlayer.addMoney(9999);
                System.out.println(9999 + " HAS BEEN ADDED TO YOUR WALLET");
                break;
            default:
                System.out.println("I do not know that option");
        }
    }
}
