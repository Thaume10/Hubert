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

    // Variable to store the selected file path
    private String selectedFilePath;

    public void initialize() {
    }

    @FXML
    private void handleSeeAllDeliveries(ActionEvent event) {
        // TO DO choisir le fichier à reprendre
        // Créer un sélecteur de dossier
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Research Folder");

        // Afficher le sélecteur de dossier
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            // Récupérer la liste des fichiers dans le dossier
            File[] files = selectedDirectory.listFiles();

            if (files != null && files.length > 0) {
                // Afficher les noms des fichiers dans la console (ou dans une étiquette, etc.)
                List<File> fileList = Arrays.asList(files);
                fileList.forEach(file -> System.out.println("File Name: " + file.getName()));

                // Vous pouvez également afficher les noms des fichiers dans une étiquette
                StringBuilder fileNames = new StringBuilder("Files in 'research' folder:\n");
                fileList.forEach(file -> fileNames.append(file.getName()).append("\n"));
                messageLabel.setText(fileNames.toString());
            } else {
                messageLabel.setText("No files found in the 'research' folder.");
            }
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
