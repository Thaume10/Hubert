package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe pour implémenter l'algorithme de Dijkstra Inverse.
 */
public class DijkstraInverse extends AbstractDijkstra {

    /**
     * Constructeur de la classe DijkstraInverse.
     *
     * @param sizeGraph La taille du graphe.
     * @param cityMap   La carte de la ville utilisée pour la navigation.
     */
    public DijkstraInverse(int sizeGraph, CityMap cityMap) {
        super(sizeGraph, cityMap);

    }

    /**
     * Relâche un nœud dans l'algorithme de relaxation de DijkstraInverse.
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
        ArrayList<Integer> parcours = new ArrayList<Integer>();
        parcours.add(j);
        while (j != start.getPos()) {
            parcours.add(this.pi[j]);
            j = this.pi[j];
        }
        for (int i = parcours.size() - 1; i > 0; i--) {
            piCopy[parcours.get(i)] = parcours.get(i - 1);
        }
    }

    /**
     * Récupère les voisins d'une intersection utilisés dans l'algorithme de DijkstraInverse.
     *
     * @param intersection L'intersection pour laquelle les voisins sont récupérés.
     * @return Une collection de segments de route voisins.
     */
    @Override
    protected Iterable<RoadSegment> getNeighbors(Intersection intersection) {
        return intersection.getPredecessors();
    }

    /**
     * Sélectionne le nœud suivant à explorer pendant l'algorithme de DijkstraInverse.
     *
     * @param roadSegment Le segment de route connectant le nœud actuel au nœud suivant.
     * @return Le nœud d'origine du segment de route.
     */
    @Override
    protected Intersection selectNode(RoadSegment roadSegment) {
        return roadSegment.getOrigin();
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
        Chemin chemin = new Chemin(destination, start, pi, cout);
        return chemin;
    }
}
