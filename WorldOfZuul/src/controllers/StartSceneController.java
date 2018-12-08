package controllers;

import game_functionality.Game;
import game_functionality.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class StartSceneController implements Initializable {

    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void HandleStartGameAction(ActionEvent event) {
        System.out.println(humanPlayer.getCurrentRoom());
        anchorPane.getScene().setRoot(humanPlayer.getCurrentRoom().getRoomFXML());
    }

    @FXML
    private void ExitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handlerHighscore(MouseEvent event) {
    }

}