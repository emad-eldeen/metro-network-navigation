package org.metroz.command;

import java.util.List;
import java.util.Map;

public abstract class Command {
    public Type commandType;
    int argsNumber;
    String helpText;
    public static List<Command> availableCommands = List.of(
            new HelpCommand(Type.HELP, 0, ""),
            new AddHeadCommand(Type.ADD_HEAD, 3, "<line name> <station name> <cost of the station>")
    );

    public abstract void execute(List<String> args);

    // whether the number of passed in arguments is valid
    void validateArgsNumber(List<String> args) {
        if (args == null || args.size() != argsNumber) {
            throw new IllegalArgumentException("Invalid arguments. This command has the following syntax: " + helpText);
        }
    }

    public enum Type {
        HELP("/help"),
        ADD_HEAD("/add-head");
        final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }
}
