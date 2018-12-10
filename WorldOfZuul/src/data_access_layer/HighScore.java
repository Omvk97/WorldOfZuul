package data_access_layer;

import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author oliver
 */
public class HighScore {

    private final static File FILE = new File("highScores.txt");
    private final static Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();

    public HighScore() {
        try (Scanner scanner = new Scanner(FILE).useDelimiter(" : |\n")) {
            try (PrintWriter writer = new PrintWriter(FILE)) {
                while (scanner.hasNext()) {
                    writer.print(scanner.next() + " : " + Double.parseDouble(scanner.next()) + "\n");
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
    }

    public void saveHighScoreToFile(String playerName, double playerHighScore) {
        try (PrintWriter writer = new PrintWriter(FILE)) {
            writer.print(playerName + " : " + playerHighScore + "\n");
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
    }

//    public void writeToHighScoreFile(double highScore) {
////        try (Scanner scanner = new Scanner(FILE).useDelimiter(" : |\n")) {
////            while (scanner.hasNext()) {
////                highScores.put(scanner.next(), Double.parseDouble(scanner.next()));
////            }
////        } catch (FileNotFoundException ex) {
////            System.out.println("The file you want to open doesn't exist");
////        }
//
//        List<String> sortedHighScoreNames = new ArrayList<>(highScores.keySet());
//        Collections.sort(sortedHighScoreNames, (String o1, String o2) -> (int) (highScores.get(o2)
//            - highScores.get(o1)));
//        LinkedHashMap<String, Double> sortedHighScores = new LinkedHashMap<>();
//        for (String playerName : sortedHighScoreNames) {
//            sortedHighScores.put(playerName, highScores.get(playerName));
//        }
//        try (PrintWriter writer = new PrintWriter(FILE)) {
//            for (String playerName : sortedHighScores.keySet()) {
//                writer.println(playerName + " : " + highScores.get(playerName));
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("The file you want to open is being used by another process");
//        }
//    }

    public String readHighScoresFromFile() {
        String content;
        try {
            content = new String(Files.readAllBytes(Paths.get("highScores.txt")));
            return content;
        } catch (IOException ex) {
        }
        return "";
    }

    public boolean isPlayerNameTaken(String playerName) {
        return readHighScoresFromFile().contains(playerName);
    }
}
