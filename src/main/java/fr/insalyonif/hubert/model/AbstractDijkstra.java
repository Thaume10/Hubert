package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Classe abstraite pour Dijkstra
public abstract class AbstractDijkstra {
    private final int INFINITY = Integer.MAX_VALUE;

    public static double[] distance;
    public static int[] pi;
    public static boolean[] visited;

    //public String[] colors;

    public static ArrayList<Integer> white = new ArrayList<>();
    public static ArrayList<Integer> gray = new ArrayList<>();
    public static ArrayList<Integer> black = new ArrayList<>();

    public static ArrayList<Chemin> chemins = new ArrayList<>();

    public static ArrayList<Intersection> deliveryRequest = new ArrayList<>();

    protected CityMap cityMap;

    public AbstractDijkstra(int sizeGraph, CityMap cityMap) {
        distance = new double[sizeGraph];
        pi = new int[sizeGraph];
        visited = new boolean[sizeGraph];
        this.cityMap = cityMap;

    }



    protected double calculateEuclideanDistance(Intersection a, Intersection b) {
        double deltaX = a.getLatitude() - b.getLatitude();
        double deltaY = a.getLongitude() - b.getLongitude();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    protected double heuristic(Intersection current, Intersection goal) {
        return calculateEuclideanDistance(current, goal);
    }

    protected boolean hasGrayNode() {
        return !gray.isEmpty();
    }

    protected boolean notAllBlack() {
        for (Intersection intersection : deliveryRequest) {
            if (!black.contains(intersection.getPos())) {
                return true;
            }
        }
        return false;
    }

    protected Intersection minGrayNode() {
        double min = INFINITY;
        Intersection minNode = null;

        for (Integer element : gray) {
            if (distance[cityMap.findIntersectionByPos(element).getPos()] < min) {
                min = distance[cityMap.findIntersectionByPos(element).getPos()];
                minNode = cityMap.findIntersectionByPos(element);
            }
        }

        return minNode;
    }



    protected void runDijkstra(Intersection start, int sizeGraph){
        white.clear();
        deliveryRequest.add(start);
        for (int i = 0; i < sizeGraph; i++) {
            distance[i] = INFINITY;
            this.pi[i] = -1;
            white.add(i);
        }
        black.clear();
        gray.clear();
        distance[start.getPos()] = 0.0;
        white.remove(Integer.valueOf(start.getPos()));
        gray.add(start.getPos());

        while (hasGrayNode() && notAllBlack()) {
            //System.out.println("test");
            Intersection u = minGrayNode();
            white.remove(Integer.valueOf(u.getPos()));
            gray.remove(Integer.valueOf(u.getPos()));
            black.add(u.getPos());

            for (RoadSegment roadSegment : getNeighbors(u)) {
                Intersection v = selectNode(roadSegment);

                if (white.contains(v.getPos()) || gray.contains(v.getPos())) {
                    //System.out.println("test");
                    relax(u, v, roadSegment.getLength());
                    white.remove(Integer.valueOf(v.getPos()));
                    black.remove(Integer.valueOf(v.getPos()));
                    gray.add(v.getPos());
                }
            }
        }

        for (int i = 0; i < distance.length; i++) {
            if (distance[i] != INFINITY && distance[i] != 0) {
                //System.out.println("pi"+Arrays.toString(pi));
                Intersection destination = cityMap.findIntersectionByPos(i);
                //System.out.println("intersection"+destination);
                if (deliveryRequest.contains(start) && deliveryRequest.contains(destination)) {

                    int[] piCopy = Arrays.copyOf(this.pi, this.pi.length);
                    Chemin chemin = createChemin(start, destination, piCopy, distance[i]);
                    //Chemin chemin = new Chemin(start, destination, piCopy, distance[i]);
                    chemins.add(chemin);
                    //System.out.println(chemin);
                }
            }
        }
        //System.out.println("FinDijkstra");
    }

    protected abstract Iterable<RoadSegment> getNeighbors(Intersection intersection);
    protected abstract Intersection selectNode(RoadSegment roadSegment);

    protected abstract Chemin createChemin(Intersection start, Intersection destination, int [] pi, double cout);
    protected abstract void relax(Intersection u, Intersection v, double weight);

    public List<Chemin> getChemins() {
        return chemins;
    }

}
