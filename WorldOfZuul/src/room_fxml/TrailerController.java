package room_fxml;

import game_functionality.Game;
import game_locations.Trailer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textArea.setText(this.roomEntrance(null));
        configureOption1();
        configureOption2();
        configureOption3();
        configureOption4();
    }

    private void configureOption1() {
        option1.setOnMouseClicked((MouseEvent event) -> {
            option1(Game.getInstanceOfSelf().getHumanPlayer(), textArea);
        });
    }

    private void configureOption2() {
        option1.setOnMouseClicked((MouseEvent event) -> {
            option1(Game.getInstanceOfSelf().getHumanPlayer(), textArea);
        });
    }

    private void configureOption3() {
        option1.setOnMouseClicked((MouseEvent event) -> {
            option1(Game.getInstanceOfSelf().getHumanPlayer(), textArea);
        });
    }

    private void configureOption4() {
        option1.setOnMouseClicked((MouseEvent event) -> {
            option1(Game.getInstanceOfSelf().getHumanPlayer(), textArea);
        });
    }

}
