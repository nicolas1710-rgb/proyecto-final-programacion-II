package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.MetodoPago;
import com.ticketflow.model.Usuario;
import com.ticketflow.enums.TipoMetodoPago;
import com.ticketflow.patterns.creacion.GestorSesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MetodosPagoController {
    @FXML
    private TableView<MetodoPago> tvMetodos;
    @FXML
    private TableColumn<MetodoPago, String> colTipo;
    @FXML
    private TableColumn<MetodoPago, String> colDetalle;
    @FXML
    private ComboBox<TipoMetodoPago> cmbTipo;
    @FXML
    private TextField txtDetalle;
    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbTipo.setItems(FXCollections.observableArrayList(TipoMetodoPago.values()));

        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo().name()));
        colDetalle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDetalle()));

        cargarMetodos();
    }

    private void cargarMetodos() {
        Usuario u = GestorSesion.getInstance().getUsuarioActual();
        if (u != null) {
            tvMetodos.setItems(FXCollections.observableArrayList(u.getMetodosDePago()));
        }
    }

    @FXML
    public void onAgregar() {
        TipoMetodoPago tipo = cmbTipo.getValue();
        String detalle = txtDetalle.getText().trim();

        if (tipo == null || detalle.isEmpty()) {
            lblMensaje.setText("Seleccione tipo y escriba el detalle.");
            return;
        }

        Usuario u = GestorSesion.getInstance().getUsuarioActual();
        MetodoPago nuevo = new MetodoPago(tipo, detalle, u.getNombreCompleto());
        u.getMetodosDePago().add(nuevo);
        Main.usuarioRepo.update(u);

        cargarMetodos();
        txtDetalle.clear();
        cmbTipo.setValue(null);
        lblMensaje.setStyle("-fx-text-fill: #4CAF50;");
        lblMensaje.setText("Método agregado.");
    }
}
