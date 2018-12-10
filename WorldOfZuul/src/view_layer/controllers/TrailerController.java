package view_layer.controllers;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.Trailer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author daniel
 * co-author oliver
 */
public class TrailerController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView player, option4, trailerPath;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Trailer gameTrailer = Game.getInstanceOfSelf().getTrailer();
    private boolean running;
    private int devilCounter = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player.setVisible(false);
        if (!running) {
            switch (Game.getInstanceOfSelf().getDirection()) {
                case "goDown":
                    goDownTransition();
                    break;
                case "goLeft":
                    goLeftTransition();
                    break;
                case "goUp":
                    goUpTransition();
                    break;
                default:
                    running = false;
                    break;
            }
        }
        textArea.setText(gameTrailer.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        option4.setImage(new Image(new File("src/pictures/starterAxe.png").toURI().toString()));
        if (humanPlayer.isAxePickedUp()) {
            anchorPane.getChildren().remove(option4);
        }
    }

    private void goDownTransition() {
        option4.setVisible(false);
        running = true;
        player.setVisible(true);
        trailerPath.setVisible(true);
        TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutY(0);
        down.setByY(170);
        down.setOnFinished((ActionEvent) -> {
            running = false;
        });
        down.play();
    }

    private void goLeftTransition() {
        running = true;
        player.setVisible(true);
        trailerPath.setVisible(true);
        option4.setVisible(false);
        TranslateTransition left = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutX(2 * player.getLayoutX() - 70);
        left.setByX(-206);
        left.setOnFinished((ActionEvent) -> {
            running = false;
        });
        left.play();
    }

    private void goUpTransition() {
        running = true;
        player.setVisible(true);
        trailerPath.setVisible(true);
        option4.setVisible(false);
        TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutY(player.getLayoutY() * 2);
        up.setByY(-170);
        up.setOnFinished((ActionEvent) -> {
            running = false;
        });
        up.play();
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
                running = false;
                if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() != 0) {
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

                    Button endButton = new Button("Ok");
                    endButton.setLayoutX(270);
                    endButton.setLayoutY(240);

                    ImageView fineScroll = new ImageView(new Image(new File("src/pictures/fine.png").toURI().toString()));
                    fineScroll.setLayoutX(anchorPane.getWidth() / 4);
                    fineScroll.setLayoutY(anchorPane.getHeight() / 4);
                    anchorPane.getChildren().addAll(fineScroll, fineLabel, hello);
                    hello.setOnAction((ActionEvent event1) -> {
                        switch (randomNum) {
                            case 1:
                                fineLabel.setText(questionOne);
                                anchorPane.getChildren().remove(hello);
                                anchorPane.getChildren().add(tester);
                                break;
                            case 2:
                                fineLabel.setText(questionTwo);
                                anchorPane.getChildren().remove(hello);
                                anchorPane.getChildren().add(tester);
                                break;
                            case 3:
                                fineLabel.setText(questionThree);
                                anchorPane.getChildren().remove(hello);
                                anchorPane.getChildren().add(tester);
                                break;
                            default:
                                System.out.println("error");
                                break;
                        }
                    });
                    tester.setOnAction((ActionEvent event1) -> {
                        Boolean correctAnswer = true;
                        switch (randomNum) {
                            case 1:
                                correctAnswer = gameTrailer.answerValidation(tester.getText(), "7");
                                break;
                            case 2:
                                correctAnswer = gameTrailer.answerValidation(tester.getText(), "200");
                                break;
                            case 3:
                                correctAnswer = gameTrailer.answerValidation(tester.getText(), "300");
                                break;
                            default:
                                break;
                        }
                        if (!correctAnswer) {
                            fineLabel.setText("WRONG, study in the library!\n"
                                + "We also need you to cover the cost of planting the trees that you forgot!\n"
                                + "Your fine adds up to " + (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200) + " gold coins");
                            anchorPane.getChildren().remove(tester);
                            anchorPane.getChildren().add(endButton);
                            humanPlayer.sleep(humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200);
                        } else {
                            fineLabel.setText("Correct! Your fine has been cut in half! We also need you\n"
                                + "to cover the cost of planting the trees that you forgot!\n"
                                + "Total cost of " + (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100) + " gold coins");
                            anchorPane.getChildren().remove(tester);
                            anchorPane.getChildren().add(endButton);
                            humanPlayer.sleep(humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100);
                        }
                        System.out.println(tester.getText());
                        tester.clear();
                    });

                    endButton.setOnAction((ActionEvent event1) -> {
                        anchorPane.getChildren().removeAll(endButton, fineScroll, fineLabel);
                    });
                }
            });
        }
    }

    @FXML
    private void handleOption4(MouseEvent event) {
        textArea.setText(gameTrailer.option4(humanPlayer));
        humanPlayer.setCharacterModel(false);
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        anchorPane.getChildren().remove(option4);
        humanPlayer.setAxePickedUp(true);
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!running) {
            switch (event.getCode()) {
                case UP:
                case W: {
                    running = true;
                    Command tester = new Command(CommandWord.GO, "north");
                    Game.getInstanceOfSelf().setDirection("goUp");
                    TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
                    up.setByY(-170);
                    up.setOnFinished((ActionEvent event1) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    walkTransition(up);
                    break;
                }

                case DOWN:
                case S: {
                    running = true;
                    Command tester = new Command(CommandWord.GO, "south");
                    Game.getInstanceOfSelf().setDirection("goDown");
                    TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                    down.setByY(170);
                    down.setOnFinished((ActionEvent event1) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    walkTransition(down);
                    break;
                }
                case LEFT:
                case A: {
                    running = true;
                    player.setVisible(false);
                    option4.setVisible(true);
                    FadeTransition upFade = new FadeTransition(Duration.seconds(1.5), trailerPath);
                    upFade.setFromValue(1);
                    upFade.setToValue(0);
                    upFade.setOnFinished((ActionEvent) -> {
                        running = false;
                        trailerPath.setVisible(false);
                    });
                    upFade.play();
                    break;
                }
                case RIGHT:
                case D: {
                    running = true;
                    Command tester = new Command(CommandWord.GO, "village");
                    Game.getInstanceOfSelf().setDirection("goRight");
                    TranslateTransition right = new TranslateTransition(Duration.seconds(1.5), player);
                    right.setByX(276);
                    right.setOnFinished((ActionEvent event1) -> {
                        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                        running = false;
                    });
                    walkTransition(right);
                    break;
                }
                default:
                    textArea.setText("There is no road that way!");
                    break;
            }
        }
    }

    private void walkTransition(Transition direction) {
        option4.setVisible(false);
        if (!trailerPath.isVisible()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), trailerPath);
            trailerPath.setVisible(true);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setOnFinished((ActionEvent e) -> {
                player.setVisible(true);
                direction.play();
            });
            fadeTransition.play();
        } else {
            player.setVisible(true);
            direction.play();
        }
    }

    @FXML
    private void giveMoneyForTesting(MouseEvent event
    ) {
        if (devilCounter > 1) {
            humanPlayer.addMoney(9999);
            devilCounter = 0;
        } else {
            devilCounter += 1;
        }
    }
}
