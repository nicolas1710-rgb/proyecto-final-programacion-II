package com.ticketflow.controller;

import com.ticketflow.model.Asiento;
import com.ticketflow.model.Zona;
import com.ticketflow.enums.EstadoAsiento;
import com.ticketflow.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MapaAsientosController {
    public static Zona zonaActual;

    @FXML
    private Label lblZonaInfo;
    @FXML
    private GridPane gridAsientos;

    private final List<Asiento> asientosSeleccionados = new ArrayList<>();

    @FXML
    public void initialize() {
        if (zonaActual == null)
            return;
        lblZonaInfo.setText("Zona: " + zonaActual.getNombre() + " - Precio: $" + zonaActual.getPrecioBase());
        cargarMapa();
    }

    private void cargarMapa() {
        gridAsientos.getChildren().clear();
        int row = 0;
        int col = 0;
        String filaActual = "";

        for (Asiento asiento : zonaActual.getAsientos()) {
            if (!asiento.getFila().equals(filaActual)) {
                filaActual = asiento.getFila();
                row++;
                col = 0;
                
                Label lblFila = new Label(filaActual);
                lblFila.setStyle("-fx-text-fill: white; -fx-padding: 5;");
                gridAsientos.add(lblFila, col++, row);
            }

            Button btnAsiento = new Button(String.valueOf(asiento.getNumero()));
            btnAsiento.getStyleClass().add("asiento-btn");

            
            switch (asiento.getEstado()) {
                case DISPONIBLE:
                    btnAsiento.getStyleClass().add("asiento-disponible");
                    btnAsiento.setOnAction(e -> toggleAsiento(asiento, btnAsiento));
                    break;
                case RESERVADO:
                case PENDIENTE_PAGO:
                    btnAsiento.getStyleClass().add("asiento-reservado");
                    btnAsiento.setDisable(true);
                    break;
                case VENDIDO:
                    btnAsiento.getStyleClass().add("asiento-vendido");
                    btnAsiento.setDisable(true);
                    break;
                case BLOQUEADO:
                case MANTENIMIENTO:
                    btnAsiento.getStyleClass().add("asiento-bloqueado");
                    btnAsiento.setDisable(true);
                    break;
            }

            gridAsientos.add(btnAsiento, col++, row);
        }
    }

    private void toggleAsiento(Asiento asiento, Button btn) {
        if (asientosSeleccionados.contains(asiento)) {
            asientosSeleccionados.remove(asiento);
            btn.setStyle(""); 
        } else {
            asientosSeleccionados.add(asiento);
            btn.setStyle("-fx-border-color: white; -fx-border-width: 2;");
        }
    }

    @FXML
    public void onConfirmarSeleccion() {
        if (asientosSeleccionados.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Debe seleccionar al menos un asiento.").show();
            return;
        }

        CarritoCompraController.asientosSeleccionados = new ArrayList<>(this.asientosSeleccionados);
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/CarritoCompraView.fxml",
                "TicketFlow - Carrito");
    }

    @FXML
    public void onVolver() {
        NavigationManager.getInstance().goBack();
    }
}
