package fr.insalyonif.hubert.views;

import fr.insalyonif.hubert.controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StartController {

    @FXML
    private StackPane root;

    @FXML
    private Label messageLabel;

    @FXML
    private Label message;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button fileLoader;

    @FXML
    private Button start;

    @FXML
    private Button allDeliveries;

    // Variable to store the selected file path
    private String selectedFilePath;

    public void initialize() {
    }

    @FXML
    private void handleSeeAllDeliveries(ActionEvent event) throws Exception {
        // TO DO choisir le fichier à reprendre
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //getClass().getResource("/fr/insalyonif/hubert/successSave.fxml")


        // Show open file dialog
        Stage stage = (Stage) allDeliveries.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // If a file is selected, store its path
        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            System.out.println("Selected File: " + selectedFilePath);

            // Use Path to extract file name and extension
            Path path = Paths.get(selectedFilePath);
            String fileName = path.getFileName().toString(); // Extracts the file name

            String[] fileNameParts = fileName.split("_");
            String lastWord = fileNameParts[fileNameParts.length - 1];

            String stratPath = "src/main/resources/fr/insalyonif/hubert/fichiersXML2022/";
            String pathMap = stratPath + lastWord;
            System.out.println("Path of the map: " + pathMap);


            // Extract date from the file name
            String datePattern = "yyyy-MM-dd"; // Adjust the pattern based on the actual date format in the file name
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);

            // Extract the date substring from the file name
            String dateString = fileName.substring(11, 21); // Adjust indices based on the actual position of the date in the file name

            // Parse the date string to LocalDate
            LocalDate fileDate = LocalDate.parse(dateString, dateFormatter);
            System.out.println("File Date: " + fileDate);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/insalyonif/hubert/ihm.fxml"));
            Parent root = loader.load();

            // Afficher la nouvelle scène
            Scene scene = new Scene(root);
            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage1.setScene(scene);

            ViewController viewController = loader.getController();
            viewController.loadMap(fileDate, pathMap);
            viewController.importAllTheDeliveriesIntoController(selectedFilePath);
            viewController.displayAllTheDeliveryPoints();

            stage.show();
        }
    }



    @FXML
    private void handleCreateNewDeliveries(ActionEvent event) {
        // Code to handle the "Create new Deliveries" button click
        System.out.println("Create new Deliveries button clicked");

        // Show additional controls
        datePicker.setVisible(true);
        fileLoader.setVisible(true);
        start.setVisible(true);
    }

    @FXML
    private void handleLoadFile(ActionEvent event) {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        //getClass().getResource("/fr/insalyonif/hubert/successSave.fxml")


        // Show open file dialog
        Stage stage = (Stage) datePicker.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // If a file is selected, store its path
        if (selectedFile != null) {
            selectedFilePath = selectedFile.getAbsolutePath();
            System.out.println("Selected File: " + selectedFilePath);
        }
    }

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        // Récupérer la date du DatePicker
        //start.setVisible(true);

        // Charger le fichier FXML "ihm.fxml"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/insalyonif/hubert/ihm.fxml"));
        Parent root = loader.load();

        // Afficher la nouvelle scène
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        ViewController viewController = loader.getController();
        viewController.loadMap(datePicker.getValue(), selectedFilePath);

        stage.show();
    }

    // Add getter for selectedFilePath if needed
    public String getSelectedFilePath() {
        return selectedFilePath;
    }

}
