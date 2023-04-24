package org.metroz.command;

import java.util.List;

public class ExitCommand extends Command {
    ExitCommand() {
        commandType = Type.EXIT;
        helpText = "";
        keyword = "exit";
    }

    @Override
    public void execute(List<String> args) {
        System.exit(0);
    }
}
