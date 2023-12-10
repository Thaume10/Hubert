package fr.insalyonif.hubert.views;

import fr.insalyonif.hubert.model.Courier;
import fr.insalyonif.hubert.model.TimeWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;


public class DeliveryIHMController implements Initializable {

    ObservableList<TimeWindow> timeWindows = FXCollections.observableArrayList(
            new TimeWindow(8, 9),
            new TimeWindow(9, 10),
            new TimeWindow(10, 11),
            new TimeWindow(11, 12));
    @FXML
    private TextField lat;

    @FXML
    private TextField lng;

    @FXML
    private ComboBox<TimeWindow> deliveryTime;

    @FXML
    private Button valider;

    public void setDeliveryTime(ComboBox<TimeWindow> deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    public void setCourier(ComboBox<Courier> courier) {
        this.courier = courier;
    }

    @FXML
    private ComboBox<Courier> courier;


    private ObservableList<Courier> listCourier;

    private  double latDouble;
    private double lngDouble;

    private TimeWindow timeWindow;

    private boolean isValiderClicked = false;


    @FXML
    void validerButton(ActionEvent event) {
        if(event.getSource()==valider) {
            String latString=lat.getText();
            String lngString=lng.getText();
            try {
                latDouble = Double.parseDouble(latString);
                lngDouble = Double.parseDouble(lngString);

                TimeWindow selectedTimeWindow = (TimeWindow) deliveryTime.getValue();

                if (selectedTimeWindow != null && courier.getValue() != null) {
                    isValiderClicked = true;
                    int startTime ;
                    int endTime ;
                    switch (selectedTimeWindow.toString()) {
                        case "Delivery between 8h and 9h":
                            startTime=8;
                            endTime=9;
                            break;
                        case "Delivery between 9h and 10h":
                            startTime=9;
                            endTime=10;
                            break;
                        case "Delivery between 10h and 11h":
                            startTime=10;
                            endTime=11;
                            break;
                        case "Delivery between 11h and 12h":
                            startTime=11;
                            endTime=12;
                            break;
                        default:
                            startTime=0;
                            endTime=0;
                            break;
                    }
                    timeWindow = new TimeWindow(startTime, endTime);
                } else {
                    // Gérer le cas où aucun créneau horaire n'est sélectionné
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("There is an empty field 😢");
                    alert.showAndWait();
                    return;  // Sortir de la méthode si aucun créneau horaire n'est sélectionné
                }
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Not a double");
                alert.showAndWait();
            }


        }
    }

    public boolean isValiderClicked() {
        return isValiderClicked;
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

    public void setLat(double lat) {
        this.lat.setText(String.valueOf(lat));
    }

    public void setLng(double lng) {
        this.lng.setText(String.valueOf(lng));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deliveryTime.setItems(timeWindows);
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

    }

    public Courier getCourier() {
        return courier.getValue();
    }

    public void setInitialCourier(Courier initCourier){
        this.courier.setValue(initCourier);
    }
    public void setInitialTimeWindow(TimeWindow timeWindow){
        this.deliveryTime.setValue((timeWindow));
    }

    public void setListCourier(ObservableList<Courier> list){
        listCourier=list;
        courier.setItems(listCourier);
    }


}
