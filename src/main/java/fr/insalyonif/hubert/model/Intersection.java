package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.List;

public class Intersection {
    private double latitude;
    private double longitude;
    private List<RoadSegment> predecessors;
    private List<RoadSegment> successors;
    private long id;
    private int pos;

    public Intersection(double latitude, double longitude, long id, int pos) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.predecessors = new ArrayList<>();;
        this.successors = new ArrayList<>();;
        this.id = id;
        this.pos = pos;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", id=" + id +
                ", pos=" + pos +
                '}';
    }

}
