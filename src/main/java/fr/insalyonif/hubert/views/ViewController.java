package fr.insalyonif.hubert.views;
import fr.insalyonif.hubert.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.util.StringConverter;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fr.insalyonif.hubert.model.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class ViewController implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private Button delivery;


    @FXML
    private ComboBox<Courier> courier;


    private ObservableList<Courier> listCourier;
    

    @FXML
    private ListView<DeliveryRequest> listViewDelivery;

    private ObservableList<DeliveryRequest> listDelivery;

    private  WebEngine engine;

   private Controller controller;

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
    void addNewCourrier(ActionEvent event){
        controller.newDeliveryTour();
        setCourierIHM(controller.getListeDelivery());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Successfully added ! :)");
        alert.showAndWait();
    }
    @FXML
    void handleOpenNewWindow(ActionEvent event) {
        if(event.getSource()==delivery) {
            if(controller !=null) {
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

                if(controller.newDeliveryPoint(deliveryIHM,0)){
                    String markersJs = drawPaths(controller.getCityMap());
                    String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);
                    engine.loadContent(mapHtml);
                    
                    
                    //this.setDeliveryRequestIHM(controller.getListeDelivery().get(0).getRequests());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Point non accessible ");
                    alert.showAndWait();
                }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Il faut d'abord choisir une MAP");
                alert.showAndWait();
            }

        }
    }


    // Méthode pour calculer la distance entre deux points géographiques en utilisant la formule de Haversine

    private StringBuilder displayDeliveryPoints() {
        StringBuilder markersJs = new StringBuilder();
        String iconUrl   = "https://cdn-icons-png.flaticon.com/512/124/124434.png";
        //https://api.iconify.design/mdi/map-marker.svg?color=%23ffae42
        String markerJs = String.format(
                "var marker = L.marker([" + controller.getCityMap().getWareHouseLocation().getLatitude() + ", " +  controller.getCityMap().getWareHouseLocation().getLongitude() + "], {icon: L.icon({iconUrl: '%s', iconSize: [30, 40], iconAnchor: [15, 30]})}).addTo(map);"
                        + "marker.bindTooltip('%s').openTooltip();", iconUrl, "Warehouse"
        );
        markersJs.append(markerJs);
        int i=0;
        for (DeliveryRequest deliveryRequest : controller.getListeDelivery().get(0).getRequests()) {
            markersJs.append(markerJs);
            iconUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ed/Map_pin_icon.svg/1200px-Map_pin_icon.svg.png";
            i++;
            markerJs = String.format(
                    "var marker = L.marker([" + deliveryRequest.getDeliveryLocation().getLatitude() + ", " +  deliveryRequest.getDeliveryLocation().getLongitude() + "],  {icon: L.icon({iconUrl: '%s', iconSize: [15, 20], iconAnchor: [8, 20]})}).addTo(map);"
                            + "marker.bindTooltip('Nb: %d').openTooltip();",
                     //deliveryRequest.getDeliveryLocation().getId()
                    iconUrl,i
            );

            markersJs.append(markerJs);
        }
        return markersJs;
    }


    private String drawPaths(CityMap cityMap) {
        StringBuilder markersJs = displayDeliveryPoints();
        int index=0;
        for (int i = controller.getListeDelivery().get(0).getPaths().size() - 1; i >= 0; i--) {
            Chemin chemin = controller.getListeDelivery().get(0).getPaths().get(i);
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


            //if(index==0){
                //String polylineJs = "L.polyline(" + polylineCoords + ", {color: '#0000FF'}).addTo(map);";
                //markersJs.append(polylineJs);
              //   System.out.println(polylineJs);
            //}else{
                String polylineJs = "L.polyline(" + polylineCoords + ", {color: '"+generateRandomColor()+"'}).addTo(map);";
                markersJs.append(polylineJs);
                 System.out.println(polylineJs);
            //}

                 System.out.println("Chemin index "+ i);
           

            index++;
        }
        return markersJs.toString();
    }

    public static String generateRandomColor() {
        // Générateur de nombres aléatoires
        Random random = new Random();

        // Génération de trois composants de couleur (R, G, B)
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Conversion des composants de couleur en format hexadécimal
        String hexRed = Integer.toHexString(red);
        String hexGreen = Integer.toHexString(green);
        String hexBlue = Integer.toHexString(blue);

        // Assurez-vous que chaque composant a deux chiffres hexadécimaux
        hexRed = padZero(hexRed);
        hexGreen = padZero(hexGreen);
        hexBlue = padZero(hexBlue);

        // Concaténation des composants pour obtenir la couleur complète
        String hexColor = "#" + hexRed + hexGreen + hexBlue;

        return hexColor.toUpperCase(); // Retourne la couleur en majuscules
    }

    private static String padZero(String hexComponent) {
        return hexComponent.length() == 1 ? "0" + hexComponent : hexComponent;
    }


    void handleCourierSelection(Courier newCourier){

        // Filter the delivery tours for the selected courier
        DeliveryTour selectedTour = controller.getListeDelivery().stream()
        .filter(tour -> tour.getCourier().equals(newCourier))
        .findFirst()
        .orElse(null);

        if (selectedTour != null) {
            setDeliveryRequestIHM(selectedTour.getRequests());


            }
         }   

    @FXML
    void handleLoadMap(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open XML Map File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                // Load the selected XML map file
                controller = new Controller(selectedFile.getAbsolutePath());

                setCourierIHM(controller.getListeDelivery());
                
                String markersJs = displayDeliveryPoints().toString();
                String mapHtml = MAP_HTML_TEMPLATE.formatted(markersJs);

                engine.loadContent(mapHtml);
            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an error message)
            }
        }
    }

    public void setDeliveryRequestIHM(ArrayList<DeliveryRequest> delivery) {
        listDelivery.clear();
        /*for (DeliveryRequest demande : delivery) {
            listDelivery.add(demande);
        }*/
        listDelivery.addAll(delivery);
    }

    public void setCourierIHM(ArrayList<DeliveryTour> deliveryTours) {
        listCourier.clear();
        for (DeliveryTour deliveryTour : deliveryTours) {
            listCourier.add(deliveryTour.getCourier());
        }      
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        listDelivery = FXCollections.observableArrayList();
        listViewDelivery.setItems(listDelivery);

        listCourier= FXCollections.observableArrayList();
       courier.setConverter(new StringConverter<Courier>() {
           @Override
           public String toString(Courier c) {
               if(c==null){
                   return "";
               }
               return "Courrier "+c.getId();
           }

           @Override
           public Courier fromString(String s) {
               return null;
           }
       });
        courier.setItems(listCourier);

        courier.valueProperty().addListener((observable, oldValue, newCourier) -> {
            // newValue is the newly selected Courier object
            if (newCourier != null) {
                handleCourierSelection(newCourier);
            }
        });

    }
}