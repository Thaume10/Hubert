package fr.insalyonif.hubert.model;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

public class DeliveryTour {
    private Courier courier;
    private ArrayList<DeliveryRequest> requests;
    private List<Chemin> paths;
    private Instant startTime;
    private Instant endTime;
    private Dijkstra dij;
    private ArrayList<Chemin> cheminDij;
    private DijkstraInverse dijInv;

    /**
     * Constructeur par défaut de la classe DeliveryTour.
     * Initialise la liste des requêtes et des chemins Dijkstra.
     */
    public DeliveryTour() {
        requests = new ArrayList<>();
        cheminDij = new ArrayList<Chemin>();
    }

    /**
     * Obtient le coursier associé à la tournée de livraison.
     *
     * @return Le coursier associé à la tournée de livraison.
     */
    public Courier getCourier() {
        return courier;
    }

    /**
     * Définit l'algorithme de Dijkstra utilisé pour la tournée de livraison.
     *
     * @param dij L'instance de l'algorithme de Dijkstra.
     */
    public void setDijkstra(Dijkstra dij) {
        this.dij = dij;
    }

    /**
     * Obtient l'algorithme de Dijkstra utilisé pour la tournée de livraison.
     *
     * @return L'instance de l'algorithme de Dijkstra.
     */
    public Dijkstra getDijkstra() {
        return this.dij;
    }

    /**
     * Définit l'algorithme de Dijkstra Inverse utilisé pour la tournée de livraison.
     *
     * @param dijInv L'instance de l'algorithme de Dijkstra Inverse.
     */
    public void setDijkstraInverse(DijkstraInverse dijInv) {
        this.dijInv = dijInv;
    }

    /**
     * Obtient l'algorithme de Dijkstra Inverse utilisé pour la tournée de livraison.
     *
     * @return L'instance de l'algorithme de Dijkstra Inverse.
     */
    public DijkstraInverse getDijkstraInverse() {
        return this.dijInv;
    }

    /**
     * Définit le coursier associé à la tournée de livraison.
     *
     * @param courier Le coursier associé à la tournée de livraison.
     */
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    /**
     * Met à jour la liste des chemins Dijkstra pour la tournée de livraison.
     *
     * @param chemins La liste des chemins Dijkstra à ajouter.
     */
    public void majCheminsDij(ArrayList<Chemin> chemins) {
        cheminDij.addAll(chemins);
    }

    /**
     * Efface la liste des chemins Dijkstra pour la tournée de livraison.
     */
    public void clearCheminsDij() {
        cheminDij.clear();
    }

    /**
     * Obtient la liste des chemins Dijkstra pour la tournée de livraison.
     *
     * @return La liste des chemins Dijkstra.
     */
    public ArrayList<Chemin> getCheminDij() {
        return cheminDij;
    }

    /**
     * Obtient la liste des requêtes de livraison pour la tournée.
     *
     * @return La liste des requêtes de livraison.
     */
    public ArrayList<DeliveryRequest> getRequests() {
        return requests;
    }

    /**
     * Définit la liste des requêtes de livraison pour la tournée.
     *
     * @param requests La liste des requêtes de livraison.
     */
    public void setRequests(ArrayList<DeliveryRequest> requests) {
        this.requests = requests;
    }

    /**
     * Obtient la liste des chemins pour la tournée de livraison.
     *
     * @return La liste des chemins pour la tournée de livraison.
     */
    public List<Chemin> getPaths() {
        return paths;
    }

    /**
     * Définit la liste des chemins pour la tournée de livraison.
     *
     * @param paths La liste des chemins pour la tournée de livraison.
     */
    public void setPaths(List<Chemin> paths) {
        this.paths = paths;
    }

    /**
     * Obtient le moment de début de la tournée de livraison.
     *
     * @return Le moment de début de la tournée de livraison.
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Définit le moment de début de la tournée de livraison.
     *
     * @param startTime Le moment de début de la tournée de livraison.
     */
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    /**
     * Obtient le moment de fin de la tournée de livraison.
     *
     * @return Le moment de fin de la tournée de livraison.
     */
    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Définit le moment de fin de la tournée de livraison.
     *
     * @param endTime Le moment de fin de la tournée de livraison.
     */
    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }
}
