package view_layer.room_animations;

import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * This class handles animations that all rooms needs.
 * @author oliver
 * co-author: Michael
 */
public class GameAnimation {

    protected final ImageView player;
    protected final Game game = Game.getInstanceOfSelf();
    protected final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    protected boolean running;

    public GameAnimation(ImageView player) {
        this.player = player;
    }
    
    /**
     * This gives a floating text style to the text in a rate that is constant no matter how long
     * the string.
     * @param animateInLabel the label that holds the text that is to be animated
     * @param textToAnimate the String which is the text that is to be animated.
     */
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
