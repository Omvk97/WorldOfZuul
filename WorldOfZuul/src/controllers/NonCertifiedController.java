package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Forest;
import game_locations.NonCertifiedForest;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class NonCertifiedController implements Initializable {

    @FXML
    private Label textArea, smallTreeLabel, mediumTreeLabel, largeTreeLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2;
    @FXML
    private ImageView player, largeTree, mediumTree, smallTree;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final NonCertifiedForest gameForest = (NonCertifiedForest) Game.getInstanceOfSelf().getNonCertificedForest();
    private boolean running;
    private Media sound;
    private final File punchFile = new File("src/pictures/PunchSound.wav");
    private final File chopFile = new File("src/pictures/ChoppingSound.wav");
    private ArrayList<Media> sounds = new ArrayList<>();

    public NonCertifiedController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(gameForest.roomEntrance(humanPlayer));
        Image image = new Image(humanPlayer.getCharacterModel().toURI().toString());
        player.setImage(image);
        smallTreeLabel.setText(Integer.toString(gameForest.countSmallTrees()));
        mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
            System.out.println("YOU DESTROYED THE EARTH, YOU HAVE CUT WAY TOO MUCH \n"
                + "NON CERTIFIED WOOD.");
            System.exit(0);
        }

        if (gameForest.playerCanCarryMoreTree(humanPlayer) && gameForest.thereIsMoreTreesToCut()) {
            int number = Integer.parseInt(gameForest.option1(humanPlayer));
            if (gameForest.lastTreeInArray().getTreeHealth() >= gameForest.getLARGE_TREE_SIZE()) {
                treeAnimationToLargeTree(number);
            } else {
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

    }

    private void treeAnimationToLargeTree(int numOfChops) {
        if (!running) {
            running = true;
            if (humanPlayer.getAxe() != null) {
                sound = new Media(chopFile.toURI().toString());
            } else {
                sound = new Media(punchFile.toURI().toString());
            }
            for (int i = 0; i < numOfChops; i++) {
                sounds.add(sound);
            }
            TranslateTransition transition = new TranslateTransition(Duration.seconds(3), player);
            transition.setByX(largeTree.getLayoutX() - player.getLayoutX() - 20);
            transition.setByY(-(player.getLayoutY() - largeTree.getLayoutY() - 75));
            transition.setOnFinished((ActionEvent event1) -> {
                playMediaTracks(sounds);
            });

            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(3), player);
            transition2.setByX(player.getLayoutX() - largeTree.getLayoutX());
            transition2.setByY(player.getLayoutY() - largeTree.getLayoutY());
            transition2.setDelay(Duration.millis(1500));
            transition2.setOnFinished((ActionEvent event1) -> {
                if (humanPlayer.getAxe() != null) {
                    textArea.setText("You have chopped down a tree! You are now carrying "
                        + humanPlayer.backPack().getAmountOfLogsInBackPack()
                        + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
                }
                textArea.setText("You have punched down a tree! You are now carrying "
                    + humanPlayer.backPack().getAmountOfLogsInBackPack()
                    + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
                running = false;
            });

            SequentialTransition axeTransition = new SequentialTransition(transition, transition2);
            axeTransition.play();
        } else {
        }
    }

    private void treeAnimationToMediumTree(int numOfChops) {
        if (!running) {
            running = true;
            if (humanPlayer.getAxe() != null) {
                sound = new Media(chopFile.toURI().toString());
            } else {
                sound = new Media(punchFile.toURI().toString());
            }
            for (int i = 0; i < numOfChops; i++) {
                sounds.add(sound);
            }
            TranslateTransition transition = new TranslateTransition(Duration.seconds(3), player);
            transition.setByX(mediumTree.getLayoutX() - player.getLayoutX() - 20);
            transition.setByY(-(player.getLayoutY() - mediumTree.getLayoutY() - 75));
            transition.setOnFinished((ActionEvent event1) -> {
                playMediaTracks(sounds);
            });

            TranslateTransition transition2 = new TranslateTransition(Duration.seconds(3), player);
            transition2.setByX(player.getLayoutX() - mediumTree.getLayoutX());
            transition2.setByY(player.getLayoutY() - mediumTree.getLayoutY());
            transition2.setDelay(Duration.millis(1500));
            transition2.setOnFinished((ActionEvent event1) -> {
                if (humanPlayer.getAxe() != null) {
                    textArea.setText("You have chopped down a tree! You are now carrying "
                        + humanPlayer.backPack().getAmountOfLogsInBackPack()
                        + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
                }
                textArea.setText("You have punched down a tree! You are now carrying "
                    + humanPlayer.backPack().getAmountOfLogsInBackPack()
                    + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
                running = false;
            });

            SequentialTransition axeTransition = new SequentialTransition(transition, transition2);
            axeTransition.play();
        } else {
        }
    }

    private void playMediaTracks(ArrayList<Media> sounds) {
        if (sounds.isEmpty()) {
            return;
        }
        MediaPlayer mediaplayer = new MediaPlayer(sounds.remove(0));
        mediaplayer.play();

        mediaplayer.setOnEndOfMedia(() -> {
            playMediaTracks(sounds);
        });
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameForest.option2(humanPlayer));
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Command tester = new Command(CommandWord.GO, "trailer");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            textArea.setText("There is no road!");
        }
    }

}
