package fr.insalyonif.hubert.model;

import java.util.Arrays;

// Classe DijkstraInverse
public class DijkstraInverse extends AbstractDijkstra {
    public DijkstraInverse(int sizeGraph, CityMap cityMap) {
        super(sizeGraph,cityMap);
    }

    @Override
    protected void relax(Intersection u, Intersection v, double weight) {
        if (distance[u.getPos()] + weight < distance[v.getPos()]) {
            distance[v.getPos()] = distance[u.getPos()] + weight;
            this.pi[u.getPos()] = v.getPos();
        }

    }


    @Override
    protected Iterable<RoadSegment> getNeighbors(Intersection intersection) {
        return intersection.getPredecessors();
    }

    @Override
    protected Intersection selectNode(RoadSegment roadSegment) {
        return roadSegment.getOrigin();
    }

    @Override
    protected Chemin createChemin(Intersection start, Intersection destination, int[] pi, double cout) {
        Chemin chemin = new Chemin(destination, start, pi, cout);;
        return chemin;
    }
}
