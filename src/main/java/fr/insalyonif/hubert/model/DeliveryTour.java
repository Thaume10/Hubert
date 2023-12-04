package fr.insalyonif.hubert.model;

import java.time.Instant;

import java.util.List;

public class DeliveryTour {
    private Courier courier;
    private List<DeliveryRequest> requests;
    private Instant startTime;
    private Instant endTime;

     // Getter for courier
     public Courier getCourier() {
        return courier;
    }

    // Setter for courier
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    // Getter for requests
    public List<DeliveryRequest> getRequests() {
        return requests;
    }

    // Setter for requests
    public void setRequests(List<DeliveryRequest> requests) {
        this.requests = requests;
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



}
