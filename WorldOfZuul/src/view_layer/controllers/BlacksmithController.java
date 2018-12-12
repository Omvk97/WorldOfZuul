package view_layer.controllers;

import domain_layer.game_elements.Axe;
import domain_layer.game_elements.AxeFactory;
import domain_layer.game_functionality.Command;
import domain_layer.game_functionality.CommandWord;
import domain_layer.game_functionality.Game;
import domain_layer.game_functionality.Player;
import domain_layer.game_locations.BlackSmith;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author steffen
 */
public class BlacksmithController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane, Buypane;
    @FXML
    private Button backBtn;
    private final Player humanPlayer = Game.getInstanceOfSelf().getHumanPlayer();
    private final BlackSmith gameBlacksmith = (BlackSmith) Game.getInstanceOfSelf().getBlacksmith();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Buypane.setVisible(false);
        textArea.setText(gameBlacksmith.roomEntrance(humanPlayer));
    }
    /*
    *gets the information of the axe and tjek is the player have enough money i
    */
    public String getAxeInfo(Player humanPlayer, Axe axe) {
        if (humanPlayer.getMoneyValue() >= axe.getPrice()) {
            humanPlayer.boughtAxe(axe);
            return "You just bought a " + axe.getDescription() + "!\n"
                + "It costs you " + axe.getPrice() + " gold coins"
                + "\nEnjoy it while it lasts!";
        } else {
            return "YOU NEED " + axe.getPrice() + " GOLD COINS TO BUY THIS AXE";
        }
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
    /*
    * See if player have an axe
    * see if players axe has taken damaget 
    * fix players axe
    */
    @FXML
    private void handlerepair(MouseEvent event) {
        gameBlacksmith.getBlackSmithNPC();
        if (humanPlayer.getAxe() == null) {
            textArea.setText(gameBlacksmith.getBlackSmithNPC() + "You don't have an axe equipped");
        } else if (humanPlayer.getAxe().getDurability() == humanPlayer.getAxe().getStartDurability()) {
            textArea.setText(gameBlacksmith.getBlackSmithNPC() + "Your axe is fine! Come back if it ever gets dull");
        } else if (humanPlayer.getAxe().getDurability() < humanPlayer.getAxe().getStartDurability()) {
            if (humanPlayer.getMoneyValue() >= gameBlacksmith.fixAxePrice(humanPlayer)) {
                System.out.println(gameBlacksmith.blackSmithNPC + "I will grind your axe for you. Please wait");
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "I will grind your axe for you. Please wait");
                gameBlacksmith.grindSound();
                gameBlacksmith.grindAxe(humanPlayer.getAxe());
                System.out.println("humanPlayer.grindedAxe(fixAxePrice)");
                humanPlayer.grindedAxe(gameBlacksmith.fixAxePrice(humanPlayer));
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "Your axe is done");
            } else {
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "You do not have enough money");
            }

        }

    }

    @FXML
    private void handleExits(KeyEvent event) {
        if (event.getCode().equals(KeyCode.DOWN) || event.getCode().equals(KeyCode.S)) {
            Command tester = new Command(CommandWord.GO, "back");
            Game.getInstanceOfSelf().goRoom(tester, anchorPane);
        } else {
            textArea.setText("There is no road!");
        }
    }

    @FXML
    private void handleBackBtn(MouseEvent event) {
        backBtn.setDisable(true);
        Command tester = new Command(CommandWord.GO, "back");
        Game.getInstanceOfSelf().goRoom(tester, anchorPane);
    }

    @FXML
    private void BuyOnAction(ActionEvent event) {
        Buypane.setVisible(true);
        textArea.setText("Which axe would you like to buy?");

    }

    @FXML
    private void HandlerIronAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, AxeFactory.createIronAxe()));
        humanPlayer.setCharacterModel(false);

    }

    @FXML
    private void handlerSteelAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, AxeFactory.createSteelAxe()));
        humanPlayer.setCharacterModel(false);
    }

    @FXML
    private void HandlerDiamondAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, AxeFactory.createDiamondAxe()));
        humanPlayer.setCharacterModel(false);
    }

    @FXML
    private void handlerFireAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, AxeFactory.createFireAxe()));
        humanPlayer.setCharacterModel(false);

    }

    @FXML
    private void handlerBack(MouseEvent event) {
        Buypane.setVisible(false);
    }

}
