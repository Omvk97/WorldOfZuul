package view_layer.controllers;

import view_layer.room_animations.ForestAnimation;
import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.PlayerInteraction;
import domain_layer.game_locations.NonCertifiedForest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import view_layer.PlayerGraphics;

/**
 *
 * This controller has the responsibility for the connection between
 * domain_layer.game_location/noncertifiedForest and the view_layer.room_fxml/NonCertifiedForest
 *
 * @author oliver
 */
public class NonCertifiedController extends ForestAnimation implements Initializable {

    private final NonCertifiedForest gameForest = (NonCertifiedForest) Game.getInstanceOfSelf()
        .getNonCertificedForest();
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    /**
     * Makes a walking animation when entering the room and sets all fxml containers.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animation.setRunning(true);
        TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
        up.setFromY(170);
        up.setByY(-170);
        up.setOnFinished((ActionEvent event) -> {
            animation.setRunning(false);
        });
        up.play();
        animation.textAnimation(textArea, gameForest.roomEntrance(humanPlayer));
        PlayerGraphics.getInstanceOfSelf().updateCharacterModel(player);
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }

    /**
     * checks to see if the player can chop a tree, and if so it runs the animation assoicated with
     * chopping down a tree. Can either go to a medium or large tree.
     *
     * @param event
     */
    @FXML
    private void handleChopTree(MouseEvent event) {
        if (!animation.isRunning()) {
            if (humanPlayer.getClimatePointsValue() == playerInteraction.getMIN_CLIMATEPOINTS()) {
                animation.textAnimation(textArea, "YOU HAVE DESTROYED TOO MUCH OF THE EARTH");
                highScoreGraphics.closeGame();
                System.exit(0);
            }
            if (humanPlayer.canCarryMoreTrees() && gameForest.thereIsMoreTreesToCut()) {
                animation.setRunning(true);
                if (gameForest.lastTreeInArray().getTreeHealth() >= gameForest.getLARGE_TREE_SIZE()) {
                    int number = gameForest.countNumOfHits(humanPlayer);
                    treeAnimationToLargeTree(number, gameForest.countLargeTrees(), gameForest);
                } else {
                    int number = gameForest.countNumOfHits(humanPlayer);
                    treeAnimationToMediumTree(number, gameForest.countMediumTrees());
                }
            } else if (humanPlayer.canCarryMoreTrees() && !gameForest.thereIsMoreTreesToCut()) {
                animation.textAnimation(textArea, "There is no more trees to fell right now!"
                    + "\nYou have to wait for the forest to regrow!");
            } else if (gameForest.thereIsMoreTreesToCut() && !humanPlayer.canCarryMoreTrees()) {
                animation.textAnimation(textArea, "You are carrying too much wood!\n"
                    + "Sell or store your logs!");
            } else {
                animation.textAnimation(textArea, "There is no trees to fell and your backpack is full!");
            }
        } else {
        }
    }

    /**
     * Animation to go to the medium tree,
     *
     * @param numOfHits how many hits the tree has to take in order to be felled.
     * @param amountOfMediumTrees the amount of medium trees left after the player has felled one.
     */
    private void treeAnimationToMediumTree(int numOfHits, int amountOfMediumTrees) {
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

        PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(false, player);
        TranslateTransition goToTree = new TranslateTransition(Duration.seconds(1.5), player);
        goToTree.setByX((mediumTree.getLayoutX() - player.getLayoutX()) + 70);
        goToTree.setByY(-(player.getLayoutY() - mediumTree.getLayoutY()) + 60);

        goToTree.setOnFinished((ActionEvent event1) -> {
            playMediaTracks(sounds);
            hitAnimation(numOfHits, true);
        });

        TranslateTransition goFromTree = new TranslateTransition(Duration.seconds(3), player);
        goFromTree.setByX((player.getLayoutX() - mediumTree.getLayoutX()) - 70);
        goFromTree.setByY((player.getLayoutY() - mediumTree.getLayoutY()) - 60);
        if (humanPlayer.playerHasAnAxe()) {
            double totalDurationChop = (chopDuration * numOfHits) * 2;
            goFromTree.setDelay(Duration.millis(totalDurationChop));
        } else {
            double totalDurationPunch = (punchDuration * numOfHits) * 2;
            goFromTree.setDelay(Duration.millis(totalDurationPunch));
        }
        goFromTree.setOnFinished((ActionEvent event1) -> {
            mediumTreeLabel.setText(Integer.toString(amountOfMediumTrees));
            gameForest.chopWood(humanPlayer);
            treeFelledConfirmation();
            PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(true, player);
        });

        SequentialTransition transition = new SequentialTransition(goToTree, goFromTree);
        transition.play();
    }

    /**
     * When the player clicks on tree info button this method is run
     *
     * @param event
     */
    @FXML
    private void handleTreeInfo(MouseEvent event) {
        animation.textAnimation(textArea, "There are " + gameForest.countFellableTrees() + " trees ready to be felled!");
    }

    /**
     * when the player wants to go back to trailer this method is run.
     *
     * @param event
     */
    @FXML
    private void handleExits(KeyEvent event) {
        if (!animation.isRunning()) {
            animation.setRunning(true);
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                playerInteraction.setPlayerDirectionInWorld("goDown");
                Command tester = new Command(CommandWord.GO, "trailer");
                TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                down.setByY(170);
                down.setOnFinished(e -> Game.getInstanceOfSelf().goRoom(tester, anchorPane));
                down.play();
            } else {
                animation.textAnimation(textArea, "There is no road!");
            }
        }
    }

}
