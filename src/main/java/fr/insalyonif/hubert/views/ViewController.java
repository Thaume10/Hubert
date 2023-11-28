package fr.insalyonif.hubert.views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fr.insalyonif.hubert.model.*;

import static fr.insalyonif.hubert.model.Dijkstra.deliveryRequest;


public class ViewController implements Initializable {
    @FXML
    private WebView webView;

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
        
        // Draw paths (chemins)
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
        CityMap cityMap = new CityMap();
        try {
            String xmlMap = "src/main/resources/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml";
            cityMap.loadFromXML(xmlMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        int sizeGraph = cityMap.getIntersections().size(); // Mettez la taille correcte de votre graphe
        Dijkstra dij = new Dijkstra(sizeGraph, cityMap);
        dij.dijkstra(cityMap.findIntersectionByPos(10), cityMap, sizeGraph);

        //minimiser les chemins
        //System.out.println(dij.getChemins());
        //System.out.println(deliveryRequest);

        Graph g = new CompleteGraph(dij.getChemins(),deliveryRequest);


        TSP tsp = new TSP1();
        tsp.searchSolution(20000, g);
        System.out.print("Solution of cost "+tsp.getSolutionCost());
        for (int i=0; i< deliveryRequest.size()  ; i++)
            System.out.print(tsp.getSolution(i)+" ");
        System.out.println("0");
        List<Chemin> bestChemin = tsp.bestCheminGlobal(dij.getChemins());

        System.out.println("Meilleur chemin global :");
        for (Chemin chemin : bestChemin) {
            System.out.println("Départ : " + chemin.getDebut() + " -> Arrivée : " + chemin.getFin()+ " | Coût : " + chemin.getCout());
        }
        cityMap.setChemins(bestChemin);
        
        String markersJs = generateMarkersJs(cityMap);
        String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);
        WebEngine engine = webView.getEngine();
        engine.loadContent(mapHtml);
    }



}
