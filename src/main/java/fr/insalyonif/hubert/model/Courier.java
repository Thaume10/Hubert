package fr.insalyonif.hubert.model;

public class Courier {
    private int id;

    /**
     * Constructeur de la classe Courier.
     *
     * @param id L'identifiant unique du courrier.
     */
    public Courier(int id) {
        this.id = id;
    }

    /**
     * Obtient l'identifiant unique du courrier.
     *
     * @return L'identifiant unique du courrier.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du courrier.
     *
     * @return Une chaîne de caractères représentant le courrier.
     */
    @Override
    public String toString() {
        return "Courier " + this.id;
    }
}
