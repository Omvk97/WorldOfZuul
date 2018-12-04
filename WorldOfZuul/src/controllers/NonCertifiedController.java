package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.NonCertifiedForest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author oliver
 * @date 1/12/2018
 */
public class NonCertifiedController extends ForestController implements Initializable {

    private final NonCertifiedForest gameForest = (NonCertifiedForest) Game.getInstanceOfSelf().getNonCertificedForest();

    public NonCertifiedController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Game.getInstanceOfSelf().getDirection().equals("goUp")) {
            TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
            player.setLayoutY(player.getLayoutY() * 2);
            up.setByY(-170);
            up.play();
        }
        textArea.setText(gameForest.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        if (!running) {
            if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
                textArea.setText("YOU HAVE DESTROYED TOO MUCH OF THE EARTH");
                highScoreGraphics.closeGame();
                System.exit(0);
            }

            if (gameForest.playerCanCarryMoreTree(humanPlayer) && gameForest.thereIsMoreTreesToCut()) {
                if (gameForest.lastTreeInArray().getTreeHealth() >= gameForest.getLARGE_TREE_SIZE()) {
                    int number = gameForest.chopWood(humanPlayer);
                    treeAnimationToLargeTree(number);
                    largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
                } else {
                    int number = Integer.parseInt(gameForest.option1(humanPlayer));
                    treeAnimationToMediumTree(number);
                }
            } else if (gameForest.playerCanCarryMoreTree(humanPlayer) && !gameForest.thereIsMoreTreesToCut()) {
                textArea.setText("There is no more trees to fell right now!"
                    + "\nYou have to wait for the forest to regrow!");
            } else if (gameForest.thereIsMoreTreesToCut() && !gameForest.playerCanCarryMoreTree(humanPlayer)) {
                textArea.setText("You are carrying too much wood!\n"
                    + "Sell or store your logs!");
            } else {
                textArea.setText("There is no trees to fell and your backpack is full!");
            }
        } else {
//            do nothing
        }
    }

    private void treeAnimationToMediumTree(int numOfHits) {
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

        humanPlayer.setCharacterModel(false);
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        TranslateTransition goToTree = new TranslateTransition(Duration.seconds(3), player);
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
            treeFelledConfirmation();
            mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        });

        SequentialTransition transition = new SequentialTransition(goToTree, goFromTree);
        transition.play();
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText("There are " + gameForest.countFellableTrees() + " trees ready to be felled!");
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Game.getInstanceOfSelf().setDirection("goDown");
            Command tester = new Command(CommandWord.GO, "trailer");
            TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
            down.setByY(player.getLayoutY() / 2);
            down.setOnFinished(e -> Game.getInstanceOfSelf().goRoom(tester, anchorPane));
            down.play();
        } else {
            textArea.setText("There is no road!");
        }
    }

}
