package view_layer.room_animations;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_locations.Trailer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TrailerAnimation extends GameAnimation {

    private AnchorPane anchorPane;
    private ImageView starterAxe, trailerPath;
    private Trailer gameTrailer;

    public static class Builder {

        private AnchorPane anchorPane;
        private final ImageView player;
        private ImageView starterAxe, trailerPath;
        private Trailer gameTrailer;

        public Builder(ImageView player) {
            this.player = player;
        }

        public Builder withAnchorPane(AnchorPane anchorPane) {
            this.anchorPane = anchorPane;
            return this;
        }

        public Builder withStarterAxe(ImageView starterAxe) {
            this.starterAxe = starterAxe;
            return this;
        }

        public Builder withTrailerPath(ImageView trailerPath) {
            this.trailerPath = trailerPath;
            return this;
        }

        public Builder withGameTrailer(Trailer gameTrailer) {
            this.gameTrailer = gameTrailer;
            return this;
        }

        public TrailerAnimation build() {
            TrailerAnimation animation = new TrailerAnimation(player);
            animation.anchorPane = this.anchorPane;
            animation.starterAxe = this.starterAxe;
            animation.trailerPath = this.trailerPath;
            animation.gameTrailer = this.gameTrailer;
            return animation;
        }
    }

    private TrailerAnimation(ImageView player) {
        super(player);
    }

    public void walkTransition(String roomToGoTo,
        String direction,
        int translateX,
        int translateY) {
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
            FadeTransition axeFadeTransition = new FadeTransition(Duration.seconds(1.5), starterAxe);
            trailerPath.setVisible(true);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            axeFadeTransition.setFromValue(1);
            axeFadeTransition.setToValue(0);
            fadeTransition.setOnFinished((ActionEvent e) -> {
                player.setVisible(true);
                starterAxe.setVisible(false);
                directionTransition.play();
            });
            axeFadeTransition.play();
            fadeTransition.play();
        } else {
            player.setVisible(true);
            directionTransition.play();
        }
    }

    public void playerEnteringTrailerTransition(int translateX,
        int translateY,
        double setLayoutX,
        double setLayoutY) {
        running = true;
        player.setVisible(true);
        trailerPath.setVisible(true);
        starterAxe.setVisible(false);
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

    public void goToTrailerFromPath(Label textArea) {
        textArea.setVisible(true);
        running = true;
        player.setVisible(false);
        starterAxe.setVisible(true);
        FadeTransition upFade = new FadeTransition(Duration.seconds(1.5), trailerPath);
        FadeTransition axefade = new FadeTransition(Duration.seconds(1.5), starterAxe);
        upFade.setFromValue(1);
        upFade.setToValue(0);
        axefade.setFromValue(0);
        axefade.setToValue(1);
        upFade.setOnFinished((ActionEvent) -> {
            running = false;
            trailerPath.setVisible(false);
            textAnimation(textArea, gameTrailer.roomEntrance(humanPlayer));
        });
        axefade.play();
        upFade.play();
    }
}
