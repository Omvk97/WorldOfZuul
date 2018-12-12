package domain_layer.game_locations;

import domain_layer.game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author steffen
 */
public class BlackSmith extends Room {

    private final String blackSmithNPC = "Smith:\n";

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

    public int grindAxe_menu(Player humanPlayer) {
        final int pricePerAxeDurability = 2;

        if (humanPlayer.getEquippedAxe() == null) {
            return 1;

        } else if (humanPlayer.getEquippedAxe().getDurability() == humanPlayer.getEquippedAxe().getStartDurability()) {
            return 2;

        } else if (humanPlayer.getEquippedAxe().getDurability() < humanPlayer.getEquippedAxe().getStartDurability()) {
            int durabilityLostOnAxe = humanPlayer.getEquippedAxe().getStartDurability() - humanPlayer.getEquippedAxe().getDurability();
            int fixAxePrice = pricePerAxeDurability * durabilityLostOnAxe;
            if (humanPlayer.getMoneyValue() >= fixAxePrice) {
                System.out.println(blackSmithNPC + "I will grind your axe for you. Please wait");
                int timeToWait = 6;
                try {
                    for (int i = 0; i < timeToWait; i++) {
                        Thread.sleep(1000);
                        System.out.println("**Ding**");
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                humanPlayer.grindedAxe(fixAxePrice);
                return 3;
            } else {
                return 4;
            }
        }
        return 0;
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
