package fr.insalyonif.hubert.model;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
	@Override
	protected double bound(Integer currentVertex, Collection<Integer> unvisited) {
		double minCost = Double.POSITIVE_INFINITY;

		for (Integer vertex : unvisited) {
			if (g.isArc(currentVertex, vertex)) {
				minCost = Math.min(minCost, g.getCost(currentVertex, vertex));
			}
		}

		return minCost;
	}

	@Override
	protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

	@Override
	public double getSolutionCost() {
		return 0;
	}
}
