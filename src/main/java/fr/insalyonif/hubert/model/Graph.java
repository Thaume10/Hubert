package fr.insalyonif.hubert.model;

import java.util.Map;

public interface Graph {
	/**
	 * @return le nombre de sommets dans <code>this</code>
	 */
	public abstract int getNbVertices();

	/**
	 * @param i
	 * @param j
	 * @return le coût de l'arc (i,j) si (i,j) est un arc ; -1 sinon
	 */
	public abstract double getCost(int i, int j);

	/**
	 * @param i
	 * @param j
	 * @return true si <code>(i,j)</code> est un arc de <code>this</code>
	 */
	public abstract boolean isArc(int i, int j);

	/**
	 * @return une Map des positions aux indices correspondants dans le graphe
	 */
	public abstract Map<Integer, Integer> getPositionToIndexMap();
}
