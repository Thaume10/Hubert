package fr.insalyonif.hubert.model;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
public class DeliveryTour {
    private Courier courier;
    private ArrayList<DeliveryRequest> requests;
    private List<Chemin> paths;
    private Instant startTime;
    private Instant endTime;
    private Dijkstra dij; 
    private DijkstraInverse dijInv;


    public DeliveryTour() {
        requests= new ArrayList<>();
    }

    // Getter for courier
     public Courier getCourier() {
        return courier;
    }

    // Setter for courier
    public void setDijkstra(Dijkstra dij) {
        this.dij = dij;
    }

     // Getter for courier
     public Dijkstra getDijkstra() {
        return this.dij;
    }

    // Setter for courier
    public void setDijkstraInverse(DijkstraInverse dijInv) {
        this.dijInv = dijInv;
    }

     // Getter for courier
     public DijkstraInverse getDijkstraInverse() {
        return this.dijInv;
    }

    // Setter for courier
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    // Getter for requests
    public ArrayList<DeliveryRequest> getRequests() {
        return requests;
    }

    // Setter for requests
    public void setRequests(ArrayList<DeliveryRequest> requests) {
        this.requests = requests;
    }

     // Getter for requests
    public List<Chemin> getPaths() {
        return paths;
    }

    // Setter for requests
    public void setPaths(List<Chemin> paths) {
        this.paths = paths;
    }

    // Getter for startTime
    public Instant getStartTime() {
        return startTime;
    }

    // Setter for startTime
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    // Getter for endTime
    public Instant getEndTime() {
        return endTime;
    }

    // Setter for endTime
    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "DeliveryTour{" +
                "courier=" + courier +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
