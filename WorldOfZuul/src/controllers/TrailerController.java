package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.CommandWords;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Trailer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TrailerController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2, option3;
    @FXML
    private ImageView player, map, option4;
    private CommandWords commands;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Trailer gameTrailer = Game.getInstanceOfSelf().getTrailer();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(gameTrailer.roomEntrance(humanPlayer));
        option4.setRotate(45);
        File file = new File("src/pictures/baseCharacter.png");
        Image image = new Image(file.toURI().toString());
        player.setImage(image);
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameTrailer.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameTrailer.option2(humanPlayer));
    }

    @FXML
    private void handleOption3(MouseEvent event) {
        textArea.setText(gameTrailer.option3(humanPlayer));
    }

    @FXML
    private void handleOption4(MouseEvent event) {
        textArea.setText(gameTrailer.option4(humanPlayer));
    }

    @FXML
    private void handleExits(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
            case W: {
                Command tester = new Command(CommandWord.GO, "north");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                break;
            }
            case DOWN:
            case S: {
                Command tester = new Command(CommandWord.GO, "south");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                break;
            }
            case RIGHT:
            case D: {
                Command tester = new Command(CommandWord.GO, "village");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                break;
            }
            default:
                textArea.setText("There is no road that way!");
                break;
        }
    }
}
