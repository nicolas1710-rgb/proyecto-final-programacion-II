package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.*;
import com.ticketflow.util.DateUtil;
import com.ticketflow.util.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Map;

public class DetalleEventoController {
    public static Evento eventoActual; 

    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblCiudad;
    @FXML
    private Label lblRecinto;
    @FXML
    private Label lblDescripcion;
    @FXML
    private Label lblPolCancelacion;
    @FXML
    private TableView<Map.Entry<Zona, Integer>> tvDisponibilidad;
    @FXML
    private TableColumn<Map.Entry<Zona, Integer>, String> colZona;
    @FXML
    private TableColumn<Map.Entry<Zona, Integer>, String> colPrecio;
    @FXML
    private TableColumn<Map.Entry<Zona, Integer>, String> colDisponibles;

    @FXML
    public void initialize() {
        if (eventoActual == null) {
            System.err.println("No hay evento seleccionado.");
            return;
        }

        lblTitulo.setText(eventoActual.getNombre());
        lblFecha.setText(DateUtil.format(eventoActual.getFechaHora()));
        lblCiudad.setText(eventoActual.getCiudad());
        lblRecinto.setText(eventoActual.getRecinto().getNombre() + " - " + eventoActual.getRecinto().getDireccion());
        lblDescripcion.setText(eventoActual.getDescripcion());
        lblPolCancelacion.setText(eventoActual.getPoliticaCancelacion());

        
        colZona.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNombre()));
        colPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.format("$%.2f", cellData.getValue().getKey().getPrecioBase())));
        colDisponibles.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValue())));

        cargarDisponibilidad();
    }

    private void cargarDisponibilidad() {
        Map<Zona, Integer> disp = Main.eventoService.obtenerDisponibilidad(eventoActual.getIdEvento());
        tvDisponibilidad.setItems(FXCollections.observableArrayList(disp.entrySet()));
    }

    @FXML
    public void onComprarNavegar() {
        Map.Entry<Zona, Integer> sel = tvDisponibilidad.getSelectionModel().getSelectedItem();
        if (sel == null) {
            new Alert(Alert.AlertType.WARNING, "Seleccione una zona para comprar.").show();
            return;
        }

        Zona zona = sel.getKey();
        if (sel.getValue() <= 0) {
            new Alert(Alert.AlertType.WARNING, "No hay asientos disponibles en esta zona.").show();
            return;
        }

        CarritoCompraController.eventoActual = eventoActual;
        CarritoCompraController.zonaSeleccionada = zona;

        
        if (zona.getAsientos() != null && !zona.getAsientos().isEmpty()) {
            MapaAsientosController.zonaActual = zona;
            NavigationManager.getInstance().navigateTo("/com/ticketflow/view/MapaAsientosView.fxml",
                    "TicketFlow - Seleccionar Asientos");
        } else {
            
            NavigationManager.getInstance().navigateTo("/com/ticketflow/view/CarritoCompraView.fxml",
                    "TicketFlow - Carrito");
        }
    }

    @FXML
    public void onVolver() {
        NavigationManager.getInstance().goBack();
    }
}
