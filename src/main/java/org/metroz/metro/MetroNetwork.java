package org.metroz.metro;

import org.metroz.graph.Utils;

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


    public void appendStation(String lineName, String stationName) {
        if (!stations.containsKey(stationName)) {
            stations.put(stationName, new MetroStation(stationName));
        }
        MetroStation station = stations.get(stationName);
        lines.get(lineName).appendStation(station);
    }

    /**
     * adds a station at the start of a metro line
     * @param lineName name of the metro line
     * @param stationName name of the station
     */
    public void addHeadStation(String lineName, String stationName) {
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


    /**
     * calculates and prints the shortest route (in terms of numbers of stations) between two stations
     * @param startLineName name of the starting line
     * @param startStationName name of the starting station
     * @param destinationStationName name of the destination station
     */
    public void getShortestRoute(String startLineName, String startStationName, String destinationStationName) {
        MetroLine startLine = lines.get(startLineName);
        MetroStation startingStation = stations.get(startStationName);
        MetroStation destinationStation = stations.get(destinationStationName);
        List<RouteNode> spanningTree = Utils.applyBDF(startingStation, startLine, destinationStation);
        printRoute(Utils.getRoute(destinationStation, startingStation, spanningTree), true);
    }

    /**
     * calculates and prints the fastest route (in terms of cost) between two stations
     * @param startLineName name of the starting line
     * @param startStationName name of the starting station
     * @param destinationStationName name of the destination station
     */
    public void getFastestRoute(String startLineName, String startStationName, String destinationStationName) {
        MetroLine startLine = lines.get(startLineName);
        MetroStation startingStation = stations.get(startStationName);
        MetroStation endingStation = stations.get(destinationStationName);
        HashMap<MetroStation, RouteNode> routeNodes = Utils.applyDijkstra(stations, links, startingStation, startLine, TRANSFER_COST);
        printRoute(Utils.getRoute(endingStation, startingStation, routeNodes.values().stream().toList()), false);
    }

    /**
     * prints the specified route
     * @param showTransfer whether to print the line transfer information
     */
    private void printRoute(Deque<RouteNode> route, boolean showTransfer) {
        MetroLine currentLine = route.getFirst().line();
        int totalCost = 0;
        while (route.size() > 0) {
            RouteNode current = route.pop();
            totalCost = current.cost;
            System.out.println(current.station);
            // if there is a next node
            if (route.size() > 0) {
                // don't remove it from the queue
                RouteNode nextNode = route.peek();
                if (nextNode.line != currentLine) {
                    // it means that current node is a transfer
                    if (showTransfer) {
                        System.out.println("Transition to line " + nextNode.line);
                        System.out.println(current.station);
                    }
                    currentLine = nextNode.line;
                }
            }
        }
        if (totalCost > 0) {
            System.out.printf("Total: %d minutes in the way%n", totalCost);
        }
    }

    public record RouteNode(MetroStation station, MetroLine line, MetroStation reachedBy, int cost) {
    }
}
