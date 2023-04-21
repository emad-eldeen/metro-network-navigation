package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class AddHeadCommand extends Command {
    AddHeadCommand(Type commandType, int argsNumber, String helpText) {
        super.argsNumber = argsNumber;
        super.helpText = helpText;
        super.commandType = commandType;
    }
    @Override
    public void execute(List<String> args) {
        // throws exception if not valid
        validateArgsNumber(args);
        String lineName = args.get(0);
        String stationName = args.get(1);
        try {
            int stationCost = Integer.parseInt(args.get(2));
            MetroNetwork.getInstance().addHeadStation(lineName, stationName, stationCost);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Please specify a valid number as the cost of the station to be added");
        }
    }

}
