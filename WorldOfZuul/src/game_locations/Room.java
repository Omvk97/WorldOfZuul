package game_locations;

import game_functionality.Player;

import java.util.HashMap;
import java.util.Set;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public abstract class Room {

    private final HashMap<String, Room> exits;
    private final HashMap<String, String> options;

    public Room() {
        exits = new HashMap<>();
        options = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setOptions(String userInput, String optionNumber) {
        options.put(userInput, optionNumber);
    }

    abstract public String roomEntrance(Player humanPlayer);

    public String getExitString() {
        Set<String> keys = exits.keySet();
        return keys.toString();//returnString.toString();
    }

    public String getOptions(String userInput) {
        return options.get(userInput);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void option1(Player humanPlayer, Label textArea) {
        System.out.println("There is no option 1 in this room");
    }

    public void option2(Player humanPlayer, Label textArea) {
        System.out.println("There is no option 2 in this room");
    }

    public void option3(Player humanPlayer, Label textArea) {
        System.out.println("There is no option 3 in this room");
    }

    public void option4(Player humanPlayer, Label textArea) {
        System.out.println("There is no option 4 in this room");
    }
    
    abstract public Parent getRoomFXML();
}
