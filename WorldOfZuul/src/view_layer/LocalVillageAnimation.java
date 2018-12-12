package view_layer;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LocalVillageAnimation extends GameAnimation {

    private Rectangle anchorPoint1, anchorPoint2;
    private Game game;
    private AnchorPane mainAnchorPane;
    private ImageView player;

    public static class Builder {

        private Rectangle anchorPoint1, anchorPoint2;
        private Game game;
        private AnchorPane mainAnchorPane;
        private ImageView player;

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

        public Builder withGame(Game game) {
            this.game = game;
            return this;
        }

        public Builder withMainAnchorPane(AnchorPane mainAnchorPane) {
            this.mainAnchorPane = mainAnchorPane;
            return this;
        }

        public LocalVillageAnimation build() {
            LocalVillageAnimation animation = new LocalVillageAnimation(player);
            animation.anchorPoint1 = this.anchorPoint1;
            animation.anchorPoint2 = this.anchorPoint2;
            animation.game = this.game;
            animation.mainAnchorPane = this.mainAnchorPane;
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
        TranslateTransition transitionToX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionToAnchorX = new TranslateTransition(Duration.seconds(1.5), player);
        TranslateTransition transitionToAnchorY = new TranslateTransition(Duration.seconds(1.5), player);
        if (dontHaveToGoDown) {
            transitionToX.setByX(endCoordinatX - StartCoordinatX);
            transitionToX.setOnFinished((ActionEvent e) -> {
                Command tester = new Command(CommandWord.GO, setCommandword);
                Game.getInstanceOfSelf().goRoom(tester, mainAnchorPane);
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
                        Game.getInstanceOfSelf().goRoom(tester, mainAnchorPane);
                    });
                    transitionToX.play();
                });
                transitionToAnchorY.play();
            });
            transitionToAnchorX.play();
        }

        Game.getInstanceOfSelf().setPlayerDirectionInWorld(setDirection);
    }
}
