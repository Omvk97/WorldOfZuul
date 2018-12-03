package DataLayer;

import game_functionality.Game;
import game_functionality.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
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

public class HighScore {

    private final static File FILE = new File("highScores.txt");
    private final static Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final static HashMap<String, Double> highScores = new HashMap<>();

    private HighScore() {
    }

    private static double getHighScore() {
        return humanPlayer.getTotalValue() + humanPlayer.getClimatePoints();
    }

    private static void saveHighScore(String playerName, double playerHighScore) {
        highScores.put(playerName, playerHighScore);
    }

    private static void writeToHighScoreFile() {
        try (Scanner scanner = new Scanner(FILE).useDelimiter(" : |\n")) {
            String name = "";
            double highScore = 0.0;
            while (scanner.hasNext()) {
                name = scanner.next();
                highScore = Double.parseDouble(scanner.next());
                highScores.put(name, highScore);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
//        List<Map.Entry<String, Double>> tester = new ArrayList<>(highScores.entrySet());
//        System.out.println(tester);

        try (PrintWriter writer = new PrintWriter(FILE)) {
            for (Map.Entry<String, Double> highScore : highScores) {
                writer.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file you want to open is being used by another process");
        }
    }

    private static String readHighScoresFromFile() {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get("highScores.txt")));
            return content;
        } catch (IOException ex) {
        }
        return "";
    }

    public static void closeGame() {
        Dialog nameDialog = new Dialog();
        nameDialog.setTitle("Player Name");
        nameDialog.setHeaderText("Your highscore is: " + getHighScore() + "!\n"
            + "Please enter the name you want assosiacted with your highscore!");
        nameDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
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
        Optional<String> result = nameDialog.showAndWait();
        playerInputField.requestFocus();
        if (result.isPresent()) {
            saveHighScore(result.get(), getHighScore());
            writeToHighScoreFile();
        }

        Alert overViewOfAllHighScores = new Alert(Alert.AlertType.INFORMATION);
        overViewOfAllHighScores.initStyle(StageStyle.UTILITY);
        overViewOfAllHighScores.setTitle("All HighScores");
        overViewOfAllHighScores.setHeaderText("Overview of all highscores achieved throughout the span of time");
        overViewOfAllHighScores.setContentText(HighScore.readHighScoresFromFile());

        overViewOfAllHighScores.showAndWait();
    }

}
