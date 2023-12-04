package fr.insalyonif.hubert.model;



import java.util.Arrays;
import java.util.List;


public class Chemin {
    private Intersection debut;
    private Intersection fin;
    private int [] pi;
    private double cout;

    public Chemin(Intersection debut, Intersection fin, int[] pi, double cout) {
        this.debut = debut;
        this.fin = fin;
        this.pi = pi;
        this.cout = cout;
    }

    public Intersection getDebut() {
        return debut;
    }

    public Intersection getFin() {
        return fin;
    }

    public int[] getPi() {
        return pi;
    }

    public double getCout() {
        return cout;
    }

    public void setDebut(Intersection debut) {
        this.debut = debut;
    }

    public void setFin(Intersection fin) {
        this.fin = fin;
    }

    public void setPi(int[] pi) {
        this.pi = pi;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

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
