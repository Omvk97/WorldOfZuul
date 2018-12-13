package view_layer.room_animations;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.PlayerInteraction;
import domain_layer.game_locations.Trailer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * This class has the responsibility of handling how the player moves around in trailer.
 *
 * @author oliver co-author: daniel
 */
public class TrailerAnimation extends GameAnimation {

    private AnchorPane anchorPane;
    private ImageView starterAxe, trailerPath;
    private Trailer gameTrailer;

    /**
     * Builder pattern with 5 attributes that is build through the build method. The attributes is
     * the attributes that is needed in order to play the animations, they should come from
     * trailercontroller
     */
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

    /**
     * Private constructor so only the builder can construct new trailerAnimations
     *
     * @param player the player image that is in the room.
     */
    private TrailerAnimation(ImageView player) {
        super(player);
    }

    /**
     * When the player walks from trailer to different rooms this method is called.
     *
     * @param roomToGoTo the room that the player wants to go to.
     * @param direction the direction that player came from when walking into the new room
     * @param translateX how much the player should move X axis
     * @param translateY how much the player should move Y axis
     */
    public void walkTransition(String roomToGoTo,
        String direction,
        int translateX,
        int translateY) {
        running = true;
        Command roomToGo = new Command(CommandWord.GO, roomToGoTo);
        PlayerInteraction.getInstanceOfSelf().setPlayerDirectionInWorld(direction);
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

    /**
     * When the player walks into trailer from different rooms this method is called.
     *
     * @param translateX how much the player should move in the X axis
     * @param translateY how much the player should move in the Y axis
     * @param setLayoutX Where the player image should start from X axis.
     * @param setLayoutY Where the player image should start from Y axis.
     */
    public void playerEnteringTrailerPath(int translateX,
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

    /**
     * When the player stands outside the trailer, and wants to go inside the trailer this method is
     * called.
     *
     * @param textArea the textArea that is in trailer.
     */
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
