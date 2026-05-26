package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Compra;
import com.ticketflow.enums.EstadoCompra;
import com.ticketflow.patterns.comportamiento.ComandoCancelarCompra;
import com.ticketflow.patterns.comportamiento.InvokerCompras;
import com.ticketflow.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GestionComprasController {
    @FXML
    private TableView<Compra> tvCompras;
    @FXML
    private TableColumn<Compra, String> colId;
    @FXML
    private TableColumn<Compra, String> colUsuario;
    @FXML
    private TableColumn<Compra, String> colFecha;
    @FXML
    private TableColumn<Compra, String> colEvento;
    @FXML
    private TableColumn<Compra, String> colTotal;
    @FXML
    private TableColumn<Compra, String> colEstado;

    @FXML
    private Label lblMensaje;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnDeshacer;

    private final InvokerCompras invoker = new InvokerCompras();
    private Compra compraSeleccionada;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(
                cf -> new SimpleStringProperty(cf.getValue().getIdCompra().toString().substring(0, 8)));
        colUsuario
                .setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getUsuario().getCorreoElectronico()));
        colFecha.setCellValueFactory(cf -> new SimpleStringProperty(DateUtil.format(cf.getValue().getFechaCreacion())));
        colEvento.setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getEvento().getNombre()));
        colTotal.setCellValueFactory(cf -> new SimpleStringProperty(String.format("$%.2f", cf.getValue().getTotal())));
        colEstado.setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getEstadoCompra().name()));

        tvCompras.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            compraSeleccionada = newV;
            btnCancelar.setDisable(newV == null || newV.getEstadoCompra() == EstadoCompra.CANCELADA);
        });

        cargarCompras();
    }

    private void cargarCompras() {
        tvCompras.setItems(FXCollections.observableArrayList(Main.compraRepo.findAll()));
        btnCancelar.setDisable(true);
    }

    @FXML
    public void onCancelarCompra() {
        if (compraSeleccionada == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que desea cancelar la compra "
                + compraSeleccionada.getIdCompra().toString().substring(0, 8) + "?");
        alert.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                
                ComandoCancelarCompra cmd = new ComandoCancelarCompra(Main.compraService,
                        compraSeleccionada.getIdCompra());
                invoker.ejecutarComando(cmd);

                lblMensaje.setText("Compra cancelada.");
                btnDeshacer.setDisable(false);
                cargarCompras();
            }
        });
    }

    @FXML
    public void onDeshacerUltimaAccion() {
        invoker.deshacerUltimo();
        lblMensaje.setText("Última acción deshecha.");
        btnDeshacer.setDisable(true);
        cargarCompras();
    }
}
