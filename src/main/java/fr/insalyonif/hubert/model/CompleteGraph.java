package fr.insalyonif.hubert.model;

import java.util.List;
import java.util.ArrayList;

public class CompleteGraph implements Graph {
	private static final int MAX_COST = 40;
	private static final int MIN_COST = 10;
	int nbVertices;
	double[][] cost = null;

	/**
	 * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
	 * @param chemins
	 */
	public CompleteGraph(List<Chemin> chemins){
		this.nbVertices = chemins.size() ;
		int iseed = 1;
		cost = new double[nbVertices][nbVertices];
		//System.out.println(cost[0].length);

		for (int i=0; i<chemins.size(); i++){
			System.out.print(chemins.get(i).getDebut().getPos()+"" );
			System.out.println(chemins.get(i).getFin().getPos() );
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

	/*public static void main(String[] args) {
		// Création des intersections
		Intersection intersection1 = new Intersection(45.7597, 4.8422, 1, 1);
		Intersection intersection2 = new Intersection(45.7583, 4.8415, 2, 2);
		Intersection intersection3 = new Intersection(45.7570, 4.8400, 3, 3);
		Intersection intersection4 = new Intersection(45.7580, 4.8390, 4, 4);
		Intersection intersection5 = new Intersection(45.7597, 4.8422, 1, 5);

		// Création de la liste de Chemin
		List<Chemin> chemins = new ArrayList<>();

		// Ajout de 4 chemins à la liste
		chemins.add(new Chemin(intersection1, intersection2, new int[]{1, 2, 3}, 10.5));
		chemins.add(new Chemin(intersection2, intersection3, new int[]{2, 3, 4}, 8.2));
		chemins.add(new Chemin(intersection3, intersection4, new int[]{3, 4, 1}, 15.0));
		chemins.add(new Chemin(intersection4, intersection1, new int[]{4, 1, 2}, 12.3));
		chemins.add(new Chemin(intersection2, intersection1, new int[]{4, 1, 2}, 12.3));
		chemins.add(new Chemin(intersection5, intersection4, new int[]{4, 1, 2}, 12.3));
		//System.out.println(chemins.size());
		// Affichage des chemins
		for (Chemin chemin : chemins) {
			System.out.println(chemin.toString());
		}
		Graph g = new CompleteGraph(chemins);
	}*/
}


