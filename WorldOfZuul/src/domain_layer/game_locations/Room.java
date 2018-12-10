package domain_layer.game_locations;

import domain_layer.game_functionality.Player;

import java.util.HashMap;
import javafx.scene.Parent;

/**
 *
 * @author oliver
 */
public abstract class Room {

    private final HashMap<String, Room> exits;

    public Room() {
        exits = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    abstract public String roomEntrance(Player humanPlayer);

    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
    abstract public Parent getRoomFXML();
}
