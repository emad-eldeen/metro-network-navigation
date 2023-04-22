package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class ShortestRouteCommand extends Command {
    ShortestRouteCommand() {
        commandType = Type.SHORTEST_ROUTE;
        helpText = "<start line> <start station> <destination station>";
        argsNumber = 3;
        keyword = "route";
    }

    @Override
    public void execute(List<String> args) {
        validateArgsNumber(args);
        String startLineName = args.get(0);
        String startStationName = args.get(1);
        String destinationStationName = args.get(2);
        MetroNetwork.getInstance().getShortestRoute(startLineName, startStationName, destinationStationName);
    }
}
