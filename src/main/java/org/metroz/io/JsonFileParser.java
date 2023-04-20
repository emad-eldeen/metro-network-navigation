package org.metroz.io;

import com.google.gson.*;
import org.metroz.metro.MetroLine;
import org.metroz.metro.MetroLink;
import org.metroz.metro.MetroNetwork;
import org.metroz.metro.MetroStation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JsonFileParser {

    /**
     * parses a json file and creates the correspondent metro network
     * @param fileName the name of the file to be parsed
     * @throws IOException in case file doesn't exist or could not be opened
     * @throws JsonSyntaxException in case the file has invalid json syntax
     */
    public static void initiateNetworkFromJsonFile(String fileName) throws IOException, JsonSyntaxException, URISyntaxException {
        System.out.println("Parsing network data...");
        URL fileUrl = JsonFileParser.class.getClassLoader().getResource(fileName);
        if (fileUrl == null) {
            throw new IOException();
        }
        Path path = Paths.get(fileUrl.toURI());
        List<String> fileLines = Files.readAllLines(path);
        String jsonString = String.join(" ", fileLines);
        // read the file as a json object
        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
        // to save the transfer for later processing
        List<TransferStrings> transferStrings = new ArrayList<>();
        // the root object keys are the metro lines
        for (String line : jsonObject.keySet()) {
            MetroLine metroLine = new MetroLine(line);
            MetroNetwork.getInstance().addLine(metroLine);
            // read the line object
            JsonObject lineJsonObject = jsonObject.getAsJsonObject(line);
            // station ids. ids could come in random order. sort them per id asc
            List<String> sortedStationsIds = lineJsonObject.keySet().stream()
                    // sort stations by id
                    .sorted(Comparator.comparingInt(Integer::valueOf)).toList();
            for (int i = 0; i < sortedStationsIds.size(); i++) {
                String stationId = sortedStationsIds.get(i);
                String stationName = lineJsonObject.getAsJsonObject(stationId).get("name").getAsString();
                JsonElement timeElement = lineJsonObject.getAsJsonObject(stationId).get("time");
                // default cost is 0
                int cost = (timeElement == null || timeElement.isJsonNull()) ? 0 : timeElement.getAsInt();
                // add station to line
                MetroStation station = MetroNetwork.getInstance().getStation(stationName);
                // if not found under the network
                if (station == null) {
                    // create it
                    MetroNetwork.getInstance().addStation(stationName);
                    station = MetroNetwork.getInstance().getStation(stationName);
                }
                metroLine.appendStation(station);
                station.addRelatedLine(metroLine);
                // if there is a next station
                if (i + 1 < sortedStationsIds.size()) {
                    // create a link to next station
                    String station2Id = sortedStationsIds.get(i + 1);
                    String station2Name = lineJsonObject.getAsJsonObject(station2Id).get("name").getAsString();
                    MetroStation station2 = MetroNetwork.getInstance().getStation(station2Name);
                    // if not found under the network
                    if (station2 == null) {
                        // create it
                        MetroNetwork.getInstance().addStation(station2Name);
                        station2 = MetroNetwork.getInstance().getStation(station2Name);
                    }
                    MetroLink link = new MetroLink(station, station2, cost);
                    MetroNetwork.getInstance().addLink(link);
                    // create the opposite link
                    MetroLink oppositeLink = new MetroLink(station2, station, cost);
                    MetroNetwork.getInstance().addLink(oppositeLink);
                }
                // get transfer array
                JsonArray transfers = lineJsonObject.getAsJsonObject(stationId).getAsJsonArray("transfer");
                transfers.forEach(transfer -> {
                    // add transfer
                    String transferLineName = transfer.getAsJsonObject().get("line").getAsString();
                    String transferStationName = transfer.getAsJsonObject().get("station").getAsString();
                    // add to list to be processes after processing lines and stations
                    transferStrings.add(new TransferStrings(line, stationName, transferLineName, transferStationName));
                });
            }

        }
        // processing transfer has to be done after creating the lines
        processTransferStrings(transferStrings);
        System.out.println("Metro network has been successfully created");
    }

    static void processTransferStrings(List<TransferStrings> transferStrings) {
        for (TransferStrings item : transferStrings) {
            MetroNetwork.getInstance().addTransfer(item.lineName, item.stationName,
                    item.transferLineName, item.transferLineStationName, false);
        }
    }

    // temp records for transfers
    record TransferStrings(String lineName, String stationName, String transferLineName,
                           String transferLineStationName) {
    }
}

