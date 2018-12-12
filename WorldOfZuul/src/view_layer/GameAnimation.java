package view_layer;

import domain_layer.game_functionality.Game;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GameAnimation {

    private final ImageView player;
    protected final Game game = Game.getInstanceOfSelf();
    protected boolean running;

    public GameAnimation(ImageView player) {
        this.player = player;
    }

    public void textAnimation(Label animateInLabel, String textToAnimate) {
        final int durationLength = textToAnimate.length() * 35;
        final javafx.animation.Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(durationLength));
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
}
