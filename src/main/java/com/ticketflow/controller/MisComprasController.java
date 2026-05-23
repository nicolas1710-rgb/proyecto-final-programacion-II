package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Compra;
import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.util.DateUtil;
import com.ticketflow.util.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

public class MisComprasController {
    @FXML
    private TableView<Compra> tvCompras;
    @FXML
    private TableColumn<Compra, String> colFecha;
    @FXML
    private TableColumn<Compra, String> colEvento;
    @FXML
    private TableColumn<Compra, String> colEstado;
    @FXML
    private TableColumn<Compra, String> colTotal;

    @FXML
    public void initialize() {
        colFecha.setCellValueFactory(
                cellData -> new SimpleStringProperty(DateUtil.format(cellData.getValue().getFechaCreacion())));
        colEvento
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEvento().getNombre()));
        colEstado.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getEstadoCompra().name()));
        colTotal.setCellValueFactory(
                cellData -> new SimpleStringProperty(String.format("$%.2f", cellData.getValue().getTotal())));

        tvCompras.setRowFactory(tv -> {
            TableRow<Compra> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    verDetalleCompra(row.getItem());
                }
            });
            return row;
        });

        cargarCompras();
    }

    private void cargarCompras() {
        java.util.UUID uid = GestorSesion.getInstance().getUsuarioActual().getIdUsuario();
        List<Compra> misCompras = Main.compraService.listarCompras(uid);
        tvCompras.setItems(FXCollections.observableArrayList(misCompras));
    }

    private void verDetalleCompra(Compra c) {
        DetalleCompraController.compraActual = c;
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/DetalleCompraView.fxml",
                "TicketFlow - Detalle Compra");
    }
    @FXML
    public void onIrAlInicio() {
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/DashboardUsuarioView.fxml", "TicketFlow - Usuario");
    }
}
