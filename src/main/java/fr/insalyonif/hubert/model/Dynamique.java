package fr.insalyonif.hubert.model;

import java.util.List;

public interface Dynamique {
    /**
     * Crée un ensemble contenant tous les entiers de 1 à n-1
     * @param n la limite supérieure de l'ensemble
     * @return l'ensemble créé
     */
    public int createSet(int n);

    /**
     * Affiche tous les éléments de l'ensemble donné
     * @param s l'ensemble à afficher
     */
    public void printSet(int s);

    /**
     * Obtient un objet Chemin à partir d'une liste de chemins basée sur les positions de début et de fin fournies
     * @param chemins la liste de chemins
     * @param debut la position de départ
     * @param fin la position de fin
     * @return L'objet Chemin correspondant aux positions de début et de fin, ou null s'il n'est pas trouvé
     */
    public Chemin getCheminBy(List<Chemin> chemins, int debut, int fin);

    /**
     * Trouve le meilleur chemin dans une liste de chemins basé sur une solution donnée
     * @param chemins la liste de chemins
     * @param g le graphe
     * @param bestSol la meilleure solution
     * @return La liste de chemins représentant le meilleur chemin
     */
    public List<Chemin> bestCheminGlobal(List<Chemin> chemins, Graph g, List<Integer> bestSol);

    /**
     * Trouve le chemin optimal en utilisant la programmation dynamique classique
     * @param start la position de départ
     * @param n la limite supérieure de l'ensemble
     * @param g le graphe
     * @param mem la matrice de mémorisation
     * @return Le chemin optimal sous forme de liste d'entiers
     */
    public List<Integer> classicPath(int start, int n, Graph g, double[][] mem);

    /**
     * Fonction récursive pour la programmation dynamique classique
     * @param i la position actuelle
     * @param s l'ensemble actuel des positions restantes
     * @param n la limite supérieure de l'ensemble
     * @param g le graphe
     * @param mem la matrice de mémorisation
     * @return Le coût minimum pour les paramètres donnés
     */
    public double classicDynamic(int i, int s, int n, Graph g, double[][] mem);

    /**
     * Trouve le chemin optimal en utilisant la programmation dynamique adaptative
     * @param start la position de départ
     * @param n la limite supérieure de l'ensemble
     * @param g le graphe
     * @param mem la matrice de mémorisation
     * @return Le chemin optimal sous forme de liste d'entiers
     */
    public List<Integer> adaptivePath(int start, int n, Graph g, double[][] mem);

    /**
     * Fonction récursive pour la programmation dynamique adaptative
     * @param debut la position de départ
     * @param s l'ensemble actuel des positions restantes
     * @param n la limite supérieure de l'ensemble
     * @param g le graphe
     * @param mem la matrice de mémorisation
     * @return Le coût minimum pour les paramètres donnés
     */
    public double adaptiveDynamic(int debut, int s, int n, Graph g, double[][] mem);
}
