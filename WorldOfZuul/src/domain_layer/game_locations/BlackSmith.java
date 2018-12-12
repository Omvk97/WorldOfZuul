package domain_layer.game_locations;

import domain_layer.game_elements.Axe;
import domain_layer.game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 *
 * @author steffen co-author: oliver
 */
public class BlackSmith extends Room {

    public final String blackSmithNPC = "Smith:\n";
    String hammer = "src/pictures/hammering_1.wav";
                Media hammerSound = new Media(new File(hammer).toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(hammerSound);
    public BlackSmith() {
    }

    public String getBlackSmithNPC() {
        return blackSmithNPC;
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return blackSmithNPC + "Welcome to my shop! \n"
            + "If you pay I will make your axe stronger \n";
    }

    public void grindSound() {
               Media hammerSound = new Media(new File(hammer).toURI().toString());
               MediaPlayer mediaPlayer = new MediaPlayer(hammerSound);
               mediaPlayer.play();
              
              
   
               
    }

    public int fixAxePrice(Player humanPlayer) {
        final int pricePerAxeDurability = 2;
        int durabilityLostOnAxe = humanPlayer.getAxe().getStartDurability() - humanPlayer.getAxe().getDurability();
        int fixAxePrice = pricePerAxeDurability * durabilityLostOnAxe;
        return fixAxePrice;
    }

       public void grindAxe(Axe axe) {
        axe.getDurabilityIntegerProperty().setValue(axe.getStartDurability());
    }
    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/Blacksmith.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
