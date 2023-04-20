package org.metroz.metro;

import java.util.*;

/**
 * a class that represents a metro line which is a linked list of metro stations
 */
public class MetroLine {
    final String name;
    final LinkedList<MetroStation> stations;

    public MetroLine(String name) {
        this.name = name;
        stations = new LinkedList<>();
    }


    public LinkedList<MetroStation> getStations() {
        return stations;
    }

    // add a station to the end of the line
    public void appendStation(MetroStation station) {
        stations.add(station);
    }

    // add a station to the start of the line
    public void addHeadStation(MetroStation station) {
        stations.addFirst(station);
    }
    public void removeStation(MetroStation station) {
        stations.remove(station);
    }

    /**
     * add a transfer from this line to another one
     * @param transferFromStation name of the station on this line where the transfer is possible
     * @param transferToLine the other line to transfer to
     * @param transferToStation name of the station on transferToLine
     */
    public void addTransfer(String transferFromStation, MetroLine transferToLine, String transferToStation) {
        // get the stations using station name
        Optional<MetroStation> firstStation = stations.stream()
                .filter(item -> Objects.equals(item.name, transferFromStation)).findFirst();
        Optional<MetroStation> secondStationOptional = stations.stream()
                .filter(item -> Objects.equals(item.name, transferToStation)).findFirst();
        // if second station is not created on the other line, create it
        MetroStation secondStation = secondStationOptional.orElse(new MetroStation(transferToStation));
        // add the transfer
        firstStation.ifPresent(station -> station.addTransfer(transferToLine, secondStation));
    }

    public MetroStation getNextStation(MetroStation station) {
        int stationIndex = stations.indexOf(station);
        // if the station is found on the line, and it is not the last one
        if(stationIndex != -1 && stationIndex + 1 < stations.size()) {
            return stations.get(stationIndex + 1);
        }
        return null;
    }
    public MetroStation getPrevStation(MetroStation station) {
        int stationIndex = stations.indexOf(station);
        // if the station is found on the line, and it is not the first one
        if(stationIndex - 1 >= 0) {
            return stations.get(stationIndex - 1) ;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * prints the stations of this line as follows:
     * "depot"
     * stations list (- transfers if any)
     * "depot"
     */
    public void print() {
        System.out.println("depot");
        for (MetroStation station: stations) {
            System.out.print(station.name);
            if (station.transfers.size() > 0) {
                // print the transfers of this station
                for (MetroLine transferMetroLine: station.transfers.keySet()) {
                    if (transferMetroLine == this) {
                        // do not print transfers to this line
                        continue;
                    }
                    MetroStation transferStation = station.transfers.get(transferMetroLine);
                    System.out.printf(" - %s (%s line)%n", transferStation.name, transferMetroLine.name);
                }
            } else {
                System.out.println();
            }
        }
        System.out.println("depot");
    }
}
