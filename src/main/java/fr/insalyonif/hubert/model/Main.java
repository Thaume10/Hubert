package fr.insalyonif.hubert.model;

import java.util.ArrayList;
import java.util.List;

// Classe Main
public class Main {
    public static void main(String[] args) throws Exception {
        // Création d'intersections et de segments de route ici...

        // Créez et initialisez votre CityMap
        CityMap cityMap = new CityMap();
        cityMap.loadFromXML("src/main/resources/fr/insalyonif/hubert/fichiersXML2022/mediumMap.xml");

        // Obtenez la taille du graphe à partir de la CityMap
        int sizeGraph = cityMap.getIntersections().size();

        // Créez une instance de Dijkstra et exécutez l'algorithme
        Dijkstra dijkstra = new Dijkstra(sizeGraph, cityMap);
        //dijkstra.addDeliveryRequest(cityMap.getWareHouseLocation());

        //System.out.println(test);
//        List<RoadSegment> successors = cityMap.findIntersectionByPos(8).getSuccessors();
//        for(RoadSegment successor : successors){
//            System.out.println("succ"+successor);
//        }
//
//        List<RoadSegment> pred = cityMap.findIntersectionByPos(8).getPredecessors();
//        for(RoadSegment p : pred){
//            System.out.println("pred"+p);
//        }

//        for (Intersection point : new ArrayList<>(dijkstra.getDeliveryRequest())) {
//            System.out.println(point);
//            if (point.getPos() != cityMap.findIntersectionByPos(8).getPos()) {
//                dijkstra.runDijkstra(point, sizeGraph);
//            }
//        }


        //System.out.println("fin");
        // Créez une instance de DijkstraInverse et exécutez l'algorithme


        DijkstraInverse dijkstraInverse = new DijkstraInverse(sizeGraph, cityMap);
        //dijkstra.runDijkstra(cityMap.findIntersectionByPos(10), sizeGraph);
        //dijkstraInverse.runDijkstra(cityMap.findIntersectionByPos(10), sizeGraph);

        boolean a = dijkstra.runDijkstra(cityMap.findIntersectionByPos(14), sizeGraph);
        boolean b =dijkstraInverse.runDijkstra(cityMap.findIntersectionByPos(14), sizeGraph);

        if(!a || !b){
            System.out.println("on ne peut pas afficher ce point");
        }

        dijkstra.runDijkstra(cityMap.findIntersectionByPos(8), sizeGraph);
        dijkstraInverse.runDijkstra(cityMap.findIntersectionByPos(8), sizeGraph);

        // Affichez les résultats, par exemple, les chemins calculés
        for (Chemin chemin : dijkstra.getChemins()) {
            System.out.println(chemin);
            //System.out.println(chemin.getPi()[345]);
            //System.out.println(chemin.getPi()[10]);
            //System.out.println(chemin.getPi()[8]);
            //System.out.println(chemin.getPi()[14]);
        }

        for (Intersection inter : dijkstra.getDeliveryRequest()) {
            System.out.println(inter);

        }

        //System.out.println("encore fin");
        // Affichez les résultats, par exemple, les chemins inverses calculés


//        Intersection intersection0 = new Intersection(45.7597, 4.8422, 1, 0);
//        Intersection intersection1 = new Intersection(45.7540, 4.8574, 2, 1);
//        Intersection intersection2 = new Intersection(45.7483, 4.8688, 3, 2);
//        Intersection intersection3 = new Intersection(45.7450, 4.8784, 4, 3);
//        Intersection intersection4 = new Intersection(45.7400, 4.8880, 5, 4);
//
//        RoadSegment segment1 = new RoadSegment(intersection0, intersection1, "Rue A", 1);
//        RoadSegment segment2 = new RoadSegment(intersection0, intersection3, "Rue B", 5);
//        RoadSegment segment3 = new RoadSegment(intersection0, intersection4, "Rue C", 9);
//        RoadSegment segment4 = new RoadSegment(intersection1, intersection2, "Rue D", 6);
//        RoadSegment segment5 = new RoadSegment(intersection1, intersection3, "Rue E", 7);
//        RoadSegment segment6 = new RoadSegment(intersection2, intersection4, "Rue F", 2);
//        RoadSegment segment7 = new RoadSegment(intersection3, intersection1, "Rue G", 7);
//        RoadSegment segment8 = new RoadSegment(intersection3, intersection2, "Rue H", 1);
//        RoadSegment segment9 = new RoadSegment(intersection3, intersection0, "Rue I", 5);
//        RoadSegment segment10 = new RoadSegment(intersection4, intersection0, "Rue J", 9);
//        RoadSegment segment11 = new RoadSegment(intersection4, intersection3, "Rue K", 4);
//
//        intersection0.getSuccessors().add(segment1);
//        intersection0.getSuccessors().add(segment2);
//        intersection0.getSuccessors().add(segment3);
//        intersection0.getPredecessors().add(segment9);
//        intersection0.getPredecessors().add(segment10);
//
//        intersection1.getSuccessors().add(segment4);
//        intersection1.getSuccessors().add(segment5);
//        intersection1.getPredecessors().add(segment1);
//        intersection1.getPredecessors().add(segment7);
//
//        intersection2.getPredecessors().add(segment4);
//        intersection2.getSuccessors().add(segment6);
//        intersection2.getPredecessors().add(segment8);
//
//        intersection3.getPredecessors().add(segment5);
//        intersection3.getSuccessors().add(segment7);
//        intersection3.getSuccessors().add(segment8);
//        intersection3.getSuccessors().add(segment9);
//        intersection3.getPredecessors().add(segment11);
//        intersection3.getPredecessors().add(segment2);
//
//        intersection4.getPredecessors().add(segment3);
//        intersection4.getPredecessors().add(segment6);
//        intersection4.getSuccessors().add(segment10);
//        intersection4.getSuccessors().add(segment11);
//
//        int sizeGraph = 5;
//
//        List<Intersection> intersections = new ArrayList<>();
//        intersections.add(intersection0);
//        intersections.add(intersection1);
//        intersections.add(intersection2);
//        intersections.add(intersection3);
//        intersections.add(intersection4);
//
//        CityMap cityMap = new CityMap();
//        cityMap.setWareHouseLocation(intersection0);
//        cityMap.setIntersections(intersections);
//        DijkstraInverse dijkstraInverse = new DijkstraInverse(sizeGraph, cityMap);
//        Dijkstra dijkstra = new Dijkstra(sizeGraph, cityMap);
//        dijkstra.runDijkstra(intersection1, sizeGraph);
//        dijkstraInverse.runDijkstra(intersection1, sizeGraph);
//        dijkstra.runDijkstra(intersection4, sizeGraph);
//        dijkstraInverse.runDijkstra(intersection4, sizeGraph);
        //dijkstraInverse.runDijkstra(intersection4, sizeGraph);

//        for (Intersection point : new ArrayList<>(dijkstra.getDeliveryRequest())) {
//            System.out.println(point);
//            if (point.getPos() != intersection1.getPos()) {
//                dijkstra.runDijkstra(point, sizeGraph);
//            }
//        }
//        //DijkstraInverse dijkstraInverse = new DijkstraInverse(sizeGraph, cityMap);
//        //dijkstraInverse.runDijkstra(intersection1, sizeGraph);



//        for(Chemin chemin : dijkstraInverse.getChemins()){
//            System.out.println(chemin);
//        }

    }


}