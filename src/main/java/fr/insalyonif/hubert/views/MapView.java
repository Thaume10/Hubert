package fr.insalyonif.hubert.views;
   
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.InputStream;

import fr.insalyonif.hubert.model.*;


public class MapView extends Application {
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
                    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                        maxZoom: 18
                    }).addTo(map);
                    var customIcon = L.icon({
                        iconUrl: 'https://cdn-icons-png.flaticon.com/512/929/929426.png',
                        iconSize: [25, 25],
                        iconAnchor: [12, 12],
                    });
                    %s
                </script>
            </body>
            </html>
            """;

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();
        CityMap cityMap = new CityMap();
        // Assuming you have a method to load your city map data
        try {
           InputStream xmlStream = getClass().getResourceAsStream("/fr/insalyonif/hubert/fichiersXML2022/smallMap.xml");
            if (xmlStream != null) {
                cityMap.loadFromXML(xmlStream);
            } else {
                throw new FileNotFoundException("Cannot find resource smallMap.xml");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String markersJs = generateMarkersJs(cityMap);
        String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);   
        webView.getEngine().loadContent(mapHtml);
        
        primaryStage.setScene(new Scene(webView));
        primaryStage.show();

        
        //System.out.println(mapHtml);
        
    // Display intersections
    //cityMap.displayIntersections();
    }

    private String generateMarkersJs(CityMap cityMap) {
        StringBuilder markersJs = new StringBuilder();
        for (Intersection intersection : cityMap.getIntersections()) {
            String markerJs = "L.marker([" + intersection.getLatitude() + ", " + intersection.getLongitude() + "], {icon: customIcon}).addTo(map);";
            markersJs.append(markerJs);
        }
        return markersJs.toString();
    }


    public static void main(String[] args) {
        launch(args);
    }
}