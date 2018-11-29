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
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        if (!running) {
            switch (Game.getInstanceOfSelf().getDirection()) {
                case "goDown":
                    running = true;
                    TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                    player.setLayoutY(0);
                    down.setByY(170);
                    down.setOnFinished((ActionEvent e) -> {running = false;});
                    down.play();
                    break;
                case "goLeft":
                    running = true;
                    TranslateTransition left = new TranslateTransition(Duration.seconds(1.5), player);
                    player.setLayoutX(2 * player.getLayoutX() - 70);
                    left.setByX(-206);
                    left.setOnFinished((ActionEvent e) -> {running = false;});
                    left.play();
                    break;
                case "goUp":
                    running = true;
                    TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
                    player.setLayoutY(player.getLayoutY() * 2);
                    up.setByY(-170);
                    up.setOnFinished((ActionEvent e) -> {running = false;});
                    up.play();
                    break;
                default:
                    running = false;
                    break;
            }
//        } else {
//            System.out.println("ayo wait up");
//        }
        textArea.setText(gameTrailer.roomEntrance(humanPlayer));
        option4.setRotate(45);
        File characterFilePlacement = new File("src/pictures/baseCharacter.png");
        Image character = new Image(characterFilePlacement.toURI().toString());
        player.setImage(character);
        File starterAxeFilePlacement = new File("src/pictures/starterAxe.png");
        Image starterAxe = new Image(starterAxeFilePlacement.toURI().toString());
        option4.setImage(starterAxe);
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
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), player);
        transition.setByX(option4.getLayoutX() - player.getLayoutX());

        transition.setOnFinished((ActionEvent event1) -> {
            File file = new File("src/pictures/characterModelWithStarterAxe.png");
            Image characterWithStarterAxePlacement = new Image(file.toURI().toString());
            player.setImage(characterWithStarterAxePlacement);
            textArea.setText(gameTrailer.option4(humanPlayer));
        });

        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1.5), player);
        transition2.setByX(player.getLayoutX() - option4.getLayoutX());

        SequentialTransition axeTransition = new SequentialTransition(transition, transition2);
        axeTransition.play();
    }

    @FXML
    private void handleExits(KeyEvent event) {

        if (!running) {
            switch (event.getCode()) {
                case UP:
                case W: {
                    Command tester = new Command(CommandWord.GO, "north");
                    running = true;
                    TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
                    up.setByY(-player.getLayoutY());
                    up.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    up.play();
                    Game.getInstanceOfSelf().setDirection("goUp");
                    break;
                }
                case DOWN:
                case S: {
                    Command tester = new Command(CommandWord.GO, "south");
                    running = true;
                    TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                    down.setByY(player.getLayoutY());
                    down.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    down.play();
                    Game.getInstanceOfSelf().setDirection("goDown");
                    break;
                }
                case RIGHT:
                case D: {
                    Command tester = new Command(CommandWord.GO, "village");
                    running = true;
                    TranslateTransition right = new TranslateTransition(Duration.seconds(1.5), player);
                    right.setByX(player.getLayoutX() - 70);
                    right.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    right.play();
                    Game.getInstanceOfSelf().setDirection("goRight");
                    break;
                }
                default:
                    textArea.setText("There is no road that way!");
                    break;
            }
        } else {
            System.out.println("ayo wait up");
        }

    }
}
