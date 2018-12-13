package view_layer.room_animations;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
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

/**
 * This class has the sole responsibility of creating the animations that local village
 * requires.
 * @author oliver
 */
public class LocalVillageAnimation extends GameAnimation {

    private Rectangle anchorPoint1, anchorPoint2;
    private AnchorPane mainAnchorPane, firstVisitGreetings;
    private Button backToPreviousRoomButton;
    private int playerEntranceCoordinatY;
    private Label firstVisitText;

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

    private LocalVillageAnimation(ImageView player) {
        super(player);
    }

    public void goToTransitionHandle(int StartCoordinatX,
        double endCoordinatX,
        String setDirection,
        String setCommandword,
        boolean dontHaveToGoDown) {
        if(firstVisitGreetings.visibleProperty().equals(true)){
            firstVisitGreetings.setVisible(false);
        }
        TranslateTransition transitionToX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionToAnchorX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionToAnchorY = new TranslateTransition(Duration.seconds(1.5), player);
        if (dontHaveToGoDown) {
            transitionToX.setByX(endCoordinatX - StartCoordinatX);
            transitionToX.setOnFinished((ActionEvent e) -> {
                Command tester = new Command(CommandWord.GO, setCommandword);
                game.goRoom(tester, mainAnchorPane);
            });
            transitionToX.play();
        } else {
            transitionToAnchorX.setByX(anchorPoint1.getLayoutX() - StartCoordinatX);
            transitionToAnchorX.setOnFinished((ActionEvent f) -> {
                transitionToAnchorY.setByY(anchorPoint2.getLayoutY() - anchorPoint1.getLayoutY());
                transitionToAnchorY.setOnFinished((ActionEvent h) -> {
                    transitionToX.setByX(endCoordinatX - anchorPoint2.getLayoutX());
                    transitionToX.setOnFinished((ActionEvent a) -> {
                        Command tester = new Command(CommandWord.GO, setCommandword);
                        game.goRoom(tester, mainAnchorPane);
                    });
                    transitionToX.play();
                });
                transitionToAnchorY.play();
            });
            transitionToAnchorX.play();
        }

        game.setPlayerDirectionInWorld(setDirection);
    }

    public void backTransitionHandle(double fromX,
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

    /*
     This method handels what's going to happen the the scenario is called.
     */
    private void firstVisit() {
        if (humanPlayer.isFirstVillageVisit()) {
            firstVisitGreetings.setVisible(true);
            humanPlayer.setFirstVillageVisit(false);
            textAnimation(firstVisitText, "Hi. welcome to our little town.");

        }
    }

    /*
     This method places the rainDrops image on random spots on the sceen and animates it down the screen
     */
    public void rainDrops(int numOfRainDropsOnScreen, int rainDropSpeed) {
        ImageView[] rainDrops = new ImageView[numOfRainDropsOnScreen];
        for (int i = 0; i < rainDrops.length; i++) {
            rainDrops[i] = new ImageView(new Image(new File("src/pictures/rain.png").toURI().toString()));
            mainAnchorPane.getChildren().add(rainDrops[i]);
            makeItRain(rainDrops[i], rainDropSpeed);
        }
    }

    /*
     * This method handles the rain animation
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
