package ressource;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author olive
 */
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
        configureMousePressed();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println("X: " + player.getLayoutX());
                System.out.println("Y: " + player.getLayoutY());
                int dx = 0, dy = 0;
                if (up) {
                    dy -= 2;
                }
                if (down) {
                    dy += 20;
                }
                if (left) {
                    dx -= 2;
                }
                if (right) {
                    dx += 2;
                }
                movePlayerBy(dx, dy);
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
    
    private double playerCharacterWidth() {
        return player.getBoundsInLocal().getWidth() / 2;
    }
    
    private double playerCharacterHeight() {
        return player.getBoundsInLocal().getHeight() / 2;
    }
    
    private void movePlayerBy(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return;
        }

        double x = playerCharacterWidth() + player.getLayoutX() + dx;
        double y = playerCharacterHeight() + player.getLayoutY() + dy;

        movePlayerTo(x, y);
    }

    private void movePlayerTo(double x, double y) {
        
        if (x - playerCharacterWidth() >= 0 && x + playerCharacterWidth() <= player.getScene().getWidth()
            && y - playerCharacterHeight() >= 0 && y + playerCharacterHeight() <= player.getScene().getHeight()) {
            player.relocate(x - playerCharacterWidth(), y - playerCharacterHeight());
        }
    }

    private void configureMousePressed() {
        wallet.setOnMouseClicked((MouseEvent event) -> {
            if (player.getLayoutX() <= 70 && player.getLayoutY() <= 2) {
                textArea.setText("Your wallet contains " + walletValue + " gold coins!");
            } else {
                textArea.setText("You are too far away from your wallet!");
            }
        });

        gold.setOnMouseClicked((MouseEvent event) -> {
            if (player.getLayoutX() >= 450 && player.getLayoutY() >= 300) {
                textArea.setText("CASH DOLLAR YEEEEEA");
                walletValue += 10;
            } else {
                textArea.setText("Get closer idiot! (You aren't an idiot btw)");
            }
        });
    }
}
