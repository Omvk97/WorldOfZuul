package ressource;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.CommandWords;
import game_functionality.Game;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView player, wallet, gold;
    private boolean up, down, left, right;
    private double walletValue;
    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    private CommandWords commands = new CommandWords();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game game = new Game();
        game.play();
        player.setOnMouseClicked((MouseEvent key) -> {
//            if(key.getCode().equals(KeyCode.LEFT)) {
            System.out.println("hello");
            game.goRoom(new Command(commands.getCommandWord("go"), "south"), anchorPane);
//            anchorPane.getChildren().setAll(game.getHumanPlayer().getCurrentRoom().getRoomFXML());
//            }
        });

    }

}
