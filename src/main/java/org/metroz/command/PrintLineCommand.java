package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class PrintLineCommand extends Command {
    PrintLineCommand() {
        commandType = Type.PRINT_LINE;
        argsNumber = 2;
        helpText = "";
        keyword = "output";
    }

    @Override
    public void execute(List<String> args) {
        validateArgsNumber(args);
        String lineName = args.get(0);
        MetroNetwork.getInstance().printLine(lineName);
    }
}
