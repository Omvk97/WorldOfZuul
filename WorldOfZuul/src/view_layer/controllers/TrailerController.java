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
 * @author daniel 
 * co-author oliver
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
    private int devilCounter = 0, randomNum;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goToTrailerTransition();
        if (humanPlayer.isAxePickedUp()) {
            anchorPane.getChildren().remove(option4);
        }
        if (trailerPath.isVisible()) {
            textArea.setText("You stand at a crossroad, you can go north, west, south or east.");
        } else {
            textArea.setText(gameTrailer.roomEntrance(humanPlayer));
        }

    }

    private void goToTrailerTransition() {
        player.setVisible(false);
        int daysLeftNum = gameTrailer.getNumOfDaysLeft();
        daysLeft.setText(daysLeftNum + (daysLeftNum == 1 ? " Day" : " Days") + " Left");
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        option4.setImage(new Image(new File("src/pictures/starterAxe.png").toURI().toString()));
        if (!running) {
            switch (Game.getInstanceOfSelf().getPlayerDirectionInWorld()) {
                case "goDown":
                    textArea.setVisible(false);
                    playerEnteringTrailerTransition(0, 170, player.getLayoutX(), 0);
                    break;
                case "goLeft":
                    textArea.setVisible(false);
                    playerEnteringTrailerTransition(-330, 0, anchorPane.getPrefWidth(), player.getLayoutY());
                    break;
                case "goUp":
                    textArea.setVisible(false);
                    playerEnteringTrailerTransition(0, -230, player.getLayoutX(), anchorPane.getPrefHeight());
                    break;
                default:
                    running = false;
                    break;
            }
        }
    }

    private void playerEnteringTrailerTransition(int translateX, int translateY, double setLayoutX, double setLayoutY) {
        running = true;
        player.setVisible(true);
        trailerPath.setVisible(true);
        option4.setVisible(false);
        TranslateTransition left = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutX(setLayoutX);
        player.setLayoutY(setLayoutY);
        left.setByX(translateX);
        left.setByY(translateY);
        left.setOnFinished((ActionEvent) -> {
            running = false;
        });
        left.play();
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
            if (gameTrailer.getNumOfDaysLeft() == 0) {
                daysLeft.setText("Goodbye");
                HighScoreGraphics highScoreDisplay = new HighScoreGraphics();
                highScoreDisplay.closeGame();
                System.exit(0);
            }
            FadeTransition sleep = new FadeTransition(Duration.seconds(3), anchorPane);
            sleep.setFromValue(1);
            sleep.setToValue(0.1);
            sleep.setCycleCount(2);
            sleep.setAutoReverse(true);
            sleep.play();
            sleep.setOnFinished((ActionEvent e) -> {
                running = false;
                int daysLeftNum = gameTrailer.getNumOfDaysLeft();
                daysLeft.setText(daysLeftNum + (daysLeftNum == 1 ? " Day" : " Days") + " Left");

                if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() != 0) {
                    fineLabel.setVisible(true);
                    fineLabel.setText("You didn't replant all the trees in the certified forest!\n"
                        + "Here's a chance to redeem yourself");
                    confirmButton.setVisible(true);
                    fineScroll.setVisible(true);
                }
                humanPlayer.sleep(0);
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
                    textArea.setVisible(false);
                    walkTransition("north", "goUp", 0, -170);
                    break;
                }
                case DOWN:
                case S: {
                    textArea.setVisible(false);
                    walkTransition("south", "goDown", 0, 170);
                    break;
                }
                case LEFT:
                case A: {
                    textArea.setVisible(true);
                    running = true;
                    player.setVisible(false);
                    option4.setVisible(true);
                    FadeTransition upFade = new FadeTransition(Duration.seconds(1.5), trailerPath);
                    FadeTransition axefade = new FadeTransition(Duration.seconds(1.5), option4);
                    upFade.setFromValue(1);
                    upFade.setToValue(0);
                    axefade.setFromValue(0);
                    axefade.setToValue(1);
                    upFade.setOnFinished((ActionEvent) -> {
                        running = false;
                        trailerPath.setVisible(false);
                        textArea.setText(gameTrailer.roomEntrance(humanPlayer));
                    });
                    axefade.play();
                    upFade.play();
                    break;
                }
                case RIGHT:
                case D: {
                    textArea.setVisible(false);
                    walkTransition("village", "goRight", 276, 0);
                    break;
                }
                default:
                    textArea.setText("There is no road that way!");
                    break;
            }
        }
    }

    private void walkTransition(String roomToGoTo, String direction, int translateX, int translateY) {
        running = true;
        Command roomToGo = new Command(CommandWord.GO, roomToGoTo);
        Game.getInstanceOfSelf().setPlayerDirectionInWorld(direction);
        TranslateTransition directionTransition = new TranslateTransition(Duration.seconds(1.5), player);
        directionTransition.setByY(translateY);
        directionTransition.setByX(translateX);
        directionTransition.setOnFinished((ActionEvent event1) -> {
            Game.getInstanceOfSelf().goRoom(roomToGo, anchorPane);
            running = false;
        });
        
        if (!trailerPath.isVisible()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), trailerPath);
            FadeTransition axeFadeTransition = new FadeTransition(Duration.seconds(1.5), option4);
            trailerPath.setVisible(true);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            axeFadeTransition.setFromValue(1);
            axeFadeTransition.setToValue(0);
            fadeTransition.setOnFinished((ActionEvent e) -> {
                player.setVisible(true);
                option4.setVisible(false);
                directionTransition.play();
            });
            axeFadeTransition.play();
            fadeTransition.play();
        } else {
            player.setVisible(true);
            directionTransition.play();
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
}
