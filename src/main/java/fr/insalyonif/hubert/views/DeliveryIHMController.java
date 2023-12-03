package fr.insalyonif.hubert.views;

import fr.insalyonif.hubert.model.CityMap;
import fr.insalyonif.hubert.model.Intersection;
import fr.insalyonif.hubert.model.TimeWindow;
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
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;


public class DeliveryIHMController implements Initializable {

    ObservableList<String> timeWindows = FXCollections.observableArrayList("8h-9h","9h-10h","10h-11h","11h-12h");
    @FXML
    private TextField lat;

    @FXML
    private TextField lng;

    @FXML
    private ComboBox deliveryTime;

    @FXML
    private Button valider;

    private  double latDouble;
    private double lngDouble;

    private TimeWindow timeWindow;


    @FXML
    void validerButton(ActionEvent event) {
        if(event.getSource()==valider) {
            String latString=lat.getText();
            String lngString=lng.getText();
            try {
                latDouble = Double.parseDouble(latString);
                lngDouble = Double.parseDouble(lngString);

                String selectedTimeWindow = (String) deliveryTime.getValue();

                if (selectedTimeWindow != null) {
                    Instant currentInstant = Instant.now();
                    ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(currentInstant, ZoneId.of("UTC"));
                    LocalTime specificTime;
                    ZonedDateTime resultDateTime;
                    Instant startTime ;
                    Instant endTime ;
                    switch (selectedTimeWindow) {
                        case "8h-9h":
                            specificTime = LocalTime.of(8, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            startTime= resultDateTime.toInstant();
                            specificTime = LocalTime.of(9, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            endTime= resultDateTime.toInstant();
                            break;
                        case "9h-10h":
                            specificTime = LocalTime.of(9, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            startTime= resultDateTime.toInstant();
                            specificTime = LocalTime.of(10, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            endTime= resultDateTime.toInstant();
                            break;
                        case "10h-11h":
                            specificTime = LocalTime.of(10,0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            startTime= resultDateTime.toInstant();
                            specificTime = LocalTime.of(11, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            endTime= resultDateTime.toInstant();
                            break;
                        case "11h-12h":
                            specificTime = LocalTime.of(11, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            startTime= resultDateTime.toInstant();
                            specificTime = LocalTime.of(12, 0);
                            resultDateTime = zonedDateTime.with(specificTime);
                            endTime= resultDateTime.toInstant();
                            break;
                        default:
                            startTime=Instant.parse("0000-00-00T00:00:00Z");
                            endTime=Instant.parse("0000-00-00T00:00:00Z");
                            break;
                    }
                    timeWindow = new TimeWindow(startTime, endTime);
                } else {
                    // Gérer le cas où aucun créneau horaire n'est sélectionné
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez sélectionner un créneau horaire");
                    alert.showAndWait();
                    return;  // Sortir de la méthode si aucun créneau horaire n'est sélectionné
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Pas un double");
                alert.showAndWait();
            }


        }
    }

    public double getLatDouble() {
        return latDouble;
    }

    public double getLngDouble() {
        return lngDouble;
    }

    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deliveryTime.setItems(timeWindows);
    }

}
