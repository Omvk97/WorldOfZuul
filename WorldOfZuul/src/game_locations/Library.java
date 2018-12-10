package game_locations;

import game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author michael
 */
public class Library extends Room {

    String libraryOwner = "Anna: \n";

    public Library() {
    }

    @Override
    public String roomEntrance(Player humanplayer) {
        return libraryOwner + "Hi there, Feel free to read any of the books on the bookshelf.";
    }
    
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/Library.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
