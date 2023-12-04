package fr.insalyonif.hubert.controller;
import fr.insalyonif.hubert.views.DeliveryIHMController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import fr.insalyonif.hubert.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;

public class Controller {
    private CityMap cityMap;

    private int sizeGraph;

    private ArrayList<DeliveryTour> listeDelivery;

    public CityMap getCityMap() {
        return cityMap;
    }

    public void setCityMap(CityMap cityMap) {
        this.cityMap = cityMap;
    }



    public int getSizeGraph() {
        return sizeGraph;
    }

    public void setSizeGraph(int sizeGraph) {
        this.sizeGraph = sizeGraph;
    }

    public ArrayList<DeliveryTour> getListeDelivery() {
        return listeDelivery;
    }

    public void setListeDelivery(ArrayList<DeliveryTour> listeDelivery) {
        this.listeDelivery = listeDelivery;
    }

    public Controller(String path) {
        //initialize class variables
        cityMap = new CityMap();
        listeDelivery = new ArrayList<>();


        //initialize default delivery tour
        Courier first = new Courier(listeDelivery.size());
        DeliveryTour defaultDeliveryTour= new DeliveryTour();
        defaultDeliveryTour.setCourier(first);
      
        try {
            String xmlMap = path;
            //"src/main/resources/fr/insalyonif/hubert/fichiersXML2022/mediumMap.xml"
            cityMap.loadFromXML(xmlMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    

        sizeGraph = cityMap.getIntersections().size(); // Mettez la taille correcte de votre graphe
        Dijkstra dij = new Dijkstra(sizeGraph, cityMap);
        defaultDeliveryTour.setDijkstra(dij);
        DijkstraInverse dijInv = new DijkstraInverse(sizeGraph,cityMap);
        defaultDeliveryTour.setDijkstraInverse(dijInv);
        listeDelivery.add(defaultDeliveryTour);

    }

    public void newDeliveryTour(){
        Courier c = new Courier(listeDelivery.size());
        DeliveryTour defaultDeliveryTour= new DeliveryTour();
        defaultDeliveryTour.setCourier(c);
        sizeGraph = cityMap.getIntersections().size(); // Mettez la taille correcte de votre graphe
        Dijkstra dij = new Dijkstra(sizeGraph, cityMap);
        defaultDeliveryTour.setDijkstra(dij);
        DijkstraInverse dijInv = new DijkstraInverse(sizeGraph,cityMap);
        defaultDeliveryTour.setDijkstraInverse(dijInv);
        listeDelivery.add(defaultDeliveryTour);
    }

    public boolean newDeliveryPoint(DeliveryIHMController deliveryIHM, int idDeliveryTour) {
        DeliveryTour deliveryTour= listeDelivery.get(idDeliveryTour);
        if(deliveryIHM.getLatDouble()!=0 && deliveryIHM.getLngDouble()!=0) {

            Intersection intersectionPlusProche = trouverIntersectionPlusProche(deliveryIHM.getLatDouble(), deliveryIHM.getLngDouble(), cityMap.getIntersections());

            // Afficher les résultats
            System.out.println("Coordonnées de l'emplacement donné : " + deliveryIHM.getLatDouble() + ", " + deliveryIHM.getLngDouble());
            System.out.println("Intersection la plus proche : " + intersectionPlusProche.getLatitude() + ", " + intersectionPlusProche.getLongitude());
            System.out.println(intersectionPlusProche);

            boolean b1 = deliveryTour.getDijkstra().runDijkstra(intersectionPlusProche, sizeGraph);
            boolean b2 = deliveryTour.getDijkstraInverse().runDijkstra(intersectionPlusProche, sizeGraph);

            
            //Si un des deux false alors pop up BOOL1 && BOOL2
            if (b1 && b2) {
                DeliveryRequest deli = new DeliveryRequest((intersectionPlusProche),deliveryIHM.getTimeWindow());
                deliveryTour.getRequests().add(deli);

                Graph g = new CompleteGraph(deliveryTour.getDijkstra().getChemins(), deliveryTour.getRequests(), cityMap);


                TSP tsp = new TSP1();
                tsp.searchSolution(20000, g);
                System.out.print("Solution of cost " + tsp.getSolutionCost());
                for (int i = 0; i < listeDelivery.size(); i++)
                    System.out.print(tsp.getSolution(i) + " ");
                System.out.println("0");
                List<Chemin> bestChemin = tsp.bestCheminGlobal(deliveryTour.getDijkstra().getChemins());

                System.out.println("Meilleur chemin global :");
                for (Chemin chemin : bestChemin) {
                    System.out.println(chemin);
                    //System.out.println("Départ : " + chemin.getDebut() + " -> Arrivée : " + chemin.getFin()+ " | Coût : " + chemin.getCout());
                }

                deliveryTour.setPaths(bestChemin);
                MAJDeliveryPointList(idDeliveryTour);
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private void MAJDeliveryPointList(int idDeliveryTour){
        DeliveryTour deliveryTour = listeDelivery.get(idDeliveryTour);
        List<Chemin> chemins =deliveryTour.getPaths();
        Map<Intersection, Integer> intersectionIndexMap = new HashMap<>();
        for (int i = 0; i < chemins.size(); i++) {
            Chemin chemin = chemins.get(i);
            intersectionIndexMap.put(chemin.getFin(), i);
        }

        // Trier la liste de points de livraison en fonction de l'ordre des intersections dans les chemins
        deliveryTour.getRequests().sort((dp1, dp2) -> {
            int index1 = intersectionIndexMap.get(dp1.getDeliveryLocation());
            int index2 = intersectionIndexMap.get(dp2.getDeliveryLocation());
            return Integer.compare(index1, index2);
        });
    }

    public static Intersection trouverIntersectionPlusProche(double lat, double lng, List<Intersection> intersections) {
        if (intersections == null || intersections.isEmpty()) {
            return null; // La liste d'intersections est vide
        }

        Intersection intersectionPlusProche = intersections.get(0);
        double distanceMin = distance(lat, lng, intersectionPlusProche.getLatitude(), intersectionPlusProche.getLongitude());

        for (Intersection intersection : intersections) {
            double distanceActuelle = distance(lat, lng, intersection.getLatitude(), intersection.getLongitude());
            if (distanceActuelle < distanceMin) {
                distanceMin = distanceActuelle;
                intersectionPlusProche = intersection;
            }
        }

        return intersectionPlusProche;
    }

    private static double distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371; // Rayon de la Terre en kilomètres

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c; // Distance en kilomètres
    }



    public void saveCityMapToFile(String filePath) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Save the CityMap data to the file
            // You can customize this based on your CityMap data structure and attributes
            writer.println("<?xml version="+"1.0"+" encoding="+"UTF-8"+" standalone="+"no"+"?>");
            writer.println("<map>");
            writer.println("    <warehouse address=\"" + cityMap.getWareHouseLocation().getId() + "\" />");

            for (DeliveryTour deliveryTour : this.listeDelivery) {
                writer.println("    <deliveryTour courier=\"" + deliveryTour.getCourier().getId() + "\" startTime="+ deliveryTour.getStartTime()+ "\" endTime="+ deliveryTour.getEndTime() +"\">");
//                writer.println("        <courier id=\"" +  + "\" />");
                for (DeliveryRequest deliveryRequest : deliveryTour.getRequests()){
                    writer.println("        <deliveryRequest deliveryTime=\""+ deliveryRequest.getDeliveryTime() +"\" >");
                    writer.println("            <deliveryLocation latitude=\"" + deliveryRequest.getDeliveryLocation().getLatitude() +"\"" +" longitude=\"" + deliveryRequest.getDeliveryLocation().getLongitude() +"\"" + " id=\"" + deliveryRequest.getDeliveryLocation().getLongitude() +"\"/>");
                    writer.println("            <timeWindow startTime=\"" + deliveryRequest.getTimeWindow().getStartTime() +"\"" +" endTime=\"" + deliveryRequest.getTimeWindow().getEndTime() + "\" />");
                    writer.println("        </deliveryRequest>");
                }
                for (Chemin paths : deliveryTour.getPaths()){
                    writer.println("        <paths debut=\"" + paths.getDebut().getId() + "\" fin=\""+ paths.getFin().getId() + "\" pi=\""+ Arrays.toString(paths.getPi())+ "\" cout=\"" + paths.getCout() + "\" />");
                }
                writer.println("    </deliveryTour>");
            }
//            "\" latitude=\"" + intersection.getLatitude() +
//                    "\" longitude=\"" + intersection.getLongitude() + "\" />");

            writer.println("</map>");
        }
    }

}
