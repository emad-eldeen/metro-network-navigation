package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class RemoveCommand extends Command {
    RemoveCommand() {
        commandType = Type.REMOVE;
        helpText = "<line name> <station name>";
        argsNumber = 2;
        keyword = "remove";
    }

    @Override
    public void execute(List<String> args) {
        validateArgsNumber(args);
        String lineName = args.get(0);
        String stationName = args.get(1);
        MetroNetwork.getInstance().removeStation(lineName, stationName);
    }
}
