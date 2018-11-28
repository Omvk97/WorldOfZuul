package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.NonCertifiedForest;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2;
    @FXML
    private ImageView player, largeTree, mediumTree, smallTree;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final NonCertifiedForest gameForest = (NonCertifiedForest) Game.getInstanceOfSelf().getNonCertificedForest();

    public NonCertifiedController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(gameForest.roomEntrance(humanPlayer));
        Image image = new Image(humanPlayer.getCharacterModel().toURI().toString());
        player.setImage(image);
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        File punchFile = new File("src/pictures/PunchSound.wav");
        Media punchSound = new Media(punchFile.toURI().toString());

        TranslateTransition transition = new TranslateTransition(Duration.seconds(3), player);
        transition.setByX(largeTree.getLayoutX() - player.getLayoutX());
        transition.setByY(-(player.getLayoutY() - largeTree.getLayoutY()));

        transition.setOnFinished((ActionEvent event1) -> {
            textArea.setText(Integer.toString(gameForest.lastTreeInArray().getTreeHealth()));
            ArrayList<Media> sounds = new ArrayList<>();
            int number = Integer.parseInt(gameForest.option1(humanPlayer));
            for (int i = 0; i < number; i++) {
                sounds.add(punchSound);
            }
            playMediaTracks(sounds);
        });

        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(3), player);
        transition2.setByX(player.getLayoutX() - largeTree.getLayoutX());
        transition2.setByY(player.getLayoutY() - largeTree.getLayoutY());
        transition2.setOnFinished((ActionEvent event1) -> {
            textArea.setText("You have punched down a tree! You are now carrying "
                + humanPlayer.backPack().getAmountOfLogsInBackPack()
                + (humanPlayer.backPack().getAmountOfLogsInBackPack() > 1 ? " logs" : " log"));
        });

        SequentialTransition axeTransition = new SequentialTransition(transition, transition2);
        axeTransition.play();
    }

    private void playMediaTracks(ArrayList<Media> mediaList) {
        if (mediaList.isEmpty()) {
            return;
        }

        MediaPlayer mediaplayer = new MediaPlayer(mediaList.remove(0));
        mediaplayer.play();

        mediaplayer.setOnEndOfMedia(() -> {
            playMediaTracks(mediaList);
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
