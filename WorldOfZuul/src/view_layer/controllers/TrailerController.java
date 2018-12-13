package view_layer.controllers;

import view_layer.HighScoreGraphics;
import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import domain_layer.game_locations.Trailer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
import view_layer.PlayerGraphics;

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
    private int devilCounter = 0, randomNum;
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        goToTrailerTransition();
        if (playerInteraction.isAxePickedUp()) {
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
        PlayerGraphics.getInstanceOfSelf().updateCharacterModel(player);
        option4.setImage(new Image(new File("src/pictures/starterAxe.png").toURI().toString()));
        if (!running) {
            switch (playerInteraction.getPlayerDirectionInWorld()) {
                case "goDown":
                    playerEnteringTrailerTransition(0, 170, player.getLayoutX(), 0);
                    break;
                case "goLeft":
                    playerEnteringTrailerTransition(-330, 0, anchorPane.getPrefWidth(), player.getLayoutY());
                    break;
                case "goUp":
                    playerEnteringTrailerTransition(0, -230, player.getLayoutX(), anchorPane.getPrefHeight());
                    break;
                default:
                    running = false;
                    break;
            }
        }
    }

    private void playerEnteringTrailerTransition(int translateX,
        int translateY,
        double setLayoutX,
        double setLayoutY) {
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

    /**
     * Stores inventory logs to trailer container and displays relevant text in the textArea in
     * trailer
     *
     * @param event This is an input event that occurs when a mouse is clicked
     */
    @FXML
    private void handleOption1(MouseEvent event) {
        if (!trailerPath.isVisible()) {
            textArea.setText(gameTrailer.storeLogs(humanPlayer));
        } else {
            textArea.setText("What?");
        }
    }

    /**
     * This method is supposed to provide sleep functionality while resetting day. The method also
     * checks if the player should receive a fine for not replanting trees in the certified forest
     *
     * @param event This is an input event that occurs when a mouse is clicked
     */
    @FXML
    private void handleOption3(MouseEvent event) {
        if (!running) {
            running = true;
            textArea.setText(gameTrailer.sleep(humanPlayer));
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

                if (playerInteraction.getNumChoppedTreesWithoutPlantingSaplings() != 0) {
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

    /**
     * Picks up the starter axe and removes it from the screen
     *
     * @param event This is an input event that occurs when a mouse is clicked
     */
    @FXML
    private void handleOption4(MouseEvent event) {
        textArea.setText(gameTrailer.pickUpStarterAxe(humanPlayer));
        PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(false,
            humanPlayer.getEquippedAxe(), player);
        anchorPane.getChildren().remove(option4);
        playerInteraction.setAxePickedUp(true);
    }

    /**
     * This method moves the player to the next room depending on the user input
     *
     * @param event This is an input event that indicates the key stroke occurred on a node
     */
    @FXML
    private void handleExits(KeyEvent event) {
        if (!running) {
            switch (event.getCode()) {
                case UP:
                case W: {
                    walkTransition("north", "goUp", 0, -170);
                    break;
                }
                case DOWN:
                case S: {
                    walkTransition("south", "goDown", 0, 170);
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
                    walkTransition("village", "goRight", 276, 0);
                    break;
                }
                default:
                    textArea.setText("There is no road that way!");
                    break;
            }
        }
    }

    /**
     * This method makes the trailer path to transition between rooms visible
     *
     * @param direction Determines what direction the player should move
     */
    private void walkTransition(String roomToGoTo, String direction, int translateX, int translateY) {
        option4.setVisible(false);
        running = true;
        Command roomToGo = new Command(CommandWord.GO, roomToGoTo);
        playerInteraction.setPlayerDirectionInWorld(direction);
        TranslateTransition directionTransition = new TranslateTransition(Duration.seconds(1.5), player);
        directionTransition.setByY(translateY);
        directionTransition.setByX(translateX);
        directionTransition.setOnFinished((ActionEvent event1) -> {
            Game.getInstanceOfSelf().goRoom(roomToGo, anchorPane);
            running = false;
        });

        if (!trailerPath.isVisible()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), trailerPath);
            trailerPath.setVisible(true);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setOnFinished((ActionEvent e) -> {
                player.setVisible(true);
                directionTransition.play();
            });
            fadeTransition.play();
        } else {
            player.setVisible(true);
            directionTransition.play();
        }
    }

    /**
     * This debug method is supposed to add a large amount of mouney
     *
     * @param event This is an input event that occurs when a mouse is clicked
     */
    @FXML
    private void giveMoneyForTesting(MouseEvent event) {
        if (devilCounter > 1) {
            humanPlayer.addMoney(9999);
            devilCounter = 0;
        } else {
            devilCounter += 1;
        }
    }

    /**
     * This method outputs a random question between 3 options after interaction with button event
     *
     * @param event This is an input event that occurs when an action from a button as been fired
     */
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

    /**
     * This method is supposed to evaluate the user's answer to the question outputted by
     * confirmButton and decides the fine the user has to pay based off a correct or incorrect
     * answer
     *
     * @param event This is an input event that occurs when an action from a textarea has been
     * entered
     */
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
                + "Your fine adds up to "
                + (playerInteraction.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200) + " gold coins");
            fineInput.setVisible(false);
            endButton.setVisible(true);
            humanPlayer.sleep(playerInteraction.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 200);
        } else {
            fineLabel.setText("Correct! Your fine has been cut in half! We also need you\n"
                + "to cover the cost of planting the trees that you forgot!\n"
                + "Total cost of "
                + (playerInteraction.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100) + " gold coins");
            fineInput.setVisible(false);
            endButton.setVisible(true);
            humanPlayer.sleep(playerInteraction.getNumChoppedTreesWithoutPlantingSaplings() * 8 + 100);
        }
    }

    /**
     * This button removes all contents addressing the fine function
     *
     * @param event This is an input event that occurs when an action from a button as been fired
     */
    @FXML
    private void handleEndButton(ActionEvent event) {
        fineScroll.setVisible(false);
        endButton.setVisible(false);
        fineLabel.setVisible(false);
    }
}
