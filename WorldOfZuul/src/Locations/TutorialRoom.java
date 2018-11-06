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
        pause(5000);
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
            pause(4300);
            goTutorial();
            optionTutorial();
            System.out.println("GREAT!");
            System.out.print("You are now done with the tutorial!\n"
                + "Have fun!\n");
            pause(2000);
            System.out.println("Type '" + CommandWord.HELP + "' if you ever need help.");
            pause(4000);
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
        System.out.println("Great job! You are now standing in the west area.");
        pause(1800);
        System.out.println("In each room you have different options you can perform\n"
            + "In this room you have these options:\n"
            + "----------------------------------\n"
            + "○ Pick up rock - You pick up a rock\n"
            + "○ Karatechop - Do a karatechop on a twig\n"
            + "----------------------------------\n");
        System.out.print("Try typing one of the options \n");
        String option = input.nextLine().toLowerCase().replaceAll("\\s","");
        boolean correctUserOption = false;
        while (!correctUserOption) {
            switch (option) {
                case "pickuprock":
                    System.out.println("You picked up a rock");
                    pause(2000);
                    correctUserOption = true;
                    break;
                case "karatechop":
                    System.out.println("You made a furious karatechop and choped a twig in half\n"
                        + "Be proud of yourself!");
                    pause(2000);
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
        String goWest = input.nextLine().toLowerCase().replaceAll("\\s", "");
        while (!goWest.equals("gowest")) {
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
