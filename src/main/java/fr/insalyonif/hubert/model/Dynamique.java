package fr.insalyonif.hubert.model;

import java.util.List;

public interface Dynamique {
    /**
     * Create a set that contains all integers ranging from 1 to n-1
     * @param n The upper limit for the set
     * @return The created set
     */
    public int createSet(int n);

    /**
     * Print all elements of the given set
     * @param s The set to print
     */
    public void printSet(int s);

    /**
     * Get a Chemin object from a list of chemins based on the given start and end positions
     * @param chemins The list of chemins
     * @param debut The starting position
     * @param fin The ending position
     * @return The Chemin object corresponding to the start and end positions, or null if not found
     */
    public Chemin getCheminBy(List<Chemin> chemins, int debut, int fin);

    /**
     * Find the best path through a list of chemins based on a given solution
     * @param chemins The list of chemins
     * @param g The graph
     * @param bestSol The best solution
     * @return The list of chemins representing the best path
     */
    public List<Chemin> bestCheminGlobal(List<Chemin> chemins, Graph g, List<Integer> bestSol);

    /**
     * Find the optimal path using classic dynamic programming
     * @param start The starting position
     * @param n The upper limit for the set
     * @param g The graph
     * @param mem The memoization matrix
     * @return The optimal path as a list of integers
     */
    public List<Integer> classicPath(int start, int n, Graph g, double[][] mem);

    /**
     * Recursive function for classic dynamic programming
     * @param i The current position
     * @param s The current set of remaining positions
     * @param n The upper limit for the set
     * @param g The graph
     * @param mem The memoization matrix
     * @return The minimum cost for the given parameters
     */
    public double classicDynamic(int i, int s, int n, Graph g, double[][] mem);

    /**
     * Find the optimal path using adaptive dynamic programming
     * @param start The starting position
     * @param n The upper limit for the set
     * @param g The graph
     * @param mem The memoization matrix
     * @return The optimal path as a list of integers
     */
    public List<Integer> adaptivePath(int start, int n, Graph g, double[][] mem);

    /**
     * Recursive function for adaptive dynamic programming
     * @param debut The starting position
     * @param s The current set of remaining positions
     * @param n The upper limit for the set
     * @param g The graph
     * @param mem The memoization matrix
     * @return The minimum cost for the given parameters
     */
    public double adaptiveDynamic(int debut, int s, int n, Graph g, double[][] mem);
}
