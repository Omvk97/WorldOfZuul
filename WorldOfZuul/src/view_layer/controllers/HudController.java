package view_layer.controllers;

import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_functionality.PlayerInteraction;
import domain_layer.game_locations.Trailer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author oliver
 */
public class HudController implements Initializable {

    @FXML
    private Label playerGold, playerClimate, logsCarrying, logsInStorage, saplingsCarrying;
    @FXML
    private ProgressBar durabilityBar;
    @FXML
    private ImageView playerWeapon;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final Trailer trailer = Game.getInstanceOfSelf().getTrailer();
    private final PlayerInteraction playerInteraction = PlayerInteraction.getInstanceOfSelf();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerGold.setText(humanPlayer.getMoneyValue() + " Gold");
        playerClimate.setText(humanPlayer.getClimatePointsValue() + " CP");
        int logsCarryingNum = humanPlayer.getLogsInBackPack().getValue();
        logsCarrying.setText(logsCarryingNum + (logsCarryingNum == 1 ? " Log" : " Logs"));
        int logsInStorageNum = trailer.getLogsInStorageProperty().getValue();
        logsInStorage.setText(logsInStorageNum + (logsInStorageNum == 1 ? " Log" : " Logs"));
        int saplingsCarryingNum = humanPlayer.getSaplingsCarryingValue();
        saplingsCarrying.setText(saplingsCarryingNum + (saplingsCarryingNum == 1 ? " Sapling" : " Saplings"));
        updateAxeHudImage();
        addListenersToLabels();
    }

    private void addListenersToLabels() {
        humanPlayer.getMoney().addListener((observable, oldValue, newValue) -> {
            playerGold.setText(humanPlayer.getMoneyValue() + " Gold");
        });

        humanPlayer.getClimatePoints().addListener((observable, oldValue, newValue) -> {
            playerClimate.setText(humanPlayer.getClimatePointsValue() + " CP");
        });

        humanPlayer.getLogsInBackPack().addListener((observable, oldValue, newValue) -> {
            int logsCarryingNum = humanPlayer.getLogsInBackPack().getValue();
            logsCarrying.setText(logsCarryingNum + (logsCarryingNum == 1 ? " Log" : " Logs"));
        });

        trailer.getLogsInStorageProperty().addListener((observable, oldValue, newValue) -> {
            int logsInStorageNum = trailer.getLogsInStorageProperty().getValue();
            logsInStorage.setText(logsInStorageNum + (logsInStorageNum == 1 ? " Log" : " Logs"));
        });

        humanPlayer.getSaplingsCarrying().addListener((observable, oldValue, newValue) -> {
            int saplingsCarryingNum = humanPlayer.getSaplingsCarryingValue();
            saplingsCarrying.setText(saplingsCarryingNum + (saplingsCarryingNum == 1 ? " Sapling" : " Saplings"));
        });

        playerInteraction.getEquippedAxeChange().addListener(((observable, oldValue, newValue) -> {
            updateAxeHudImage();
        }));

        if (humanPlayer.playerHasAnAxe()) {
            humanPlayer.getEquippedAxe().getDurabilityIntegerProperty().addListener((observable, oldValue, newValue) -> {
                double durabilityProgress = humanPlayer.getAxeDurabilityPercentage();
                durabilityBar.setProgress(durabilityProgress);
            });
        }
    }

    public void updateAxeHudImage() {
        if (humanPlayer.playerHasAnAxe()) {
            switch (humanPlayer.getEquippedAxe().getDescription()) {
                case "iron axe":
                    playerWeapon.setImage(new Image(new File("src/pictures/ironAxe.png").toURI().toString()));
                    break;
                case "steel axe":
                    playerWeapon.setImage(new Image(new File("src/pictures/steelAxe.png").toURI().toString()));
                    break;
                case "diamond axe":
                    playerWeapon.setImage(new Image(new File("src/pictures/diamondAxe.png").toURI().toString()));
                    break;
                case "fire axe":
                    playerWeapon.setImage(new Image(new File("src/pictures/fireAxe.png").toURI().toString()));
                    break;
                default:
                    playerWeapon.setImage(new Image(new File("src/pictures/starterAxe.png").toURI().toString()));
                    break;
            }
            double durabilityProgress = humanPlayer.getAxeDurabilityPercentage();
            durabilityBar.setProgress(durabilityProgress);
        } else {
            playerWeapon.setImage(new Image(new File("src/pictures/punchWeapon.png").toURI().toString()));
            durabilityBar.setProgress(1);
        }
    }
}
