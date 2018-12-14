package domain_layer.game_locations;

import domain_layer.game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * handles the logic of library.
 *
 * @author michael
 */
public class Library extends Room {

    private final String libraryOwner = "Anna: \n";

    /**
     * Sets the library roomEntrance
     *
     * @param humanplayer
     * @return String
     */
    @Override
    public String roomEntrance(Player humanplayer) {
        return libraryOwner + "Hi there, Feel free to read any of the books on the bookshelf.";
    }

    /**
     * Sets what FXML document it should try to load.
     *
     * @return FXML
     */
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/Library.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
