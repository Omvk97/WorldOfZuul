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

    public String option1(Player humanPlayer) {
        return "There is no option 1 in this room";
    }

    public String option2(Player humanPlayer) {
        return "There is no option 2 in this room";
    }

    public String option3(Player humanPlayer) {
        return "There is no option 3 in this room";
    }

    public String option4(Player humanPlayer) {
        return "There is no option 4 in this room";
    }
    
    abstract public Parent getRoomFXML();
}
