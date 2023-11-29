package fr.insalyonif.hubert.model;

import java.util.ArrayList;
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
            this.pi[v.getPos()] = u.getPos();
        }

    }
    protected void piCopyConstructor(int [] piCopy,Intersection start, Intersection delivery){
        int j = delivery.getPos();
        ArrayList<Integer> parcours = new ArrayList<Integer>();
        parcours.add(j);
        while (j!=start.getPos()) {
            parcours.add(this.pi[j]);
            j = this.pi[j];
        }
        for (int i = parcours.size()-1; i>0; i--) {
            piCopy[parcours.get(i)]=parcours.get(i-1);
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