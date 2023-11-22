package tsp;

import java.util.List;

public class CompleteGraph implements Graph {
	private static final int MAX_COST = 40;
	private static final int MIN_COST = 10;
	int nbVertices;
	int[][] cost;

	List Chemin;
	
	/**
	 * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
	 * @param nbVertices
	 */
	public CompleteGraph(int nbVertices){
		this.nbVertices = nbVertices;
		int iseed = 1;
		cost = new int[nbVertices][nbVertices];
		for (int i=0; i<nbVertices; i++){
			cost[Chemin.de]
		}

	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public int getCost(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return -1;
		return cost[i][j];
	}

	@Override
	public boolean isArc(int i, int j) {
		if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
			return false;
		return i != j;
	}

}
