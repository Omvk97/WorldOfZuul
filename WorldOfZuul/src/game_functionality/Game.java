package game_functionality;

import game_elements.BackPack;
import game_locations.*;

public class Game {

    private final Parser parser;
    private final BackPack starterBackPack = new BackPack("Starter Backpack", 0, 5);
    private final Room trailer = new Trailer("inside your trailer");
    private final Player humanPlayer = new Player(starterBackPack, (Trailer) trailer);
    private final Room certifiedForest = new CertifiedForest("in a certified forest");
    private final Room nonCertificedForest = new NonCertifiedForest("in a non certified forest");
    private final Room localVillage = new LocalVillage("in a local village");
    private final Room weatherCenter = new WeatherReportCenter("in a weather report center from around the world");
    private final Room store = new Store("in the LumberJack shop");
    private final Room tutorialRoom = new TutorialRoom("the tutorial room");
    private final Room blacksmith = new BlackSmith("the Blacksmith");
    private final Room library = new Library("the Library");

    public Game() {
        setExitsForRooms();
        setOptionsForRooms();
        parser = new Parser(humanPlayer);
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
        localVillage.setExit("weathercenter", weatherCenter);
        
        blacksmith.setExit("village", localVillage);
        
        library.setExit("village", localVillage);
    }

    private void setOptionsForRooms() {
        // certifiedForest
        certifiedForest.setOptions("choptree", "1");
        certifiedForest.setOptions("chop", "1");
        certifiedForest.setOptions("chopwood", "1");
        certifiedForest.setOptions("treesleft", "2");
        certifiedForest.setOptions("trees", "2");
        certifiedForest.setOptions("replanttrees", "3");
        certifiedForest.setOptions("replant", "3");
        // WeatherCenter
        weatherCenter.setOptions("globalnews", "1");
        weatherCenter.setOptions("global", "1");
        weatherCenter.setOptions("localnews", "2");
        weatherCenter.setOptions("local", "2");
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
        nonCertificedForest.setOptions("treesleft", "2");
        nonCertificedForest.setOptions("trees", "2");
        //Store
        store.setOptions("selllogs", "1");
        store.setOptions("sell", "1");
        store.setOptions("buyitems", "2");
        store.setOptions("buy", "2");
        //Blacksmith
        blacksmith.setOptions("repair","1");
        blacksmith.setOptions("repairaxe","1");
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

    public void play() {
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
            + "You have " +Player.getNumPlayDays() + " days playtime to earn as much money as you can\n"
            + "without destroying the earth!\n");
        System.out.println(humanPlayer.getCurrentRoom().getLongDescription(humanPlayer));
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
        
        // makes sure that the player can always just write go back to get back
        if (direction.equals("back")) {
            if (humanPlayer.getPreviousRoom() != null && !(humanPlayer.getPreviousRoom() instanceof TutorialRoom)) {
                humanPlayer.setCurrentRoom(humanPlayer.getPreviousRoom());
                System.out.println(humanPlayer.getCurrentRoom().getLongDescription(humanPlayer));
                return;
            }
        }

        Room nextRoom = humanPlayer.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no road!");
        } else {
            humanPlayer.setCurrentRoom(nextRoom);
            System.out.println(humanPlayer.getCurrentRoom().getLongDescription(humanPlayer));
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
                humanPlayer.getCurrentRoom().option1(humanPlayer);
                break;
            case "2":
                humanPlayer.getCurrentRoom().option2(humanPlayer);
                break;
            case "3":
                humanPlayer.getCurrentRoom().option3(humanPlayer);
                break;
            case "4":
                humanPlayer.getCurrentRoom().option4(humanPlayer);
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
