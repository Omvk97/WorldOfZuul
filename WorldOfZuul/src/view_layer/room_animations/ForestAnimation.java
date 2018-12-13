package view_layer.room_animations;

import view_layer.HighScoreGraphics;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.Forest;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import view_layer.PlayerGraphics;

/**
 * This class contains all ressources related to the two forests, it handles when the user
 * wants to chop a tree and how to play hit and chop sound when chopping trees.
 * @author oliver
 */
abstract public class ForestAnimation {

    @FXML
    protected Label textArea, smallTreeLabel, mediumTreeLabel, largeTreeLabel;
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    protected ImageView player, largeTree, mediumTree;
    protected final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    protected final File punchFile = new File("src/pictures/PunchSound.wav");
    protected final Media punchSound = new Media(punchFile.toURI().toString());
    protected final File chopFile = new File("src/pictures/ChoppingSound.wav");
    protected final Media chopSound = new Media(chopFile.toURI().toString());
    protected final File treeFallingFile = new File("src/pictures/treeFallingSound.wav");
    protected final Media treeFallingSound = new Media(treeFallingFile.toURI().toString());
    protected final ArrayList<Media> sounds = new ArrayList<>();
    protected final int punchDuration = 195;
    protected final int chopDuration = 335;
    protected final HighScoreGraphics highScoreGraphics = new HighScoreGraphics();
    protected final GameAnimation animation = new GameAnimation(player);

    /**
     * When the player wants to chop a tree, if it's a big tree the player chops down this
     * method will handle where the player image should go and what animations to play.
     * @param numOfHits how many hits the tree takes to be felled and therefor how many sounds
     * is to be played.
     * @param treeCount how many trees that's left after the player has felled the tree.
     * @param gameForest the forest that the animation is to be run in.
     */
    protected void treeAnimationToLargeTree(int numOfHits, int treeCount, Forest gameForest) {
        if (humanPlayer.playerHasAnAxe()) {
            for (int i = 0; i < numOfHits; i++) {
                sounds.add(chopSound);
            }
        } else {
            for (int i = 0; i < numOfHits; i++) {
                sounds.add(punchSound);
            }
        }

        sounds.add(treeFallingSound);
        PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(true, player);
        TranslateTransition goToTree = new TranslateTransition(Duration.seconds(1.5), player);
        goToTree.setByX((largeTree.getLayoutX() - player.getLayoutX()) - 20);
        goToTree.setByY(-(player.getLayoutY() - largeTree.getLayoutY()) + 75);
        goToTree.setOnFinished((ActionEvent event1) -> {
            playMediaTracks(sounds);
            hitAnimation(numOfHits, false);
        });

        TranslateTransition goFromTree = new TranslateTransition(Duration.seconds(3), player);
        goFromTree.setByX((player.getLayoutX() - largeTree.getLayoutX()) + 20);
        goFromTree.setByY((player.getLayoutY() - largeTree.getLayoutY()) - 75);
        if (humanPlayer.playerHasAnAxe()) {
            double totalDurationChop = (chopDuration * numOfHits) * 2;
            goFromTree.setDelay(Duration.millis(totalDurationChop));
        } else {
            double totalDurationPunch = (punchDuration * numOfHits) * 2;
            goFromTree.setDelay(Duration.millis(totalDurationPunch));
        }
        goFromTree.setOnFinished((ActionEvent event1) -> {
            largeTreeLabel.setText(Integer.toString(treeCount));
            gameForest.chopWood(humanPlayer);
            treeFelledConfirmation();
        });

        SequentialTransition transition = new SequentialTransition(goToTree, goFromTree);
        transition.play();
    }

    /**
     * A recursive method that makes sure that the sounds that is to be played will be played
     * one after another. This makes sure that the compiler doesn't run all sounds at the same time.
     * @param sounds all the sounds that is to be played in an arraylist.
     */
    protected void playMediaTracks(ArrayList<Media> sounds) {
        if (sounds.isEmpty()) {
            return;
        }

        MediaPlayer mediaplayer = new MediaPlayer(sounds.remove(0));
        mediaplayer.play();

        mediaplayer.setOnEndOfMedia(() -> {
            playMediaTracks(sounds);
        });
    }

    /**
     * When the player has chopped down the tree, they need to get som sort of visual confirmation
     * This methods takes care of that and also handles if the players axe is destroyed.
     */
    protected void treeFelledConfirmation() {
        if (humanPlayer.playerHasAnAxe()) {
            animation.textAnimation(textArea, "You have chopped down a tree with your "
                + humanPlayer.getEquippedAxe().getDescription() + "!");
            boolean axeDestroyed = humanPlayer.useAxe();
            if (axeDestroyed) {
                PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(false, player);
                animation.textAnimation(textArea, "Your axe broke!");
            }
        } else {
            animation.textAnimation(textArea, "You have punched down a tree! But your knuckles hurt");
        }
        animation.setRunning(false);
    }

    /**
     * The animation of "hitting" the tree is controlled in this class. It is meant to be called
     * after the animation walking to the tree is finished.
     * @param numOfHits how many hits that should be animated
     * @param characterGoingRight whether or not the player image is going right.
     */
    protected void hitAnimation(int numOfHits, boolean characterGoingRight) {
        TranslateTransition hitAnimation = new TranslateTransition();
        hitAnimation.setNode(player);
        if (humanPlayer.playerHasAnAxe()) {
            hitAnimation.setDuration(Duration.millis(chopDuration));
        } else {
            hitAnimation.setDuration(Duration.millis(punchDuration));
        }
        if (!characterGoingRight) {
            hitAnimation.setByX(40);
        } else {
            hitAnimation.setByX(-40);
        }
        hitAnimation.setAutoReverse(true);
        hitAnimation.setCycleCount(numOfHits * 2);
        hitAnimation.setInterpolator(Interpolator.LINEAR);
        hitAnimation.setOnFinished((ActionEvent event1) -> {
            PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(false, player);
        });
        hitAnimation.play();
    }
}
