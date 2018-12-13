package domain_layer.game_locations;

import domain_layer.game_elements.BackPack;
import domain_layer.game_elements.BackPackFactory;
import domain_layer.game_elements.Tree;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

/**
 *
 * @author oliver
 */
public class Store extends Room {

    private static final int SAPLING_PRICE = 5;
    private final String StoreOwner = "Reginald:\n";
    private BackPack smallBackPack = BackPackFactory.createSmallBackPack();
    private BackPack mediumBackPack = BackPackFactory.createMediumBackPack();
    private BackPack largeBackPack = BackPackFactory.createLargeBackPack();

    public Store() {
    }

    @Override
    public String roomEntrance(Player humanPlayer) {
        return StoreOwner + "Hi " + "Welcome to my store!\n"
            + "Here you can sell your logs and purchase stuffs!\n";
    }

    /**
     * To make sure that the player can't buy a getEquippedBackPack they already own.
     */
    public void createNewBackPacks() {
        smallBackPack = BackPackFactory.createSmallBackPack();
        mediumBackPack = BackPackFactory.createMediumBackPack();
        largeBackPack = BackPackFactory.createLargeBackPack();
    }

    public String getItemInfo(ImageView image) {
        switch (image.getId()) {
            case "smallBackPack":
                return smallBackPack.toString();
            case "mediumBackPack":
                return mediumBackPack.toString();
            case "largeBackPack":
                return largeBackPack.toString();
            case "oneSapling":
                return "One sapling with a price of " + SAPLING_PRICE + " gold coins!";
            case "fiveSapling":
                return "Five saplings with a total price of " + (SAPLING_PRICE * 5) + " gold coins!";
            case "tenSapling":
                return "Ten saplings with a total price of " + (SAPLING_PRICE * 10) + " gold coins!";
        }
        return "";
    }

    public boolean sellLogs(Player humanPlayer) {
        final Trailer trailer = Game.getInstanceOfSelf().getTrailer();

        if (trailer.getLogsInStorage().isEmpty()
            && humanPlayer.getEquippedBackPack().getLogsInBackPack().isEmpty()) {
            return false;
        }
        if (!humanPlayer.getEquippedBackPack().getLogsInBackPack().isEmpty()) {
            for (Tree tree : humanPlayer.getEquippedBackPack().getLogsInBackPack()) {
                humanPlayer.addMoney(tree.getTreePrice());
            }
            humanPlayer.getEquippedBackPack().emptyBackpack();
        }
        if (!trailer.getLogsInStorage().isEmpty()) {
            trailer.getLogsInStorage().forEach((tree)
                -> humanPlayer.addMoney(tree.getTreePrice()));
            trailer.loadOffLogsInStorage();
        }
        return true;
    }

    public boolean buyItem(String id, Player humanPlayer) {
        switch (id) {
            case "smallBackPack":
                return humanPlayer.buyBackPack(smallBackPack);
            case "mediumBackPack":
                return humanPlayer.buyBackPack(mediumBackPack);
            case "largeBackPack":
                return humanPlayer.buyBackPack(largeBackPack);
            case "oneSapling":
                return humanPlayer.buySapling(1, SAPLING_PRICE);
            case "fiveSapling":
                return humanPlayer.buySapling(5, SAPLING_PRICE);
            case "tenSapling":
                return humanPlayer.buySapling(10, SAPLING_PRICE);
        }
        return false;
    }

    @Override
    public Parent getRoomFXML() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view_layer/room_fxml/Store.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
