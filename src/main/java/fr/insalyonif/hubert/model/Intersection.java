package fr.insalyonif.hubert.model;



import java.util.ArrayList;
import java.util.List;

/**
 * Représente une intersection dans une carte de ville.
 * Chaque intersection est définie par ses coordonnées géographiques,
 * un identifiant unique et une position. Elle peut également avoir des segments de route
 * qui la précèdent et la suivent.
 */
public class Intersection {
    private double latitude;
    private double longitude;
    private List<RoadSegment> predecessors;
    private List<RoadSegment> successors;
    private long id;
    private int pos;

    /**
     * Constructeur pour créer une nouvelle intersection.
     *
     * @param latitude la latitude de l'intersection.
     * @param longitude la longitude de l'intersection.
     * @param id l'identifiant unique de l'intersection.
     * @param pos la position de l'intersection dans un contexte spécifique (par exemple, dans une liste).
     */
    public Intersection(double latitude, double longitude, long id, int pos) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.predecessors = new ArrayList<>();;
        this.successors = new ArrayList<>();;
        this.id = id;
        this.pos = pos;
    }

    /**
     * Retourne la latitude de l'intersection.
     *
     * @return la latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la latitude de l'intersection.
     *
     * @param latitude la nouvelle latitude.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Retourne la longitude de l'intersection.
     *
     * @return la longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la longitude de l'intersection.
     *
     * @param longitude la nouvelle longitude.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Retourne la liste des segments de route précédant l'intersection.
     *
     * @return la liste des prédécesseurs.
     */
    public List<RoadSegment> getPredecessors() {
        return predecessors;
    }

    /**
     * Définit la liste des segments de route précédant l'intersection.
     *
     * @param predecessors la nouvelle liste des prédécesseurs.
     */
    public void setPredecessors(List<RoadSegment> predecessors) {
        this.predecessors = predecessors;
    }

    /**
     * Retourne la liste des segments de route suivant l'intersection.
     *
     * @return la liste des successeurs.
     */
    public List<RoadSegment> getSuccessors() {
        return successors;
    }

    /**
     * Définit la liste des segments de route suivant l'intersection.
     *
     * @param successors la nouvelle liste des successeurs.
     */
    public void setSuccessors(List<RoadSegment> successors) {
        this.successors = successors;
    }

    /**
     * Retourne une représentation en chaîne de caractères des coordonnées de l'intersection.
     *
     * @return les coordonnées sous forme de chaîne de caractères.
     */
    public String getCoordinates() {
        return "lat: " + this.latitude + " ; lng: " + this.longitude;
    }

    /**
     * Retourne l'identifiant unique de l'intersection.
     *
     * @return l'identifiant.
     */
    public long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'intersection.
     *
     * @param id le nouvel identifiant.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Retourne la position de l'intersection.
     *
     * @return la position.
     */
    public int getPos() {
        return pos;
    }

    /**
     * Définit la position de l'intersection.
     *
     * @param pos la nouvelle position.
     */
    public void setPos(int pos) {
        this.pos = pos;
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'intersection.
     *
     * @return la représentation textuelle de l'intersection.
     */
    @Override
    public String toString() {
        return "Intersection{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", id=" + id +
                ", pos=" + pos +
                '}';
    }

}
