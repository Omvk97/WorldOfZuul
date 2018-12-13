package view_layer;

import data_access_layer.HighScore;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;

/**
 * This class has access to the local highScore file and handles how the highScore data should
 * be displayed.
 * @author oliver
 */
public class HighScoreGraphics {
    private final HighScore highScore = Game.getInstanceOfSelf().getHighScoreData();
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    
    /**
     * This method is to be called from all the places in the game where the game needs to close
     * and record the highScore that the player has achieved.
     */
    public void closeGame() {
        Dialog nameDialog = new Dialog();
        nameDialog.setTitle("Player Name");
        nameDialog.setHeaderText("Your highscore is: " + humanPlayer.getHighScore() + "!\n"
            + "Please enter the name you want assosiacted with your highscore!");
        nameDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        TextField playerInputField = new TextField();
        playerInputField.setMinHeight(25);
        playerInputField.setMinWidth(25);
        Label playNameLabel = new Label("Name: ");
        playNameLabel.setMinHeight(25);
        playNameLabel.setMinWidth(25);
        playNameLabel.setAlignment(Pos.CENTER);
        HBox layout = new HBox();
        layout.setPadding(new Insets(20, 150, 10, 10));
        layout.setSpacing(10);
        layout.getChildren().addAll(playNameLabel, playerInputField);
        nameDialog.getDialogPane().setContent(layout);
        nameDialog.initStyle(StageStyle.UTILITY);
        nameDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK && playerInputField.getText().length() > 1) {
                return playerInputField.getText();
            }
            return null;
        });

        Platform.runLater(() -> {
            playerInputField.requestFocus();
        });
        Optional result = nameDialog.showAndWait();
        if (result.isPresent()) {
            while (highScore.isPlayerNameTaken((String) result.get())){
                nameDialog.close();
                nameDialog.setHeaderText("That name is already being used!");
                result = nameDialog.showAndWait();
            }
            highScore.saveHighScoreToFile((String) result.get(), humanPlayer.getHighScore());
        }
        highScoreOverViewDialog();
    }
    
    /**
     * This methods opens up an alert which gives an overview of how the highScore looks, it
     * makes sure that the highScore from highScore.txt is ordered with the highest highscore on 
     * top.
     */
    public void highScoreOverViewDialog() {
        Alert overViewOfAllHighScores = new Alert(Alert.AlertType.INFORMATION);
        overViewOfAllHighScores.initStyle(StageStyle.UTILITY);
        overViewOfAllHighScores.setTitle("All HighScores");
        overViewOfAllHighScores.setHeaderText("Overview of all highscores achieved throughout the "
            + "span of time");
        HashMap<String, Double> highScores = highScore.readHighScoresFromFile();
        List<String> sortedHighScoreNames = new ArrayList<>(highScores.keySet());
        Collections.sort(sortedHighScoreNames, (String o1, String o2) -> (int) (highScores.get(o2)
            - highScores.get(o1)));
        StringBuilder highScoreString = new StringBuilder();
        for (String playerName : sortedHighScoreNames) {
            highScoreString.append(playerName).append(" : ").append(highScores.get(playerName)).append("\n");
        }
        overViewOfAllHighScores.setContentText(highScoreString.toString());
        overViewOfAllHighScores.showAndWait();
    }

}