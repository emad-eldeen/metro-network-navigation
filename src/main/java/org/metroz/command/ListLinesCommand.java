package org.metroz.command;

import org.metroz.metro.MetroLine;
import org.metroz.metro.MetroNetwork;

import java.util.List;

public class ListLinesCommand extends Command {
    ListLinesCommand() {
        argsNumber = 0;
        keyword = "list-lines";
        helpText = "";
        commandType = Type.LIST_LINES;
    }

    @Override
    public void execute(List<String> args) {
        for (MetroLine line: MetroNetwork.getInstance().getLines().values()) {
            System.out.println(line);
        }
    }
}
