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

    public String[] colors;

//    public static ArrayList<Integer> white = new ArrayList<>();
//    public static ArrayList<Integer> gray = new ArrayList<>();
//    public static ArrayList<Integer> black = new ArrayList<>();

    public static ArrayList<Chemin> chemins = new ArrayList<>();

    public static ArrayList<Intersection> deliveryRequest = new ArrayList<>();

    protected CityMap cityMap;

    public AbstractDijkstra(int sizeGraph, CityMap cityMap) {
        this.distance = new double[sizeGraph];
        this.pi = new int[sizeGraph];
        this.visited = new boolean[sizeGraph];
        this.cityMap = cityMap;

        colors = new String[sizeGraph];
        Arrays.fill(colors, "white");

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
        for (String color : colors) {
            if (color.equals("gray")) {
                return true;
            }
        }
        return false;
    }



//    protected boolean notAllBlack() {
//        for (Intersection intersection : deliveryRequest) {
//            if (!black.contains(intersection.getPos())) {
//                return true;
//            }
//        }
//        return false;
//    }

    protected Intersection minGrayNode() {
        double min = INFINITY;
        Intersection minNode = null;

//        for (Integer element : gray) {
//            if (distance[cityMap.findIntersectionByPos(element).getPos()] < min) {
//                min = distance[cityMap.findIntersectionByPos(element).getPos()];
//                minNode = cityMap.findIntersectionByPos(element);
//            }
//        }
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals("gray") && distance[i] < min) {
                min = distance[i];
                minNode = cityMap.findIntersectionByPos(i);
                //System.out.println(minNode);
            }
        }

        return minNode;
    }



    protected void runDijkstra(Intersection start, int sizeGraph){
        //white.clear();
        deliveryRequest.add(start);
        for (int i = 0; i < sizeGraph; i++) {
            distance[i] = INFINITY;
            this.pi[i] = -1;
            //white.add(i);
            colors[i] = "white";
        }
        //black.clear();
        //gray.clear();
        distance[start.getPos()] = 0.0;
        //white.remove(Integer.valueOf(start.getPos()));
        //gray.add(start.getPos());
        colors[start.getPos()] = "gray";

        while (hasGrayNode() ) {
            //System.out.println("test");
            Intersection u = minGrayNode();
            //white.remove(Integer.valueOf(u.getPos()));
            //gray.remove(Integer.valueOf(u.getPos()));
            //black.add(u.getPos());
            System.out.println("gray "+Arrays.toString(colors));
            System.out.println("d "+Arrays.toString(distance));
            System.out.println("pi "+Arrays.toString(pi));

            for (RoadSegment roadSegment : getNeighbors(u)) {
                Intersection v = selectNode(roadSegment);

                if (colors[v.getPos()].equals("white") || colors[v.getPos()].equals("gray")) {
                    //System.out.println("test");
                    relax(u, v, roadSegment.getLength());
                    //white.remove(Integer.valueOf(v.getPos()));
                    //black.remove(Integer.valueOf(v.getPos()));
                    //gray.add(v.getPos());
                    colors[v.getPos()] = "gray";
                }
            }
            colors[u.getPos()] = "black";
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