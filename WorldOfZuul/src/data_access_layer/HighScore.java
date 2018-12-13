package data_access_layer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * saves and loads local file highScore.txt and returns a hashMap representation of the file
 * so that other classes can decide what to do with the data.
 * @author oliver
 */
public class HighScore {

    private final File FILE = new File("highScores.txt");

    /**
     * when a new highScore is achieved this methods is to be called.
     * @param playerName the player who has achieved the new highScore
     * @param playerHighScore the players new highscore.
     */
    public void saveHighScoreToFile(String playerName, double playerHighScore) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(FILE, true))) {
            writer.print(playerName + "," + playerHighScore + "\n");
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
    }

    /**
     * Reads all the data from highScore file
     * @return hashMap representation of the data from the file.
     */
    public HashMap<String, Double> readHighScoresFromFile() {
        HashMap<String, Double> highScoresHashMap = new HashMap<>();
        try (Scanner scanner = new Scanner(FILE).useDelimiter(",|\n")) {
            while (scanner.hasNext()) {
                highScoresHashMap.put(scanner.next(), Double.parseDouble(scanner.next()));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("The file you want to open doesn't exist");
        }
        return highScoresHashMap;
    }

    public boolean isPlayerNameTaken(String playerName) {
        return readHighScoresFromFile().containsKey(playerName);
    }
}
