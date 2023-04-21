package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class AppendCommand extends Command {
    AppendCommand() {
        commandType = Type.APPEND;
        helpText = "";
        argsNumber = 3;
        keyword = "append";
    }

    @Override
    public void execute(List<String> args) {
        validateArgsNumber(args);
        String lineName = args.get(0);
        String stationName = args.get(1);
        try {
            int stationCost = Integer.parseInt(args.get(2));
            MetroNetwork.getInstance().appendStation(lineName, stationName, stationCost);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please specify a valid number as the cost of the station to be added");
        }
    }
}
