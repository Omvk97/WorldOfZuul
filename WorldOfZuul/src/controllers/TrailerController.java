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
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TrailerController implements Initializable {

    @FXML
    private Label textArea, daysLeftLabel;
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
        System.out.println(Game.getInstanceOfSelf().getDirection());

//        if (!running) {
        switch (Game.getInstanceOfSelf().getDirection()) {
            case "goDown":
                running = true;
                TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                player.setLayoutY(0);
                down.setByY(170);
                down.setOnFinished((ActionEvent e) -> {
                    running = false;
                });
                down.play();
                break;
            case "goLeft":
                running = true;
                TranslateTransition left = new TranslateTransition(Duration.seconds(1.5), player);
                player.setLayoutX(2 * player.getLayoutX() - 70);
                left.setByX(-206);
                left.setOnFinished((ActionEvent e) -> {
                    running = false;
                });
                left.play();
                break;
            case "goUp":
                running = true;
                TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
                player.setLayoutY(player.getLayoutY() * 2);
                up.setByY(-170);
                up.setOnFinished((ActionEvent e) -> {
                    running = false;
                });
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
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
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
    private void handleDoor(MouseEvent event) {
        Command tester = new Command(CommandWord.GO, "village");
                    running = true;
                    Game.getInstanceOfSelf().setDirection("goRight");
                    TranslateTransition right = new TranslateTransition(Duration.seconds(1.5), player);
                    right.setByX(player.getLayoutX() - 70);
                    right.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    right.play();
    }

    @FXML
    private void handleOption3(MouseEvent event) {
        if (!running) {
            running = true;
            textArea.setText(gameTrailer.option3(humanPlayer));
            FadeTransition sleep = new FadeTransition(Duration.seconds(3), anchorPane);
            sleep.setFromValue(1);
            sleep.setToValue(0.1);
            sleep.setCycleCount(2);
            sleep.setAutoReverse(true);
            sleep.play();
            sleep.setOnFinished((ActionEvent e) -> {
                daysLeftLabel.setText(gameTrailer.getNUM_PLAY_DAYS() - gameTrailer.getNumOfDaysGoneBy() + " days left");
                running = false;
            });
        }
        
        if (true /* En metode fra trailer der validere om spilleren skal have en bøde*/) {
            TextField tester = new TextField("Indtast din tekst her");
            Button hello = new Button("CLICK ME NIGGA");
            hello.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(tester.getText());
                    tester.clear();
                }
            });
            anchorPane.getChildren().add(new ImageView(new Image(new File("src/pictures/fine.png").toURI().toString())));
            anchorPane.getChildren().add(new Label("Tekst her"/*Det spørgsmål der nu skal være her.*/));
            anchorPane.getChildren().addAll(tester, hello);
        }
    }

    @FXML
    private void handleOption4(MouseEvent event) {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), player);
        transition.setByX(option4.getLayoutX() - player.getLayoutX());

        transition.setOnFinished((ActionEvent event1) -> {
            textArea.setText(gameTrailer.option4(humanPlayer));
            humanPlayer.setCharacterModel(false);
            player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
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
                    Game.getInstanceOfSelf().setDirection("goUp");
                    TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
                    up.setByY(-player.getLayoutY());
                    up.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    up.play();
                    break;
                }
                case DOWN:
                case S: {
                    Command tester = new Command(CommandWord.GO, "south");
                    running = true;
                    Game.getInstanceOfSelf().setDirection("goDown");
                    TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                    down.setByY(player.getLayoutY());
                    down.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    down.play();
                    break;
                }
                case RIGHT:
                case D: {
                    Command tester = new Command(CommandWord.GO, "village");
                    running = true;
                    Game.getInstanceOfSelf().setDirection("goRight");
                    TranslateTransition right = new TranslateTransition(Duration.seconds(1.5), player);
                    right.setByX(player.getLayoutX() - 70);
                    right.setOnFinished((ActionEvent e) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    right.play();
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
