package view_layer.controllers;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.Store;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import view_layer.room_animations.GameAnimation;

/**
 * Records what items the player is clicking on and whether or not the player wants to buy the item
 * they have clicked on. Talks with both associated FXML and with game_locations.store.
 *
 * @author oliver
 */
public class StoreController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button backBtn;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Store gameStore = (Store) Game.getInstanceOfSelf().getStore();
    private final ImageView storeShelf = new ImageView(new Image(
        new File("src/pictures/storeShelf.png").toURI().toString()));
    private final ImageView blueDot = new ImageView(new Image(
        new File("src/pictures/blueDot.png").toURI().toString()));
    private final Button closeShelfButton = new Button("Done");
    private final Button buySelectedItemButton = new Button("Buy");
    private final ArrayList<Node> allItemsAssociatedWithShelf = new ArrayList<>();
    private final GameAnimation animation = new GameAnimation(null);

    /**
     * Makes the shelf ready when the player wants to buy something.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animation.textAnimation(textArea, gameStore.roomEntrance(humanPlayer));
        setLayoutOfShelf();
        setLayoutOfItemsOnShelf();
        handleUserPurchase();
    }

    /**
     * sells logs when the player clicks the first button.
     *
     * @param event the player click event.
     */
    @FXML
    private void handleSellLogs(MouseEvent event) {
        if (gameStore.sellLogs(humanPlayer)) {
            animation.textAnimation(textArea, "You have sold all your logs!");
        } else {
            animation.textAnimation(textArea, "You have no logs to sell!");
        }
    }

    /**
     * Sets the anchorPane children of the fxml document to include the layout of the shelf If the
     * player clicks again while the layout of the shelf already is up nothing happens.
     *
     * @param event
     */
    @FXML
    private void handleBuyItems(MouseEvent event) {
        if (anchorPane.getChildren().contains(storeShelf)) {
//            do nothing
        } else {
            gameStore.createNewBackPacks();
            anchorPane.getChildren().addAll(allItemsAssociatedWithShelf);
        }
        animation.textAnimation(textArea, "Click on the things you would like to buy!");
    }

    /**
     * Handles when the player goes out of the store.
     *
     * @param event when the keyboard is used to go back.
     */
    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Command tester = new Command(CommandWord.GO, "back");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            animation.textAnimation(textArea, "There is no road!");
        }
    }

    /**
     * Handles when the player goes out of the store by click the right hand arrow.
     *
     * @param event when goBack arrow is pressed.
     */
    @FXML
    private void handleBackBtn(MouseEvent event) {
        backBtn.setDisable(true);
        Command tester = new Command(CommandWord.GO, "back");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }

    /**
     * Adds all the visual items that is required to display the shelf.
     */
    private void setLayoutOfShelf() {
        storeShelf.setLayoutX(152);
        storeShelf.setLayoutY(80);
        storeShelf.setFitHeight(250);
        storeShelf.setFitWidth(300);
        buySelectedItemButton.setLayoutX(375);
        buySelectedItemButton.setLayoutY(305);
        closeShelfButton.setLayoutX(buySelectedItemButton.getLayoutX() - 80);
        closeShelfButton.setLayoutY(buySelectedItemButton.getLayoutY());
        closeShelfButton.setOnMouseClicked((MouseEvent event1) -> {
            anchorPane.getChildren().removeAll(allItemsAssociatedWithShelf);
        });

        blueDot.setFitHeight(7);
        blueDot.setFitWidth(7);
        allItemsAssociatedWithShelf.add(storeShelf);
        allItemsAssociatedWithShelf.add(closeShelfButton);
        allItemsAssociatedWithShelf.add(buySelectedItemButton);
        allItemsAssociatedWithShelf.add(blueDot);
    }

    /**
     * Puts items into the shelf visually.
     */
    private void setLayoutOfItemsOnShelf() {
        ImageView smallBackPack = new ImageView(new Image(new File("src/pictures/backPack.png").toURI().toString()));
        smallBackPack.setFitHeight(35);
        smallBackPack.setFitWidth(35);
        smallBackPack.setId("smallBackPack");
        userClick(smallBackPack);
        ImageView mediumBackPack = new ImageView(new Image(new File("src/pictures/backPack.png").toURI().toString()));
        mediumBackPack.setFitHeight(40);
        mediumBackPack.setFitWidth(40);
        mediumBackPack.setId("mediumBackPack");
        userClick(mediumBackPack);
        ImageView largeBackPack = new ImageView(new Image(new File("src/pictures/backPack.png").toURI().toString()));
        largeBackPack.setFitHeight(50);
        largeBackPack.setFitWidth(50);
        largeBackPack.setId("largeBackPack");
        userClick(largeBackPack);
        ImageView sapling = new ImageView(new Image(new File("src/pictures/sapling.png").toURI().toString()));
        sapling.setFitHeight(40);
        sapling.setFitWidth(40);
        sapling.setId("oneSapling");
        userClick(sapling);
        ImageView saplingX5 = new ImageView(new Image(new File("src/pictures/saplingX5.png").toURI().toString()));
        saplingX5.setFitHeight(40);
        saplingX5.setFitWidth(40);
        saplingX5.setId("fiveSapling");
        userClick(saplingX5);
        ImageView saplingX10 = new ImageView(new Image(new File("src/pictures/saplingX10.png").toURI().toString()));
        saplingX10.setFitHeight(40);
        saplingX10.setFitWidth(40);
        saplingX10.setId("tenSapling");
        userClick(saplingX10);
        FlowPane flow = new FlowPane(smallBackPack, mediumBackPack, largeBackPack,
            sapling, saplingX5, saplingX10);
        flow.setPrefWrapLength(storeShelf.getFitWidth() - 60);
        flow.setVgap(35);
        flow.setHgap(40);
        flow.setLayoutX(storeShelf.getLayoutX() + 50);
        flow.setLayoutY(storeShelf.getLayoutY() + 40);
        allItemsAssociatedWithShelf.add(flow);
    }

    /**
     * When the player click on an item in the shelf a blue dot will appear on the selected item,
     * and the blueDots ID will be updated to correspond with the ID of the selected item. Sets an
     * on action handler.
     *
     * @param image the image that is clicked on.
     */
    private void userClick(ImageView image) {
        image.setOnMouseClicked((MouseEvent event) -> {
            blueDot.setLayoutX(image.getLayoutX() + image.getParent().getLayoutX());
            blueDot.setLayoutY(image.getLayoutY() + image.getParent().getLayoutY() - 5);
            blueDot.setId(image.getId());
            animation.textAnimation(textArea, gameStore.getItemInfo(image.getId()));
        });
    }

    /**
     * when the user clicks the buy button, it records where the blueDot is placed and attempts to
     * purchase the item.
     */
    private void handleUserPurchase() {
        buySelectedItemButton.setOnMouseClicked((MouseEvent event) -> {
            if (blueDot.getId() != null) {
                if (gameStore.buyItem(blueDot.getId(), humanPlayer)) {
                    animation.textAnimation(textArea, "Here you go!");
                } else {
                    animation.textAnimation(textArea, "You can't afford that!");
                }
            } else {
                animation.textAnimation(textArea, "You don't have anything selected!");
            }
        });
    }
}
