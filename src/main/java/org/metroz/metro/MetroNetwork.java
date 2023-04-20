package org.metroz.metro;

import java.util.*;

/**
 * represents the metro network
 */
public class MetroNetwork {
    // map of lines. keys are lines' names
    private final HashMap<String, MetroLine> lines;
    // map of stations. keys are stations' names
    private final HashMap<String, MetroStation> stations;
    // singleton class
    private static MetroNetwork instance;
    private final List<MetroLink> links;
    // cost of transfer between metro lines
    static final int TRANSFER_COST = 5;

    private MetroNetwork() {
        lines = new HashMap<>();
        stations = new HashMap<>();
        links = new ArrayList<>();
    }

    public static MetroNetwork getInstance() {
        if (instance == null) {
            instance = new MetroNetwork();
        }
        return instance;
    }

    public void addLine(MetroLine line) {
        lines.put(line.name, line);
    }

    // get station by name
    public MetroStation getStation(String stationName) {
        return stations.get(stationName);
    }

    // add a station to this network
    public void addStation(String stationName) {
        stations.put(stationName, new MetroStation(stationName));
    }

    /**
     * prints the metro line information
     * @param line name of the line to print
     */
    public void printLine(String line) {
        if (lines.containsKey(line)) {
            lines.get(line).print();
        } else {
            System.out.printf("Line: %s doesn't exist on this metro network%n", line);
        }
    }


    public void appendStation(String lineName, String stationName, int cost) {
        if (!stations.containsKey(stationName)) {
            stations.put(stationName, new MetroStation(stationName));
        }
        MetroStation station = stations.get(stationName);
        MetroStation oldLastStation = lines.get(lineName).getStations().getLast();
        lines.get(lineName).appendStation(station);
        // add a link between the old last station and the new one
        addLink(new MetroLink(oldLastStation, station, cost));
    }

    /**
     *
     * @param lineName
     * @param stationName
     * @param cost cost to move from this station to next one and vice versa
     */
    public void addHeadStation(String lineName, String stationName, int cost) {
        if (!stations.containsKey(stationName)) {
            stations.put(stationName, new MetroStation(stationName));
        }
        MetroStation station = stations.get(stationName);
        lines.get(lineName).addHeadStation(station);
    }

    public void removeStation(String line, String stationName) {
        MetroStation station = stations.get(stationName);
        if (station != null) {
            lines.get(line).removeStation(station);
        }
    }

    public void addTransfer(String line1Name, String station1Name, String line2Name, String station2Name, boolean addBackTransfer) {
        MetroLine line1 = lines.get(line1Name);
        MetroLine line2 = lines.get(line2Name);
        line1.addTransfer(station1Name, line2, station2Name);
        if (addBackTransfer) {
            line2.addTransfer(station2Name, line1, station1Name);
        }
    }


    public void addLink(MetroLink link) {
        links.add(link);
    }

    record RouteNode(MetroStation station, MetroLine line, MetroStation reachedBy, int cost) {
    }
}
