package org.metroz.graph;

import org.metroz.metro.MetroLine;
import org.metroz.metro.MetroLink;
import org.metroz.metro.MetroNetwork;
import org.metroz.metro.MetroStation;

import java.util.*;

public class Utils {

    /**
     * calculates a spanning tree using Breadth Depth First algorithm starting from the start station
     * cost of moving between the stations is considered to be zero
     * @param startStation starting station
     * @param startLine starting metro line
     * @param destinationStation destination station
     * @return a spanning tree including the start and end stations
     */
    public static List<MetroNetwork.RouteNode> applyBDF(MetroStation startStation, MetroLine startLine, MetroStation destinationStation) {
        // visited nodes, and how each node was reached
        List<MetroNetwork.RouteNode> visited = new ArrayList<>();
        Deque<MetroNetwork.RouteNode> stationsToVisit = new ArrayDeque<>();
        // start from the starting station
        stationsToVisit.add(new MetroNetwork.RouteNode(startStation, startLine, null, 0));
        while (!stationsToVisit.isEmpty()) {
            MetroNetwork.RouteNode currentNode = stationsToVisit.pop();
            visited.add(currentNode);
            if (currentNode.station() == destinationStation) {
                // search has finished
                break;
            }
            // for each related metro line of this station
            for (MetroLine line : currentNode.station().getRelatedLines()) {
                MetroStation nextStation = line.getNextStation(currentNode.station());
                // if still not visited, add it to the queue
                if (nextStation != null && visited.stream().noneMatch(item -> item.station() == nextStation)) {
                    stationsToVisit.add(new MetroNetwork.RouteNode(nextStation, line, currentNode.station(), 0));
                }

                MetroStation prevStation = line.getPrevStation(currentNode.station());
                // if still not visited, add it to the queue
                if (prevStation != null && visited.stream().noneMatch(item -> item.station() == prevStation)) {
                    stationsToVisit.add(new MetroNetwork.RouteNode(prevStation, line, currentNode.station(), 0));
                }
            }
        }
        return visited;
    }

    /**
     * calculates a spanning tree using Dijkstra algorithm starting from the start station
     * @param stations complete list of metro stations
     * @param startStation starting station
     * @param startingLine starting metro line
     * @param links the list of all the links of the network
     * @param transferCost cost of the transfer between metro lines
     * @return a spanning tree
     */
    public static HashMap<MetroStation, MetroNetwork.RouteNode> applyDijkstra(HashMap<String, MetroStation> stations, List<MetroLink> links,
                                                                              MetroStation startStation, MetroLine startingLine, int transferCost) {
        // visited nodes, and how each node was reached
        List<MetroStation> visited = new ArrayList<>();
        HashMap<MetroStation, MetroNetwork.RouteNode> spanningTree = new HashMap<>();
        // initial costs as infinity
        for (MetroStation station : stations.values()) {
            spanningTree.put(station, new MetroNetwork.RouteNode(station, null, null, Integer.MAX_VALUE));
        }
        // add cost to first station as 0
        spanningTree.put(startStation, new MetroNetwork.RouteNode(startStation, null, null, 0));
        MetroStation currentStation = startStation;
        while (currentStation != null) {
            visited.add(currentStation);
            // for each link going out of the current station
            MetroStation finalCurrentStation = currentStation;
            MetroLine currentLine = startingLine;
            // prefer to stay on the same line
            currentLine = currentStation.getRelatedLines().contains(currentLine) ? currentLine :
                    currentStation.getRelatedLines().stream().findFirst().orElse(null);
            for (MetroLink link : links.stream().filter(item -> item.startStation == finalCurrentStation).toList()) {
                MetroStation endStation = link.endStation;
                // if station is on the current line. otherwise there is a transfer
                int linkCost = endStation.getRelatedLines().contains(currentLine) ? link.cost : link.cost + transferCost;
                // add the cost needed to reach the starting station of the link
                int costToCurrent = linkCost + spanningTree.get(link.startStation).cost();
                // if this route cheaper
                if (costToCurrent < spanningTree.get(endStation).cost()) {
                    spanningTree.put(endStation, new MetroNetwork.RouteNode(endStation, currentLine, currentStation, costToCurrent));
                }
            }
            currentStation = getNextUnprocessedStation(stations, visited, spanningTree).orElse(null);
        }
        return spanningTree;
    }

    /**
     * gets a random metro station from the list of stations that have not yet been processed
     * @param stations list of all stations
     * @param visited list of already processed stations
     * @param costs list of costs to each metro stations
     * @return a metro station to be processed next
     */
    private static Optional<MetroStation> getNextUnprocessedStation(HashMap<String, MetroStation> stations, List<MetroStation> visited, HashMap<MetroStation, MetroNetwork.RouteNode> costs) {
        return stations.values().stream().filter(item -> !visited.contains(item))
                .filter(item -> costs.get(item).cost() != Integer.MAX_VALUE).findFirst();
    }

    /**
     * gets a route between the specified start and destination stations using the provided spanning tree
     * @return the route from start to destination
     */
    public static Deque<MetroNetwork.RouteNode> getRoute(MetroStation destination, MetroStation statingStation, List<MetroNetwork.RouteNode> spanningTree) {
        MetroStation currentStation = destination;
        Deque<MetroNetwork.RouteNode> route = new ArrayDeque<>();
        // maximum of 100 hops
        final int MAXIMUM_ITERATIONS_NUMBER = 100;
        int iteration = 0;
        while (iteration < MAXIMUM_ITERATIONS_NUMBER) {
            MetroStation finalCurrentStation = currentStation;
            Optional<MetroNetwork.RouteNode> routeNode =
                    spanningTree.stream().filter(item -> item.station() == finalCurrentStation).findFirst();
            if (routeNode.isEmpty()) {
                throw new RuntimeException("Invalid route!");
            }
            route.addFirst(routeNode.get());
            if (currentStation == statingStation) {
                break;
            }
            currentStation = routeNode.get().reachedBy();
            iteration++;
        }
        return route;
    }
}
