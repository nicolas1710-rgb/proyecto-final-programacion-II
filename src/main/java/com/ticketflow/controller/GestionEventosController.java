package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Evento;
import com.ticketflow.model.Recinto;
import com.ticketflow.enums.CategoriaEvento;
import com.ticketflow.enums.EstadoEvento;
import com.ticketflow.patterns.creacion.ConciertoFactory;
import com.ticketflow.patterns.creacion.ConferenciaFactory;
import com.ticketflow.patterns.creacion.EventoCreator;
import com.ticketflow.patterns.creacion.TeatroFactory;
import com.ticketflow.util.DateUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;

public class GestionEventosController {
    @FXML
    private TableView<Evento> tvEventos;
    @FXML
    private TableColumn<Evento, String> colNombre;
    @FXML
    private TableColumn<Evento, String> colFecha;
    @FXML
    private TableColumn<Evento, String> colEstado;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private TextField txtHora; // formto HH:mm
    @FXML
    private ComboBox<CategoriaEvento> cmbCategoria;
    @FXML
    private ComboBox<Recinto> cmbRecinto;
    @FXML
    private ComboBox<EstadoEvento> cmbEstado;
    @FXML
    private Label lblMensaje;

    private Evento eventoSeleccionado;

    @FXML
    public void initialize() {
        cmbCategoria.setItems(FXCollections.observableArrayList(CategoriaEvento.values()));
        cmbEstado.setItems(FXCollections.observableArrayList(EstadoEvento.values()));
        cmbRecinto.setItems(FXCollections.observableArrayList(Main.recintoService.listarRecintos()));

        colNombre.setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getNombre()));
        colFecha.setCellValueFactory(cf -> new SimpleStringProperty(DateUtil.format(cf.getValue().getFechaHora())));
        colEstado.setCellValueFactory(cf -> new SimpleStringProperty(cf.getValue().getEstadoEvento().name()));

        tvEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null)
                seleccionarEvento(newV);
        });

        cargarEventos();
    }

    private void cargarEventos() {
        tvEventos.setItems(FXCollections.observableArrayList(Main.eventoRepo.findAll()));
    }

    private void seleccionarEvento(Evento e) {
        eventoSeleccionado = e;
        txtNombre.setText(e.getNombre());
        txtDescripcion.setText(e.getDescripcion());
        dpFecha.setValue(e.getFechaHora().toLocalDate());
        txtHora.setText(String.format("%02d:%02d", e.getFechaHora().getHour(), e.getFechaHora().getMinute()));
        cmbCategoria.setValue(e.getCategoria());
        cmbRecinto.setValue(e.getRecinto());
        cmbEstado.setValue(e.getEstadoEvento());
    }

    @FXML
    public void onGuardar() {
        try {
            String nombre = txtNombre.getText().trim();
            String desc = txtDescripcion.getText().trim();
            LocalDateTime fh = dpFecha.getValue().atTime(Integer.parseInt(txtHora.getText().split(":")[0]),
                    Integer.parseInt(txtHora.getText().split(":")[1]));
            CategoriaEvento cat = cmbCategoria.getValue();
            Recinto r = cmbRecinto.getValue();

            if (eventoSeleccionado == null) {
                // Nuevo evento - Uso del Factory Method
                EventoCreator factory;
                switch (cat) {
                    case CONCIERTO:
                        factory = new ConciertoFactory();
                        break;
                    case TEATRO:
                        factory = new TeatroFactory();
                        break;
                    case CONFERENCIA:
                        factory = new ConferenciaFactory();
                        break;
                    default:
                        factory = new ConciertoFactory();
                        break; // Simplificación
                }
                Evento nuevo = factory.crearEvento(nombre, desc, r.getCiudad(), fh, r);
                nuevo.setEstadoEvento(cmbEstado.getValue() != null ? cmbEstado.getValue() : EstadoEvento.BORRADOR);
                Main.eventoRepo.save(nuevo);
                lblMensaje.setText("Evento creado.");
            } else {
                // Modificar existente
                eventoSeleccionado.setNombre(nombre);
                eventoSeleccionado.setDescripcion(desc);
                eventoSeleccionado.setFechaHora(fh);
                eventoSeleccionado.setCategoria(cat);
                eventoSeleccionado.setRecinto(r);
                if (cmbEstado.getValue() != null && cmbEstado.getValue() != eventoSeleccionado.getEstadoEvento()) {
                    if (cmbEstado.getValue() == EstadoEvento.CANCELADO) {
                        Main.eventoService.cancelarEvento(eventoSeleccionado.getIdEvento());
                    } else if (cmbEstado.getValue() == EstadoEvento.PUBLICADO) {
                        Main.eventoService.publicarEvento(eventoSeleccionado.getIdEvento());
                    } else {
                        eventoSeleccionado.setEstadoEvento(cmbEstado.getValue());
                        Main.eventoRepo.update(eventoSeleccionado);
                    }
                } else {
                    Main.eventoRepo.update(eventoSeleccionado);
                }
                lblMensaje.setText("Evento actualizado.");
            }
            cargarEventos();
            onLimpiar();
        } catch (Exception e) {
            lblMensaje.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onLimpiar() {
        eventoSeleccionado = null;
        txtNombre.clear();
        txtDescripcion.clear();
        dpFecha.setValue(null);
        txtHora.clear();
        cmbCategoria.setValue(null);
        cmbRecinto.setValue(null);
        cmbEstado.setValue(null);
        tvEventos.getSelectionModel().clearSelection();
    }
}
