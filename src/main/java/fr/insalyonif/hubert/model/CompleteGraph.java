package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.List;

import static fr.insalyonif.hubert.model.Dijkstra.deliveryRequest;

public class CompleteGraph implements Graph {
	private static final int MAX_COST = 40;
	private static final int MIN_COST = 10;
	int nbVertices;
	double[][] cost;

	/**
	 * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
	 * @param chemins
	 */
	public CompleteGraph(List<Chemin> chemins, ArrayList<Intersection> intersections){
		this.nbVertices = intersections.size() ;
		System.out.println("nb vertice "+ nbVertices);

		cost = new double[nbVertices][nbVertices];
		System.out.println("Cost size "+ cost[0].length);

		System.out.println("chemins size "+ chemins.size());

		for (int i=0; i<chemins.size(); i++) {
			for (int j = 0; j < chemins.size(); j++) {

				cost[chemins.get(i).getDebut().getPos()][chemins.get(i).getFin().getPos()]= chemins.get(i).getCout();
				//cost[i][j] = chemins.get(i).getCout();
			}
		}

			System.out.println("Tableau des coûts :");
			for (int i = 0; i < cost.length; i++) {
				for (int j = 0; j < cost[i].length; j++) {
					System.out.print(cost[i][j] + "\t");
				}
				System.out.println(); // Passer à la ligne après chaque ligne du tableau
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
