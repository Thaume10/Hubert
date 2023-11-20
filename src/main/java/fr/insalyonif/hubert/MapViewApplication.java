package fr.insalyonif.hubert;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.concurrent.Worker;

public class MapViewApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();

        // Chargement de la carte Mapbox
        String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Carte Mapbox</title>
                <meta charset="utf-8" />
                <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
                <script src="https://unpkg.com/leaflet@1.7.1/dist/leaflet.js"></script>
            </head>
            <body>
                <div id="map" style="width: 100%; height: 100%;"></div>
                <script>
                    var map = L.map('map').setView([45.775486, 4.888253], 13);
                    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
                        attribution: '© <a href="https://www.mapbox.com/about/maps/" target="_blank">Mapbox</a> contributors',
                        maxZoom: 19,
                        id: 'mapbox/streets-v11',
                        accessToken: 'pk.eyJ1IjoibGFpbW9uZXR0byIsImEiOiJjbGlhYWFzdGswMXFmM3BwM2h2ZHVlbHJ3In0.nTYGZH89AUiU1Vr3Zij0Iw'
                    }).addTo(map);
                </script>
            </body>
            </html>
            """;
        webView.getEngine().loadContent(htmlContent);

        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                System.err.println("Échec du chargement de la page : " + webView.getEngine().getLoadWorker().getException());
            }
        });

        Scene scene = new Scene(webView, 800, 600);

        primaryStage.setTitle("Test de la carte Mapbox");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
