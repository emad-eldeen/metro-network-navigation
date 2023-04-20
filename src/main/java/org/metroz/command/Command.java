package org.metroz.command;

import java.util.List;
import java.util.Map;

public abstract class Command {
    int argsNumber;
    String help;
    public static Map<String, Command> availableCommands = Map.ofEntries(
            Map.entry("add-head", new AddHeadCommand(3, "<line name> <station name> <cost of the station>")));
    public abstract void execute(List<String> args);

    // whether the number of passed in arguments is valid
    void validateArgsNumber(List<String> args) {
        if (args == null || args.size() != argsNumber) {
            throw new IllegalArgumentException("Invalid arguments. This command has the following syntax: " + help);
        }
    }
}
