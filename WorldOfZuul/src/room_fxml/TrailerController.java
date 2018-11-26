package room_fxml;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.CommandWords;
import game_functionality.Game;
import game_functionality.Player;
import game_locations.Trailer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class TrailerController extends Trailer implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button option1, option2, option3;
    @FXML
    private ImageView player, map, option4;
    private CommandWords commands;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(this.roomEntrance(null));
        configureOption1();
        configureOption2();
        configureOption3();
        configureOption4();
        setExits();
    }

    private void configureOption1() {
        option1.setOnMouseClicked((MouseEvent event) -> {
            option1(humanPlayer, textArea);
        });
    }

    private void configureOption2() {
        option2.setOnMouseClicked((MouseEvent event) -> {
            option2(humanPlayer, textArea);
        });
    }

    private void configureOption3() {
        option3.setOnMouseClicked((MouseEvent event) -> {
            option3(Game.getInstanceOfSelf().getHumanPlayer(), textArea);
        });
    }

    private void configureOption4() {
        option4.setOnMouseClicked((MouseEvent event) -> {
            option4(Game.getInstanceOfSelf().getHumanPlayer(), textArea);
        });
    }

    private void setExits() {
        anchorPane.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.W)) {
                Command tester = new Command(CommandWord.GO, "north");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane, textArea);
            } else if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
                Command tester = new Command(CommandWord.GO, "south");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane, textArea);
            } else if (event.getCode().equals(KeyCode.RIGHT) || event.getCode().equals(KeyCode.D)) {
                Command tester = new Command(CommandWord.GO, "village");
                Game.getInstanceOfSelf().goRoom(tester, anchorPane, textArea);
            } else {
                textArea.setText("There is no door that way mister!");
            }
        });
    }

}
