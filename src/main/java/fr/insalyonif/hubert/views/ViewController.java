package fr.insalyonif.hubert.views;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.insalyonif.hubert.model.*;
import javafx.stage.Stage;


public class ViewController implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private Button delivery;

    private CityMap cityMap;
    private Dijkstra dij;
    private DijkstraInverse dijInv;
    private int sizeGraph;

    private  WebEngine engine;
    private List<DeliveryRequest> listeDelivery;

    private static final String MAP_HTML_TEMPLATE = """
        <!DOCTYPE html>
        <html>
        <head>
            <title>Simple Map</title>
            <meta charset="utf-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" href="https://unpkg.com/leaflet@1.7.1/dist/leaflet.css" />
            <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
            <style>
                #map { width: 800px; height: 600px; }
            </style>
        </head>
        <body>
            <div id="map"></div>
            <script>
                var map = L.map('map').setView([45.755, 4.87], 15);
                L.tileLayer('https://{s}.basemaps.cartocdn.com/light_all/{z}/{x}/{y}.png', {
                    attribution: 'Map tiles by Carto, under CC BY 3.0. Data by OpenStreetMap, under ODbL.',
                    maxZoom: 18
                }).addTo(map);
                var customIcon = L.icon({
                    iconUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Map_pin_icon.svg/1200px-Map_pin_icon.svg.png',
                    iconSize: [15, 20],
                    iconAnchor: [12, 12],
                });
                %s
            </script>
        </body>
        </html>
        """;


    @FXML
    void handleOpenNewWindow(ActionEvent event) {
        if(event.getSource()==delivery) {
            try {
                // Charger le fichier FXML de la nouvelle fenêtre
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/insalyonif/hubert/addDelivery.fxml"));
                Parent root = (Parent) loader.load();


                // Créer une nouvelle fenêtre
                Stage newStage = new Stage();
                newStage.setTitle("add Delivery");
                newStage.setScene(new Scene(root));
                DeliveryIHMController deliveryIHM = loader.getController();


                // Afficher la nouvelle fenêtre
                newStage.showAndWait();
                Intersection intersectionPlusProche = trouverIntersectionPlusProche(deliveryIHM.getLatDouble(), deliveryIHM.getLngDouble(), cityMap.getIntersections());

                // Afficher les résultats
                System.out.println("Coordonnées de l'emplacement donné : " + deliveryIHM.getLatDouble() + ", " + deliveryIHM.getLngDouble());
                System.out.println("Intersection la plus proche : " + intersectionPlusProche.getLatitude() + ", " + intersectionPlusProche.getLongitude());
                listeDelivery.add(new DeliveryRequest((intersectionPlusProche)));

                dij.runDijkstra(intersectionPlusProche, sizeGraph);
                dijInv.runDijkstra(intersectionPlusProche, sizeGraph);
                cityMap.setChemins(dij.getChemins());

                String markersJs = drawPaths(cityMap);
                String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);

                engine.loadContent(mapHtml);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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

    // Méthode pour calculer la distance entre deux points géographiques en utilisant la formule de Haversine
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
    private String generateMarkersJs(CityMap cityMap) {
        StringBuilder markersJs = new StringBuilder();
        for (Intersection intersection : cityMap.getIntersections()) {
            String markerJs = String.format(
                    "var marker = L.marker([" + intersection.getLatitude() + ", " + intersection.getLongitude() + "], {icon: customIcon}).addTo(map);"
                            + "marker.bindTooltip('ID: %d').openTooltip();",
                    intersection.getId()
            );
            markersJs.append(markerJs);
        }
        return markersJs.toString();
    }
    private String drawPaths(CityMap cityMap) {
        StringBuilder markersJs = new StringBuilder();
        int index=0;
        for (Chemin chemin : cityMap.getChemins()) {
            // Begin with the end intersection
            int currentIndex = chemin.getFin().getPos();
            int nextIndex = chemin.getPi()[currentIndex];

            StringBuilder polylineCoords = new StringBuilder("[");
            polylineCoords.append("[").append(chemin.getFin().getLatitude()).append(", ").append(chemin.getFin().getLongitude()).append("],");

            System.out.println("fin"+chemin.getFin());
            // Loop through pi array
            while (nextIndex != -1) {
                Intersection currentIntersection = cityMap.findIntersectionByPos(nextIndex);
                System.out.println(currentIntersection);
                if (currentIntersection != null) {
                    polylineCoords.append("[").append(currentIntersection.getLatitude()).append(", ").append(currentIntersection.getLongitude()).append("],");
                }
                currentIndex = nextIndex;
                nextIndex = chemin.getPi()[currentIndex];
            }
            System.out.println("debut"+chemin.getDebut());
            // End with the start intersection
            polylineCoords.append("[").append(chemin.getDebut().getLatitude()).append(", ").append(chemin.getDebut().getLongitude()).append("]");

            polylineCoords.append("]");
            if(index==0){
                String polylineJs = "L.polyline(" + polylineCoords + ", {color: 'blue'}).addTo(map);";
                markersJs.append(polylineJs);
            }else{
                String polylineJs = "L.polyline(" + polylineCoords + ", {color: 'red'}).addTo(map);";
                markersJs.append(polylineJs);
            }


            index++;
        }
        return markersJs.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cityMap = new CityMap();
        listeDelivery = new ArrayList<>();
        engine = webView.getEngine();
        try {
            String xmlMap = "src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml";
            cityMap.loadFromXML(xmlMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sizeGraph = cityMap.getIntersections().size(); // Mettez la taille correcte de votre graphe
        dij = new Dijkstra(sizeGraph, cityMap);
        dijInv = new DijkstraInverse(sizeGraph,cityMap);



        String markersJs = generateMarkersJs(cityMap);
        String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);

        engine.loadContent(mapHtml);
    }
}