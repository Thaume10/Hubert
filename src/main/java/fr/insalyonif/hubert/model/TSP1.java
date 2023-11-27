package fr.insalyonif.hubert.model;

import java.util.Collection;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
	@Override
	protected int bound(Integer currentVertex, Collection<Integer> unvisited) {
		return 0;
	}

	@Override
	public Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, Graph g) {
		return new SeqIter(unvisited, currentVertex, g);
	}

}
