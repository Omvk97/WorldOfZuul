package view_layer.controllers;

import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.LocalVillage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import view_layer.room_animations.LocalVillageAnimation;

/**
 * This controller class handles every UI element and interaction from the FXML document.
 * @author Michael
 */
public class LocalVillageController implements Initializable {

    private static final int SLOW = 6;
    private static final int MEDIUM = 4;
    private static final int FAST = 2;
    @FXML
    private Label firstVisitText, villagerText;
    @FXML
    private AnchorPane mainAnchorPane, villageGiftAndScenario, firstVisitGreatings;
    @FXML
    private Button backToPreviousRoomButton;
    @FXML
    private ImageView player, sun;
    @FXML
    private Rectangle blacksmithEntrance, storeEntrance, libraryEntrance, anchorpoint1, anchorpoint2;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final LocalVillage gameVillage = (LocalVillage) Game.getInstanceOfSelf().getLocalVillage();
    private int playerEntranceCoordinatX, playerEntranceCoordinatY, currentDialouge;
    private LocalVillageAnimation animation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGlobalVariables();
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        animation.setRunning(true);
        backToPreviousRoomButton.setVisible(true);
        transition();
        player.setImage(new Image(humanPlayer.getCharacterModel().toURI().toString()));
        choosingRainScenario();
    }
    
    public void setGlobalVariables() {
        playerEntranceCoordinatX = (int) mainAnchorPane.getPrefWidth() / 2;
        playerEntranceCoordinatY = ((int) mainAnchorPane.getPrefHeight() / 4) + 40;
        currentDialouge = 0;
        this.animation = new LocalVillageAnimation.Builder(player)
            .withAnchorPoint1(anchorpoint1)
            .withAnchorPoint2(anchorpoint2)
            .withFirstVisitGreeTingsPane(firstVisitGreatings)
            .withMainAnchorPane(mainAnchorPane)
            .withPlayerEntranceCoordinates(playerEntranceCoordinatY)
            .withfirstVisitText(firstVisitText)
            .withbackToPreviousRoomButton(backToPreviousRoomButton)
            .build();
    }

    /**
     * This method is used to determine the rain's
     * intensity depending on the amount of climatepoints
     */
    private void choosingRainScenario() {
        switch (getClimateScenario()) {
            case -1:
                animation.rainDrops(70, SLOW);
                break;
            case -2:
                animation.rainDrops(150, SLOW);
                break;
            case -3:
                animation.rainDrops(250, MEDIUM);
                break;
            case -4:
                animation.rainDrops(350, MEDIUM);
                break;
            case -5:
                animation.rainDrops(450, FAST);
                break;
            default:
                sun.setVisible(true);
        }
    }

    /**
     * 
     * @return int based on the players climate Points
     */
    private int getClimateScenario() {
        int climatePoints = humanPlayer.getClimatePointsValue();
        if (climatePoints < 0 && climatePoints > -19) {
            return -1;
        } else if (climatePoints < -19 && climatePoints > -29) {
            return -2;
        } else if (climatePoints < -29 && climatePoints > -39) {
            return -3;
        } else if (climatePoints < -39 && climatePoints > -49) {
            return -4;
        } else if (climatePoints < -49) {
            return -5;
        }
        return 0;
    }

  /**
   * Calls scenario when a mouse event occurs.
   * @param event 
   */
    @FXML
    private void handleTalkToVillageButton(MouseEvent event) {
        scenario();
    }

    /**
     * Calls the corresponding animation depending on the players direction in the game world.
     * first it checks if an animation is allready running to dertermin if it can run.
     * Note: it also disables the backToPreviousRoomButton button.
     * @param event 
     */
    @FXML
    private void handleBackToPreviousButton(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        if (!animation.isRunning()) {
            animation.setRunning(true);

            switch (Game.getInstanceOfSelf().getPlayerDirectionInWorld()) {
                case "goRight":
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, 0, "goLeft", "trailer", true);
                    break;
                case "goStore":
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, storeEntrance.getLayoutX(), "goStore", "store", false);
                    break;
                case "goBlacksmith":
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, blacksmithEntrance.getLayoutX(), "goBlacksmith", "blacksmith", true);
                    break;
                case "goLibrary":
                    animation.goToTransitionHandle(playerEntranceCoordinatX / 2, libraryEntrance.getLayoutX(), "goLibrary", "library", false);
                    break;
            }
        }
    }

   /**
     * Calls the corresponding animation depending on if a mouse event occurs.
     * first it checks if an animation is allready running to dertermin if it can run.
     * Note: it also disables the backToPreviousRoomButton button 
     * and hides firstVisitGreatings and villageGiftAndScenario anchorpoint.
     * @param event 
     */
    @FXML
    private void handleGoToStore(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!animation.isRunning()) {
            animation.setRunning(true);
            animation.goToTransitionHandle(playerEntranceCoordinatX / 2, storeEntrance.getLayoutX(),
                "goStore", "store", false);
        }
    }

    /**
     * Calls the corresponding animation depending on if a mouse event occurs.
     * First it checks if an animation is allready running to dertermin if it can run.
     * Note: it also disables the backToPreviousRoomButton button 
     * and hides firstVisitGreatings and villageGiftAndScenario anchorpoint.
     * @param event 
     */
    @FXML
    private void handleGoToBlacksmith(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!animation.isRunning()) {
            animation.setRunning(true);
            animation.goToTransitionHandle(playerEntranceCoordinatX / 2, blacksmithEntrance.getLayoutX(),
                "goBlacksmith", "blacksmith", true);
        }
    }

    /**
     * Calls the corresponding animation depending on if a mouse event occurs.
     * First it checks if an animation is allready running to dertermin if it can run.
     * Note: it also disables the backToPreviousRoomButton button 
     * and hides firstVisitGreatings and villageGiftAndScenario anchorpoint.
     * @param event 
     */
    @FXML
    private void handleGoToLibrary(MouseEvent event) {
        backToPreviousRoomButton.setDisable(true);
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!animation.isRunning()) {
            animation.setRunning(true);
            animation.goToTransitionHandle(playerEntranceCoordinatX / 2, libraryEntrance.getLayoutX(),
                "goLibrary", "library", false);
        }
    }

    /**
     * Calls the corresponding animation depending on if a key event occurs.
     * First it checks if an animation is allready running to dertermin if it can run.
     * Note: it hides firstVisitGreatings and villageGiftAndScenario anchorpoint.
     * @param event 
     */
    @FXML
    private void handleExits(KeyEvent event) {
        firstVisitGreatings.setVisible(false);
        villageGiftAndScenario.setVisible(false);
        if (!animation.isRunning()) {
            if (event.getCode().equals(KeyCode.LEFT) || event.getCode().equals(KeyCode.A)) {
                animation.setRunning(true);
                animation.goToTransitionHandle(playerEntranceCoordinatX / 2, 0, "goLeft", "trailer", true);
            } else {
            }
        }
    }

    /**
     * Calls the corresponding animation depending on where the player comes from.
     * Note: it also checks if its the first time the player visits and if the player
     * has received a gift. Both are true it calls scenario.
     */
    private void transition() {
        switch (Game.getInstanceOfSelf().getPlayerDirectionInWorld()) {
            case "goRight":
                animation.backTransitionHandle(0, playerEntranceCoordinatY, playerEntranceCoordinatX / 2, true);
                if (humanPlayer.isGiftHasBeenGivenToday() && !humanPlayer.isFirstVillageVisit()) {
                    scenario();
                }
                break;
            case "goStore":
                animation.backTransitionHandle(storeEntrance.getLayoutX(), storeEntrance.getLayoutY() - 20, playerEntranceCoordinatX / 2, false);
                break;
            case "goBlacksmith":
                animation.backTransitionHandle(blacksmithEntrance.getLayoutX(), playerEntranceCoordinatY, playerEntranceCoordinatX / 2, true);
                break;
            case "goLibrary":
                animation.backTransitionHandle(libraryEntrance.getLayoutX(), libraryEntrance.getLayoutY(), playerEntranceCoordinatX / 2, false);
                break;
        }
    }

    /**
     * Shows the villageGiftAndScenario anchor pane to animate the corresponding text
     * depending on what it recives from localVillage.java.
     */
    private void scenario() {
        currentDialouge = 1;
        villageGiftAndScenario.setVisible(true);
        animation.textAnimation(villagerText, gameVillage.getScenario(humanPlayer));

    }
    
    /**
     * Checks if the player has been given a gift that day.
     * If true it cycles through, animats the scenario again.
     * If False it closes the window.
     */

    @FXML
    private void handleNextScenarioTextButton() {
        if (humanPlayer.isGiftHasBeenGivenToday() && currentDialouge == 1) {
            animation.textAnimation(villagerText, gameVillage.getScenario(humanPlayer));
            currentDialouge++;
        } else {
            villageGiftAndScenario.setVisible(false);
        }
    }

    /**
     * Calls the corresponding animation depending on the currentDialouge on every mouse event.
     * If 3 it closes the firstVisitGreatings anchorpane.
     * @param event 
     */
    @FXML
    private void handleNextDialougeFirstVisitButton(MouseEvent event) {
        switch (currentDialouge) {
            case 0:
                animation.textAnimation(firstVisitText, "I Havent seen you around here before.\n"
                    + "Well anyways it's always nice to meet new people. ");
                currentDialouge++;
                break;
            case 1:
                animation.textAnimation(firstVisitText, "If you plan to stay please feel free\n"
                    + "to explore the towns various shops.");
                currentDialouge++;
                break;
            case 2:
                animation.textAnimation(firstVisitText, "Or maybe do a bit of reading in the library?\n"
                    + "See you around friend!");
                currentDialouge++;
                break;
            case 3:
                firstVisitGreatings.setVisible(false);
                scenario();
        }
    }
}
