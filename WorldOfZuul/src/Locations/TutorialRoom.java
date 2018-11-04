package Locations;

import gameFunctionality.CommandWord;
import gameFunctionality.Player;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorialRoom extends Room {

    Scanner input = new Scanner(System.in);
    boolean answer;
    private final Trailer trailer;

    public TutorialRoom(String description, Player player, Trailer trailer) {
        super(description, player);
        this.trailer = trailer;
    }

    private void getAnswer() {
        pause(6000);
        System.out.println("Would you like to take a tutorial? [Y / N]");
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
                + "Here you will learn how the game works\n"
                + "Now you stand inside your trailer!\n"
                + "From here you can either go north, east, west or south");
            pause(7000);
            goTutorial();
            optionTutorial();
            System.out.println("GREAT!");
            pause(3000);
            System.out.print("You are now done with the tutorial!\n"
                + "Have fun!\n");
            pause(3000);
            System.out.println("Type '" + CommandWord.HELP + "' if you ever need help.");
            pause(5000);
            humanPlayer.setCurrentRoom(trailer);
            return trailer.getLongDescription();
        } else {
            System.out.println("Alright have fun!");
            System.out.println("Type '" + CommandWord.HELP + "' if you ever need help. \n");
            humanPlayer.setCurrentRoom(trailer);
            return trailer.getLongDescription();
        }

    }

    private void optionTutorial() {
        System.out.println("Great job! You are now standing in the west area");
        pause(3000);
        System.out.println("In each room you have different options you can perform\n"
            + "In this room you have these options:\n"
            + "Option 1 - Pick up a rock\n"
            + "Option 2 - Karatechop a twig");
        pause(6000);
        System.out.print("Try typing 'option' followed by the choice you would like to make \n");
        String option = input.nextLine();
        boolean correctUserOption = false;
        while (!correctUserOption) {
            switch (option) {
                case "option 1":
                    System.out.println("Great you picked up a rock");
                    pause(4000);
                    correctUserOption = true;
                    break;
                case "option 2":
                    System.out.println("You made a furious karatechop and choped a twig in half\n"
                        + "Be proud of yourself!");
                    pause(4000);
                    correctUserOption = true;
                    break;
                default:
                    System.out.println("Incorrect answer, try again!");
                    option = input.nextLine();
            }
        }
    }

    private void goTutorial() {
        System.out.println("Try typing 'go west'");
        String goWest = input.nextLine();
        while (!goWest.equals("go west")) {
            System.out.println("Incorrect, try again");
            goWest = input.nextLine();
        }
    }
    
    private void pause(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(TutorialRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
