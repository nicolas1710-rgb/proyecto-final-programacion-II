package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Evento;
import com.ticketflow.enums.CategoriaEvento;
import com.ticketflow.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExplorarEventosController {
    @FXML
    private DatePicker dpFecha;
    @FXML
    private ComboBox<String> cmbCiudad;
    @FXML
    private ComboBox<CategoriaEvento> cmbCategoria;
    @FXML
    private TextField txtPrecioMax;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridEventos;

    private List<Evento> eventosActivos = new ArrayList<>();

    @FXML
    public void initialize() {
        
        cmbCategoria.setItems(FXCollections.observableArrayList(CategoriaEvento.values()));
        cmbCiudad.setItems(FXCollections.observableArrayList("Bogotá", "Medellín", "Cali"));

        
        scrollPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            reorganizarGrid(newVal.doubleValue());
        });

        cargarEventos();
    }

    @FXML
    public void onFiltrar() {
        cargarEventos();
    }

    @FXML
    public void onLimpiar() {
        dpFecha.setValue(null);
        cmbCiudad.setValue(null);
        cmbCategoria.setValue(null);
        txtPrecioMax.clear();
        cargarEventos();
    }

    private void cargarEventos() {
        LocalDateTime fecha = dpFecha.getValue() != null ? dpFecha.getValue().atStartOfDay() : null;
        String ciudad = cmbCiudad.getValue();
        CategoriaEvento categoria = cmbCategoria.getValue();
        Double precioMax = null;
        try {
            if (!txtPrecioMax.getText().isEmpty())
                precioMax = Double.parseDouble(txtPrecioMax.getText());
        } catch (NumberFormatException ignored) {
        }

        eventosActivos = Main.eventoService.buscarConFiltros(fecha, ciudad, categoria, precioMax);
        
        
        double currentWidth = scrollPane.getWidth();
        if (currentWidth <= 100) {
            currentWidth = 800; 
        }
        reorganizarGrid(currentWidth);
    }

    private void reorganizarGrid(double width) {
        if (gridEventos == null) return;
        
        gridEventos.getChildren().clear();
        
        if (eventosActivos == null || eventosActivos.isEmpty()) {
            return;
        }

        
        double cardWidth = 240.0;
        int columns = (int) Math.max(1, Math.floor((width - 20) / cardWidth));

        
        columns = Math.min(4, columns);

        int row = 0;
        int col = 0;

        for (Evento ev : eventosActivos) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ticketflow/view/EventoCardView.fxml"));
                VBox card = loader.load();

                EventoCardController controller = loader.getController();
                controller.setEvento(ev, () -> verDetalleEvento(ev));

                gridEventos.add(card, col, row);

                col++;
                if (col >= columns) {
                    col = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al cargar tarjeta del evento: " + ev.getNombre());
            }
        }
    }

    private void verDetalleEvento(Evento evento) {
        DetalleEventoController.eventoActual = evento;
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/DetalleEventoView.fxml",
                "TicketFlow - Detalle Evento");
    }
}
