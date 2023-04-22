package org.metroz.metro;


/**
 * a class that represents a link between two metro stations
 */
public class MetroLink {
    public MetroStation startStation;
    public MetroStation endStation;
    public int cost;

    public MetroLink(MetroStation startStation, MetroStation endStation, int cost) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.cost = cost;
    }
}
