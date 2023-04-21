package org.metroz.command;

import java.util.List;

public class HelpCommand extends Command {

    HelpCommand(Type commandType, int argsNumber, String helpText) {
        super.helpText = helpText;
        super.commandType = commandType;
        super.argsNumber = argsNumber;
    }
    @Override
    public void execute(List<String> args) {
        for (Command command: availableCommands) {
            // skip help command
            if (command.commandType == Type.HELP) {
                continue;
            }
            System.out.println(command.helpText);
        }
    }
}
