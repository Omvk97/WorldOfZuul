package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.LocalVillage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

public class LocalVillageController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2, backBtn;
    @FXML
    private ImageView player, map, store, blacksmith, library;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final LocalVillage gameVillage = (LocalVillage) Game.getInstanceOfSelf().getLocalVillage();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition roomTransition = new TranslateTransition(Duration.seconds(1.5), player);
        switch (Game.getInstanceOfSelf().getDirection()) {
            case "goRight":
                player.setLayoutX(0);
                roomTransition.setByX(276);
                roomTransition.play();
                break;
            case "goStore":
                player.setLayoutX(store.getLayoutX());
                player.setLayoutY(store.getLayoutY());
                roomTransition.setByX(276 - store.getLayoutX());
                roomTransition.setByY(170 - store.getLayoutY());
                roomTransition.play();
                break;
            case "goBlacksmith":
                player.setLayoutX(blacksmith.getLayoutX());
                player.setLayoutY(blacksmith.getLayoutY());
                roomTransition.setByX(276 - blacksmith.getLayoutX());
                roomTransition.setByY(170 - blacksmith.getLayoutY());
                roomTransition.play();
                break;
            case "goLibrary":
                player.setLayoutX(library.getLayoutX());
                player.setLayoutY(library.getLayoutY());
                roomTransition.setByX(276 - library.getLayoutX());
                roomTransition.setByY(170 - library.getLayoutY());
                roomTransition.play();
                break;
        }

        textArea.setText(gameVillage.roomEntrance(humanPlayer));
        File file = new File("src/pictures/baseCharacter.png");
        Image image = new Image(file.toURI().toString());
        player.setImage(image);
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        textArea.setText(gameVillage.option1(humanPlayer));
    }

    @FXML
    private void handleOption2(MouseEvent event) {
        textArea.setText(gameVillage.option2(humanPlayer));
    }
    
    @FXML
    private void handleBackBtn(MouseEvent event){
        TranslateTransition transistionToTrailer = new TranslateTransition(Duration.seconds(1.5), player);
            transistionToTrailer.setByX(-276);
            transistionToTrailer.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "trailer");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionToTrailer.play();
            Game.getInstanceOfSelf().setDirection("goTrailer");
    }

    @FXML
    private void handleGoToStore(MouseEvent event) {
        TranslateTransition transistionToStore = new TranslateTransition(Duration.seconds(1.5), player);
        transistionToStore.setByX(store.getLayoutX() - 276);
        transistionToStore.setByY(store.getLayoutY() - 170);
        transistionToStore.setOnFinished((ActionEvent) -> {
            Command tester = new Command(CommandWord.GO, "store");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        });
        transistionToStore.play();
        Game.getInstanceOfSelf().setDirection("goStore");
    }

    @FXML
    private void handleGoToBlacksmith(MouseEvent event) {
        TranslateTransition transistionToBlacksmith = new TranslateTransition(Duration.seconds(1.5), player);
        transistionToBlacksmith.setByX(blacksmith.getLayoutX() - 276);
        transistionToBlacksmith.setByY(blacksmith.getLayoutY() - 170);
        transistionToBlacksmith.setOnFinished((ActionEvent) -> {
            Command tester = new Command(CommandWord.GO, "blacksmith");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        });
        transistionToBlacksmith.play();
        Game.getInstanceOfSelf().setDirection("goBlacksmith");
    }

    @FXML
    private void handleGoToLibrary(MouseEvent event) {
        TranslateTransition transistionToLibrary = new TranslateTransition(Duration.seconds(1.5), player);
        transistionToLibrary.setByX(library.getLayoutX() - 276);
        transistionToLibrary.setByY(library.getLayoutY() - 170);
        transistionToLibrary.setOnFinished((ActionEvent) -> {
            Command tester = new Command(CommandWord.GO, "library");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        });
        transistionToLibrary.play();
        Game.getInstanceOfSelf().setDirection("goLibrary");
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
            TranslateTransition transistionToTrailer = new TranslateTransition(Duration.seconds(1.5), player);
            transistionToTrailer.setByX(-276);
            transistionToTrailer.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "trailer");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionToTrailer.play();
            Game.getInstanceOfSelf().setDirection("goTrailer");
        } else {
            textArea.setText("There is no road!");
        }
    }
}
