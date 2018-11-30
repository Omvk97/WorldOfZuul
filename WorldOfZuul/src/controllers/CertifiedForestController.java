package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.CertifiedForest;
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

public class CertifiedForestController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2;
    @FXML
    private ImageView player, map;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final CertifiedForest gameForest = (CertifiedForest) Game.getInstanceOfSelf().getCertifiedForest();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(Game.getInstanceOfSelf().getDirection());
        TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutY(player.getLayoutY() * 2);
        up.setByY(-170);
        up.play();
        textArea.setText(gameForest.roomEntrance(humanPlayer));
        File file = new File("src/pictures/baseCharacter.png");
        Image image = new Image(file.toURI().toString());
        player.setImage(image);
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameForest.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameForest.option2(humanPlayer));
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
            Game.getInstanceOfSelf().setDirection("goUp");
            Command tester = new Command(CommandWord.GO, "trailer");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            System.out.println(Game.getInstanceOfSelf().getDirection());
        } else {
            textArea.setText("There is no road!");
        }
    }
}
