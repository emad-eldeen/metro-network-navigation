package org.metroz.command;

import java.util.List;

public class HelpCommand extends Command {
    HelpCommand() {
        commandType = Type.HELP;
        keyword = "help";
    }
    @Override
    public void execute(List<String> args) {
        System.out.println("List of available commands:");
        for (Command command: availableCommands) {
            // skip help command
            if (command.commandType == Type.HELP) {
                continue;
            }
            System.out.printf("%s%s %s%n", Command.PREFIX, command.keyword, command.helpText);
        }
    }
}
