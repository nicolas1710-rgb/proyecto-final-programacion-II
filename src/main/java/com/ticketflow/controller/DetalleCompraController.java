package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.*;
import com.ticketflow.enums.EstadoCompra;
import com.ticketflow.util.DateUtil;
import com.ticketflow.util.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class DetalleCompraController {
    public static Compra compraActual;

    @FXML
    private Label lblIdCompra;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblEvento;
    @FXML
    private Label lblTotal;
    @FXML
    private TableView<Entrada> tvEntradas;
    @FXML
    private TableColumn<Entrada, String> colCodigo;
    @FXML
    private TableColumn<Entrada, String> colZona;
    @FXML
    private TableColumn<Entrada, String> colAsiento;
    @FXML
    private TableColumn<Entrada, String> colEstado;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnDescargar;

    @FXML
    public void initialize() {
        if (compraActual == null)
            return;
        cargarDatos();

        colCodigo.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getCodigoQR().substring(0, 8) + "..."));
        colZona.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getZona().getNombre()));
        colAsiento.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getAsiento() != null ? cellData.getValue().getAsiento().getFila() + "-"
                        + cellData.getValue().getAsiento().getNumero() : "N/A"));
        colEstado.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getEstadoEntrada().name()));
    }

    private void cargarDatos() {
        // Refrescar la compra
        compraActual = Main.compraService.obtenerDetalle(compraActual.getIdCompra());

        lblIdCompra.setText("Compra #" + compraActual.getIdCompra().toString().substring(0, 8).toUpperCase());
        lblFecha.setText(DateUtil.format(compraActual.getFechaCreacion()));
        lblEstado.setText(compraActual.getEstadoCompra().name());
        lblEvento.setText(compraActual.getEvento().getNombre());
        lblTotal.setText(String.format("$%.2f", compraActual.getTotal()));

        List<Entrada> entradas = Main.entradaService.obtenerPorCompra(compraActual.getIdCompra());
        tvEntradas.setItems(FXCollections.observableArrayList(entradas));

        boolean esCancelable = compraActual.getEstadoCompra() == EstadoCompra.CONFIRMADA
                || compraActual.getEstadoCompra() == EstadoCompra.PAGADA;
        btnCancelar.setDisable(!esCancelable);
        btnDescargar.setDisable(entradas.isEmpty() || compraActual.getEstadoCompra() == EstadoCompra.CANCELADA);
    }

    @FXML
    public void onCancelarCompra() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que desea cancelar esta compra?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Main.compraService.cancelarCompra(compraActual.getIdCompra());
                    new Alert(Alert.AlertType.INFORMATION, "Compra cancelada exitosamente.").show();
                    cargarDatos();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
                }
            }
        });
    }

    @FXML
    public void onDescargarTickets() {
        // Simulación de descarga
        new Alert(Alert.AlertType.INFORMATION, "Tickets descargados en PDF (Simulado).").show();
    }

    @FXML
    public void onVolver() {
        NavigationManager.getInstance().goBack();
    }
    @FXML
    public void onIrAlInicio() {
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/DashboardUsuarioView.fxml", "TicketFlow - Usuario");
    }
}
