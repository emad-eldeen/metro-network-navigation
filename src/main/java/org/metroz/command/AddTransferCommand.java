package org.metroz.command;

import org.metroz.metro.MetroNetwork;

import java.util.List;

public class AddTransferCommand extends Command {

    AddTransferCommand() {
        commandType = Type.ADD_TRANSFER;
        helpText = "";
        argsNumber = 4;
        keyword = "connect";
    }

    @Override
    public void execute(List<String> args) {
        validateArgsNumber(args);
        String lineFromName = args.get(0);
        String stationFromName = args.get(1);
        String lineToName = args.get(2);
        String stationToName = args.get(3);
        MetroNetwork.getInstance().addTransfer(lineFromName, stationFromName, lineToName, stationToName, true);
    }
}
