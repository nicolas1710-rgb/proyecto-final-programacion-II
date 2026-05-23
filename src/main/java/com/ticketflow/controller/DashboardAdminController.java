package com.ticketflow.controller;

import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class DashboardAdminController {
    @FXML
    private Label lblBienvenida;
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        if (GestorSesion.getInstance().getAdminActual() != null) {
            lblBienvenida.setText("Panel Admin: " + GestorSesion.getInstance().getAdminActual().getNombreCompleto());
        }
        cargarVista("/com/ticketflow/view/MetricasView.fxml");
    }

    @FXML
    public void onMetricas() {
        cargarVista("/com/ticketflow/view/MetricasView.fxml");
    }

    @FXML
    public void onGestionEventos() {
        cargarVista("/com/ticketflow/view/GestionEventosView.fxml");
    }

    @FXML
    public void onGestionRecintos() {
        cargarVista("/com/ticketflow/view/GestionRecintosView.fxml");
    }

    @FXML
    public void onGestionZonasAsientos() {
        cargarVista("/com/ticketflow/view/GestionZonasView.fxml");
    }

    @FXML
    public void onGestionCompras() {
        cargarVista("/com/ticketflow/view/GestionComprasView.fxml");
    }

    @FXML
    public void onGestionUsuarios() {
        cargarVista("/com/ticketflow/view/GestionUsuariosView.fxml");
    }

    @FXML
    public void onIncidencias() {
        cargarVista("/com/ticketflow/view/IncidenciasView.fxml");
    }

    @FXML
    public void onReportes() {
        cargarVista("/com/ticketflow/view/ReportesView.fxml");
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
