package fr.insalyonif.hubert.views;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

import fr.insalyonif.hubert.model.*;




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



    private String generateMarkersJs(CityMap cityMap) {
        StringBuilder markersJs = new StringBuilder();
        for (Intersection intersection : cityMap.getIntersections()) {
            String markerJs = "L.marker([" + intersection.getLatitude() + ", " + intersection.getLongitude() + "], {icon: customIcon}).addTo(map);";
            markersJs.append(markerJs);
        }
        System.out.println(markersJs.toString());  // Debugging
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

        String markersJs = generateMarkersJs(cityMap);
        String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);
        WebEngine engine = webView.getEngine();
        engine.loadContent(mapHtml);
    }
}
