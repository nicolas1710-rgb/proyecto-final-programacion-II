package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Incidencia;
import com.ticketflow.enums.TipoIncidencia;
import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;

public class IncidenciasController {
    @FXML
    private TableView<Incidencia> tvIncidencias;
    @FXML
    private TableColumn<Incidencia, String> colFecha;
    @FXML
    private TableColumn<Incidencia, String> colUsuario;
    @FXML
    private TableColumn<Incidencia, String> colTipo;
    @FXML
    private TableColumn<Incidencia, String> colEstado;

    @FXML
    private TextArea txtDescripcion;
    @FXML
    private ComboBox<TipoIncidencia> cmbTipo;
    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        cmbTipo.setItems(FXCollections.observableArrayList(TipoIncidencia.values()));

        colFecha.setCellValueFactory(cf -> new SimpleStringProperty(DateUtil.format(cf.getValue().getFechaCreacion())));
        colUsuario
                .setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getUsuario().getCorreoElectronico()));
        colTipo.setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getTipo().name()));
        colEstado.setCellValueFactory(
                cf -> new SimpleStringProperty(cf.getValue().isResuelta() ? "Resuelta" : "Pendiente"));

        tvIncidencias.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                txtDescripcion.setText(newV.getDescripcion());
            }
        });

        cargarIncidencias();
    }

    private void cargarIncidencias() {
        tvIncidencias.setItems(FXCollections.observableArrayList(Main.incidenciaRepo.findAll()));
    }

    @FXML
    public void onRegistrar() {
        TipoIncidencia tipo = cmbTipo.getValue();
        String desc = txtDescripcion.getText().trim();

        if (tipo == null || desc.isEmpty()) {
            lblMensaje.setText("Seleccione tipo y escriba descripción.");
            return;
        }

        
        
        if (GestorSesion.getInstance().getUsuarioActual() != null) {
            Incidencia inc = new Incidencia(GestorSesion.getInstance().getUsuarioActual(), null, tipo, desc);
            Main.incidenciaService.registrar(inc);
            lblMensaje.setText("Incidencia registrada.");
            cargarIncidencias();
        } else {
            lblMensaje.setText("Solo los usuarios pueden registrar incidencias (Simulación).");
        }
    }

    @FXML
    public void onResolver() {
        Incidencia inc = tvIncidencias.getSelectionModel().getSelectedItem();
        if (inc == null) {
            lblMensaje.setText("Seleccione incidencia a resolver.");
            return;
        }
        inc.setResuelta(true);
        inc.setFechaResolucion(LocalDateTime.now());
        Main.incidenciaRepo.update(inc);
        lblMensaje.setText("Incidencia resuelta.");
        cargarIncidencias();
    }
}
