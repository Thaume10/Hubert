package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;

public class RunTSP {
	public static void main(String[] args) {
		Intersection intersection1 = new Intersection(45.7597, 4.8422, 1, 1);
		Intersection intersection2 = new Intersection(45.7583, 4.8415, 2, 2);
		Intersection intersection3 = new Intersection(45.7570, 4.8400, 3, 3);


// Création de la liste de Chemin
		List<Chemin> chemins = new ArrayList<>();

// Ajout de 4 chemins à la liste
		chemins.add(new Chemin(intersection1, intersection2, new int[]{1, 2, 3}, 2));
		chemins.add(new Chemin(intersection1, intersection3, new int[]{4, 1, 2}, 3));
		chemins.add(new Chemin(intersection2, intersection3, new int[]{4, 1, 2}, 2));
		chemins.add(new Chemin(intersection3, intersection2, new int[]{4, 1, 2}, 3));
		chemins.add(new Chemin(intersection3, intersection1, new int[]{4, 1, 2}, 1));
		chemins.add(new Chemin(intersection2, intersection1, new int[]{4, 1, 2}, 5));
		// Création du graphe complet
		Graph g = new CompleteGraph(chemins);

		// Utilisation de TSP1 pour résoudre le problème
		TSP tsp = new TSP1();

		// Application du TSP sur le graphe
		tsp.searchSolution(2000, g);

		// Affichage de la meilleure solution trouvée

		System.out.println("Best Solution Cost: " + tsp.getSolutionCost());
		for (int i=0; i<g.getNbVertices(); i++)
			System.out.print(tsp.getSolution(i)+" ");
		System.out.println("0");
		// TSP tsp = new TSP1();

		/*for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			System.out.println("Graphs with "+nbVertices+" vertices:");
			Graph g = new CompleteGraph(nbVertices);
			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, g);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println("0");*/
		}
	}


