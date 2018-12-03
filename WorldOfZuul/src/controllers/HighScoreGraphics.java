package controllers;

import DataLayer.HighScore;
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

public class HighScoreGraphics {
    private final HighScore highScore = new HighScore();
    
    public void closeGame() {
        Dialog nameDialog = new Dialog();
        nameDialog.setTitle("Player Name");
        nameDialog.setHeaderText("Your highscore is: " + highScore.getHighScore() + "!\n"
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
        highScore.writeToHighScoreFile();
        Optional result = nameDialog.showAndWait();
        if (result.isPresent()) {
            while (highScore.isPlayerNameTaken((String) result.get())){
                nameDialog.close();
                nameDialog.setHeaderText("That name is already being used!");
                result = nameDialog.showAndWait();
            }
            highScore.saveHighScore((String) result.get(), highScore.getHighScore());
            highScore.writeToHighScoreFile();
        }

        Alert overViewOfAllHighScores = new Alert(Alert.AlertType.INFORMATION);
        overViewOfAllHighScores.initStyle(StageStyle.UTILITY);
        overViewOfAllHighScores.setTitle("All HighScores");
        overViewOfAllHighScores.setHeaderText("Overview of all highscores achieved throughout the span of time");
        overViewOfAllHighScores.setContentText(highScore.readHighScoresFromFile());

        overViewOfAllHighScores.showAndWait();
    }

}