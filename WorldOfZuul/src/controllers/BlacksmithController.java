package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.BlackSmith;
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
import javafx.scene.transform.Translate;
import javafx.util.Duration;

public class BlacksmithController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2, backBtn;
    @FXML
    private ImageView player, map;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final BlackSmith gameBlacksmith = (BlackSmith) Game.getInstanceOfSelf().getBlacksmith();
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        running = true;
        backBtn.setDisable(true);
        transition();
        textArea.setText(gameBlacksmith.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));

    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameBlacksmith.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameBlacksmith.option2(humanPlayer));
    }

    @FXML
    private void handleBackBtn(MouseEvent event) {
        backBtn.setDisable(true);
        if (!running) {
            running = true;
            TranslateTransition transistionFromBlacksmith = new TranslateTransition(Duration.seconds(1.5), player);
            transistionFromBlacksmith.setByY(player.getLayoutY());
            transistionFromBlacksmith.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "back");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionFromBlacksmith.play();
        }
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!running) {
            running = true;
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                TranslateTransition transistionFromBlacksmith = new TranslateTransition(Duration.seconds(1.5), player);
                transistionFromBlacksmith.setByY(player.getLayoutY());
                transistionFromBlacksmith.setOnFinished((ActionEvent) -> {
                    Command tester = new Command(CommandWord.GO, "back");
                    Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                });
                transistionFromBlacksmith.play();
            } else {
                textArea.setText("There is no road!");
            }
        }
    }
    
    private void transition() {
        TranslateTransition roomTransition = new TranslateTransition(Duration.seconds(1.5), player);
        if (Game.getInstanceOfSelf().getDirection().equals("goBlacksmith")) {
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
