package controllers;

import game_functionality.Game;
import game_functionality.Player;
import game_locations.Forest;
import java.io.File;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 *
 * @author oliver
 */
abstract public class ForestController {

    @FXML
    protected Label textArea, smallTreeLabel, mediumTreeLabel, largeTreeLabel;
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    protected ImageView player, largeTree, mediumTree;
    protected final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    protected boolean running;
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

    protected void treeAnimationToLargeTree(int numOfHits, int treeCount, Forest gameForest) {
        running = true;
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
        humanPlayer.setCharacterModel(true);
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));

        TranslateTransition goToTree = new TranslateTransition(Duration.seconds(3), player);
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

    protected void treeFelledConfirmation() {
        if (humanPlayer.playerHasAnAxe()) {
            textArea.setText("You have chopped down a tree with your" + 
                humanPlayer.getAxe().getDescription() + "!");
            double axeDurability = humanPlayer.useAxe();
            if (axeDurability == 0.5) {
                textArea.setText("Your axe is at half durability!");
            } else if (axeDurability == 0) {
                textArea.setText("Your axe broke!");
                humanPlayer.setCharacterModel(true);
                player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
            }
        } else {
            textArea.setText("You have punched down a tree! But your knuckles hurt :(");
        }
        running = false;
    }

    protected void hitAnimation(int numOfChops, boolean characterGoingRight) {
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
        hitAnimation.setCycleCount(numOfChops * 2);
        hitAnimation.setInterpolator(Interpolator.LINEAR);
        hitAnimation.setOnFinished((ActionEvent event1) -> {
            humanPlayer.setCharacterModel(characterGoingRight);
            player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        });
        hitAnimation.play();
    }
}
