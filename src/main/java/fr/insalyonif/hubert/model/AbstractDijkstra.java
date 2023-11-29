package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Classe abstraite pour Dijkstra
public abstract class AbstractDijkstra {
    private final int INFINITY = Integer.MAX_VALUE;

    public double[] distance;
    public int[] pi;
    public boolean[] visited;

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



    public boolean runDijkstra(Intersection start, int sizeGraph){
        //white.clear();
        if( !deliveryRequest.contains(start)){
            deliveryRequest.add(start);
        }

        //System.out.println(Arrays.toString(new ArrayList[]{deliveryRequest}));
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
            //System.out.println("gray "+Arrays.toString(colors));
            //System.out.println("d "+Arrays.toString(distance));
            //System.out.println("pi "+Arrays.toString(pi));


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


        /*for (int i = 0; i < distance.length; i++) {
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
        }*

         */
        // Vérifiez si le point de départ peut atteindre tous les points de livraison
        boolean canReachAllDeliveryPoints = true;
        for (Intersection deliveryPoint : deliveryRequest) {
            if (distance[deliveryPoint.getPos()] == INFINITY) {
                canReachAllDeliveryPoints = false;
                break;
            }
        }

        if (!canReachAllDeliveryPoints) {
            // Retirez le point de départ des demandes de livraison
            deliveryRequest.remove(start);

            // Supprimez les chemins qui partent du point de départ
            chemins.removeIf(chemin -> chemin.getDebut().equals(start));
            chemins.removeIf(chemin -> chemin.getFin().equals(start));

            return false;
        }

        for (Intersection deliveryRequest : deliveryRequest){
            if(deliveryRequest!=start){
                int [] piCopy = new int[sizeGraph];
                for (int i=0; i < sizeGraph; i++){
                    piCopy[i]= -1;
                }
                if(pi[deliveryRequest.getPos()]==-1){
                    //this.deliveryRequest.remove(deliveryRequest);
                    return false;
                }
                piCopyConstructor(piCopy,start,deliveryRequest);
                Chemin chemin = createChemin(start, deliveryRequest, piCopy, distance[deliveryRequest.getPos()]);
                //System.out.println(chemin);
                chemins.add(chemin);
            }
        }
        return true;

        //System.out.println("FinDijkstra");
    }

    protected abstract Iterable<RoadSegment> getNeighbors(Intersection intersection);
    protected abstract Intersection selectNode(RoadSegment roadSegment);

    protected abstract Chemin createChemin(Intersection start, Intersection destination, int [] pi, double cout);
    protected abstract void relax(Intersection u, Intersection v, double weight);

    public List<Chemin> getChemins() {
        return chemins;
    }

    protected abstract void piCopyConstructor(int [] piCopy,Intersection start, Intersection delivery);
    public List<Intersection> getDeliveryRequest() {
        return deliveryRequest;
    }

}