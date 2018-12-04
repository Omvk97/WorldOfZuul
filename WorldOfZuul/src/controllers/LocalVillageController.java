package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.LocalVillage;
import java.net.URL;
import java.util.ResourceBundle;
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

public class LocalVillageController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2;
    @FXML
    private ImageView player, map;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final LocalVillage gameVillage = (LocalVillage) Game.getInstanceOfSelf().getLocalVillage();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(gameVillage.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));

    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameVillage.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameVillage.option2(humanPlayer));
    }

    @FXML
    private void handleGoToStore(MouseEvent event) {
        Command tester = new Command(CommandWord.GO, "store");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }

    @FXML
    private void handleGoToBlacksmith(MouseEvent event) {
        Command tester = new Command(CommandWord.GO, "blacksmith");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }
    
        @FXML
    private void handleGoToLibrary(MouseEvent event) {
        Command tester = new Command(CommandWord.GO, "library");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
            Command tester = new Command(CommandWord.GO, "trailer");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            textArea.setText("There is no road!");
        }
    }
}
