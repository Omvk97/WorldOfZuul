package ressource;

import game_functionality.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MakingCharacterMove extends Application {
    Game game = new Game();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage Primarystage) throws Exception {
        game.start(Primarystage);
    }

}
