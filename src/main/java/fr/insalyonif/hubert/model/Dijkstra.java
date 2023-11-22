package fr.insalyonif.hubert.model;
import java.util.*;

public class Dijkstra {
    private static final int INFINITY = Integer.MAX_VALUE;

    public static double[] distance;
    public static int[] pi;
    public static boolean[] visited;

    public static String[] colors;

    public static ArrayList<Chemin> chemins = new ArrayList<Chemin>();



    public Dijkstra(int sizeGraph, CityMap cityMap) {
        this.distance = new double[sizeGraph];
        this.pi = new int[sizeGraph];
        this.visited = new boolean[sizeGraph];
        this.colors = new String[sizeGraph];



        //test

        // cache warehouse
        dijkstra(cityMap.getWareHouseLocation(), cityMap, sizeGraph);
        // TO DO
    }

    /*public static void dijkstra(Intersection start, CityMap cityMap) {
        // Initialiser la distance pour le nœud de départ
        // Initialiser toutes les distances à l'infini, les prédécesseurs à null et les nœuds visités à false
        Arrays.fill(distance, INFINITY);
        Arrays.fill(pi, 0);
        Arrays.fill(visited, false);

        distance[ start.getPos()] = 0.0;
        for (int i = 0; i < distance.length; i++) {
            System.out.println("distance["+i+"] = "+ distance[i]);
        }
        for (int i = 0; i < distance.length; i++) {
            System.out.println("visited["+i+"] = "+ visited[i]);
        }


        // Continuer tant qu'il reste des nœuds non visités
        while (!allNodesVisited()) {
            // Choisir le nœud avec la distance minimale non visitée
            int indiceMinDistance = minDistance();
            if(indiceMinDistance == -1){
                //sortir de la boucle while
                break;
            }

            System.out.println("indiceMin = "+indiceMinDistance);
            Intersection u = cityMap.findIntersectionByPos(indiceMinDistance);

            // Marquer le nœud comme visité
            visited[ u.getPos()] = true;

            // Mettre à jour les distances des nœuds adjacents non visités
            for (RoadSegment roadSegment : u.getSuccessors()) {
                Intersection v = roadSegment.getDestination();
                if (!visited[ v.getPos()] && distance[u.getPos()] != INFINITY &&
                        distance[u.getPos()] + roadSegment.getLength() < distance[v.getPos()]) {
                    distance[v.getPos()] = distance[u.getPos()] + roadSegment.getLength();
                    pi[v.getPos()] = u.getPos();
                }
            }
        }

        // Afficher les distances les plus courtes depuis le nœud de départ
        for (int i = 0; i < distance.length; i++) {
            Chemin chemin = new Chemin(start, cityMap.findIntersectionByPos(i), pi, distance[i]) ;
            //System.out.println(chemin);
            chemins.add(chemin);
        }


    }

    private static int minDistance() {
        double min = INFINITY;
        int minIntersection = -1;



        for (int i = 0; i < distance.length; i++) {
            System.out.println("passe");
            //System.out.println(visited[i]);
            //System.out.println(distance[i]);
            if (!visited[i] && distance[i] < min) {
                System.out.println("min = "+distance[i]+" ,i = "+i);
                min = distance[i];
                minIntersection = i;
            }
        }

        return minIntersection;
    }

    // Méthode pour vérifier si tous les nœuds ont été visités
    private static boolean allNodesVisited() {
        for (boolean nodeVisited : visited) {
            if (!nodeVisited) {
                return false;
            }
        }
        return true;
    }*/
    public static void dijkstra(Intersection start, CityMap cityMap, int sizeGraph) {
        for (int i = 0; i < sizeGraph; i++) {
            distance[i] = INFINITY;
            pi[i] = -1;
            colors[i] = "white";
        }
        distance[start.getPos()] = 0.0;
        colors[start.getPos()] = "gray";

        while (hasGrayNode()) {
            Intersection u = minGrayNode(cityMap);
            colors[u.getPos()] = "black";

            for (RoadSegment roadSegment : u.getSuccessors()) {
                Intersection v = roadSegment.getDestination();

                if (colors[v.getPos()].equals("white") || colors[v.getPos()].equals("gray")) {
                    relax(u, v, roadSegment.getLength());
                    colors[v.getPos()] = "gray";
                }
            }
        }

        for (int i = 0; i < distance.length; i++) {
            if(distance[i] != INFINITY && distance[i] != 0){
                Chemin chemin = new Chemin(start, cityMap.findIntersectionByPos(i), pi, distance[i]);
                chemins.add(chemin);
            }

        }
    }

    private static boolean hasGrayNode() {
        for (String color : colors) {
            if (color.equals("gray")) {
                return true;
            }
        }
        return false;
    }

    private static Intersection minGrayNode(CityMap cityMap) {
        double min = INFINITY;
        Intersection minNode = null;
        //réduire ça pour regarder le min que des gris
        // TO DO
        for (Intersection intersection : cityMap.getIntersections()) {
            if (colors[intersection.getPos()].equals("gray") && distance[intersection.getPos()] < min) {
                min = distance[intersection.getPos()];
                minNode = intersection;
            }
        }

        return minNode;
    }

    private static void relax(Intersection u, Intersection v, double weight) {
        if (distance[u.getPos()] + weight < distance[v.getPos()]) {
            distance[v.getPos()] = distance[u.getPos()] + weight;
            pi[v.getPos()] = u.getPos();
        }
    }

    public static void main(String[] args) {
        // Création d'intersections
        Intersection intersectionA = new Intersection(0, 0, 89 , 0);
        Intersection intersectionB = new Intersection(1, 1, 82 , 1);
        Intersection intersectionC = new Intersection(2, 2, 92,  2);
        Intersection intersectionD = new Intersection(3, 3, 92,  3);

        List<Intersection> intersections = new ArrayList<>();
        intersections.add(intersectionA);
        intersections.add(intersectionB);
        intersections.add(intersectionC);
        intersections.add(intersectionD);


        // Création de segments de route avec des longueurs
        RoadSegment segmentAB = new RoadSegment(intersectionA, intersectionB, "AB", 5.0);
        RoadSegment segmentAC = new RoadSegment(intersectionA, intersectionC, "AC", 2.0);
        RoadSegment segmentBD = new RoadSegment(intersectionB, intersectionD, "BD", 4.0);
        RoadSegment segmentCD = new RoadSegment(intersectionC, intersectionD, "CD", 1.0);

        // Ajout des successeurs pour chaque intersection
        List<RoadSegment> successorsA = new ArrayList<>();
        successorsA.add(segmentAB);
        successorsA.add(segmentAC);
        intersectionA.setSuccessors(successorsA);

        List<RoadSegment> successorsB = new ArrayList<>();
        successorsB.add(segmentBD);
        intersectionB.setSuccessors(successorsB);

        List<RoadSegment> successorsC = new ArrayList<>();
        successorsC.add(segmentCD);
        intersectionC.setSuccessors(successorsC);

        List<RoadSegment> successorsD = new ArrayList<>();
        intersectionD.setSuccessors(successorsD);

        // Initialisation de l'algorithme de Dijkstra
        int sizeGraph = 4; // Mettez la taille correcte de votre graphe

        CityMap cityMap = new CityMap(intersections, intersectionA);


        Dijkstra dij = new Dijkstra(sizeGraph, cityMap);
        //System.out.println(pi[2]);

        // Appel de l'algorithme de Dijkstra avec le nœud de départ et le nœud d'arrivée
        //dij.dijkstra(intersectionA, cityMap);
        System.out.println("intersectionB");
        dij.dijkstra(intersectionB, cityMap, sizeGraph);
        dij.dijkstra(intersectionC, cityMap, sizeGraph);
        dij.dijkstra(intersectionD, cityMap, sizeGraph);

        for (Chemin chemin : chemins) {
            System.out.println("Chemin de " + chemin.getDebut() + " à " + chemin.getFin() +
                    " : " + chemin.getCout());
        }
    }


}




