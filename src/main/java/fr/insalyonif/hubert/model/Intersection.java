package fr.insalyonif.hubert.model;

import java.util.List;

public class Intersection {
    private double latitude;
    private double longitude;
    private List<RoadSegment> predecessors;
    private List<RoadSegment> successors;

    public Intersection(double latitude, double longitude, List<RoadSegment> predecessors, List<RoadSegment> successors) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.predecessors = predecessors;
        this.successors = successors;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<RoadSegment> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(List<RoadSegment> predecessors) {
        this.predecessors = predecessors;
    }

    public List<RoadSegment> getSuccessors() {
        return successors;
    }

    public void setSuccessors(List<RoadSegment> successors) {
        this.successors = successors;
    }

    public String getCoordinates() {
        return "lat: "+this.latitude+" ; lng: "+this.longitude;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", predecessors=" + predecessors +
                ", successors=" + successors +
                '}';
    }
}
