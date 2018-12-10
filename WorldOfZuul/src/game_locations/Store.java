package game_locations;

import game_elements.BackPack;
import game_elements.BackPackFactory;
import game_elements.Tree;
import game_functionality.Player;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;

/**
 *
 * @author oliver
 * @date 2/12/2018
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
     * To make sure that the player can't buy a getBackPack they already own.
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
        if (humanPlayer.getLogsInStorage().isEmpty()
            && humanPlayer.getBackPack().getLogsInBackPack().isEmpty()) {
            return false;
        }
        if (!humanPlayer.getBackPack().getLogsInBackPack().isEmpty()) {
            for (Tree tree : humanPlayer.getBackPack().getLogsInBackPack()) {
                humanPlayer.addMoney(tree.getTreePrice());
            }
            humanPlayer.getBackPack().emptyBackpack();
        }
        if (!humanPlayer.getLogsInStorage().isEmpty()) {
            humanPlayer.getLogsInStorage().forEach((tree)
                -> humanPlayer.addMoney(tree.getTreePrice()));
            humanPlayer.loadOffLogsInStorage();
        }
        return true;
    }

    public boolean buyItem(String id, Player humanPlayer) {
        switch (id) {
            case "smallBackPack":
                return humanPlayer.boughtBackPack(smallBackPack);
            case "mediumBackPack":
                return humanPlayer.boughtBackPack(mediumBackPack);
            case "largeBackPack":
                return humanPlayer.boughtBackPack(largeBackPack);
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
            Parent root = FXMLLoader.load(getClass().getResource("/room_fxml/Store.fxml"));
            return root;
        } catch (IOException ex) {
            System.out.println("The fxml does not exist");
        }
        return null;
    }

}
