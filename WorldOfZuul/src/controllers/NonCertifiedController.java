package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
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
    private ImageView player, largeTree, mediumTree;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final NonCertifiedForest gameForest = (NonCertifiedForest) Game.getInstanceOfSelf().getNonCertificedForest();
    private boolean running;
    private final File punchFile = new File("src/pictures/PunchSound.wav");
    private final Media punchSound = new Media(punchFile.toURI().toString());
    private final File chopFile = new File("src/pictures/ChoppingSound.wav");
    private final Media chopSound = new Media(chopFile.toURI().toString());
    private final File treeFallingFile = new File("src/pictures/treeFallingSound.wav");
    private final Media treeFallingSound = new Media(treeFallingFile.toURI().toString());
    private final ArrayList<Media> sounds = new ArrayList<>();

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
        if (!running) {
            System.out.println("Hello there");
            if (humanPlayer.getClimatePoints() == Player.getMIN_CLIMATEPOINTS()) {
                System.out.println("YOU DESTROYED THE EARTH, YOU HAVE CUT WAY TOO MUCH \n"
                    + "NON CERTIFIED WOOD.");
                System.exit(0);
            }

            if (gameForest.playerCanCarryMoreTree(humanPlayer) && gameForest.thereIsMoreTreesToCut()) {
                if (gameForest.lastTreeInArray().getTreeHealth() >= gameForest.getLARGE_TREE_SIZE()) {
                    int number = Integer.parseInt(gameForest.option1(humanPlayer));
                    treeAnimationToLargeTree(number);
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

        }
    }

    private void treeAnimationToLargeTree(int numOfChops) {
        running = true;
        if (humanPlayer.getAxe() != null) {
            for (int i = 0; i < numOfChops; i++) {
                sounds.add(chopSound);
            }
        } else {
            for (int i = 0; i < numOfChops; i++) {
                sounds.add(punchSound);
            }
        }
        sounds.add(treeFallingSound);

        TranslateTransition goToTree = new TranslateTransition(Duration.seconds(3), player);
        goToTree.setByX((largeTree.getLayoutX() - player.getLayoutX()) - 20);
        goToTree.setByY(-(player.getLayoutY() - largeTree.getLayoutY()) + 75);
        goToTree.setOnFinished((ActionEvent event1) -> {
            playMediaTracks(sounds);
        });

        TranslateTransition goFromTree = new TranslateTransition(Duration.seconds(3), player);
        goFromTree.setByX((player.getLayoutX() - largeTree.getLayoutX()) + 20);
        goFromTree.setByY((player.getLayoutY() - largeTree.getLayoutY()) - 75);
        goFromTree.setDelay(Duration.millis(2500));
        goFromTree.setOnFinished((ActionEvent event1) -> {
            treeFelledConfirmation();
            largeTreeLabel.setText(Integer.toString(gameForest.countLargeTrees()));
        });

        SequentialTransition transition = new SequentialTransition(goToTree, goFromTree);
        transition.play();
    }

    private void treeAnimationToMediumTree(int numOfChops) {
        running = true;
        if (humanPlayer.getAxe() != null) {
            for (int i = 0; i < numOfChops; i++) {
                sounds.add(chopSound);
            }
        } else {
            for (int i = 0; i < numOfChops; i++) {
                sounds.add(punchSound);
            }
        }
        sounds.add(treeFallingSound);
        TranslateTransition goToTree = new TranslateTransition(Duration.seconds(3), player);
        goToTree.setByX((mediumTree.getLayoutX() - player.getLayoutX()) + 55);
        goToTree.setByY(-(player.getLayoutY() - mediumTree.getLayoutY()) + 60);
        goToTree.setOnFinished((ActionEvent event1) -> {
            playMediaTracks(sounds);
        });

        TranslateTransition goFromTree = new TranslateTransition(Duration.seconds(3), player);
        goFromTree.setByX((player.getLayoutX() - mediumTree.getLayoutX()) - 55);
        goFromTree.setByY((player.getLayoutY() - mediumTree.getLayoutY()) - 60);
        goFromTree.setDelay(Duration.millis(2000));
        goFromTree.setOnFinished((ActionEvent event1) -> {
            treeFelledConfirmation();
            mediumTreeLabel.setText(Integer.toString(gameForest.countMediumTrees()));
        });

        SequentialTransition transition = new SequentialTransition(goToTree, goFromTree);
        transition.play();
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
    
    private void treeFelledConfirmation() {
        if (humanPlayer.getAxe() != null) {
                textArea.setText("You have chopped down a tree! You are now carrying "
                    + humanPlayer.backPack().getAmountOfLogsInBackPack()
                    + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
                double numOfHits = humanPlayer.useAxe();
                if (numOfHits == 0.5) {
                    textArea.setText("Your axe is at half durability!");
                } else if (numOfHits == 0) {
                    textArea.setText("Your axe broke!");
                    humanPlayer.setCharacterModel(null);
                    player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
                }
            } else {
                textArea.setText("You have punched down a tree! You are now carrying "
                    + humanPlayer.backPack().getAmountOfLogsInBackPack()
                    + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
            }
            running = false;
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
