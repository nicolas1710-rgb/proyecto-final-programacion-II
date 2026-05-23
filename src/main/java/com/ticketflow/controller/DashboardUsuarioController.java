package com.ticketflow.controller;

import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardUsuarioController {
    @FXML
    private Label lblBienvenida;
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        if (GestorSesion.getInstance().getUsuarioActual() != null) {
            lblBienvenida.setText("Hola, " + GestorSesion.getInstance().getUsuarioActual().getNombreCompleto());
        }
        cargarVista("/com/ticketflow/view/ExplorarEventosView.fxml");
    }

    @FXML
    public void onExplorarEventos() {
        cargarVista("/com/ticketflow/view/ExplorarEventosView.fxml");
    }

    @FXML
    public void onMisCompras() {
        cargarVista("/com/ticketflow/view/MisComprasView.fxml");
    }

    @FXML
    public void onMiPerfil() {
        cargarVista("/com/ticketflow/view/PerfilView.fxml");
    }

    @FXML
    public void onMetodosPago() {
        cargarVista("/com/ticketflow/view/MetodosPagoView.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        GestorSesion.getInstance().logout();
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/LoginView.fxml",
                "TicketFlow - Iniciar Sesión");
    }

    private void cargarVista(String fxmlPatch) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPatch));
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
