package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.List;

public class RunTSP {
	public static void main(String[] args) {



		ArrayList<Intersection> intersections = new ArrayList<>();
		Intersection intersection0 = new Intersection(45.7597, 4.8422, 1, 0);
		Intersection intersection1 = new Intersection(45.7583, 4.8415, 2, 1);
		Intersection intersection2 = new Intersection(45.7570, 4.8400, 3, 2);
		Intersection intersection3 = new Intersection(45.7570, 4.8400, 4, 3);
		Intersection intersection4 = new Intersection(45.7570, 4.8400, 5, 4);
		intersections.add(intersection0);
		intersections.add(intersection2);
		intersections.add(intersection3);
		intersections.add(intersection4);
		intersections.add(intersection1);


// Création de la liste de Chemin
		List<Chemin> chemins = new ArrayList<>();

// Ajout de 4 chemins à la liste
		chemins.add(new Chemin(intersection0, intersection2, new int[]{1, 2, 3, 4, 5}, 2));
		chemins.add(new Chemin(intersection2, intersection0, new int[]{2, 3, 4, 5, 1}, 7)); // Coût différent
		chemins.add(new Chemin(intersection0, intersection3, new int[]{2, 3, 4, 5, 1}, 3));
		chemins.add(new Chemin(intersection3, intersection0, new int[]{4, 5, 1, 2, 3}, 6)); // Coût différent
		chemins.add(new Chemin(intersection0, intersection4, new int[]{3, 4, 5, 1, 2}, 5));
		chemins.add(new Chemin(intersection4, intersection0, new int[]{5, 1, 2, 3, 4}, 1)); // Coût différent
		chemins.add(new Chemin(intersection0, intersection1, new int[]{4, 5, 1, 2, 3}, 4));
		chemins.add(new Chemin(intersection1, intersection0, new int[]{1, 2, 3, 4, 5}, 9)); // Coût différent

		chemins.add(new Chemin(intersection2, intersection3, new int[]{5, 4, 3, 2, 1}, 1));
		chemins.add(new Chemin(intersection3, intersection2, new int[]{2, 1, 5, 4, 3}, 3)); // Coût différent
		chemins.add(new Chemin(intersection2, intersection4, new int[]{4, 3, 2, 1, 5}, 6));
		chemins.add(new Chemin(intersection4, intersection2, new int[]{1, 2, 3, 4, 5}, 8)); // Coût différent
		chemins.add(new Chemin(intersection2, intersection1, new int[]{3, 2, 1, 5, 4}, 2));
		chemins.add(new Chemin(intersection1, intersection2, new int[]{1, 5, 4, 3, 2}, 7)); // Coût différent

		chemins.add(new Chemin(intersection3, intersection4, new int[]{2, 1, 5, 4, 3}, 3));
		chemins.add(new Chemin(intersection4, intersection3, new int[]{5, 1, 2, 3, 4}, 4)); // Coût différent
		chemins.add(new Chemin(intersection3, intersection1, new int[]{1, 5, 4, 3, 2}, 7));
		chemins.add(new Chemin(intersection1, intersection3, new int[]{4, 3, 2, 1, 5}, 1)); // Coût différent

		chemins.add(new Chemin(intersection4, intersection1, new int[]{5, 1, 2, 3, 4}, 4));
		chemins.add(new Chemin(intersection1, intersection4, new int[]{3, 2, 1, 5, 4}, 5));

		// Création du graphe complet
		Graph g = new CompleteGraph(chemins,intersections);

		TSP tsp = new TSP1();
		tsp.searchSolution(20000, g);
		System.out.print("Solution of cost "+tsp.getSolutionCost());
		for (int i=0; i< intersections.size()  ; i++)
			System.out.print(tsp.getSolution(i)+" ");
		System.out.println("0");
		List<Chemin> bestChemin = tsp.bestCheminGlobal(chemins);
		System.out.println("Meilleur chemin global :");
		for (Chemin chemin : bestChemin) {
			System.out.println("Départ : " + chemin.getDebut() + " -> Arrivée : " + chemin.getFin()+ " | Coût : " + chemin.getCout()) ;
		}

		/*for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
			System.out.println("Graphs with "+nbVertices+" vertices:");

			long startTime = System.currentTimeMillis();
			tsp.searchSolution(20000, g);
			System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
					+(System.currentTimeMillis() - startTime)+"ms : ");
			for (int i=0; i<nbVertices; i++)
				System.out.print(tsp.getSolution(i)+" ");
			System.out.println("0");
		}*/
	}

}
