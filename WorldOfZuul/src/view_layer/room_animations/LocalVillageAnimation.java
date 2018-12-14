package view_layer.room_animations;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.PlayerInteraction;
import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view_layer.PlayerGraphics;

/**
 * This class has the sole responsibility of creating the animations that local village requires.
 *
 * @author oliver co-author: michael
 */
public class LocalVillageAnimation extends GameAnimation {

    private Rectangle anchorPoint1, anchorPoint2;
    private AnchorPane mainAnchorPane, firstVisitGreetings;
    private Button backToPreviousRoomButton;
    private int playerEntranceCoordinatY;
    private Label firstVisitText;
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    /**
     * Builder pattern with 8 attributes that is build through the build method. The attributes is
     * the attributes that is needed in order to play the animations, they should come from
     * localVillage controller
     */
    public static class Builder {

        private Rectangle anchorPoint1, anchorPoint2;
        private AnchorPane mainAnchorPane, firstVisitGreetings;
        private final ImageView player;
        private Button backToPreviousRoomButton;
        private int playerEntranceCoordinatY;
        private Label firstVisitText;

        public Builder(ImageView player) {
            this.player = player;
        }

        public Builder withAnchorPoint1(Rectangle anchorPoint1) {
            this.anchorPoint1 = anchorPoint1;
            return this;
        }

        public Builder withAnchorPoint2(Rectangle anchorPoint2) {
            this.anchorPoint2 = anchorPoint2;
            return this;
        }

        public Builder withMainAnchorPane(AnchorPane mainAnchorPane) {
            this.mainAnchorPane = mainAnchorPane;
            return this;
        }

        public Builder withFirstVisitGreeTingsPane(AnchorPane firstVisitGreetings) {
            this.firstVisitGreetings = firstVisitGreetings;
            return this;
        }

        public Builder withbackToPreviousRoomButton(Button backToPreviousRoomButton) {
            this.backToPreviousRoomButton = backToPreviousRoomButton;
            return this;
        }

        public Builder withPlayerEntranceCoordinates(int playerEntranceCoordinatY) {
            this.playerEntranceCoordinatY = playerEntranceCoordinatY;
            return this;
        }

        public Builder withfirstVisitText(Label firstVisitText) {
            this.firstVisitText = firstVisitText;
            return this;
        }

        public LocalVillageAnimation build() {
            LocalVillageAnimation animation = new LocalVillageAnimation(player);
            animation.anchorPoint1 = this.anchorPoint1;
            animation.anchorPoint2 = this.anchorPoint2;
            animation.mainAnchorPane = this.mainAnchorPane;
            animation.firstVisitGreetings = this.firstVisitGreetings;
            animation.backToPreviousRoomButton = this.backToPreviousRoomButton;
            animation.playerEntranceCoordinatY = this.playerEntranceCoordinatY;
            animation.firstVisitText = this.firstVisitText;
            return animation;
        }
    }

    /**
     * Private constructor so only the builder can create instances of LocalVillageAnimation.
     *
     * @param player
     */
    private LocalVillageAnimation(ImageView player) {
        super(player);
    }

    /**
     * This method is used when the player goes to buildins (store, blacksmith or library) and when
     * the player goes out from local village back to trailer.
     *
     * @param startCoordinateX where the player should start
     * @param endCoordinatX where the player should end
     * @param setDirection the direction the player has to go at.
     * @param roomNameToGoTo the room the player is to go to.
     * @param dontHaveToGoDown whether the building the player wants to go to is laying above or
     * under players current position
     */
    public void goToTransitionHandle(int startCoordinateX,
        double endCoordinatX,
        String setDirection,
        String roomNameToGoTo,
        boolean dontHaveToGoDown) {
        if (firstVisitGreetings.isVisible()) {
            firstVisitGreetings.setVisible(false);
        }
        TranslateTransition transitionToX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionToAnchorX = new TranslateTransition(Duration.seconds(0.5), player);
        TranslateTransition transitionToAnchorY = new TranslateTransition(Duration.seconds(1.5), player);
        if (dontHaveToGoDown) {
            transitionToX.setByX(endCoordinatX - startCoordinateX);
            transitionToX.setOnFinished((ActionEvent e) -> {
                Command tester = new Command(CommandWord.GO, roomNameToGoTo);
                game.goRoom(tester, mainAnchorPane);
            });
            transitionToX.play();
        } else {
            transitionToAnchorX.setByX(anchorPoint1.getLayoutX() - startCoordinateX);
            transitionToAnchorX.setOnFinished((ActionEvent f) -> {
                transitionToAnchorY.setByY(anchorPoint2.getLayoutY() - anchorPoint1.getLayoutY());
                transitionToAnchorY.setOnFinished((ActionEvent h) -> {
                    transitionToX.setByX(endCoordinatX - anchorPoint2.getLayoutX());
                    transitionToX.setOnFinished((ActionEvent a) -> {
                        Command tester = new Command(CommandWord.GO, roomNameToGoTo);
                        game.goRoom(tester, mainAnchorPane);
                    });
                    transitionToX.play();
                });
                transitionToAnchorY.play();
            });
            transitionToAnchorX.play();
        }
        PlayerInteraction.getInstanceOfSelf().setPlayerDirectionInWorld(setDirection);
    }

    /**
     * This method is used when the player goes back from one of the buildings (store, library or
     * blacksmith).
     *
     * @param fromX where the player image X axis should start at
     * @param fromY where the player image Y axis should start at
     * @param toX where the player image X axis should end at.
     * @param dontHaveToGoDown whether the building the player wants to go to is laying above or
     * under players current position
     */
    public void backTransitionHandle(double fromX,
        double fromY,
        int toX,
        boolean dontHaveToGoDown) {
        PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(false, player);
        TranslateTransition roomTransitionX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition roomTransitionY = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionXAnchor = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionYAnchor = new TranslateTransition(Duration.seconds(1.5), player);
        player.setLayoutX(fromX);
        player.setLayoutY(fromY);
        if (dontHaveToGoDown) {
            roomTransitionX.setByX(toX - fromX);
            roomTransitionX.setOnFinished((ActionEvent e) -> {
                firstVisit();
                running = false;
                backToPreviousRoomButton.setDisable(false);
            });
            roomTransitionX.play();
        } else {
            transitionXAnchor.setByX(anchorPoint2.getLayoutX() - fromX);
            transitionXAnchor.setOnFinished((ActionEvent f) -> {
                transitionYAnchor.setByY((playerEntranceCoordinatY + 40) - anchorPoint2.getLayoutY());
                transitionYAnchor.setOnFinished((ActionEvent h) -> {
                    roomTransitionY.setByX(toX - anchorPoint1.getLayoutX());
                    roomTransitionY.setOnFinished((ActionEvent a) -> {
                        firstVisit();
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

    /**
     * This method handles what's going to happen when the scenario is called.
     */
    private void firstVisit() {
        if (playerInteraction.isFirstVillageVisit()) {
            firstVisitGreetings.setVisible(true);
            playerInteraction.setFirstVillageVisit(false);
            textAnimation(firstVisitText, "Hi. welcome to our little town.");

        }
    }

    /**
     * This method creates all the images of rain that is to be displayed on screen.
     *
     * @param numOfRainDropsOnScreen the amount of rain drops on screen
     * @param rainDropSpeed how quickly the rain should run down the screen.
     */
    public void rainDrops(int numOfRainDropsOnScreen, int rainDropSpeed) {
        ImageView[] rainDrops = new ImageView[numOfRainDropsOnScreen];
        for (int i = 0; i < rainDrops.length; i++) {
            rainDrops[i] = new ImageView(new Image(new File("src/pictures/rain.png").toURI().toString()));
            mainAnchorPane.getChildren().add(rainDrops[i]);
            makeItRain(rainDrops[i], rainDropSpeed);
        }
    }

    /**
     * Recursive method handles the rain animation on screen. It starts an animation on all
     * raindrops on the screen and as soon as the image reach the end of the screen, the method
     * calls itself so the image will be placed again
     *
     * @param rain the image that the animation is to be started on
     * @param rainDropSpeed how quickly the rain should run down the screen.
     */
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
}
