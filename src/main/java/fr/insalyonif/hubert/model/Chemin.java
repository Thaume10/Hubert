package fr.insalyonif.hubert.model;

import java.util.Arrays;

public class Chemin {
    private Intersection debut;
    private Intersection fin;
    private int[] pi;
    private double cout;

    /**
     * Constructeur de la classe Chemin.
     *
     * @param debut L'Intersection de départ du chemin.
     * @param fin L'Intersection de fin du chemin.
     * @param pi Un tableau représentant le chemin en termes d'indices des intersections visitées.
     * @param cout Le coût total du chemin.
     */
    public Chemin(Intersection debut, Intersection fin, int[] pi, double cout) {
        this.debut = debut;
        this.fin = fin;
        this.pi = pi;
        this.cout = cout;
    }

    /**
     * Obtient l'Intersection de départ du chemin.
     *
     * @return L'Intersection de départ du chemin.
     */
    public Intersection getDebut() {
        return debut;
    }

    /**
     * Obtient l'Intersection de fin du chemin.
     *
     * @return L'Intersection de fin du chemin.
     */
    public Intersection getFin() {
        return fin;
    }

    /**
     * Obtient le tableau représentant le chemin en termes d'indices des intersections visitées.
     *
     * @return Le tableau d'indices représentant le chemin.
     */
    public int[] getPi() {
        return pi;
    }

    /**
     * Obtient le coût total du chemin.
     *
     * @return Le coût total du chemin.
     */
    public double getCout() {
        return cout;
    }

    /**
     * Modifie l'Intersection de départ du chemin.
     *
     * @param debut La nouvelle Intersection de départ du chemin.
     */
    public void setDebut(Intersection debut) {
        this.debut = debut;
    }

    /**
     * Modifie l'Intersection de fin du chemin.
     *
     * @param fin La nouvelle Intersection de fin du chemin.
     */
    public void setFin(Intersection fin) {
        this.fin = fin;
    }

    /**
     * Modifie le tableau représentant le chemin en termes d'indices des intersections visitées.
     *
     * @param pi Le nouveau tableau d'indices représentant le chemin.
     */
    public void setPi(int[] pi) {
        this.pi = pi;
    }

    /**
     * Modifie le coût total du chemin.
     *
     * @param cout Le nouveau coût total du chemin.
     */
    public void setCout(double cout) {
        this.cout = cout;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du chemin.
     *
     * @return Une chaîne de caractères représentant le chemin.
     */
    @Override
    public String toString() {
        return "Chemin{" +
                "debut=" + debut +
                ", fin=" + fin +
                ", pi=" + Arrays.toString(pi) +
                ", cout=" + cout +
                '}';
    }
}
