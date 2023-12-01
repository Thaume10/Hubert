package fr.insalyonif.hubert.model;
import java.util.*;
/**
 * Classe pour implémenter l'algorithme de Dijkstra.
 */
public class Dijkstra extends AbstractDijkstra {

    private final int INFINITY = Integer.MAX_VALUE;

    /**
     * Constructeur de la classe Dijkstra.
     *
     * @param sizeGraph La taille du graphe.
     * @param cityMap   La carte de la ville utilisée pour la navigation.
     */
    public Dijkstra(int sizeGraph, CityMap cityMap) {
        super(sizeGraph, cityMap);
        // Ajoute le point de départ (entrepôt) aux demandes de livraison.
        chemins = new ArrayList<>();
        deliveryRequest = new ArrayList<>();
        deliveryRequest.add(cityMap.getWareHouseLocation());
    }

    /**
     * Relâche un nœud dans l'algorithme de relaxation de Dijkstra.
     *
     * @param u      Le nœud source.
     * @param v      Le nœud de destination.
     * @param weight Le poids associé à l'arête entre u et v.
     */
    @Override
    protected void relax(Intersection u, Intersection v, double weight) {
        // Met à jour la distance et le prédecesseur si un chemin plus court est trouvé.
        if (distance[u.getPos()] + weight < distance[v.getPos()]) {
            distance[v.getPos()] = distance[u.getPos()] + weight;
            this.pi[v.getPos()] = u.getPos();
        }
    }

    /**
     * Construit la copie partielle du tableau pi lors de l'ajout d'une demande de livraison.
     *
     * @param piCopy    Tableau partiellement copié.
     * @param start     L'intersection de départ.
     * @param delivery  L'intersection de livraison.
     */
    protected void piCopyConstructor(int[] piCopy, Intersection start, Intersection delivery) {
        int j = delivery.getPos();
        while (j != start.getPos()) {
            piCopy[j] = this.pi[j];
            j = this.pi[j];
        }
    }

    /**
     * Récupère les voisins d'une intersection utilisés dans l'algorithme de Dijkstra.
     *
     * @param intersection L'intersection pour laquelle les voisins sont récupérés.
     * @return Une collection de segments de route voisins.
     */
    @Override
    protected Iterable<RoadSegment> getNeighbors(Intersection intersection) {
        return intersection.getSuccessors();
    }

    /**
     * Sélectionne le nœud suivant à explorer pendant l'algorithme de Dijkstra.
     *
     * @param roadSegment Le segment de route connectant le nœud actuel au nœud suivant.
     * @return Le nœud de destination du segment de route.
     */
    @Override
    protected Intersection selectNode(RoadSegment roadSegment) {
        return roadSegment.getDestination();
    }

    /**
     * Crée un objet Chemin représentant le chemin entre deux intersections.
     *
     * @param start       L'intersection de départ.
     * @param destination L'intersection de destination.
     * @param pi          Tableau de prédécesseurs représentant le chemin.
     * @param cout        Le coût total du chemin.
     * @return Un objet Chemin représentant le chemin calculé.
     */
    @Override
    protected Chemin createChemin(Intersection start, Intersection destination, int[] pi, double cout) {
        Chemin chemin = new Chemin(start, destination, pi, cout);
        return chemin;
    }

    /**
     * Ajoute une intersection de livraison à la liste des demandes de livraison.
     *
     * @param deliveryIntersection L'intersection de livraison à ajouter.
     */
    public void addDeliveryRequest(Intersection deliveryIntersection) {
        deliveryRequest.add(deliveryIntersection);
    }
}
