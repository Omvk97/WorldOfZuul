package game_functionality;

public enum CommandWord {

    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), OPTION("option"), EXITS("exits");

    private final String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String getCommandString() {
        return commandString;
    }

    @Override
    public String toString() {
        return commandString;
    }
}
