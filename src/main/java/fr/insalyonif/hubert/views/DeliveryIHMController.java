package fr.insalyonif.hubert.views;

import fr.insalyonif.hubert.model.CityMap;
import fr.insalyonif.hubert.model.Intersection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class DeliveryIHMController implements Initializable {
    @FXML
    private TextField lat;

    @FXML
    private TextField lng;

    @FXML
    private Button valider;

    private  double latDouble;
    private double lngDouble;


    @FXML
    void validerButton(ActionEvent event) {
        if(event.getSource()==valider) {
            String latString=lat.getText();
            String lngString=lng.getText();
            try {
                latDouble = Double.parseDouble(latString);
                lngDouble = Double.parseDouble(lngString);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
