package Locations;

import gameFunctionality.Player;
import java.util.Set;
import java.util.HashMap;

public class Room {

    private final String description;
    private final HashMap<String, Room> exits;
    private final HashMap<String, String> options;
    public Player player = new Player(); // Her instaniseres spilleren så dens variabler kan tilgås fra rummene

    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
        options = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are standing " + description + ".\n" + getExitString();
    }

    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getOption(String optionNumber) {
        return options.get(optionNumber);
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
}
