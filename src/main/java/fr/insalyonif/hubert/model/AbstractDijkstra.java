package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe abstraite pour implémenter l'algorithme de Dijkstra.
 */
public abstract class AbstractDijkstra {
    private final int INFINITY = Integer.MAX_VALUE;

    public double[] distance;
    public int[] pi;
    public boolean[] visited;

    public String[] colors;

    private ArrayList<Chemin> chemins ;

    public ArrayList<Intersection> deliveryRequest ;

    protected CityMap cityMap;

    /**
     * Constructeur de la classe AbstractDijkstra.
     *
     * @param sizeGraph La taille du graphe.
     * @param cityMap   La carte de la ville.
     */
    public AbstractDijkstra(int sizeGraph, CityMap cityMap) {
        this.distance = new double[sizeGraph];
        this.pi = new int[sizeGraph];
        this.visited = new boolean[sizeGraph];
        this.cityMap = cityMap;
        this.chemins = new ArrayList<Chemin>();
        deliveryRequest = new ArrayList<Intersection>();
        deliveryRequest.add(cityMap.getWareHouseLocation());





        colors = new String[sizeGraph];
        Arrays.fill(colors, "white");
    }

    public void cleanDij(){
        chemins.clear();
    }

    /**
     * Calcule la distance euclidienne entre deux intersections.
     *
     * @param a Intersection A.
     * @param b Intersection B.
     * @return La distance euclidienne entre les deux intersections.
     */
    protected double calculateEuclideanDistance(Intersection a, Intersection b) {
        double deltaX = a.getLatitude() - b.getLatitude();
        double deltaY = a.getLongitude() - b.getLongitude();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Fonction heuristique pour l'algorithme A*.
     *
     * @param current L'intersection actuelle.
     * @param goal    L'intersection objectif.
     * @return La valeur heuristique entre l'intersection actuelle et l'objectif.
     */
    protected double heuristic(Intersection current, Intersection goal) {
        return calculateEuclideanDistance(current, goal);
    }

    /**
     * Vérifie s'il y a un nœud gris dans le graphe.
     *
     * @return true s'il y a un nœud gris, sinon false.
     */
    protected boolean hasGrayNode() {
        for (String color : colors) {
            if (color.equals("gray")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Trouve le nœud gris avec la plus petite distance dans le graphe.
     *
     * @return L'intersection correspondant au nœud gris avec la plus petite distance.
     */
    protected Intersection minGrayNode() {
        double min = INFINITY;
        Intersection minNode = null;

        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals("gray") && distance[i] < min) {
                min = distance[i];
                minNode = cityMap.findIntersectionByPos(i);
            }
        }

        return minNode;
    }

    /**
     * Exécute l'algorithme de Dijkstra pour trouver les chemins les plus courts.
     *
     * @param start     L'intersection de départ.
     * @param sizeGraph La taille du graphe.
     * @return true si le point de départ peut atteindre tous les points de livraison, sinon false.
     */
    public boolean runDijkstra(Intersection start, int sizeGraph) {
        if (!deliveryRequest.contains(start)) {
            deliveryRequest.add(start);
        }
        System.out.println("caca" + deliveryRequest);

        for (int i = 0; i < sizeGraph; i++) {
            distance[i] = INFINITY;
            this.pi[i] = -1;
            colors[i] = "white";
        }

        distance[start.getPos()] = 0.0;
        colors[start.getPos()] = "gray";

        while (hasGrayNode()) {
            Intersection u = minGrayNode();

            for (RoadSegment roadSegment : getNeighbors(u)) {
                Intersection v = selectNode(roadSegment);

                if (colors[v.getPos()].equals("white") || colors[v.getPos()].equals("gray")) {
                    relax(u, v, roadSegment.getLength());
                    colors[v.getPos()] = "gray";
                }
            }
            colors[u.getPos()] = "black";
        }

        boolean canReachAllDeliveryPoints = true;
        for (Intersection deliveryPoint : deliveryRequest) {
            if (distance[deliveryPoint.getPos()] == INFINITY) {
                canReachAllDeliveryPoints = false;
                break;
            }
        }

        if (!canReachAllDeliveryPoints) {
            deliveryRequest.remove(start);
            chemins.removeIf(chemin -> chemin.getDebut().equals(start));
            chemins.removeIf(chemin -> chemin.getFin().equals(start));
            return false;
        }

        for (Intersection deliveryRequest : deliveryRequest) {
            if (deliveryRequest != start) {
                int[] piCopy = new int[sizeGraph];
                Arrays.fill(piCopy, -1);
                if (pi[deliveryRequest.getPos()] == -1) {
                    return false;
                }
                piCopyConstructor(piCopy, start, deliveryRequest);
                Chemin chemin = createChemin(start, deliveryRequest, piCopy, distance[deliveryRequest.getPos()]);
                chemins.add(chemin);
            }
        }
        return true;
    }

    protected abstract Iterable<RoadSegment> getNeighbors(Intersection intersection);
    protected abstract Intersection selectNode(RoadSegment roadSegment);
    protected abstract Chemin createChemin(Intersection start, Intersection destination, int[] pi, double cout);
    protected abstract void relax(Intersection u, Intersection v, double weight);

    /**
     * Copie les prédécesseurs du chemin pour une demande de livraison spécifique.
     *
     * @param piCopy    Tableau de prédécesseurs à copier.
     * @param start     Intersection de départ.
     * @param delivery  Intersection de destination (demande de livraison).
     */
    protected abstract void piCopyConstructor(int[] piCopy, Intersection start, Intersection delivery);

    /**
     * Retourne la liste des chemins calculés par l'algorithme de Dijkstra.
     *
     * @return Une liste de chemins.
     */
    public ArrayList<Chemin> getChemins() {
        return chemins;
    }


    /**
     * Retourne la liste des intersections faisant l'objet de demandes de livraison.
     *
     * @return Une liste d'intersections représentant les demandes de livraison.
     */
    public List<Intersection> getDeliveryRequest() {
        return deliveryRequest;
    }
}
