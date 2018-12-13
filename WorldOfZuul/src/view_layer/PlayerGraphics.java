package view_layer;

import domain_layer.game_elements.Axe;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class responsibility is to update and keep a record of what the player's image should be It
 * is a singleton in order to make sure that all images that represent the player is using the same
 * character model File.
 *
 * @author oliver
 */
public class PlayerGraphics {

    private final File baseModelFile = new File("src/pictures/baseCharacter.png");
    private final File baseModelRightFile = new File("src/pictures/baseCharacterRight.png");
    private final File modelStarterAxeFile = new File("src/pictures/characterWithStarterAxe.png");
    private final File modelStarterAxeRightFile = new File("src/pictures/characterWithStarterAxeRight.png");
    private final File modelIronAxeFile = new File("src/pictures/characterWithIronAxe.png");
    private final File modelIronAxeRightFile = new File("src/pictures/characterWithIronAxeRight.png");
    private final File modelSteelAxeFile = new File("src/pictures/characterWithSteelAxe.png");
    private final File modelSteelAxeRightFile = new File("src/pictures/characterWithSteelAxeRight.png");
    private final File modelDiamondAxeFile = new File("src/pictures/characterWithDiamondAxe.png");
    private final File modelDiamondAxeRightFile = new File("src/pictures/characterWithDiamondAxeRight.png");
    private final File modelFireAxeFile = new File("src/pictures/characterWithFireAxe.png");
    private final File modelFireAxeRightFile = new File("src/pictures/characterWithFireAxeRight.png");
    private File characterModel = baseModelFile;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private static final PlayerGraphics instance = new PlayerGraphics();

    private PlayerGraphics() {
    }

    public static PlayerGraphics getInstanceOfSelf() {
        return instance;
    }

    /**
     * A method that combines setCharactModel and updateCharacterModel, in the rooms that uses both
     * right after eachother when the model both needs to be updated and the player needs to be set.
     *
     * @param characterGoingRight the players walking direction
     * @param playerImage the image representation of Player
     */
    public void setAndUpdateCharacterModel(boolean characterGoingRight, ImageView playerImage) {
        setCharacterModel(characterGoingRight);
        updateCharacterModel(playerImage);
    }

    /**
     * Used to set the player image in all rooms to the correct image.
     *
     * @param playerImage the image representation of Player
     */
    public void updateCharacterModel(ImageView playerImage) {
        playerImage.setImage(new Image(characterModel.toURI().toString()));
    }

    /**
     * automatically figures out what image the player should be depending on what axe is equipped
     * and if the player's walking direction should be left or right.
     *
     * @param characterGoingRight the players walking direction
     */
    public void setCharacterModel(boolean characterGoingRight) {
        if (!characterGoingRight) {
            if (humanPlayer.getEquippedAxe() == null) {
                characterModel = baseModelFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("starter axe")) {
                characterModel = modelStarterAxeFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("iron axe")) {
                characterModel = modelIronAxeFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("steel axe")) {
                characterModel = modelSteelAxeFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("diamond axe")) {
                characterModel = modelDiamondAxeFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("fire axe")) {
                characterModel = modelFireAxeFile;
            }
        } else {
            if (humanPlayer.getEquippedAxe() == null) {
                characterModel = baseModelRightFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("starter axe")) {
                characterModel = modelStarterAxeRightFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("iron axe")) {
                characterModel = modelIronAxeRightFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("steel axe")) {
                characterModel = modelSteelAxeRightFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("diamond axe")) {
                characterModel = modelDiamondAxeRightFile;
            } else if (humanPlayer.getEquippedAxe().getDescription().equals("fire axe")) {
                characterModel = modelFireAxeRightFile;
            }
        }
    }
}
