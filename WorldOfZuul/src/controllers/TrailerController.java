package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.CommandWords;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Trailer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

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
        System.out.println(option4.getTranslateX());
        System.out.println(option4.getTranslateY());
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
        Line circle = new Line(player.getLayoutX(), player.getLayoutY(), option4.getLayoutX(), option4.getLayoutY());
        PathTransition transition = new PathTransition();
        transition.setNode(player);
        transition.setDuration(Duration.seconds(3));
        transition.setPath(circle);
        transition.setCycleCount(1);
        transition.play();
        textArea.setText(gameTrailer.option4(humanPlayer));

    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
            Command tester = new Command(CommandWord.GO, "north");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Command tester = new Command(CommandWord.GO, "south");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
            Command tester = new Command(CommandWord.GO, "village");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            textArea.setText("There is no road that way!");
        }
    }
}
