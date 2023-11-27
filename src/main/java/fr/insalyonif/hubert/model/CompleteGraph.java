package fr.insalyonif.hubert.model;

import java.util.List;

public class CompleteGraph implements Graph {
	private static final int MAX_COST = 40;
	private static final int MIN_COST = 10;
	int nbVertices;
	double[][] cost;

	/**
	 * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
	 * @param chemins
	 * @param intersections
	 */
	public CompleteGraph(List<Chemin> chemins, List<Intersection> intersections){
		this.nbVertices = intersections.size() ;
		System.out.println("nb vertice "+ nbVertices);

		cost = new double[nbVertices][nbVertices];
		System.out.println("Cost size "+ cost[0].length);

		for (int i=0; i<intersections.size(); i++){
			/*System.out.println("position début"+ chemins.get(i).getDebut().getPos());
			System.out.println("position fin"+ chemins.get(i).getFin().getPos()); */
			cost[chemins.get(i).getDebut().getPos()][chemins.get(i).getFin().getPos()]= chemins.get(i).getCout();
		}
	}

	@Override
	public int getNbVertices() {
		return nbVertices;
	}

	@Override
	public double getCost(int i, int j) {
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
