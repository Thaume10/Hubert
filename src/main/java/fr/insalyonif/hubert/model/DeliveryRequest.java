package fr.insalyonif.hubert.model;

/**
 * Représente une demande de livraison caractérisée par un lieu de livraison et une fenêtre temporelle.
 * Cette classe est utilisée pour gérer les informations relatives à une demande de livraison spécifique.
 */
public class DeliveryRequest {
    private Intersection deliveryLocation;
    private TimeWindow timeWindow;

    /**
     * Constructeur pour créer une nouvelle demande de livraison.
     *
     * @param deliveryLocation l'intersection représentant le lieu de livraison.
     * @param timeWindow la fenêtre temporelle pendant laquelle la livraison doit être effectuée.
     */
    public DeliveryRequest(Intersection deliveryLocation, TimeWindow timeWindow) {
        this.deliveryLocation = deliveryLocation;
        this.timeWindow = timeWindow;
    }

    /**
     * Retourne le lieu de livraison de la demande.
     *
     * @return l'intersection représentant le lieu de livraison.
     */
    public Intersection getDeliveryLocation() {
        return deliveryLocation;
    }

    /**
     * Définit le lieu de livraison de la demande.
     *
     * @param deliveryLocation l'intersection représentant le nouveau lieu de livraison.
     */
    public void setDeliveryLocation(Intersection deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    /**
     * Retourne la fenêtre temporelle associée à la demande de livraison.
     *
     * @return la fenêtre temporelle.
     */
    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    /**
     * Définit la fenêtre temporelle associée à la demande de livraison.
     *
     * @param timeWindow la nouvelle fenêtre temporelle.
     */
    public void setTimeWindow(TimeWindow timeWindow) {
        this.timeWindow = timeWindow;
    }

    /**
     * Retourne une représentation en chaîne de caractères de la demande de livraison.
     *
     * @return la représentation textuelle de la demande de livraison.
     */
    @Override
    public String toString() {
        return "DeliveryRequest{" +
                "deliveryLocation=" + deliveryLocation +
                ", timeWindow=" + timeWindow +
                '}';
    }
}
