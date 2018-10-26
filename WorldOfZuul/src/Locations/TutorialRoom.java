package Locations;

import gameFunctionality.Player;
import java.util.Scanner;

public class TutorialRoom extends Room {

    Scanner input = new Scanner(System.in);
    boolean answer;
    private final Trailer trailer;

    public TutorialRoom(String description, Player player, Trailer trailer) {

        super(description, player);
        this.trailer = trailer;
    }

    public void getAnswer() {
        System.out.println("Would you like to take a tutorial? Y|N");
        String playerInput = input.nextLine();
        if (playerInput.toUpperCase().equals("Y")) {
            answer = true;
        } else {
            answer = false;

        }
    }

    @Override
    public String getLongDescription() {
        getAnswer();
        if (answer) {
            System.out.println("Welcome to the turtorial room! \n"
                + "Here youl you'll learn how the game world funktions and how to naivagte\n"
                + "You are standing inside your trailer!\n"
                + "From here you can either go north, east, west or south\n"
                + "Try go west\n");
            String goWest = input.nextLine();
            while (!goWest.equals("go west")) {
                System.out.println("Try typing 'go west'");
                goWest = input.nextLine();
            }
            System.out.println("You are now in the west area\n"
                + "Here you have diffrent options like\n"
                + "option 1: pick up rock\n"
                + "option 2: karatechop a twig\n"
                + "Try typing 'option' followed by the choise you would like to make\n");
            String option = input.nextLine();
            

            return getExitString();
        } else {

            humanPlayer.setCurrentRoom(trailer);
            return "You are standing inside your trailer!\n"
                + "This is your home, you have " + humanPlayer.getClimatePoints() + " climate points,"
                + " your options are: \n"
                + "Option 1 - Load off logs you are carrying \n"
                + "Option 2 - Look in your wallet \n"
                + "Option 3 - Sleep";
        }

    }
}
