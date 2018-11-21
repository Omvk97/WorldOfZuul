package game_locations;

import game_functionality.CommandWord;
import game_functionality.Player;
import java.io.IOException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class TutorialRoom extends Room {

    Scanner input = new Scanner(System.in);
    boolean answer;

    public TutorialRoom() {
    }

    private void getAnswer() {
        System.out.println("Would you like to take a tutorial? [Y / N]");
        String playerInput = input.nextLine();
        answer = playerInput.toUpperCase().equals("Y");
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
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
            return humanPlayer.putPlayerInTrailer();
        } else {
            System.out.println("Alright have fun!");
            System.out.println("Type '" + CommandWord.HELP + "' if you ever need help. \n");
            return humanPlayer.putPlayerInTrailer();
        }
    }

    private void optionTutorial() {
        System.out.println("Great job! You are now standing in the west area.");
        pause(1800);
        System.out.println("In each room you have different options you can perform\n"
            + "In this room you have these options:\n"
            + "----------------------------------\n"
            + "○ Pick up rock ➤ You pick up a rock\n"
            + "○ Karatechop   ➤ Do a karatechop on a twig\n"
            + "----------------------------------");
        System.out.print("Try typing one of the options \n");
        String option = input.nextLine().toLowerCase().replaceAll("\\s", "");
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
                    option = input.nextLine().toLowerCase().replaceAll("\\s", "");
            }
        }
    }

    private void goTutorial() {
        System.out.println("Try typing 'go west'");
        String goWest = input.nextLine().toLowerCase().replaceAll("\\s", "");
        while (!goWest.equals("gowest")) {
            System.out.println("Incorrect, try again");
            goWest = input.nextLine().toLowerCase().replaceAll("\\s", "");
        }
    }

    private void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            Logger.getLogger(TutorialRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("TutorialRoom.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
