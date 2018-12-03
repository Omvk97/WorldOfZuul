package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Store;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class StoreController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2, backBtn;
    @FXML
    private ImageView player, map;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Store gameStore = (Store) Game.getInstanceOfSelf().getStore();
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        running = true;
        backBtn.setDisable(true);
        transition();
        textArea.setText(gameStore.roomEntrance(humanPlayer));
        File file = new File("src/pictures/baseCharacter.png");
        Image image = new Image(file.toURI().toString());
        player.setImage(image);
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameStore.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameStore.option2(humanPlayer));
    }

    @FXML
    private void handleBackBtn(MouseEvent event) {
        backBtn.setDisable(true);
        if (!running) {
            running = true;
            TranslateTransition transistionFromStore = new TranslateTransition(Duration.seconds(1.5), player);
            transistionFromStore.setByY(player.getLayoutY());
            transistionFromStore.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "back");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionFromStore.play();
        }
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!running) {
            running = true;
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                TranslateTransition transistionFromStore = new TranslateTransition(Duration.seconds(1.5), player);
                transistionFromStore.setByY(player.getLayoutY());
                transistionFromStore.setOnFinished((ActionEvent) -> {
                    Command tester = new Command(CommandWord.GO, "back");
                    Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                });
                transistionFromStore.play();
            } else {
                textArea.setText("There is no road!");
            }
        }
    }

    private void transition() {
        TranslateTransition roomTransition = new TranslateTransition(Duration.seconds(1.5), player);
        if (Game.getInstanceOfSelf().getDirection().equals("goStore")) {
            player.setLayoutY(player.getLayoutY() * 2);
            roomTransition.setByY(-170);
            roomTransition.setOnFinished((ActionEvent) -> {
                running = false;
                backBtn.setDisable(false);
            });
            roomTransition.play();
        }
    }
}
