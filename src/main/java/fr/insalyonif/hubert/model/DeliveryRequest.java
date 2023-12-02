package fr.insalyonif.hubert.model;

import java.time.Instant;

/**
 * Représente une demande de livraison caractérisée par un lieu de livraison et une fenêtre temporelle.
 * Cette classe est utilisée pour gérer les informations relatives à une demande de livraison spécifique.
 */
public class DeliveryRequest {
    private Intersection deliveryLocation;
    private TimeWindow timeWindow;
    private Instant deliveryTime;

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
     * Constructeur pour créer une nouvelle demande de livraison sans timeWindow.
     *
     * @param deliveryLocation l'intersection représentant le lieu de livraison.
     */
    public DeliveryRequest(Intersection deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
        this.timeWindow = new TimeWindow(Instant.parse("2023-11-27T08:00:00Z"),Instant.parse("2023-11-27T09:00:00Z"));
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
     * Retourne l'heure de livraison.
     *
     * @param timeWindow la nouvelle fenêtre temporelle.
     */
    public Instant getDeliveryTime() {
        return this.deliveryTime;
    }

    /**
     * Définit l'heure de livraison.
     *
     *
     * @param timeWindow la nouvelle fenêtre temporelle.
     */
    public void setDeliveryTime(Instant time) {
        this.deliveryTime = time;
    }


    /**
     * Retourne une représentation en chaîne de caractères de la demande de livraison.
     *
     * @return la représentation textuelle de la demande de livraison.
     */
    @Override
    public String toString() {
        String deliveryTimeString = deliveryTime != null ? deliveryTime.toString() : "Non spécifié";
        return "Delivery at intersection: " + deliveryLocation.getId() +
                "\nScheduled for: " + deliveryTimeString;
    }
}
