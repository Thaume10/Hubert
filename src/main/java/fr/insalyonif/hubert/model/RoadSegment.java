package fr.insalyonif.hubert.model;



public class RoadSegment {
    private Intersection origin;
    private Intersection destination;
    private String name;
    private double length;

    public RoadSegment(Intersection origin, Intersection destination, String name, double length) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.length = length;
    }

    public Intersection getOrigin() {
        return origin;
    }

    public Intersection getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "RoadSegment{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
