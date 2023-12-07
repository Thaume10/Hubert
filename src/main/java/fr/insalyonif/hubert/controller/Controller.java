package fr.insalyonif.hubert.controller;
import fr.insalyonif.hubert.views.DeliveryIHMController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.*;

import java.time.*;
import java.util.*;

import fr.insalyonif.hubert.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.time.LocalDate;

import static fr.insalyonif.hubert.model.Dynamique.*;
import static java.util.Collections.addAll;

public class Controller {
    private CityMap cityMap;

    private int sizeGraph;

    private ArrayList<DeliveryTour> listeDelivery;

    public ArrayList<TimeWindow> timeWindowList= setTimeWindowList();

    private static LocalDate globalDate;
    private String fileName;

    public static LocalDate getGlobalDate() {
        return globalDate;
    }

    public static void setGlobalDate(LocalDate globalDate) {
        Controller.globalDate = globalDate;
    }

    public String getFileName() {
        return fileName;
    }

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
            Path filePath = Paths.get(xmlMap);
            //"src/main/resources/fr/insalyonif/hubert/fichiersXML2022/mediumMap.xml"
            fileName = filePath.getFileName().toString();
            int extensionIndex = fileName.lastIndexOf('.');
            if (extensionIndex > 0) {
                fileName = fileName.substring(0, extensionIndex);
            }

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

    private List<Chemin> runTSP(DeliveryTour deliveryTour){
        ArrayList <DeliveryRequest> requests8 = new ArrayList<>();
        ArrayList <DeliveryRequest> requests9 = new ArrayList<>();
        ArrayList <DeliveryRequest> requests10 = new ArrayList<>();
        ArrayList <DeliveryRequest> requests11 = new ArrayList<>();

        ArrayList <Integer> pos8 = new ArrayList<>();
        ArrayList <Integer> pos9 = new ArrayList<>();
        ArrayList <Integer> pos10 = new ArrayList<>();
        ArrayList <Integer> pos11 = new ArrayList<>();


        int startTime = deliveryTour.getRequests().get(0).getTimeWindow().getStartTime();



        for (int i=0; i < deliveryTour.getRequests().size(); i++) {
            DeliveryRequest request = deliveryTour.getRequests().get(i);

            System.out.println(request.getTimeWindow().getStartTime());

            if (request.getTimeWindow().isInTimeWindow(8)){
                System.out.println("8");
                requests8.add(request);
                pos8.add(i+1);

            }
            if (request.getTimeWindow().isInTimeWindow(9)){
                System.out.println("9");
                requests9.add(request);
                pos9.add(i+1);
            }
            if (request.getTimeWindow().isInTimeWindow(10)){
                System.out.println("10");
                requests10.add(request);
                pos10.add(i+1);
            }
            if (request.getTimeWindow().isInTimeWindow(11)){
                System.out.println("11");
                requests11.add(request);
                pos11.add(i+1);
            }
        }
        /*pour huit : pour chaque point de requests9, creer des graphes qui prennent warehouse comme début et le point de requests9
         * pour neuf : pour chaque point de requests10, creer des graphes qui prennent le point choisi de requests9 comme début et le point de requests10
         * */
        double d = 0;
        List<Integer> optimalPath = new ArrayList<>();
        optimalPath.add(0,0);
        Graph g = new CompleteGraph(deliveryTour.getCheminDij(), deliveryTour.getRequests(), cityMap);
        Dynamique dynamique = new Dynamique(g);
//                    List<Chemin> bestChemin = new ArrayList<>();
//                    huit
        if (!requests8.isEmpty()){
            Graph g8 = new CompleteGraph(deliveryTour.getCheminDij(), requests8, cityMap);
            Dynamique dynamique8 = new Dynamique(g8);
            int n = g8.getNbVertices();
            int s = dynamique8.createSet(n); // s contains all integer values ranging between 1 and n-1

            double[][] memD = new double[n][s + 1];
            for (int i = 0; i < n; i++) {
                Arrays.fill(memD[i], 0);
            }

            d += computeD(0, s, n, g8, memD);
            List<Integer> optimalPath8 = dynamique8.findOptimalPath(0, n, g8, memD);

            System.out.printf("Optimal Hamiltonian Circuit Path: %s\n", optimalPath8);
            System.out.printf("Pos: %s\n", pos8);
            for (Integer i : optimalPath8) {
                if (i != 0){
                    optimalPath.add(pos8.get(i-1));
                }

            }


//                        bestChemin.addAll(dynamique8.bestCheminGlobal(deliveryTour.getCheminDij(),g8,dynamique8.findOptimalPath(0, n, g8, memD)));
        }


        //                    neuf
        if (!requests9.isEmpty()){
            Graph g9 = new CompleteGraph(deliveryTour.getCheminDij(), requests9, cityMap);
            Dynamique dynamique9 = new Dynamique(g9);

            int n = g9.getNbVertices();
            int s = dynamique9.createSet(n); // s contains all integer values ranging between 1 and n-1

            double[][] memD = new double[n][s + 1];
            for (int i = 0; i < n; i++) {
                Arrays.fill(memD[i], 0);
            }


            d += computeD(0, s, n, g9, memD);

            List<Integer> optimalPath9 = dynamique9.findOptimalPath(0, n, g9, memD);

            for (Integer i : optimalPath9) {
                if (i != 0){
                    optimalPath.add(pos9.get(i-1));
                }
            }
//                        optimalPath.addAll(dynamique9.findOptimalPath(0, n, g9, memD));
//                        bestChemin.addAll(dynamique9.bestCheminGlobal(deliveryTour.getCheminDij(),g9,dynamique9.findOptimalPath(0, n, g9, memD)));
        }
        //                    dix
        if (!requests10.isEmpty()){
            Graph g10 = new CompleteGraph(deliveryTour.getCheminDij(), requests10, cityMap);
            Dynamique dynamique10 = new Dynamique(g10);

            int n = g10.getNbVertices();
            int s = dynamique10.createSet(n); // s contains all integer values ranging between 1 and n-1

            double[][] memD = new double[n][s + 1];
            for (int i = 0; i < n; i++) {
                Arrays.fill(memD[i], 0);
            }

            d += computeD(0, s, n, g10, memD);
            List<Integer> optimalPath10 = dynamique10.findOptimalPath(0, n, g10, memD);
            for (Integer i : optimalPath10) {
                if (i != 0){
                    optimalPath.add(pos10.get(i-1));
                }
            }
//                        bestChemin.addAll(dynamique10.bestCheminGlobal(deliveryTour.getCheminDij(),g10,dynamique10.findOptimalPath(0, n, g10, memD)));
        }
        //                    onze
        if (!requests11.isEmpty()){
            Graph g11 = new CompleteGraph(deliveryTour.getCheminDij(), requests11, cityMap);

            Dynamique dynamique11 = new Dynamique(g11);

            int n = g11.getNbVertices();
            int s = dynamique11.createSet(n); // s contains all integer values ranging between 1 and n-1

            double[][] memD = new double[n][s + 1];
            for (int i = 0; i < n; i++) {
                Arrays.fill(memD[i], 0);
            }

            d += computeD(0, s, n, g11, memD);
            List<Integer> optimalPath11 = dynamique11.findOptimalPath(0, n, g11, memD);
            System.out.println(optimalPath11);
            System.out.println(pos11);
            for (Integer i : optimalPath11) {
                if (i != 0){
                    optimalPath.add(pos11.get(i-1));
                }
            }

//                        bestChemin.addAll(dynamique11.bestCheminGlobal(deliveryTour.getCheminDij(),g11,dynamique11.findOptimalPath(0, n, g11, memD)));
        }

        optimalPath.add(0);
        System.out.printf("Length of the smallest hamiltonian circuit = %f\n", d);
        System.out.printf("Optimal Hamiltonian Circuit Path: %s\n", optimalPath);
        List<Chemin> bestChemin = dynamique.bestCheminGlobal(deliveryTour.getCheminDij(),g,optimalPath);
        System.out.println("Meilleur chemin global, trop bien :");
        for (Chemin chemin : bestChemin) {
            System.out.println(chemin);
        }
        return bestChemin;
    }

    public int newDeliveryPoint(DeliveryIHMController deliveryIHM, int idDeliveryTour) {
        DeliveryTour deliveryTour= listeDelivery.get(idDeliveryTour);
        if(deliveryIHM.getLatDouble()!=0 && deliveryIHM.getLngDouble()!=0) {

            Intersection intersectionPlusProche = trouverIntersectionPlusProche(deliveryIHM.getLatDouble(), deliveryIHM.getLngDouble(), cityMap.getIntersections());

            // Afficher les résultats
            System.out.println("Coordonnées de l'emplacement donné : " + deliveryIHM.getLatDouble() + ", " + deliveryIHM.getLngDouble());
            System.out.println("Intersection la plus proche : " + intersectionPlusProche.getLatitude() + ", " + intersectionPlusProche.getLongitude());
            System.out.println(intersectionPlusProche);

            boolean intersectionExist = false;
            for (DeliveryRequest request : deliveryTour.getRequests()) {
                if (request.getDeliveryLocation().equals(intersectionPlusProche)) {
                    System.out.println("equal intersection");
                    if(request.getTimeWindow().getEndTime() == deliveryIHM.getTimeWindow().getEndTime()){
                        System.out.println("equal timewindow");
                        intersectionExist = true;
                        break;
                    }
                }
            }

            if (!intersectionExist) {

                boolean b1 = deliveryTour.getDijkstra().runDijkstra(intersectionPlusProche, sizeGraph);
                boolean b2 = deliveryTour.getDijkstraInverse().runDijkstra(intersectionPlusProche, sizeGraph);

                //Si un des deux false alors pop up BOOL1 && BOOL2
                if (b1 && b2) {
                    deliveryTour.clearCheminsDij();
                    deliveryTour.majCheminsDij(deliveryTour.getDijkstra().getChemins());
                    deliveryTour.majCheminsDij(deliveryTour.getDijkstraInverse().getChemins());
                    DeliveryRequest deli = new DeliveryRequest((intersectionPlusProche),deliveryIHM.getTimeWindow());
                    deliveryTour.getRequests().add(deli);


                    List<Chemin> bestChemin = runTSP(deliveryTour);



                    deliveryTour.setPaths(bestChemin);
                    MAJDeliveryPointList(idDeliveryTour);
                    return 0; //0 for success
                } else {
                    return 1; //Error -> Non accessible
                }
            } else {
                //System.out.println("L'intersection est déjà présente dans les demandes de livraison.");
                return 2; //Error -> Point déjà présent
            }
        }
        return 0;
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

    public Object[] findBestCourier(){
        int min = 9999;
        Courier bestCourier = null;
        TimeWindow timeWindow =null;
        for(DeliveryTour deliveryTour : listeDelivery){

            for(TimeWindow timeWindow1 : timeWindowList){
                int cpt =0;
                if(timeWindowPossible(timeWindow1)){

                    for(DeliveryRequest deliveryRequest1 : deliveryTour.getRequests()){
                        if(deliveryRequest1.getTimeWindow().getStartTime() == timeWindow1.getStartTime()){
                            System.out.println("nb delivery "+ cpt);
                            cpt++;
                        }
                    }
                }
                if(cpt!=0 && cpt<min){
                    min = cpt;
                    bestCourier=deliveryTour.getCourier();
                    timeWindow = timeWindow1;
                }
            }
        }
        System.out.println("Best Courier "+ bestCourier);
        System.out.println("Best Timwindow "+ timeWindow);
        return new Object[]{bestCourier, timeWindow};
    }
    public ArrayList<TimeWindow> setTimeWindowList(){
        ArrayList<TimeWindow> timeWindows= new ArrayList<>();
        for(int i=8; i<12;i++){
            timeWindows.add(new TimeWindow(i,i+1));
        }
        return timeWindows;
    }

    public boolean timeWindowPossible(TimeWindow timeWindow){
        //A implementer pour vérifier que la time window peut encore recevoir des nouvelles délivery
        return true;
    }





    public boolean saveCityMapToFile(String filePath) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Save the CityMap data to the file
            // You can customize this based on your CityMap data structure and attributes
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            writer.println("<map fileName=\"" + fileName + "\""+ " globalDate=\""+ globalDate + "\" >");
            writer.println("    <warehouse address=\"" + cityMap.getWareHouseLocation().getId() + "\" />");

            for (DeliveryTour deliveryTour : this.listeDelivery) {
                writer.println("    <deliveryTour courier=\"" + deliveryTour.getCourier().getId() + "\" startTime=\""+ deliveryTour.getStartTime()+ "\" endTime=\""+ deliveryTour.getEndTime() +"\">");
//                writer.println("        <courier id=\"" +  + "\" />");
                for (DeliveryRequest deliveryRequest : deliveryTour.getRequests()){
                    writer.println("        <deliveryRequest deliveryTime=\""+ deliveryRequest.getDeliveryTime() +"\" >");
                    writer.println("            <deliveryLocation latitude=\"" + deliveryRequest.getDeliveryLocation().getLatitude() +"\"" +" longitude=\"" + deliveryRequest.getDeliveryLocation().getLongitude() +"\"" + " id=\"" + deliveryRequest.getDeliveryLocation().getId() +"\"/>");
                    writer.println("            <timeWindow startTime=\"" + deliveryRequest.getTimeWindow().getStartTime() +"\"" +" endTime=\"" + deliveryRequest.getTimeWindow().getEndTime() + "\" />");
                    writer.println("        </deliveryRequest>");
                }

                writer.println("    </deliveryTour>");
            }
//            "\" latitude=\"" + intersection.getLatitude() +
//                    "\" longitude=\"" + intersection.getLongitude() + "\" />");

            writer.println("</map>");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if an IOException occurs
        }

    }

    public void loadArchiveFile(String path) throws Exception {
        // Création d'une instance de File pour le fichier XML
        listeDelivery.clear();

        File xmlFile = new File(path);

        // Initialisation du constructeur de documents XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        // Parsing du document XML
        Document doc = dBuilder.parse(xmlFile);

        // Normalisation du document XML pour éliminer les espaces blancs inutiles
        doc.getDocumentElement().normalize();
        System.out.println("loadArchiveFile");



        //All the delivery tours
        NodeList deliveryTourList = doc.getElementsByTagName("deliveryTour");
        for (int i = 0; i < deliveryTourList.getLength(); i++) {
            Element deliveryTourElement = (Element) deliveryTourList.item(i);

            //Add the couriers
            Courier c = new Courier((int) Long.parseLong(deliveryTourElement.getAttribute("courier")));
            DeliveryTour defaultDeliveryTour= new DeliveryTour();
            defaultDeliveryTour.setCourier(c);
            sizeGraph = cityMap.getIntersections().size(); // Mettez la taille correcte de votre graphe
            Dijkstra dij = new Dijkstra(sizeGraph, cityMap);
            defaultDeliveryTour.setDijkstra(dij);
            DijkstraInverse dijInv = new DijkstraInverse(sizeGraph,cityMap);
            defaultDeliveryTour.setDijkstraInverse(dijInv);
            listeDelivery.add(defaultDeliveryTour);

            System.out.println(c);
            for (Chemin chemin : dij.getChemins()) {
                System.out.println(" get 0 = "+ chemin);
            }



            //Add the delivery requests
            NodeList deliveryRequestList = deliveryTourElement.getElementsByTagName("deliveryRequest");
            for (int j = 0; j < deliveryRequestList.getLength(); j++) {
                Element deliveryRequest = (Element) deliveryRequestList.item(j);
                Element deliveryLocation = (Element) deliveryRequest.getElementsByTagName("deliveryLocation").item(0);
                Element timeWindow = (Element) deliveryRequest.getElementsByTagName("timeWindow").item(0);
                //DeliveryTour deliveryTour= listeDelivery.get(idDeliveryTour);

                //Get Intersection
                //long idInter = Long.parseLong(deliveryLocation.getAttribute("id"));
                double idInter = Double.parseDouble(deliveryLocation.getAttribute("id"));
                System.out.println("idInter "+idInter );


                Intersection intersectionPlusProche = cityMap.findIntersectionByID((long) idInter);
                System.out.println(intersectionPlusProche );

                boolean b1 = listeDelivery.get(i).getDijkstra().runDijkstra(intersectionPlusProche, sizeGraph);
                boolean b2 = listeDelivery.get(i).getDijkstraInverse().runDijkstra(intersectionPlusProche, sizeGraph);



                if(b1 && b2) {
                    System.out.println(timeWindow.getAttribute("startTime"));
                    int startTime = Integer.parseInt(timeWindow.getAttribute("startTime"));
                    int endTime = Integer.parseInt(timeWindow.getAttribute("endTime"));
                    TimeWindow timeWindowToCreate = new TimeWindow(startTime,endTime);


                    listeDelivery.get(i).clearCheminsDij();
                    listeDelivery.get(i).majCheminsDij(listeDelivery.get(i).getDijkstra().getChemins());
                    listeDelivery.get(i).majCheminsDij(listeDelivery.get(i).getDijkstraInverse().getChemins());
                    DeliveryRequest deli = new DeliveryRequest(intersectionPlusProche,timeWindowToCreate);
                    listeDelivery.get(i).getRequests().add(deli);

                    List<Chemin> bestChemin = runTSP(listeDelivery.get(i));

                    listeDelivery.get(i).setPaths(bestChemin);
                    MAJDeliveryPointList(i);



//                    Graph g = new CompleteGraph(listeDelivery.get(i).getCheminDij(), listeDelivery.get(i).getRequests(), cityMap);
//
//
//                    TSP tsp = new TSP1();
//                    tsp.searchSolution(20000, g);
//                    System.out.print("Solution of cost " + tsp.getSolutionCost());
////                    for (int k = 0; k < listeDelivery.size(); k++)
////                        System.out.print(tsp.getSolution(k) + " ");
////                    System.out.println("0");
//                    List<Chemin> bestChemin = tsp.bestCheminGlobal(listeDelivery.get(i).getCheminDij());
//
//                    System.out.println("Meilleur chemin global :");
//                    for (Chemin chemin : bestChemin) {
//                        System.out.println(chemin);
//                        //System.out.println("Départ : " + chemin.getDebut() + " -> Arrivée : " + chemin.getFin()+ " | Coût : " + chemin.getCout());
//                    }
//
//                    listeDelivery.get(i).setPaths(bestChemin);
//                    MAJDeliveryPointList(i);
                }
            }

//            for (Chemin chemin : listeDelivery.get(i).getPaths()) {
//                System.out.println(" get 0 = "+ chemin);
//            }
        }
    }



}
