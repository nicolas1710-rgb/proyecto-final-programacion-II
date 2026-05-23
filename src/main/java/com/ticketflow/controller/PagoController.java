package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Compra;
import com.ticketflow.model.MetodoPago;
import com.ticketflow.model.Pago;
import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.patterns.estructural.*;
import com.ticketflow.util.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PagoController {
    public static Compra compraActual;

    @FXML
    private Label lblTotal;
    @FXML
    private ComboBox<MetodoPago> cmbMetodoPago;
    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        if (compraActual == null)
            return;

        lblTotal.setText("Total a Pagar: $" + String.format("%.2f", compraActual.getTotal()));

        // Cargar métodos de pago del usuario
        cmbMetodoPago.setItems(
                FXCollections.observableArrayList(GestorSesion.getInstance().getUsuarioActual().getMetodosDePago()));
    }

    @FXML
    public void onPagar() {
        MetodoPago metodo = cmbMetodoPago.getValue();
        if (metodo == null) {
            lblMensaje.setText("Seleccione un método de pago.");
            return;
        }

        try {
            // Confirmar pago usando el servicio (que usa el Strategy internamente y el
            // adapter)
            // Para la demo, lo pasaremos como está, Main.compraService.confirmarPago lo
            // hace
            Pago p = Main.compraService.confirmarPago(compraActual.getIdCompra(), metodo);

            // Generar entradas (Facade simplificado en realidad, o paso manual)
            Main.entradaService.generarEntradas(compraActual);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Pago aprobado. Entradas generadas.");
            alert.showAndWait();

            NavigationManager.getInstance().navigateTo("/com/ticketflow/view/MisComprasView.fxml",
                    "TicketFlow - Mis Compras");
        } catch (Exception e) {
            lblMensaje.setText("Error procesando pago: " + e.getMessage());
        }
    }

    @FXML
    public void onCancelar() {
        NavigationManager.getInstance().goBack();
    }
}
