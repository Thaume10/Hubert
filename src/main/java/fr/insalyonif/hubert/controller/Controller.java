package fr.insalyonif.hubert.controller;
import fr.insalyonif.hubert.views.DeliveryIHMController;

import java.util.*;

import fr.insalyonif.hubert.model.*;

import static fr.insalyonif.hubert.model.CreateDynamique.*;

public class Controller {
    private CityMap cityMap;

    private int sizeGraph;

    private ArrayList<DeliveryTour> listeDelivery;

    public ArrayList<TimeWindow> timeWindowList= setTimeWindowList();

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
                    //System.out.println(deliveryTour.getCheminDij());
                    //System.out.println(deliveryTour.getRequests());



                    //Utilisation du TSP Dynamique pour résoudre le problème d'ordre des points de livraison
                    List<Chemin> bestChemin = UseDynamic(deliveryTour);
                            System.out.println("Meilleur chemin global :");
                    for (Chemin chemin : bestChemin) {
                        System.out.println(chemin);
                    }


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


    private List<Chemin> UseDynamic(DeliveryTour deliveryTour){
        ArrayList <DeliveryRequest> requests8 = new ArrayList<>();
        ArrayList <DeliveryRequest> requests9 = new ArrayList<>();
        ArrayList <DeliveryRequest> requests10 = new ArrayList<>();
        ArrayList <DeliveryRequest> requests11 = new ArrayList<>();

        ArrayList <Integer> pos8 = new ArrayList<>();
        ArrayList <Integer> pos9 = new ArrayList<>();
        ArrayList <Integer> pos10 = new ArrayList<>();
        ArrayList <Integer> pos11 = new ArrayList<>();




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

        double d = 0;
        List<Integer> optimalPath = new ArrayList<>();
        optimalPath.add(0,0);
        Graph g = new CompleteGraph(deliveryTour.getCheminDij(), deliveryTour.getRequests(), cityMap);
        CreateDynamique dynamique = new CreateDynamique(g);
        int nextStart = 0;
        ArrayList<DeliveryRequest> nextrequests = new ArrayList<>();
        ArrayList<DeliveryRequest> rTemp = new ArrayList<>();
//                    huit

        if (!requests8.isEmpty()){
            List<Integer> optimalPath8 = new ArrayList<>();
            double d8 = Double.MAX_VALUE;
            rTemp.addAll(requests8);
            if (!requests9.isEmpty()){
                nextrequests.addAll(requests9);
            }else if (!requests10.isEmpty()) {
                nextrequests.addAll(requests10);
            }else if (!requests11.isEmpty()) {
                nextrequests.addAll(requests11);
            }else {nextrequests = null;}
            if (nextrequests != null){
                for (int request = 0; request < nextrequests.size(); request++) {
                    rTemp.remove(0);
                    rTemp.add(nextrequests.get(request));
                    Graph g8 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
                    CreateDynamique dynamique8 = new CreateDynamique(g8);
                    int n = g8.getNbVertices();
                    int s = dynamique8.createSet(n); // s contains all integer values ranging between 1 and n

                    double[][] memD = new double[n][s + 1];
                    for (int i = 0; i < n; i++) {
                        Arrays.fill(memD[i], 0);
                    }

                    if (dynamique8.adaptiveDynamic(nextStart, s, n, g8, memD)< d8){
                        d8 = dynamique8.adaptiveDynamic(nextStart, s, n, g8, memD);
                        optimalPath8 = dynamique8.adaptivePath(nextStart, n, g8, memD);
                        optimalPath8.remove(optimalPath8.size() - 1);
                        nextStart = request;
                    }
                }
            } else {

                Graph g8 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
                CreateDynamique dynamique8 = new CreateDynamique(g8);
                int n = g8.getNbVertices();
                int s = dynamique8.createSet(n); // s contains all integer values ranging between 1 and n

                double[][] memD = new double[n][s + 1];
                for (int i = 0; i < n; i++) {
                    Arrays.fill(memD[i], 0);
                }
                d8 = dynamique8.classicDynamic(nextStart, s, n, g8, memD);
                optimalPath8 = dynamique8.classicPath(nextStart, n, g8, memD);
            }
            d+=d8;
            System.out.printf("Length of the smallest hamiltonian circuit8 = %f\n", d8);
            System.out.printf("Optimal Hamiltonian Circuit Path8: %s\n", optimalPath8);
            System.out.printf("Pos: %s\n", pos8);
            for (Integer i : optimalPath8) {
                if (i != 0){
                    optimalPath.add(pos8.get(i-1));
                }

            }
        }


        if (!requests9.isEmpty()){
            List<Integer> optimalPath9 = new ArrayList<>();
            double d9 = Double.MAX_VALUE;
            rTemp.clear();
            rTemp.addAll(requests9);
            if (!requests10.isEmpty()) {
                nextrequests.clear();
                nextrequests.addAll(requests10);
            }else if (!requests11.isEmpty()) {
                nextrequests.clear();
                nextrequests.addAll(requests11);
            }else {nextrequests = null;}
            if (nextrequests != null){
                for (int request = 0; request < nextrequests.size(); request++) {
                    rTemp.remove(0);
                    rTemp.add(nextrequests.get(request));
                    Graph g9 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
                    CreateDynamique dynamique9 = new CreateDynamique(g9);
                    int n = g9.getNbVertices();
                    int s = dynamique9.createSet(n); // s contains all integer values ranging between 1 and n

                    double[][] memD = new double[n][s + 1];
                    for (int i = 0; i < n; i++) {
                        Arrays.fill(memD[i], 0);
                    }

                    if (dynamique9.adaptiveDynamic(nextStart, s, n, g9, memD)< d9){
                        d9 = dynamique9.adaptiveDynamic(nextStart, s, n, g9, memD);
                        optimalPath9 = dynamique9.adaptivePath(nextStart, n, g9, memD);
                        optimalPath9.remove(optimalPath9.size() - 1);
                        nextStart = request;
                    }
                }
            } else {

                Graph g9 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
                CreateDynamique dynamique9 = new CreateDynamique(g9);
                int n = g9.getNbVertices();
                int s = dynamique9.createSet(n); // s contains all integer values ranging between 1 and n

                double[][] memD = new double[n][s + 1];
                for (int i = 0; i < n; i++) {
                    Arrays.fill(memD[i], 0);
                }
                d9 = dynamique9.classicDynamic(nextStart, s, n, g9, memD);
                optimalPath9 = dynamique9.classicPath(nextStart, n, g9, memD);
            }
            d+=d9;
            System.out.printf("Length of the smallest hamiltonian circuit9 = %f\n", d9);
            System.out.printf("Optimal Hamiltonian Circuit Path9: %s\n", optimalPath9);
            System.out.printf("Pos: %s\n", pos9);
            for (Integer i : optimalPath9) {
                if (i != 0){
                    optimalPath.add(pos9.get(i-1));
                }

            }
        }

        if (!requests10.isEmpty()){
            List<Integer> optimalPath10 = new ArrayList<>();
            double d10 = Double.MAX_VALUE;
            rTemp.clear();
            rTemp.addAll(requests10);
            if (!requests11.isEmpty()) {
                nextrequests.clear();
                nextrequests.addAll(requests11);
            }else {nextrequests = null;}
            if (nextrequests != null){
                for (int request = 0; request < nextrequests.size(); request++) {
                    rTemp.remove(0);
                    rTemp.add(nextrequests.get(request));
                    Graph g10 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
                    CreateDynamique dynamique10 = new CreateDynamique(g10);
                    int n = g10.getNbVertices();
                    int s = dynamique10.createSet(n); // s contains all integer values ranging between 1 and n

                    double[][] memD = new double[n][s + 1];
                    for (int i = 0; i < n; i++) {
                        Arrays.fill(memD[i], 0);
                    }

                    if (dynamique10.adaptiveDynamic(nextStart, s, n, g10, memD)< d10){
                        d10 = dynamique10.adaptiveDynamic(nextStart, s, n, g10, memD);
                        optimalPath10 = dynamique10.adaptivePath(nextStart, n, g10, memD);
                        optimalPath10.remove(optimalPath10.size() - 1);
                        nextStart = request;
                    }
                }
            } else {

                Graph g10 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
                CreateDynamique dynamique10 = new CreateDynamique(g10);
                int n = g10.getNbVertices();
                int s = dynamique10.createSet(n); // s contains all integer values ranging between 1 and n

                double[][] memD = new double[n][s + 1];
                for (int i = 0; i < n; i++) {
                    Arrays.fill(memD[i], 0);
                }
                d10 = dynamique10.classicDynamic(nextStart, s, n, g10, memD);
                optimalPath10 = dynamique10.classicPath(nextStart, n, g10, memD);
            }
            d+=d10;
            System.out.printf("Length of the smallest hamiltonian circuit10 = %f\n", d10);
            System.out.printf("Optimal Hamiltonian Circuit Path10: %s\n", optimalPath10);
            System.out.printf("Pos: %s\n", pos10);
            for (Integer i : optimalPath10) {
                if (i != 0){
                    optimalPath.add(pos10.get(i-1));
                }

            }
        }

        if (!requests11.isEmpty()){
            List<Integer> optimalPath11 = new ArrayList<>();
            double d11 = Double.MAX_VALUE;
            rTemp.clear();
            rTemp.addAll(requests11);


            Graph g11 = new CompleteGraph(deliveryTour.getCheminDij(), rTemp, cityMap);
            CreateDynamique dynamique11 = new CreateDynamique(g11);
            int n = g11.getNbVertices();
            int s = dynamique11.createSet(n); // s contains all integer values ranging between 1 and n

            double[][] memD = new double[n][s + 1];
            for (int i = 0; i < n; i++) {
                Arrays.fill(memD[i], 0);
            }
            d11 = dynamique11.classicDynamic(nextStart, s, n, g11, memD);
            optimalPath11 = dynamique11.classicPath(nextStart, n, g11, memD);

            d+=d11;
            System.out.printf("Length of the smallest hamiltonian circuit11 = %f\n", d11);
            System.out.printf("Optimal Hamiltonian Circuit Path11: %s\n", optimalPath11);
            System.out.printf("Pos: %s\n", pos11);
            for (Integer i : optimalPath11) {
                if (i != 0){
                    optimalPath.add(pos11.get(i-1));
                }

            }
        }



        optimalPath.add(0);
        System.out.printf("Length of the smallest hamiltonian circuit = %f\n", d);
        System.out.printf("Optimal Hamiltonian Circuit Path: %s\n", optimalPath);
        return(dynamique.bestCheminGlobal(deliveryTour.getCheminDij(),g,optimalPath));
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



}
