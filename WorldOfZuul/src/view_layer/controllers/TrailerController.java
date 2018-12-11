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
 * @author daniel co-author oliver
 */
public class TrailerController implements Initializable {

    @FXML
    private Label textArea, fineLabel, daysLeft;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView player, option4, trailerPath, fineScroll;
    @FXML
    private Button confirmButton, endButton;
    @FXML
    private TextField fineInput;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Trailer gameTrailer = Game.getInstanceOfSelf().getTrailer();
    private boolean running;
    private int devilCounter = 0, days = 1, randomNum;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player.setVisible(false);
        int daysLeftNum = humanPlayer.getNumOfDaysLeft();
        daysLeft.setText(daysLeftNum + (daysLeftNum == 1 ? " Day" : " Days") + " Left");
        daysLeftListener();
        if (!running) {
            switch (Game.getInstanceOfSelf().getPlayerDirectionInWorld()) {
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
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        option4.setImage(new Image(new File("src/pictures/starterAxe.png").toURI().toString()));
        if (humanPlayer.isAxePickedUp()) {
            anchorPane.getChildren().remove(option4);
        }
        if (trailerPath.isVisible()) {
            textArea.setText("You stand at a crossroad, you can go north, west, south or east.");
        } else {
            textArea.setText(gameTrailer.roomEntrance(humanPlayer));
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
        if (!trailerPath.isVisible()) {
            textArea.setText(gameTrailer.option1(humanPlayer));
        } else {
            textArea.setText("What?");
        }
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
                days++;
                humanPlayer.getNumOfDaysgoneBy().set(days);
                if (humanPlayer.getNumOfDaysLeft() < 0) {
                    HighScoreGraphics highScoreDisplay = new HighScoreGraphics();
                    highScoreDisplay.closeGame();
                }
                if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() != 0) {

                    fineLabel.setVisible(true);
                    fineLabel.setText("You didn't replant all the trees in the certified forest!\n"
                        + "Here's a chance to redeem yourself");
                    confirmButton.setVisible(true);
                    fineScroll.setVisible(true);
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
                    Game.getInstanceOfSelf().setPlayerDirectionInWorld("goUp");
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
                    Game.getInstanceOfSelf().setPlayerDirectionInWorld("goDown");
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
                        textArea.setText(gameTrailer.roomEntrance(humanPlayer));
                    });
                    upFade.play();
                    break;
                }
                case RIGHT:
                case D: {
                    running = true;
                    Command tester = new Command(CommandWord.GO, "village");
                    Game.getInstanceOfSelf().setPlayerDirectionInWorld("goRight");
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
    private void giveMoneyForTesting(MouseEvent event) {
        if (devilCounter > 1) {
            humanPlayer.addMoney(9999);
            devilCounter = 0;
        } else {
            devilCounter += 1;
        }
    }

    @FXML
    private void handleConfirmButton(ActionEvent event) {

        String questionOne = "How many million hectare forest area disappear each year?";
        String questionTwo = "How many million hectare forest area does FSC cover over?";
        String questionThree = "How many million hectare forest area does PEFC cover over?";

        randomNum = (int) (Math.random() * 3) + 1;
        switch (randomNum) {
            case 1:
                fineLabel.setText(questionOne);
                confirmButton.setVisible(false);
                fineInput.setVisible(true);
                this.randomNum = 1;
                break;
            case 2:
                fineLabel.setText(questionTwo);
                confirmButton.setVisible(false);
                fineInput.setVisible(true);
                this.randomNum = 2;
                break;
            case 3:
                fineLabel.setText(questionThree);
                confirmButton.setVisible(false);
                fineInput.setVisible(true);
                this.randomNum = 3;
                break;
            default:
                break;
        }
    }

    @FXML
    private void handleFineInput(ActionEvent event) {
        Boolean correctAnswer = true;
        switch (randomNum) {
            case 1:
                correctAnswer = gameTrailer.answerValidation(fineInput.getText(), "7");
                break;
            case 2:
                correctAnswer = gameTrailer.answerValidation(fineInput.getText(), "200");
                break;
            case 3:
                correctAnswer = gameTrailer.answerValidation(fineInput.getText(), "300");
                break;
            default:
                break;
        }
        if (!correctAnswer) {
            fineLabel.setText("WRONG, study in the library!\n"
                + "We also need you to cover the cost of planting the trees that you forgot!\n"
                + "Your fine adds up to " + (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200) + " gold coins");
            fineInput.setVisible(false);
            endButton.setVisible(true);
            humanPlayer.sleep(humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200);
        } else {
            fineLabel.setText("Correct! Your fine has been cut in half! We also need you\n"
                + "to cover the cost of planting the trees that you forgot!\n"
                + "Total cost of " + (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100) + " gold coins");
            fineInput.setVisible(false);
            endButton.setVisible(true);
            humanPlayer.sleep(humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100);
        }
    }

    @FXML
    private void handleEndButton(ActionEvent event) {
        fineScroll.setVisible(false);
        endButton.setVisible(false);
        fineLabel.setVisible(false);
    }

    private void daysLeftListener() {
        humanPlayer.getNumOfDaysgoneBy().addListener((observable, oldValue, newValue) -> {
            int daysLeftNum = humanPlayer.getNumOfDaysLeft();
            daysLeft.setText(daysLeftNum + (daysLeftNum == 1 ? " Day" : " Days") + " Left");
        });
    }

}
