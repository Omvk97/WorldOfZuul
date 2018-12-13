package view_layer.controllers;

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
 * @author oliver
 * This controller has the responsibility for the connection between
 * domain_layer.game_location/noncertifiedForest and the view_layer.room_fxml/NonCertifiedForest 
 * 
 * 
 */
public class NonCertifiedController extends ForestController implements Initializable {

    private final NonCertifiedForest gameForest = (NonCertifiedForest) Game.getInstanceOfSelf()
        .getNonCertificedForest();
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    public NonCertifiedController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        running = true;
        TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
        up.setFromY(170);
        up.setByY(-170);
        up.setOnFinished((ActionEvent event) -> {
            running = false;
        });
        up.play();
        textArea.setText(gameForest.roomEntrance(humanPlayer));
        PlayerGraphics.getInstanceOfSelf().updateCharacterModel(player);
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }
    @FXML
    private void handleChopTree(MouseEvent event) {
        if (!running) {
            if (humanPlayer.getClimatePointsValue() == playerInteraction.getMIN_CLIMATEPOINTS()) {
                textArea.setText("YOU HAVE DESTROYED TOO MUCH OF THE EARTH");
                highScoreGraphics.closeGame();
                System.exit(0);
            }
            if (humanPlayer.canCarryMoreTrees() && gameForest.thereIsMoreTreesToCut()) {
                running = true;
                if (gameForest.lastTreeInArray().getTreeHealth() >= gameForest.getLARGE_TREE_SIZE()) {
                    int number = gameForest.countNumOfHits(humanPlayer);
                    treeAnimationToLargeTree(number, gameForest.countLargeTrees(), gameForest);
                } else {
                    int number = gameForest.countNumOfHits(humanPlayer);
                    treeAnimationToMediumTree(number, gameForest.countMediumTrees());
                }
            } else if (humanPlayer.canCarryMoreTrees() && !gameForest.thereIsMoreTreesToCut()) {
                textArea.setText("There is no more trees to fell right now!"
                    + "\nYou have to wait for the forest to regrow!");
            } else if (gameForest.thereIsMoreTreesToCut() && !humanPlayer.canCarryMoreTrees()) {
                textArea.setText("You are carrying too much wood!\n"
                    + "Sell or store your logs!");
            } else {
                textArea.setText("There is no trees to fell and your backpack is full!");
            }
        } else {
        }
    }

    private void treeAnimationToMediumTree(int numOfHits, int treeCount) {
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

        PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(false,
            humanPlayer.getEquippedAxe(), player);
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
            mediumTreeLabel.setText(Integer.toString(treeCount));
            gameForest.chopWood(humanPlayer);
            treeFelledConfirmation();
            PlayerGraphics.getInstanceOfSelf().setAndUpdateCharacterModel(true,
                humanPlayer.getEquippedAxe(), player);
        });

        SequentialTransition transition = new SequentialTransition(goToTree, goFromTree);
        transition.play();
    }

    @FXML
    private void handleTreeInfo(MouseEvent event) {
        textArea.setText("There are " + gameForest.countFellableTrees() + " trees ready to be felled!");
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!running) {
            running = true;
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                playerInteraction.setPlayerDirectionInWorld("goDown");
                Command tester = new Command(CommandWord.GO, "trailer");
                TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
                down.setByY(170);
                down.setOnFinished(e -> Game.getInstanceOfSelf().goRoom(tester, anchorPane));
                down.play();
            } else {
                textArea.setText("There is no road!");
            }
        }
    }

}
