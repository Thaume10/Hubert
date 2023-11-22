package fr.insalyonif.hubert.model;
import java.util.*;

public class Dijkstra {
    private static final int INFINITY = Integer.MAX_VALUE;

    public double[] distance;
    public int[] pi;
    public boolean[] visited;

    public Dijkstra(int sizeGraph) {
        this.distance = new double[sizeGraph];
        this.pi = new int[sizeGraph];
        this.visited = new boolean[sizeGraph];
        // Initialiser toutes les distances à l'infini, les prédécesseurs à null et les nœuds visités à false
        Arrays.fill(distance, INFINITY);
        Arrays.fill(pi, 0);
        Arrays.fill(visited, false);
    }

    public static void dijkstra(Intersection inter, Intersection warehouse, int sizeGraph) {
        //int n = graph.length;
        //d[i.getId()]=0;
        //int[] distance = new int[n];
        //boolean[] visited = new boolean[n];

        distance[start.getId()] = 0.0;

        for (int i = 0; i < sizeGraph - 1; i++) {
            // Choisir le nœud avec la distance minimale non visitée
            int u = minDistance(distance, visited);

            // Marquer le nœud comme visité
            visited[u] = true;

            // Mettre à jour les distances des nœuds adjacents non visités
            for (int v = 0; v < sizeGraph; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != INFINITY &&
                        distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }

        // Afficher les distances les plus courtes depuis le nœud de départ
        for (int i = 0; i < sizeGraph; i++) {
            System.out.println("Distance de " + start + " à " + i + " : " + distance[i]);
        }


    }

    private Intersection minDistance() {
        double min = INFINITY;
        Intersection minIntersection = null;

        for (int i = 0; i < distance.length; i++) {
            if (!visited[i] && distance[i] < min) {
                min = distance[i];
                minIntersection = new Intersection(0, 0, null, null, i);
            }
        }

        return minIntersection;
    }




}




