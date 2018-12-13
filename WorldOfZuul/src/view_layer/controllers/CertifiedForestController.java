package view_layer.controllers;

import view_layer.room_animations.ForestAnimation;
import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_locations.CertifiedForest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author oliver
 */
public class CertifiedForestController extends ForestAnimation implements Initializable {

    private final CertifiedForest gameForest = (CertifiedForest) Game.getInstanceOfSelf().getCertifiedForest();

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
        animation.textAnimation(textArea, gameForest.roomEntrance(humanPlayer));
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        if (!animation.isRunning()) {
            if (gameForest.playerCanCarryMoreTree(humanPlayer) && gameForest.thereIsMoreTreesToCut()) {
                animation.setRunning(true);
                int number = gameForest.countNumOfHits(humanPlayer);
                treeAnimationToLargeTree(number, gameForest.countLargeTrees(), gameForest);
            } else if (gameForest.playerCanCarryMoreTree(humanPlayer) && !gameForest.thereIsMoreTreesToCut()) {
                animation.textAnimation(textArea, "There is no more trees to fell right now!"
                    + "\nYou have to wait for the forest to regrow!");
            } else if (gameForest.thereIsMoreTreesToCut() && !gameForest.playerCanCarryMoreTree(humanPlayer)) {
                animation.textAnimation(textArea, "You are carrying too much wood!\n"
                    + "Sell or store your logs!");
            } else {
                animation.textAnimation(textArea, "There is no trees to fell and your backpack is full!");
            }
        } else {
//            do nothing
        }
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        animation.textAnimation(textArea, "There are " + gameForest.countLargeTrees() + " trees ready to be felled!");
    }

    @FXML
    private void handleOption3(MouseEvent event) {
        if (humanPlayer.getNumChoppedTreesWithoutPlantingSaplings() > 0) {
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
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!animation.isRunning()) {
            animation.setRunning(true);
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                animation.setRunning(true);
                Game.getInstanceOfSelf().setPlayerDirectionInWorld("goUp");
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
