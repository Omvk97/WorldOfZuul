package ressource;

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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView player, wallet, gold;
    private boolean up, down, left, right;
    private double walletValue;
    @FXML
    private Label textArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        player.setFocusTraversable(true);
        configureKeyPressed();
        configureKeyReleased();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;
                if (up) {
                    dy -= 2;
                }
                if (down) {
                    dy += 2;
                }
                if (left) {
                    dx -= 2;
                }
                if (right) {
                    dx += 2;
                }
            }
        };
        timer.start();
    }

    private void configureKeyPressed() {
        player.setOnKeyPressed((KeyEvent key) -> {
            switch (key.getCode()) {
                case W:
                case UP:
                    up = true;
                    
                    break;
                case D:
                case RIGHT:
                    right = true;
                    break;
                case S:
                case DOWN:
                    down = true;
                    break;
                case A:
                case LEFT:
                    left = true;
                    break;
            }
        });
    }

    private void configureKeyReleased() {
        player.setOnKeyReleased((KeyEvent key) -> {
            switch (key.getCode()) {
                case W:
                case UP:
                    up = false;
                    break;
                case D:
                case RIGHT:
                    right = false;
                    break;
                case S:
                case DOWN:
                    down = false;
                    break;
                case A:
                case LEFT:
                    left = false;
                    break;
            }
        });
    }
    
    private void goRoom(){
        
    }
}
