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
 * @author steffen This Class have the responsibility for making and grinding axes
 */
public class BlackSmith extends Room {

    public final String blackSmithNPC = "Smith:\n";
    String hammer = "src/pictures/hammering_1.wav";

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

    /**
     * Makes the sound when the player grinds an axe.
     */
    public void grindSound() {
        Media hammerSound = new Media(new File(hammer).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hammerSound);
        mediaPlayer.play();
    }

    /**
     * Grainding of the axe.
     *
     * @param humanPlayer
     * @return
     */
    public int fixAxePrice(Player humanPlayer) {
        final int pricePerAxeDurability = 2;
        int durabilityLostOnAxe = humanPlayer.getEquippedAxe().getStartDurability()
            - humanPlayer.getEquippedAxe().getDurability();
        int fixAxePrice = pricePerAxeDurability * durabilityLostOnAxe;
        return fixAxePrice;
    }

    /**
     * Mothod for grind the axe.
     *
     * @param axe
     */
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
