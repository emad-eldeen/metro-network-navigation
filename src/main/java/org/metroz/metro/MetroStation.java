package org.metroz.metro;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MetroStation {
    String name;
    // transfers to other metro lines that are possible from this station
    HashMap<MetroLine, MetroStation> transfers;
    // a station can belong to multiple lines (transfer station)
    Set<MetroLine> relatedLines;

    public MetroStation(String name) {
        this.name = name;
        transfers = new HashMap<>();
        relatedLines = new HashSet<>();
    }


    /**
     * add a possibility to transfer from this station to another line
     * @param line the other line that this station can transfer to
     * @param station the station on `line` that this station is connected to
     */
    public void addTransfer(MetroLine line, MetroStation station) {
        transfers.put(line, station);
    }

    /**
     * add a metro line that crosses this station
     * @param line the metro line
     */
    public void addRelatedLine(MetroLine line) {
        relatedLines.add(line);
    }

    /**
     *
     * @return the metro lines that this station belongs to
     */
    public Set<MetroLine> getRelatedLines() {
        return relatedLines;
    }

    @Override
    public String toString() {
        return name;
    }
}
