package com.ticketflow.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;




public class NavigationManager {
    private static NavigationManager instancia;
    private Stage stage;
    private final Stack<Scene> historial = new Stack<>();

    private NavigationManager() {
    }

    public static NavigationManager getInstance() {
        if (instancia == null) {
            instancia = new NavigationManager();
        }
        return instancia;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void navigateTo(String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            
            scene.getStylesheets().add(getClass().getResource("/com/ticketflow/css/styles.css").toExternalForm());

            if (stage.getScene() != null) {
                historial.push(stage.getScene());
            }

            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar vista: " + fxmlPath);
        }
    }

    public void goBack() {
        if (!historial.isEmpty()) {
            Scene previousScene = historial.pop();
            stage.setScene(previousScene);
        }
    }
}
