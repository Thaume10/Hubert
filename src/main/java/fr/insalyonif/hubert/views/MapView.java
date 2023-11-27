package fr.insalyonif.hubert.views;
   
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import fr.insalyonif.hubert.model.*;


public class MapView implements Initializable {

    @FXML
    private WebView webView;

    private WebEngine engine;
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
                %s  // This will be replaced by the markers JavaScript
            </script>
        </body>
        </html>
        """;



    private String generateMarkersJs(CityMap cityMap) {
        StringBuilder markersJs = new StringBuilder();
        for (Intersection intersection : cityMap.getIntersections()) {
            String markerJs = "L.marker([" + intersection.getLatitude() + ", " + intersection.getLongitude() + "]).addTo(map);";
            markersJs.append(markerJs);
        }
        System.out.println(markersJs.toString());  // Debugging
        return markersJs.toString();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webView = new WebView();
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
        engine=webView.getEngine();
        //engine.loadContent(mapHtml);
        engine.load("http://www.google.com");
    }
}