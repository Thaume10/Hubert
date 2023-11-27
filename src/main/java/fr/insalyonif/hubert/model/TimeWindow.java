package fr.insalyonif.hubert.model;
import java.time.Instant;

/**
 * Représente une fenêtre temporelle définie par un instant de début et un instant de fin.
 * Cette classe est utile pour gérer des périodes définies dans le temps.
 */
public class TimeWindow {
    private Instant startTime;
    private Instant endTime;

    /**
     * Constructeur pour créer une nouvelle fenêtre temporelle.
     *
     * @param startTime l'instant de début de la fenêtre temporelle.
     * @param endTime l'instant de fin de la fenêtre temporelle.
     */
    public TimeWindow(Instant startTime, Instant endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Retourne l'instant de début de la fenêtre temporelle.
     *
     * @return l'instant de début.
     */
    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Définit l'instant de début de la fenêtre temporelle.
     *
     * @param startTime le nouvel instant de début.
     */
    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    /**
     * Retourne l'instant de fin de la fenêtre temporelle.
     *
     * @return l'instant de fin.
     */
    public Instant getEndTime() {
        return endTime;
    }

    /**
     * Définit l'instant de fin de la fenêtre temporelle.
     *
     * @param endTime le nouvel instant de fin.
     */
    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    /**
     * Retourne une représentation en chaîne de caractères de la fenêtre temporelle.
     *
     * @return la représentation textuelle de la fenêtre temporelle.
     */
    @Override
    public String toString() {
        return "TimeWindow{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    /**
     * Vérifie si un instant spécifique se situe à l'intérieur de la fenêtre temporelle.
     *
     * @param time l'instant à vérifier.
     * @return true si l'instant est à l'intérieur de la fenêtre temporelle, false sinon.
     */
    public boolean isInTimeWindow(Instant time){
        return time.isBefore(this.endTime) && time.isAfter(this.startTime);
    }
}
