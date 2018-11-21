package room_fxml;

import game_functionality.Command;
import game_functionality.CommandWord;
import game_functionality.CommandWords;
import game_functionality.Game;
import game_functionality.Parser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Label textArea;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField userAnswers;
    private final CommandWords commands = new CommandWords();
    private Command userCommand;
    private Parser userInput;
    private final Game game = new Game();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        game.play(textArea);
        userInput = new Parser(game.getHumanPlayer());
        userAnswers.setOnKeyPressed((KeyEvent key) -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                userCommand = userInput.getCommand(userAnswers.getText());
                userAnswers.clear();
                validateCommand(userCommand);
            }
        });
    }

    private boolean validateCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (null != commandWord) {
            switch (commandWord) {
                case HELP:
                    game.printHelp(textArea);
                    break;
                case GO:
                    game.goRoom(command, anchorPane, textArea);
                    break;
                case QUIT:
                    wantToQuit = game.quit(command, textArea);
                    break;
                case OPTION:
                    game.doOption(command, textArea);
                    break;
                case EXITS:
                    textArea.setText(game.getHumanPlayer().getCurrentRoom().getExitString());
                    break;
                default:
                    break;
            }
        }
        return wantToQuit;
    }

}
