package Locations;

import gameFunctionality.Player;
import java.util.Set;
import java.util.HashMap;

public class Room {

    private final String description;
    private final HashMap<String, Room> exits;
    protected Player humanPlayer;

    public Room(String description, Player player) {
        this.description = description;
        this.humanPlayer = player;
        exits = new HashMap();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are standing " + description;
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
    
    public void option5() {
        System.out.println("There is no option 5 in this room");
    }
}
