package view_layer.controllers;

import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.Library;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import view_layer.room_animations.GameAnimation;

/**
 * This controller class handles the UI and player interactions.
 *
 * @author Michael
 */
public class LibraryController implements Initializable {

    @FXML
    private Label textArea, BookTextArea1, BookTextArea2, Title, by;
    @FXML
    private AnchorPane anchorPane, Bookshelf, BookText;
    @FXML
    private Button option1, backBtn;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Library gameLibrary = (Library) Game.getInstanceOfSelf().getLibrary();
    private final GameAnimation animation = new GameAnimation(null);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        animation.textAnimation(textArea, gameLibrary.roomEntrance(humanPlayer));
        BookTextArea1.setWrapText(true);
        BookTextArea2.setWrapText(true);
        BookText.setVisible(false);
        Bookshelf.setVisible(false);
    }

    /**
     * Shows the Bookshelf anchor pane
     *
     * @param event
     */
    @FXML
    private void handleOption1(MouseEvent event) {
        Bookshelf.setVisible(true);
        backBtn.setDisable(true);
        option1.setDisable(true);
        textArea.setDisable(true);
    }

    /**
     * Closes the Book anchorpane if its open and if not closes bookshelf anchor pane.
     *
     * @param event
     */
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

    /**
     * Shows the book anchor pane and shows text.
     *
     * @param event
     */
    @FXML
    private void handleBook1(MouseEvent event) {
        BookText.setVisible(true);
        BookTextArea1.setText("Each year, around the globe, 7-million-hectare forest"
            + " area disappears due to deforestation.\n"
            + "The decreased forest area causes several species to become endangered\n");
        BookTextArea2.setText("It is estimated that 15% of all greenhouse"
            + " gas emissions are the result \n"
            + "of deforestation.\n This is very bad. Subscribe to pewdiepie *CLAP* *CLAP*");
        Title.setText("The Felling of trees");
        by.setText("- written by professors.");
    }

    /**
     * Shows the book anchor pane and shows text.
     *
     * @param event
     */
    @FXML
    private void handleBook2(MouseEvent event) {
        BookText.setVisible(true);
        BookTextArea1.setText("FSC - Forest Stewardship Council, is one"
            + " of several large organizations"
            + " dedicated to fight deforestation.\n");
        BookTextArea2.setText("In order to reduce reckless deforestation, FSC certifies and regulates forest areas.\n"
            + "They cover 200 million certified hectare forest area globally.");
        Title.setText("The Story of FSC");
        by.setText("- written by FSC.");
    }

    /**
     * Shows the book anchor pane and shows text.
     *
     * @param event
     */
    @FXML
    private void handleBook3(MouseEvent event) {
        BookText.setVisible(true);
        BookTextArea1.setText("PEFC - Programme for the Endorsement of Forest Certification. Is the largest"
            + " organization dedicated to combat deforestation.");
        BookTextArea2.setText("In order to reduce reckless deforestation, PEFC certifies and regulates forest areas."
            + "They cover 300 million hectare certified forest areas globally.");
        Title.setText("The Story of PEFC");
        by.setText(" - written by PEFC.");
    }

    /**
     * sends the player back to the previus room when clicked.
     *
     * @param event
     */
    @FXML
    private void handleBackBtn(MouseEvent event) {
        BookText.setVisible(true);
        Command tester = new Command(CommandWord.GO, "back");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }

    /**
     * sends the player back to the previus room when key event occours.
     *
     * @param event
     */
    @FXML
    private void handleExits(KeyEvent event) {
        if (!((Bookshelf.visibleProperty().getValue() == true) || (BookText.visibleProperty().getValue() == true))) {
            if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                Command tester = new Command(CommandWord.GO, "back");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane);
            } else {
                animation.textAnimation(textArea, "There is no road!");
            }
        }
    }
}
