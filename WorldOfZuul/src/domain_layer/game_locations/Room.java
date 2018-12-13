package domain_layer.game_locations;

import domain_layer.game_functionality.Player;

import java.util.HashMap;
import javafx.scene.Parent;

/**
 * Contains methods that makes sure that movement between rooms is possible. And forces subclasses
 * to implement movement between rooms.
* @author oliver
 */
public abstract class Room {

    private final HashMap<String, Room> exits;

    public Room() {
        exits = new HashMap<>();
    }

    /**
     * Used by Game class in order to set valid exits for all rooms.
     * @param directionToRoom what direction the player should move in order to go to another room
     * @param roomToGoTo The room that the direction leads to.
     */
    public void setExit(String directionToRoom, Room roomToGoTo) {
        exits.put(directionToRoom, roomToGoTo);
    }

    /**
     * Meant to be used to introduce what room the player is standing in.
     * It also here logic is to be placed if something needs to happen each time the player enters
     * the room.
     * @param humanPlayer the player that moves around
     * @return the introduction string of where the player resides.
     */
    abstract public String roomEntrance(Player humanPlayer);

    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
    /**
     * @return the associated FXML root document when the player moves between rooms and the 
     * graphics and logic has to be changed to another room's graphics and logic.
     */
    abstract public Parent getRoomFXML();
}
