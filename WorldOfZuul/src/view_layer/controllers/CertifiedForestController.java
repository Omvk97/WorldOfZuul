package view_layer.controllers;

import view_layer.room_animations.ForestAnimation;
import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.PlayerInteraction;
import domain_layer.game_locations.CertifiedForest;
import java.net.URL;
import java.util.ResourceBundle;
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
 * Talks to both the room certifiedForest and the associated FXML, to visually and logically
 * represent when the user chops a tree and when the user plants new trees.
 *
 * @author oliver
 */
public class CertifiedForestController extends ForestAnimation implements Initializable {

    private final CertifiedForest gameForest = (CertifiedForest) Game.getInstanceOfSelf().getCertifiedForest();

    /**
     * Makes a walking animation when entering the room and sets all fxml containers.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animation.setRunning(true);
        TranslateTransition down = new TranslateTransition(Duration.seconds(1.5), player);
        down.setFromY(-170);
        down.setByY(170);
        down.setOnFinished((ActionEvent event) -> {
            animation.setRunning(false);
        });
        down.play();
        PlayerGraphics.getInstanceOfSelf().updateCharacterModel(player);
        animation.textAnimation(textArea, gameForest.roomEntrance(humanPlayer));
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }

    /**
     * If there isn't another animation running, the method will check if the player can chop trees
     * and start the animation if the player can chop tree.
     *
     * @param event
     */
    @FXML
    private void handleTreeFelling(MouseEvent event) {
        if (!animation.isRunning()) {
            if (humanPlayer.canCarryMoreTrees() && gameForest.thereIsMoreTreesToCut()) {
                animation.setRunning(true);
                int number = gameForest.countNumOfHits(humanPlayer);
                treeAnimationToLargeTree(number, gameForest.countLargeTrees(), gameForest);
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

    @FXML
    private void handleCountFellableTrees(MouseEvent event) {
        animation.textAnimation(textArea, "There are " + gameForest.countLargeTrees() + " trees ready to be felled!");
    }

    /**
     * If the player is able to plant trees, the controller will ask game_locations.certfiedForest
     * to replant trees.
     *
     * @param event
     */
    @FXML
    private void handlePlantSeeds(MouseEvent event) {
        if (PlayerInteraction.getInstanceOfSelf().getNumChoppedTreesWithoutPlantingSaplings() > 0) {
            int amountOfSeedsPlanted = gameForest.replantTrees(humanPlayer);
            if (amountOfSeedsPlanted > 0) {
                animation.textAnimation(textArea, "You just planted " + (amountOfSeedsPlanted > 1
                    ? amountOfSeedsPlanted + " saplings!" : "1 sapling!"));
            } else if (amountOfSeedsPlanted == 0) {
                animation.textAnimation(textArea, "You don't have any saplings, go buy some!");
            }
        } else {
            animation.textAnimation(textArea, "You haven't chopped any trees today!");
        }
        gameForest.moveChoppableTreesUp();
    }

    /**
     * When the player wants to exit the room, this method will represent visually how that looks.
     *
     * @param event
     */
    @FXML
    private void handleExits(KeyEvent event) {
        if (!animation.isRunning()) {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                animation.setRunning(true);
                PlayerInteraction.getInstanceOfSelf().setPlayerDirectionInWorld("goUp");
                Command tester = new Command(CommandWord.GO, "trailer");
                TranslateTransition up = new TranslateTransition(Duration.seconds(1.5), player);
                up.setByY(-170);
                up.setOnFinished((ActionEvent e) -> {
                    Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                    animation.setRunning(false);
                });
                up.play();
            } else {
                animation.textAnimation(textArea, "There is no road!");
            }
        }
    }
}
