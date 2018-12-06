package game_locations;

import game_elements.Axe;
import game_elements.AxeFactory;
import game_functionality.Player;
import java.io.IOException;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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

        if (humanPlayer.getAxe() == null) {
            return 1;

        } else if (humanPlayer.getAxe().getDurability() == humanPlayer.getAxe().getStartDurability()) {
            return 2;

        } else if (humanPlayer.getAxe().getDurability() < humanPlayer.getAxe().getStartDurability()) {
            int durabilityLostOnAxe = humanPlayer.getAxe().getStartDurability() - humanPlayer.getAxe().getDurability();
            int fixAxePrice = pricePerAxeDurability * durabilityLostOnAxe;
            if (humanPlayer.getMoney() >= fixAxePrice) {
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
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/Blacksmith.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }
}
