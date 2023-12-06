package fr.insalyonif.hubert.model;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Représente une fenêtre temporelle définie par un instant de début et un instant de fin.
 * Cette classe est utile pour gérer des périodes définies dans le temps.
 */
public class TimeWindow {
    private int startTime;
    private int endTime;

    /**
     * Constructeur pour créer une nouvelle fenêtre temporelle.
     *
     * @param startTime l'instant de début de la fenêtre temporelle.
     * @param endTime l'instant de fin de la fenêtre temporelle.
     */
    public TimeWindow(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Retourne l'instant de début de la fenêtre temporelle.
     *
     * @return l'instant de début.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Définit l'instant de début de la fenêtre temporelle.
     *
     * @param startTime le nouvel instant de début.
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Retourne l'instant de fin de la fenêtre temporelle.
     *
     * @return l'instant de fin.
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Définit l'instant de fin de la fenêtre temporelle.
     *
     * @param endTime le nouvel instant de fin.
     */
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    /**
     * Retourne une représentation en chaîne de caractères de la fenêtre temporelle.
     *
     * @return la représentation textuelle de la fenêtre temporelle.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH").withZone(ZoneId.systemDefault());

        // Formater les instants de début et de fin
        /*String startTimeFormatted = formatter.format(startTime);
        String endTimeFormatted = formatter.format(endTime);*/

        // Retourner la représentation en chaîne de la fenêtre temporelle avec la mention spécifique
        return "Passage entre " + startTime + "h et " + endTime+"h";
    }



    /**
     * Vérifie si un instant spécifique se situe à l'intérieur de la fenêtre temporelle.
     *
     * @param time l'instant à vérifier.
     * @return true si l'instant est à l'intérieur de la fenêtre temporelle, false sinon.
     */
    public boolean isInTimeWindow(int time){
        return (time<endTime && time>=startTime);
    }
}
