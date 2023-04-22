package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class AppendCommand extends Command {
    AppendCommand() {
        commandType = Type.APPEND;
        helpText = "<line name> <station name>";
        argsNumber = 2;
        keyword = "append";
    }

    @Override
    public void execute(List<String> args) {
        validateArgsNumber(args);
        String lineName = args.get(0);
        String stationName = args.get(1);
        MetroNetwork.getInstance().appendStation(lineName, stationName);
    }
}
