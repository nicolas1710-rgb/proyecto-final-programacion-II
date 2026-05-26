package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Compra;
import com.ticketflow.model.MetodoPago;
import com.ticketflow.model.Pago;
import com.ticketflow.enums.EstadoCompra;
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
    private ComboBox<MetodoPago> cmbMetodoPago;
    @FXML
    private Button btnPagar;
    @FXML
    private Label lblMensaje;

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

        tvCompras.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.getEstadoCompra() == EstadoCompra.CREADA) {
                List<MetodoPago> metodos = GestorSesion.getInstance().getUsuarioActual().getMetodosDePago();
                if (metodos.isEmpty()) {
                    lblMensaje.setText("Registra un método de pago en tu perfil.");
                    cmbMetodoPago.setDisable(true);
                    btnPagar.setDisable(true);
                } else {
                    cmbMetodoPago.setItems(FXCollections.observableArrayList(metodos));
                    cmbMetodoPago.setDisable(false);
                    btnPagar.setDisable(false);
                    lblMensaje.setText("");
                }
            } else {
                cmbMetodoPago.setValue(null);
                cmbMetodoPago.setDisable(true);
                btnPagar.setDisable(true);
                lblMensaje.setText("");
            }
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

    @FXML
    public void onPagarCompra() {
        Compra compra = tvCompras.getSelectionModel().getSelectedItem();
        MetodoPago metodo = cmbMetodoPago.getValue();
        if (compra == null || metodo == null) {
            lblMensaje.setText("Selecciona una compra y un método de pago.");
            return;
        }

        try {
            Pago pago = Main.compraService.confirmarPago(compra.getIdCompra(), metodo);
            Main.entradaService.generarEntradas(compra);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pago aprobado exitosamente. Entradas generadas.");
            alert.showAndWait();
            
            cargarCompras();
        } catch (Exception e) {
            lblMensaje.setText("Error en pago: " + e.getMessage());
        }
    }
}
