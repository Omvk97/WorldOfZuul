package view_layer.controllers;

import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerGold.setText(humanPlayer.getMoneyValue() + " Gold");
        playerClimate.setText(humanPlayer.getClimatePointsValue() + " CP");
        int logsCarryingNum = humanPlayer.getLogsInBackPack().getValue();
        logsCarrying.setText(logsCarryingNum + (logsCarryingNum == 1 ? " Log" : " Logs"));
        int logsInStorageNum = humanPlayer.getLogsInStorageProperty().getValue();
        logsInStorage.setText(logsInStorageNum + (logsInStorageNum == 1 ? " Log" : " Logs"));
        int saplingsCarryingNum = humanPlayer.getSaplingsCarryingValue();
        saplingsCarrying.setText(saplingsCarryingNum + (saplingsCarryingNum == 1 ? " Sapling" : " Saplings"));
        if (humanPlayer.playerHasAnAxe()) {
            double durabilityProgress = humanPlayer.getAxeDurabilityPercentage();
            durabilityBar.setProgress(durabilityProgress);
        }
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

        humanPlayer.getLogsInStorageProperty().addListener((observable, oldValue, newValue) -> {
            int logsInStorageNum = humanPlayer.getLogsInStorageProperty().getValue();
            logsInStorage.setText(logsInStorageNum + (logsInStorageNum == 1 ? " Log" : " Logs"));
        });

        humanPlayer.getSaplingsCarrying().addListener((observable, oldValue, newValue) -> {
            int saplingsCarryingNum = humanPlayer.getSaplingsCarryingValue();
            saplingsCarrying.setText(saplingsCarryingNum + (saplingsCarryingNum == 1 ? " Sapling" : " Saplings"));
        });

        if (humanPlayer.playerHasAnAxe()) {
            humanPlayer.getAxe().getDurabilityIntegerProperty().addListener((observable, oldValue, newValue) -> {
                double durabilityProgress = humanPlayer.getAxeDurabilityPercentage();
                durabilityBar.setProgress(durabilityProgress);
            });
        }
    }
}
