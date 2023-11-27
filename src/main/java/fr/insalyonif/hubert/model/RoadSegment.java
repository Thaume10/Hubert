package tsp;



/**
 * Représente un segment de route dans une carte de ville.
 * Chaque segment est défini par une origine, une destination, un nom et une longueur.
 */
public class RoadSegment {
    private Intersection origin;
    private Intersection destination;
    private String name;
    private double length;

    /**
     * Constructeur pour créer un nouveau segment de route.
     *
     * @param origin l'intersection d'origine du segment de route.
     * @param destination l'intersection de destination du segment de route.
     * @param name le nom du segment de route.
     * @param length la longueur du segment de route, en kilomètres.
     */
    public RoadSegment(Intersection origin, Intersection destination, String name, double length) {
        this.origin = origin;
        this.destination = destination;
        this.name = name;
        this.length = length;
    }

    /**
     * Retourne l'intersection d'origine du segment de route.
     *
     * @return l'intersection d'origine.
     */
    public Intersection getOrigin() {
        return origin;
    }

    /**
     * Retourne l'intersection de destination du segment de route.
     *
     * @return l'intersection de destination.
     */
    public Intersection getDestination() {
        return destination;
    }

    /**
     * Retourne le nom du segment de route.
     *
     * @return le nom du segment.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne la longueur du segment de route.
     *
     * @return la longueur du segment, en kilomètres.
     */
    public double getLength() {
        return length;
    }

    /**
     * Définit l'intersection d'origine du segment de route.
     *
     * @param origin la nouvelle intersection d'origine.
     */
    public void setOrigin(Intersection origin) {
        this.origin = origin;
    }

    /**
     * Définit l'intersection de destination du segment de route.
     *
     * @param destination la nouvelle intersection de destination.
     */
    public void setDestination(Intersection destination) {
        this.destination = destination;
    }

    /**
     * Définit le nom du segment de route.
     *
     * @param name le nouveau nom du segment.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Définit la longueur du segment de route.
     *
     * @param length la nouvelle longueur du segment, en kilomètres.
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Retourne une représentation en chaîne de caractères du segment de route.
     *
     * @return la représentation textuelle du segment de route.
     */
    @Override
    public String toString() {
        return "RoadSegment{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", name='" + name + '\'' +
                ", length=" + length +
                '}';
    }
}
