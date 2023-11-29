package fr.insalyonif.hubert.model;
import java.util.*;

public class Dijkstra extends AbstractDijkstra {

    private final int INFINITY = Integer.MAX_VALUE;
    public Dijkstra(int sizeGraph, CityMap cityMap) {
        super(sizeGraph, cityMap);
        deliveryRequest.add(cityMap.getWareHouseLocation());
        //System.out.println(Arrays.toString(deliveryRequest));

    }

    @Override
    protected void relax(Intersection u, Intersection v, double weight) {
        if (distance[u.getPos()] + weight < distance[v.getPos()]) {
            distance[v.getPos()] = distance[u.getPos()] + weight;
            this.pi[v.getPos()] = u.getPos();
        }
    }

    protected void piCopyConstructor(int [] piCopy,Intersection start, Intersection delivery) {
        int j = delivery.getPos();
        while (j!=start.getPos()) {
            //System.out.println(this.pi[j]);
            piCopy[j]=this.pi[j];
            j = this.pi[j];
        }
    }


    @Override
    protected Iterable<RoadSegment> getNeighbors(Intersection intersection) {
        return intersection.getSuccessors();
    }

//    @Override
//    protected boolean canReachAllDeliveryPoints(Intersection intersection) {
//        boolean canReachAllDeliveryPoints = true;
//        for (Intersection deliveryPoint : deliveryRequest) {
//            if (distance[deliveryPoint.getPos()] == INFINITY) {
//                canReachAllDeliveryPoints = false;
//                break;
//            }
//        }
//        return false;
//    }

    @Override
    protected Intersection selectNode(RoadSegment roadSegment) {
        return roadSegment.getDestination();
    }

    @Override
    protected Chemin createChemin(Intersection start, Intersection destination, int[] pi, double cout) {
        Chemin chemin = new Chemin(start, destination, pi, cout);;
        return chemin;
    }


    public void addDeliveryRequest(Intersection deliveryIntersection) {
        deliveryRequest.add(deliveryIntersection);
    }
//    private final int INFINITY = Integer.MAX_VALUE;
//
//    public double[] distance;
//    public int[] pi;
//    public boolean[] visited;
//
//    //public String[] colors;
//
//    public ArrayList<Integer> white = new ArrayList<>();
//    public ArrayList<Integer> gray = new ArrayList<>();
//    public ArrayList<Integer> black = new ArrayList<>();
//
//    public static ArrayList<Chemin> chemins = new ArrayList<>();
//
//    public static ArrayList<Intersection> deliveryRequest = new ArrayList<>();
//
//
//
//    public Dijkstra(int sizeGraph, CityMap cityMap) {
//        this.distance = new double[sizeGraph];
//        this.pi = new int[sizeGraph];
//        this.visited = new boolean[sizeGraph];
//        //this.colors = new String[sizeGraph];
//        deliveryRequest.add(cityMap.getWareHouseLocation());
//
//
//
//        //test
//
//        // cache warehouse
//        //dijkstra(cityMap.getWareHouseLocation(), cityMap, sizeGraph);
//        // TO DO
//    }
//    public void dijkstra(Intersection start, CityMap cityMap, int sizeGraph) {
//        deliveryRequest.add(start);
//        //white.clear();
//        for(int i = 0; i < sizeGraph; i++) {
//            distance[i] = INFINITY;
//            this.pi[i] = -1;
//            //colors[i] = "white";
//            white.add(i);
//        }
//        black.clear();
//        gray.clear();
//        distance[start.getPos()] = 0.0;
//        //colors[start.getPos()] = "gray";
//        white.remove(Integer.valueOf(start.getPos()));
//        gray.add(start.getPos());
//
//        while (hasGrayNode() && notAllBlack()) {
//            Intersection u = minGrayNode(cityMap);
//            white.remove(Integer.valueOf(u.getPos()));
//            gray.remove(Integer.valueOf(u.getPos()));
//            black.add(u.getPos());
//            //colors[u.getPos()] = "black";
//
//            for (RoadSegment roadSegment : u.getSuccessors()) {
//                Intersection v = roadSegment.getDestination();
//
//                //if (colors[v.getPos()].equals("white") || colors[v.getPos()].equals("gray")) {
//                if (white.contains(v.getPos()) || gray.contains(v.getPos())) {
//                    relax(u, v, roadSegment.getLength());
//
//                    // Mettez à jour la distance en ajoutant l'heuristique
//                    //double heuristicValue = heuristic(v, cityMap.getWareHouseLocation());
//                    //distance[v.getPos()] = distance[u.getPos()] + roadSegment.getLength() ;
//                    //pi[v.getPos()] = u.getPos();
//                    white.remove(Integer.valueOf(v.getPos()));
//                    black.remove(Integer.valueOf(v.getPos()));
//                    gray.add(v.getPos());
//                    //colors[v.getPos()] = "gray";
//                }
//            }
//        }
////        for (Integer in : black) {
////            System.out.println("black "+in);
////
////        }
//
//
//
//        for (int i = 0; i < distance.length; i++) {
//            if(distance[i] != INFINITY && distance[i] != 0){
//                Intersection destination = cityMap.findIntersectionByPos(i);
//                if (deliveryRequest.contains(start) && deliveryRequest.contains(destination)) {
//                    int[] piCopy = Arrays.copyOf(this.pi, this.pi.length);
//                    Chemin chemin = new Chemin(start, destination, piCopy, distance[i]);
//                    chemins.add(chemin);
//                    //System.out.println(Arrays.toString(chemin.getPi()));
//                    //System.out.println(chemin);
//                }
//            }
//        }
//
////        for (Integer inter : black) {
////            System.out.println("black "+inter);
////            if(distance[inter] != INFINITY && distance[inter] != 0){
////                Intersection destination = cityMap.findIntersectionByPos(inter);
////                if (deliveryRequest.contains(start) && deliveryRequest.contains(destination)) {
////                    int[] piCopy = Arrays.copyOf(this.pi, this.pi.length);
////                    Chemin chemin = new Chemin(start, destination, piCopy, distance[inter]);
////                    chemins.add(chemin);
////                    System.out.println(Arrays.toString(chemin.getPi()));
////                    System.out.println(chemin);
////                }
////            }
////        }
//
//
//        dijkstraReverse(start, cityMap, sizeGraph);
//    }
//
//
//
//    public void dijkstraReverse(Intersection start, CityMap cityMap, int sizeGraph) {
//        //deliveryRequest.add(start);
//        //white.clear();
//        for (int i = 0; i < sizeGraph; i++) {
//            distance[i] = INFINITY;
//            this.pi[i] = -1;
//            //colors[i] = "white";
//            white.add(i);
//        }
//        black.clear();
//        gray.clear();
//        distance[start.getPos()] = 0.0;
//        //colors[start.getPos()] = "gray";
//        white.remove(Integer.valueOf(start.getPos()));
//        gray.add(start.getPos());
//
//        while (hasGrayNode() && notAllBlack()) {
//            Intersection u = minGrayNode(cityMap);
//            //System.out.println("u "+u);
//            //colors[u.getPos()] = "black";
//            white.remove(Integer.valueOf(u.getPos()));
//            gray.remove(Integer.valueOf(u.getPos()));
//            black.add(u.getPos());
//
//            for (RoadSegment roadSegment : u.getPredecessors()) {
//                Intersection v = roadSegment.getOrigin();
//                //System.out.println("u "+);
//
//                //if (colors[v.getPos()].equals("white") || colors[v.getPos()].equals("gray")) {
//                if (white.contains(v.getPos()) || gray.contains(v.getPos())) {
//                    relaxInverse(u, v, roadSegment.getLength());
//
//                    //Mettez à jour la distance en ajoutant l'heuristique
//                    //double heuristicValue = heuristic(v, cityMap.getWareHouseLocation());
//                    //distance[v.getPos()] = distance[u.getPos()] + roadSegment.getLength();
//                    //pi[v.getPos()] = u.getPos();
//
//                    //colors[v.getPos()] = "gray";
//                    white.remove(Integer.valueOf(v.getPos()));
//                    black.remove(Integer.valueOf(v.getPos()));
//                    gray.add(v.getPos());
//                }
//            }
//        }
//        /*for (int i = 0; i < pi.length; i++) {
//            System.out.println("pi "+pi[i]);
//
//        }*/
//
//
//        for (int i = 0; i < distance.length; i++) {
//            if(distance[i] != INFINITY && distance[i] != 0){
//                Intersection destination = cityMap.findIntersectionByPos(i);
//                if (deliveryRequest.contains(start) && deliveryRequest.contains(destination)) {
//                    int[] piCopy = Arrays.copyOf(this.pi, this.pi.length);
//                    Chemin chemin = new Chemin( destination,start, piCopy, distance[i]);
//                    chemins.add(chemin);
//                    //System.out.println(Arrays.toString(chemin.getPi()));
//                    //System.out.println(chemin);
//                }
//            }
//        }
//
////        for (Integer inter : black) {
////            //System.out.println("black "+inter);
////            if(distance[inter] != INFINITY && distance[inter] != 0){
////                Intersection destination = cityMap.findIntersectionByPos(inter);
////                if (deliveryRequest.contains(start) && deliveryRequest.contains(destination)) {
////                    int[] piCopy = Arrays.copyOf(this.pi, this.pi.length);
////                    Chemin chemin = new Chemin( destination,start, piCopy, distance[inter]);
////                    chemins.add(chemin);
////                    System.out.println(Arrays.toString(chemin.getPi()));
////                    System.out.println(chemin);
////                }
////            }
////        }
//    }
//
//
//
//    private double calculateEuclideanDistance(Intersection a, Intersection b) {
//        double deltaX = a.getLatitude() - b.getLatitude();
//        double deltaY = a.getLongitude() - b.getLongitude();
//        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
//    }
//
//    // Dans la méthode dijkstra, utilisez la distance euclidienne comme heuristique
//    private double heuristic(Intersection current, Intersection goal) {
//        return calculateEuclideanDistance(current, goal);
//    }
//
//    private boolean hasGrayNode() {
//        return !gray.isEmpty();
//    }
//
//    private boolean notAllBlack() {
//
//
//        for (Intersection intersection : deliveryRequest) {
//            if (!black.contains(intersection.getPos())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private Intersection minGrayNode(CityMap cityMap) {
//        double min = INFINITY;
//        Intersection minNode = null;
//
//        //Essayer de parcourir que les grays
//        /*for (Intersection intersection : cityMap.getIntersections()) {
//            if (colors[intersection.getPos()].equals("gray") && distance[intersection.getPos()] < min) {
//                min = distance[intersection.getPos()];
//                minNode = intersection;
//            }
//        }*/
//
//        for (Integer element : gray) {
//            if ( distance[cityMap.findIntersectionByPos(element).getPos()] < min) {
//                min = distance[cityMap.findIntersectionByPos(element).getPos()];
//                minNode = cityMap.findIntersectionByPos(element);
//            }
//        }
//
//
//        return minNode;
//    }
//
//    private void relax(Intersection u, Intersection v, double weight) {
//        if (distance[u.getPos()] + weight < distance[v.getPos()]) {
//            distance[v.getPos()] = distance[u.getPos()] + weight;
//            this.pi[v.getPos()] = u.getPos();
//        }
//    }
//
//    private void relaxInverse(Intersection u, Intersection v, double weight) {
//        if (distance[u.getPos()] + weight < distance[v.getPos()]) {
//            distance[v.getPos()] = distance[u.getPos()] + weight;
//            this.pi[u.getPos()] = v.getPos();
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        // Création d'intersections
//        Intersection intersectionA = new Intersection(0, 0, 89 , 0);
//        Intersection intersectionB = new Intersection(1, 1, 82 , 1);
//        Intersection intersectionC = new Intersection(2, 2, 92,  2);
//        Intersection intersectionD = new Intersection(3, 3, 92,  3);
//
//        List<Intersection> intersections = new ArrayList<>();
//        intersections.add(intersectionA);
//        intersections.add(intersectionB);
//        intersections.add(intersectionC);
//        intersections.add(intersectionD);
//
//
//        // Création de segments de route avec des longueurs
//        RoadSegment segmentAB = new RoadSegment(intersectionA, intersectionB, "AB", 5.0);
//        RoadSegment segmentAC = new RoadSegment(intersectionA, intersectionC, "AC", 2.0);
//        RoadSegment segmentBD = new RoadSegment(intersectionB, intersectionD, "BD", 4.0);
//        RoadSegment segmentCD = new RoadSegment(intersectionC, intersectionD, "CD", 1.0);
//        //RoadSegment segmentBA = new RoadSegment(intersectionB, intersectionA, "BA", 5.0);
//        RoadSegment segmentCA = new RoadSegment(intersectionC, intersectionA, "CA", 2.0);
//        //RoadSegment segmentDB = new RoadSegment(intersectionD, intersectionB, "DB", 4.0);
//        RoadSegment segmentDC = new RoadSegment(intersectionD, intersectionC, "DC", 1.0);
//
//
//        // Ajout des successeurs pour chaque intersection
//        List<RoadSegment> successorsA = new ArrayList<>();
//        successorsA.add(segmentAB);
//        successorsA.add(segmentAC);
//        intersectionA.setSuccessors(successorsA);
////
//        List<RoadSegment> successorsB = new ArrayList<>();
//        successorsB.add(segmentBD);
//        intersectionB.setSuccessors(successorsB);
////
//        List<RoadSegment> successorsC = new ArrayList<>();
//        successorsC.add(segmentCA);
//        successorsC.add(segmentCD);
////
//        intersectionC.setSuccessors(successorsC);
////
//        List<RoadSegment> successorsD = new ArrayList<>();
//        successorsD.add(segmentDC);
//        intersectionD.setSuccessors(successorsD);
//
////        List<RoadSegment> predecessorsB = new ArrayList<>();
////        predecessorsB.add(segmentAB);
////        intersectionB.setPredecessors(predecessorsB);
////
////        List<RoadSegment> predecessorsC = new ArrayList<>();
////        predecessorsC.add(segmentAC);
////        intersectionC.setPredecessors(predecessorsC);
////
////        List<RoadSegment> predecessorsD = new ArrayList<>();
////        predecessorsD.add(segmentBD);
////        predecessorsD.add(segmentCD);
////        intersectionC.setPredecessors(predecessorsD);
//
//
//        List<RoadSegment> predecessorsA = new ArrayList<>();
//        predecessorsA.add(segmentCA);
//        intersectionB.setPredecessors(predecessorsA);
//
//        //Ajout des prédécesseurs pour chaque intersection
//        List<RoadSegment> predecessorsB = new ArrayList<>();
//        predecessorsB.add(segmentAB);
//        intersectionB.setPredecessors(predecessorsB);
//
//        List<RoadSegment> predecessorsC = new ArrayList<>();
//        predecessorsC.add(segmentAC);
//        predecessorsC.add(segmentDC);
//        intersectionC.setPredecessors(predecessorsC);
//
//        List<RoadSegment> predecessorsD = new ArrayList<>();
//        predecessorsD.add(segmentBD);
//        predecessorsD.add(segmentCD);
//        intersectionD.setPredecessors(predecessorsD);
//
//
//        // Initialisation de l'algorithme de Dijkstra
//
//
//        CityMap cityMap = new CityMap();
//        //deliveryRequest.add(intersectionA);
//        //deliveryRequest.add(intersectionD);
//        cityMap.loadFromXML("src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml");
//        int sizeGraph = cityMap.getIntersections().size(); // Mettez la taille correcte de votre graphe
//
//
//        Dijkstra dij = new Dijkstra(sizeGraph, cityMap);
//        //System.out.println(pi[2]);
//
//        // Appel de l'algorithme de Dijkstra avec le nœud de départ et le nœud d'arrivée
//        //dij.dijkstra(intersectionA, cityMap);
//        System.out.println("intersectionB");
//        //dij.dijkstra(intersectionD, cityMap, sizeGraph);
//        //dij.dijkstra(intersectionD, cityMap, sizeGraph);
//        dij.dijkstra(cityMap.findIntersectionByPos(8), cityMap, sizeGraph);
//        //dij.dijkstra(intersectionD, cityMap, sizeGraph);
//
//
//
//        //dij.dijkstra(intersectionB, cityMap, sizeGraph);
//        //dij.dijkstraReverse(intersectionB, cityMap, sizeGraph);
//
////        for (Chemin chemin : chemins) {
////            int currentIndex = chemin.getFin().getPos();
////            int nextIndex = chemin.getPi()[currentIndex];
////
////            //System.out.println("début: " + currentIndex + ", fin: " + nextIndex);
////
////            // Loop through pi array
////            while (nextIndex != -1) {
////                Intersection currentIntersection = cityMap.findIntersectionByPos(currentIndex);
////                Intersection nextIntersection = cityMap.findIntersectionByPos(nextIndex);
////
////                if (currentIntersection != null && nextIntersection != null) {
////                    // Check if nextIndex is a successor of currentIndex
////                    List<RoadSegment> successors = currentIntersection.getSuccessors();
////                    int finalNextIndex = nextIndex;
////                    boolean isNextIndexSuccessor = successors.stream()
////                            .anyMatch(segment -> segment.getDestination().getPos() == finalNextIndex);
////
////                    if (isNextIndexSuccessor) {
////                        System.out.println("début: " + currentIndex + ", fin: " + nextIndex);
////                        //polylineCoords.append("[").append(currentIntersection.getLatitude()).append(", ").append(currentIntersection.getLongitude()).append("],");
////                    } else {
////                        // Handle the case where nextIndex is not a valid successor
////                        System.out.println("début: " + currentIndex + ", fin: " + nextIndex);
////                        System.out.println("début: " + currentIntersection.getId() + ", fin: " + nextIntersection.getId());
////                        System.out.println("Erreur : nextIndex n'est pas un successeur de currentIndex.");
////                        break;  // Sortir de la boucle pour éviter une boucle infinie
////                    }
////                }
////
////                currentIndex = nextIndex;
////                nextIndex = chemin.getPi()[currentIndex];
////            }
////        }
//
//        for (Chemin chemin : chemins) {
//            //System.out.println("Chemin de " + chemin.getDebut() + " à " + chemin.getFin() + " : " + chemin.getCout());
//            System.out.println(chemin);
//            //System.out.println(chemin.getPi()[141]);
//            //System.out.println(Arrays.toString(chemin.getPi()));
//        }
//    }
//
//
}