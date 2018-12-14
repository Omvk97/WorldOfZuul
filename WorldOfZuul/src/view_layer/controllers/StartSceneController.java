package view_layer.controllers;

import view_layer.HighScoreGraphics;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author steffen This controller handles the start game Exit game and highscoreboard.
 *
 */
public class StartSceneController implements Initializable {

    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final HighScoreGraphics highScoreOverView = new HighScoreGraphics();

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void HandleStartGameAction(ActionEvent event) {
        anchorPane.getScene().setRoot(PlayerInteraction.getInstanceOfSelf().getCurrentRoom().getRoomFXML());
    }

    @FXML
    private void ExitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handlerHighscore(MouseEvent event) {
        highScoreOverView.highScoreOverViewDialog();
    }

}
