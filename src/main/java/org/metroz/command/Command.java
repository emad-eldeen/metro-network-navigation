package org.metroz.command;

import java.util.List;
import java.util.Optional;

public abstract class Command {
    public static String PREFIX = "/";
    public Type commandType;
    public String keyword;
    int argsNumber;
    String helpText;
    public static List<Command> availableCommands = List.of(
            new HelpCommand(),
            new AddHeadCommand(),
            new AddTransferCommand(),
            new AppendCommand(),
            new PrintLineCommand(),
            new RemoveCommand(),
            new FastestRouteCommand(),
            new ShortestRouteCommand(),
            new ExitCommand(),
            new ListLinesCommand()
    );

    public abstract void execute(List<String> args);

    // whether the number of passed in arguments is valid
    void validateArgsNumber(List<String> args) {
        if (args == null || args.size() != argsNumber) {
            throw new IllegalArgumentException("Invalid arguments. This command has the following syntax: " + helpText);
        }
    }

    public static Optional<Command> getCommandByKeyword(String keyword) {
        return availableCommands.stream()
                .filter(item -> keyword.equals(PREFIX + item.keyword))
                .findFirst();
    }

    public enum Type {
        HELP,
        ADD_HEAD,
        APPEND,
        REMOVE,
        PRINT_LINE,
        ADD_TRANSFER,
        SHORTEST_ROUTE,
        FASTEST_ROUTE,
        EXIT,
        LIST_LINES,
    }
}
