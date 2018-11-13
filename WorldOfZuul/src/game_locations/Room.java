package game_locations;

import game_functionality.Player;

import java.util.HashMap;
import java.util.Set;

public abstract class Room {

    private final String description;
    private final HashMap<String, Room> exits;
    private final HashMap<String, String> options;

    private final int[] NEGATIVE_SCENARIO_POINTS = new int[]{-49, -99, -149, -199, -249};
    private final int[] POSITIVE_SCENARIO_POINTS = new int[]{49, 99, 149, 199, 249};

    public Room(String description) {
        this.description = description;
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

    abstract public String getLongDescription(Player humanPlayer);

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

    public int[] getNEGATIVE_SCENARIO_POINTS() {
        return NEGATIVE_SCENARIO_POINTS;
    }

    public int[] getPOSITIVE_SCENARIO_POINTS() {
        return POSITIVE_SCENARIO_POINTS;
    }

    public void option1(Player humanPlayer) {
        System.out.println("There is no option 1 in this room");
    }

    public void option2(Player humanPlayer) {
        System.out.println("There is no option 2 in this room");
    }

    public void option3(Player humanPlayer) {
        System.out.println("There is no option 3 in this room");
    }

    public void option4(Player humanPlayer) {
        System.out.println("There is no option 4 in this room");
    }
}
