package fr.insalyonif.hubert.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fr/insalyonif/hubert/ihm.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
//        stage.setTitle("Hubert!");
//        stage.setScene(scene);
//        stage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/insalyonif/hubert/start.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 800);
        stage.setTitle("Hubert!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}