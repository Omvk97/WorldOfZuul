package game_functionality;

import java.util.Scanner;

public class Parser {

    private final CommandWords commands;
    private final Scanner reader;
    private final Player humanPlayer;

    public Parser(Player humanPlayer) {
        commands = new CommandWords();
        reader = new Scanner(System.in);
        this.humanPlayer = humanPlayer;
    }

    /**
     * modtager og gemmer userInput og tjekker userInput om det svarer til options i det rum
     * som de står i.
     * @return Command som senere tjekkes om det er et Command der er valid eller ej.
     */
    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next().toLowerCase();
            }
        }
        inputLine = inputLine.toLowerCase().replaceAll("\\s","");
        if (humanPlayer.getCurrentRoom().getOptions(inputLine) != null) {
            return new Command(CommandWord.OPTION, humanPlayer.getCurrentRoom().getOptions(inputLine));
        }
        return new Command(commands.getCommandWord(word1), word2);
    }

    public void showCommands() {
        commands.showAll();
    }
    
    
}