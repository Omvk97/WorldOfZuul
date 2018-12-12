package view_layer.controllers;

import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.LocalVillage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view_layer.LocalVillageAnimation;

/**
 *
 * @author michael
 */
public class LocalVillageController implements Initializable {

    private static final int SLOW = 6;
    private static final int MEDIUM = 4;
    private static final int FAST = 2;
    @FXML
    private Label firstVisitText, villagerText;
    @FXML
    private AnchorPane mainAnchorPane, villageGiftAndScenario, firstVisitGreatings;
    @FXML
    private Button backToPreviousRoomButton, firstVisitContinueButton, scenarioContinueButton, talkToVillageButton;
    @FXML
    private ImageView player, sun;
    @FXML
    private Rectangle store, blacksmith, library, blacksmithEntrance, storeEntrance, libraryEntrance, anchorpoint1, anchorpoint2;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final LocalVillage gameVillage = (LocalVillage) Game.getInstanceOfSelf().getLocalVillage();
    private boolean running;
    private int playerEntranceCoordinatX, playerEntranceCoordinatY, currentDialouge;
    private final LocalVillageAnimation animation;
    
    public LocalVillageController() {
        this.animation = new LocalVillageAnimation.Builder(player)
            .withAnchorPoint1(anchorpoint1)
            .withAnchorPoint2(anchorpoint2)
            .withGame(Game.getInstanceOfSelf())
            .withMainAnchorPane(mainAnchorPane)
            .build();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        running = true;
        backToPreviousRoomButton.setVisible(true);
        transition();
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        choosingRainScenario();
    }

    private void choosingRainScenario() {
        switch (getClimateScenario()) {
            case -1:
                rainDrops(70, SLOW);
                break;
            case -2:
                rainDrops(150, SLOW);
                break;
            case -3:
                rainDrops(250, MEDIUM);
                break;
            case -4:
                rainDrops(350, MEDIUM);
                break;
            case -5:
                rainDrops(450, FAST);
                break;
            default:
                sun.setVisible(true);
        }
    }

    private void rainDrops(int numOfRainDropsOnScreen, int rainDropSpeed) {
        ImageView[] rainDrops = new ImageView[numOfRainDropsOnScreen];
        for (int i = 0; i < rainDrops.length; i++) {
            rainDrops[i] = new ImageView(new Image(new File("src/pictures/rain.png").toURI().toString()));
            mainAnchorPane.getChildren().add(rainDrops[i]);
            makeItRain(rainDrops[i], rainDropSpeed);
        }
    }

    private int getClimateScenario() {
        int climatePoints = humanPlayer.getClimatePointsValue();
        if (climatePoints < 0 && climatePoints > -19) {
            return -1;
        } else if (climatePoints < -19 && climatePoints > -29) {
            return -2;
        } else if (climatePoints < -29 && climatePoints > -39) {
            return -3;
        } else if (climatePoints < -39 && climatePoints > -49) {
            return -4;
        } else if (climatePoints < -49) {
            return -5;
        }
        return 0;
    }

    private void makeItRain(ImageView rain, int rainDropSpeed) {
        rain.setTranslateX((Math.random() * 620) + 1);
        rain.setTranslateY((Math.random() * 320) + 1);
        int timeToGoToBottom = (int) (420 - rain.getTranslateY()) * rainDropSpeed;
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(timeToGoToBottom), new KeyValue(rain.translateYProperty(), 400)));
        timeline.setOnFinished((ActionEvent event) -> {
            makeItRain(rain, rainDropSpeed);
        });
        timeline.play();
    }

    @FXML
    private void handleTalkToVillageButton(MouseEvent event) {
        scenario();
    }

    @FXML
    private void handleBackToPreviousButton(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        if (!running) {
            running = true;

            switch (Game.getInstanceOfSelf().getPlayerDirectionInWorld()) {
                case "goRight":
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, 0, "goLeft", "trailer", true);
                    break;
                case "goStore":
                    running = true;
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, storeEntrance.getLayoutX(), "goStore", "store", false);
                    break;
                case "goBlacksmith":
                    running = true;
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, blacksmith.getLayoutX(), "goBlacksmith", "blacksmith", true);
                    break;
                case "goLibrary":
                    running = true;
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, libraryEntrance.getLayoutX(), "goLibrary", "library", false);
                    break;
            }
        }
    }

    @FXML
    private void handleGoToStore(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!running) {
            running = true;
            animation.goToTransitionHandle(playerEntranceCoordinatX / 2, storeEntrance.getLayoutX(),
                "goStore", "store", false);
        }
    }

    @FXML
    private void handleGoToBlacksmith(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!running) {
            running = true;
            animation.goToTransitionHandle(playerEntranceCoordinatX / 2, blacksmithEntrance.getLayoutX(),
                "goBlacksmith", "blacksmith", true);
        }
    }

    @FXML
    private void handleGoToLibrary(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!running) {
            running = true;
            animation.goToTransitionHandle(playerEntranceCoordinatX / 2, libraryEntrance.getLayoutX(),
                "goLibrary", "library", false);
        }
    }

    @FXML
    private void handleExits(KeyEvent event) {
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!running) {
            if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                running = true;
                animation.goToTransitionHandle(playerEntranceCoordinatX / 2, 0, "goLeft", "trailer", true);
            } else {
            }
        }
    }

    private void transition() {
        playerEntranceCoordinatX = (int) mainAnchorPane.getPrefWidth() / 2;
        playerEntranceCoordinatY = ((int) mainAnchorPane.getPrefHeight() / 4) + 40;
        switch (Game.getInstanceOfSelf().getPlayerDirectionInWorld()) {
            case "goRight":
                backTransitionHandle(0, playerEntranceCoordinatY, playerEntranceCoordinatX / 2, true);
                if (!humanPlayer.isGiftHasBeenGivenToday() && !humanPlayer.isFirstVillageVisit()) {
                    scenario();
                }
                break;
            case "goStore":
                backTransitionHandle(storeEntrance.getLayoutX(), storeEntrance.getLayoutY() - 20, playerEntranceCoordinatX / 2, false);
                break;
            case "goBlacksmith":
                backTransitionHandle(blacksmithEntrance.getLayoutX(), playerEntranceCoordinatY, playerEntranceCoordinatX / 2, true);
                break;
            case "goLibrary":
                backTransitionHandle(libraryEntrance.getLayoutX(), libraryEntrance.getLayoutY(), playerEntranceCoordinatX / 2, false);
                break;
        }
    }

    private void textAnimation(Label animateInLabel, String textToAnimate) {
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(2000));
            }

            @Override
            protected void interpolate(double frac) {
                final int length = textToAnimate.length();
                final int n = Math.round(length * (float) frac);
                animateInLabel.setText(textToAnimate.substring(0, n));
            }
        };
        animation.play();
    }

    private void scenario() {
        currentDialouge = 1;
        villageGiftAndScenario.setVisible(true);
        textAnimation(villagerText, gameVillage.getScenario(humanPlayer));

    }

    @FXML
    private void handleNextScenarioTextButton() {
        if (humanPlayer.isGiftHasBeenGivenToday() && currentDialouge == 1) {
            textAnimation(villagerText, gameVillage.getScenario(humanPlayer));
            currentDialouge++;
        } else {
            villageGiftAndScenario.setVisible(false);
        }
    }

    private void firstVisit(Player humanPlayer) {
        currentDialouge = 0;
        if (humanPlayer.isFirstVillageVisit()) {
            firstVisitGreatings.setVisible(true);
            humanPlayer.setFirstVillageVisit(false);
            textAnimation(firstVisitText, "Hi. welcome to our little town.");

        }
    }

    @FXML
    private void handleNextDialougeFirstVisitButton(MouseEvent event) {
        switch (currentDialouge) {
            case 0:
                textAnimation(firstVisitText, "I Havent seen you around here before.\n"
                    + "Well anyways it's always nice to meet new people. ");
                currentDialouge++;
                break;
            case 1:
                textAnimation(firstVisitText, "If you plan to stay please feel free\n"
                    + "to explore the towns various shops.");
                currentDialouge++;
                break;
            case 2:
                textAnimation(firstVisitText, "Or maybe do a bit of reading in the library?\n"
                    + "See you around friend!");
                currentDialouge++;
                break;
            case 3:
                firstVisitGreatings.setVisible(false);
                scenario();
        }
    }

//    private void goToTransitionHandle(int StartCoordinatX,
//        double endCoordinatX,
//        String setDirection,
//        String setCommandword,
//        boolean dontHaveToGoDown) {
//        TranslateTransition transitionToX = new TranslateTransition(Duration.seconds(1.5), player);
//        TranslateTransition transitionToAnchorX = new TranslateTransition(Duration.seconds(1.5), player);
//        TranslateTransition transitionToAnchorY = new TranslateTransition(Duration.seconds(1.5), player);
//        if (dontHaveToGoDown) {
//            transitionToX.setByX(endCoordinatX - StartCoordinatX);
//            transitionToX.setOnFinished((ActionEvent e) -> {
//                Command tester = new Command(CommandWord.GO, setCommandword);
//                Game.getInstanceOfSelf().goRoom(tester, mainAnchorPane);
//            });
//            transitionToX.play();
//        } else {
//            transitionToAnchorX.setByX(anchorpoint1.getLayoutX() - StartCoordinatX);
//            transitionToAnchorX.setOnFinished((ActionEvent f) -> {
//                transitionToAnchorY.setByY(anchorpoint2.getLayoutY() - anchorpoint1.getLayoutY());
//                transitionToAnchorY.setOnFinished((ActionEvent h) -> {
//                    transitionToX.setByX(endCoordinatX - anchorpoint2.getLayoutX());
//                    transitionToX.setOnFinished((ActionEvent a) -> {
//                        Command tester = new Command(CommandWord.GO, setCommandword);
//                        Game.getInstanceOfSelf().goRoom(tester, mainAnchorPane);
//                    });
//                    transitionToX.play();
//                });
//                transitionToAnchorY.play();
//            });
//            transitionToAnchorX.play();
//        }
//
//        Game.getInstanceOfSelf().setPlayerDirectionInWorld(setDirection);
//    }
//
    private void backTransitionHandle(double fromX,
        double fromY,
        int toX,
        boolean dontHaveToGoDown) {
        TranslateTransition roomTransitionX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition roomTransitionY = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionXAnchor = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionYAnchor = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutX(fromX);
        player.setLayoutY(fromY);
        if (dontHaveToGoDown) {
            roomTransitionX.setByX(toX - fromX);
            roomTransitionX.setOnFinished((ActionEvent e) -> {
                if (humanPlayer.isFirstVillageVisit()) {
                    firstVisit(humanPlayer);

                }
                running = false;
                backToPreviousRoomButton.setDisable(false);
            });
            roomTransitionX.play();
        } else {
            transitionXAnchor.setByX(anchorpoint2.getLayoutX() - fromX);
            transitionXAnchor.setOnFinished((ActionEvent f) -> {
                transitionYAnchor.setByY((playerEntranceCoordinatY + 40) - anchorpoint2.getLayoutY());
                transitionYAnchor.setOnFinished((ActionEvent h) -> {
                    roomTransitionY.setByX(toX - anchorpoint1.getLayoutX());
                    roomTransitionY.setOnFinished((ActionEvent a) -> {
                        if (humanPlayer.isFirstVillageVisit()) {
                            firstVisit(humanPlayer);

                        }
                        running = false;
                        backToPreviousRoomButton.setDisable(false);
                    });
                    roomTransitionY.play();
                });
                transitionYAnchor.play();
            });
            transitionXAnchor.play();
        }
    }
}
