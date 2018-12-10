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
    private Axe ironAxe = AxeFactory.createIronAxe();
    private Axe steelAxe = AxeFactory.createSteelAxe();
    private Axe diamondAxe = AxeFactory.createDiamondAxe();
    private Axe fireAxe = AxeFactory.createFireAxe();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Buypane.setVisible(false);
        textArea.setText(gameBlacksmith.roomEntrance(humanPlayer));
    }

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

    @FXML
    private void handlerepair(MouseEvent event) {
        gameBlacksmith.getBlackSmithNPC();
        switch (gameBlacksmith.grindAxe_menu(humanPlayer)) {
            case 1:
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "You don't have an axe equipped");
                break;
            case 2:
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "Your axe is fine! Come back if it ever gets dull");
                break;
            case 3:
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "Your axe is done");
                break;
            case 4:
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "You do not have enough money");
                break;
            default:
                textArea.setText(gameBlacksmith.getBlackSmithNPC() + "dont know what you mean???");
                break;

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
        textArea.setText(getAxeInfo(humanPlayer, getIronAxe()));
        humanPlayer.setCharacterModel(false);

    }

    @FXML
    private void handlerSteelAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, getSteelAxe()));
        humanPlayer.setCharacterModel(false);
    }

    @FXML
    private void HandlerDiamondAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, getDiamondAxe()));
        humanPlayer.setCharacterModel(false);
    }

    @FXML
    private void handlerFireAxe(MouseEvent event) {
        textArea.setText(getAxeInfo(humanPlayer, getFireAxe()));
        humanPlayer.setCharacterModel(false);

    }

    @FXML
    private void handlerBack(MouseEvent event) {
        Buypane.setVisible(false);
    }

    /**
     * @return the ironAxe
     */
    public Axe getIronAxe() {
        return ironAxe;
    }

    /**
     * @return the steelAxe
     */
    public Axe getSteelAxe() {
        return steelAxe;
    }

    /**
     * @return the diamondAxe
     */
    public Axe getDiamondAxe() {
        return diamondAxe;
    }

    /**
     * @return the fireAxe
     */
    public Axe getFireAxe() {
        return fireAxe;
    }

    /**
     * @param fireAxe the fireAxe to set
     */
    public void setFireAxe(Axe fireAxe) {
        this.fireAxe = fireAxe;
    }

    /**
     * @param ironAxe the ironAxe to set
     */
    public void setIronAxe(Axe ironAxe) {
        this.ironAxe = ironAxe;
    }

    /**
     * @param steelAxe the steelAxe to set
     */
    public void setSteelAxe(Axe steelAxe) {
        this.steelAxe = steelAxe;
    }

    /**
     * @param diamondAxe the diamondAxe to set
     */
    public void setDiamondAxe(Axe diamondAxe) {
        this.diamondAxe = diamondAxe;
    }

}
