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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TrailerController implements Initializable {

    @FXML
    private Label textArea, daysLeftLabel, climateLabel, logsStorageLabel;
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
        climateLabel.setText(humanPlayer.getClimatePoints() + " climate");
        logsStorageLabel.setText(gameTrailer.getLogsInStorage().size() + " logs");
        if (humanPlayer.getClimatePoints() <= -50) {
            climateLabel.setTextFill(Color.RED);
        }

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
                    System.out.println(player.getLayoutX());
                    System.out.println(player.getLayoutY());
                    player.relocate(482, 170);
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
        logsStorageLabel.setText(gameTrailer.getLogsInStorage().size() + " logs");
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameTrailer.option2(humanPlayer));
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

//                 if (true /* En metode fra trailer der validere om spilleren skal have en bøde*/) {
                String questionOne = "How many million hectare forest area disappear each year?";
                String questionTwo = "How many million hectare forest area does FSC cover over?";
                String questionThree = "How many million hectare forest area does PEFC cover over?";
                int randomNum = (int) (Math.random() * 3) + 1;

                TextField tester = new TextField();
                tester.setAlignment(Pos.CENTER);
                tester.setLayoutX(160);
                tester.setLayoutY(240);

                Label fineLabel = new Label("You didn't replant all the trees in the certified forest!\n"
                        + "Here's a chance to redeem yourself");
                fineLabel.setAlignment(Pos.CENTER);
                fineLabel.setLayoutX(160);
                fineLabel.setLayoutY(170);

                Button hello = new Button("Ok");
                hello.setLayoutX(270);
                hello.setLayoutY(240);
//                hello.toFront();

                ImageView fineScroll = new ImageView(new Image(new File("src/pictures/fine.png").toURI().toString()));
                fineScroll.setLayoutX(anchorPane.getWidth() / 4);
                fineScroll.setLayoutY(anchorPane.getHeight() / 4);

                hello.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Boolean correctAnswer = true;
                        switch (randomNum) {
                            case 1:
                                fineLabel.setText(questionOne);
                                anchorPane.getChildren().remove(hello);
                                anchorPane.getChildren().add(tester);
                                correctAnswer = gameTrailer.answerValidation(tester.getText(), "7");
                                break;
                            case 2:
                                fineLabel.setText(questionTwo);
                                anchorPane.getChildren().remove(hello);
                                anchorPane.getChildren().add(tester);
                                correctAnswer = gameTrailer.answerValidation(tester.getText(), "200");
                                break;
                            case 3:
                                fineLabel.setText(questionThree);
                                anchorPane.getChildren().remove(hello);
                                anchorPane.getChildren().add(tester);
                                correctAnswer = gameTrailer.answerValidation(tester.getText(), "300");
                                break;
                            default:
                                System.out.println("error");
                                break;
                        }
                        tester.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                System.out.println(tester.getText());
                            }
                        });
                        if (!correctAnswer) {
                            fineLabel.setText("WRONG, study in the library!");
                        }
                        System.out.println(tester.getText());
                        tester.clear();
                    }

                });

//                    anchorPane.getChildren().add(new ImageView(new Image(new File("src/pictures/fine.png").toURI().toString())));
//                    anchorPane.getChildren().add(new Label("Tekst her"/*Det spørgsmål der nu skal være her.*/));
                anchorPane.getChildren().addAll(fineScroll, fineLabel, hello);
            });
        }
    }

    @FXML
    private void handleOption4(MouseEvent event) {
        if (!running) {
            running = true;
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), player);
            transition.setByX(-231);

            transition.setOnFinished((ActionEvent event1) -> {
                File file = new File("src/pictures/characterModelWithStarterAxe.png");
                Image characterWithStarterAxePlacement = new Image(file.toURI().toString());
                player.setImage(characterWithStarterAxePlacement);
                textArea.setText(gameTrailer.option4(humanPlayer));
            });

            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1.5), player);
            transition2.setByX(231);

            SequentialTransition axeTransition = new SequentialTransition(transition, transition2);
            axeTransition.play();
            axeTransition.setOnFinished((ActionEvent e) -> {
                running = false;
            });
        }
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
                    up.setByY(-170);
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
                    down.setByY(170);
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
                    right.setByX(206);
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
