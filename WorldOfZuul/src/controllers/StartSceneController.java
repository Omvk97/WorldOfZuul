/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game_functionality.Game;
import game_functionality.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author steff
 */
public class StartSceneController implements Initializable {

    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button txtStartGameButton;
    @FXML
    private Button btnOptionhandler;
    @FXML
    private Button btnEXIT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void HandleStartGameAction(ActionEvent event) {
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
