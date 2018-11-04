package Locations;

import gameFunctionality.Player;
import java.util.Set;
import java.util.HashMap;

public abstract class Room {

    private final String description;
    private final HashMap<String, Room> exits;
    private final HashMap<String, String> options;
    protected final Player humanPlayer;

    public Room(String description, Player player) {
        this.description = description;
        this.humanPlayer = player;
        exits = new HashMap();
        options = new HashMap();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public void setOptions(String userInput, String optionNumber) {
        options.put(userInput, optionNumber);
    }

    public String getShortDescription() {
        return description;
    }

    abstract public String getLongDescription();

    public String getExitString() {
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
    }

    public String getOptions(String userInput) {
        return options.get(userInput);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void option1() {
        System.out.println("There is no option 1 in this room");
    }

    public void option2() {
        System.out.println("There is no option 2 in this room");
    }

    public void option3() {
        System.out.println("There is no option 3 in this room");
    }

    public void option4() {
        System.out.println("There is no option 4 in this room");
    }
}
