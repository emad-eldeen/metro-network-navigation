package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class AddHeadCommand extends Command {
    AddHeadCommand() {
        commandType = Type.ADD_HEAD;
        keyword = "add-head";
        helpText = "<line name> <station name>";
        argsNumber = 2;
    }

    @Override
    public void execute(List<String> args) {
        // throws exception if not valid
        validateArgsNumber(args);
        String lineName = args.get(0);
        String stationName = args.get(1);
        MetroNetwork.getInstance().addHeadStation(lineName, stationName);
    }

}
