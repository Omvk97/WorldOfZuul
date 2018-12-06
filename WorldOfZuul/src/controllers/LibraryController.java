package controllers;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Library;
import java.net.URL;
import java.util.ResourceBundle;
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
import jdk.nashorn.internal.ir.BreakNode;

public class LibraryController implements Initializable {

    @FXML
    private Label textArea, BookTextArea1, BookTextArea2, Title, by;
    @FXML
    private AnchorPane anchorPane, Bookshelf, BookText;
    @FXML
    private Button option1, backBtn;
    @FXML
    private ImageView player, map;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Library gameLibrary = (Library) Game.getInstanceOfSelf().getLibrary();
    private boolean running;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transition();
        textArea.setText(gameLibrary.roomEntrance(humanPlayer));
        BookTextArea1.setWrapText(true);
        BookTextArea2.setWrapText(true);
        BookText.setVisible(false);
        Bookshelf.setVisible(false);
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
    }

    @FXML
    private void handleOption1(MouseEvent event) {
        Bookshelf.setVisible(true);
        backBtn.setDisable(true);
        option1.setDisable(true);
        textArea.setDisable(true);
    }

    @FXML
    private void handleClose(MouseEvent event) {
        if (BookText.visibleProperty().getValue() == true) {
            BookText.setVisible(false);
            Bookshelf.setVisible(true);
        } else {
            Bookshelf.setVisible(false);
            backBtn.setDisable(false);
            option1.setDisable(false);
            textArea.setDisable(false);

        }
    }

    @FXML
    private void handleBook1(MouseEvent event) {
        BookText.setVisible(true);
        BookTextArea1.setText("Around the globe 7 million hectare of forest"
                + " area disappear each year due to deforestation.\n"
                + "The decreased forest areas causes"
                + " several species to become endangered.\n");
        BookTextArea2.setText("It is estimated that 15% of all greenhouse"
                + " gas emissions are the result\n"
                + "of deforestation.\n This is very bad.");
        Title.setText("The Falling of the trees");
        by.setText("- written by professors.");
    }

    @FXML
    private void handleBook2(MouseEvent event) {
        BookText.setVisible(true);
        BookTextArea1.setText("FSC, Forest Stewardship Council, is one"
                + " of several large organizations"
                + " dedicated to combat deforestation.\n");
        BookTextArea2.setText("Their main function"
                + " involves certifying and regulating"
                + "forest areas to reduce reckless deforestation."
                + "They cover over 200 million hectare certified"
                + " forest areas globally.");
        Title.setText("The Story of FSC");
        by.setText("- written by FSC.");
    }

    @FXML
    private void handleBook3(MouseEvent event) {
        BookText.setVisible(true);
        BookTextArea1.setText("PEFC, Programme for the Endorsement of Forest Certification, is the largest"
                + " organization dedicated to combat deforestation.");
        BookTextArea2.setText("Their main function involves certifying and regulating "
                + "forest areas to reduce reckless deforestation."
                + "They cover over 300 million hectare certified forest areas globally.");
        Title.setText("The Story of PEFC");
        by.setText(" - written by PEFC.");
    }

    @FXML
    private void handleBackBtn(MouseEvent event) {
        BookText.setVisible(true);
        if (!running) {
            running = true;
            TranslateTransition transistionFromLibrary = new TranslateTransition(Duration.seconds(1.5), player);
            transistionFromLibrary.setByX(-276);
            transistionFromLibrary.setOnFinished((ActionEvent) -> {
                Command tester = new Command(CommandWord.GO, "back");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            });
            transistionFromLibrary.play();
        }
    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (!running && !((Bookshelf.visibleProperty().getValue() == true) || (BookText.visibleProperty().getValue() == true))) {
            running = true;
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                TranslateTransition transistionFromLibrary = new TranslateTransition(Duration.seconds(1.5), player);
                transistionFromLibrary.setByX(-276);
                transistionFromLibrary.setOnFinished((ActionEvent) -> {
                    Command tester = new Command(CommandWord.GO, "back");
                    Game.getInstanceOfSelf().goRoom(tester, anchorPane);
                });
                transistionFromLibrary.play();
            } else {
                textArea.setText("There is no road!");
                running = false;
            }
        }
    }

    private void transition() {
        running = true;
        backBtn.setDisable(true);
        option1.setDisable(true);
        TranslateTransition roomTransition = new TranslateTransition(Duration.seconds(1.5), player);
        if (Game.getInstanceOfSelf().getDirection().equals("goLibrary")) {
            player.setLayoutX(0);
            roomTransition.setByX(276);
            roomTransition.setOnFinished((ActionEvent) -> {
                running = false;
                backBtn.setDisable(false);
                option1.setDisable(false);
            });
            roomTransition.play();
        }
    }
}
