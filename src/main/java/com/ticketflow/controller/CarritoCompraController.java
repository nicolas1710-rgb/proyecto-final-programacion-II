package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.*;
import com.ticketflow.enums.TipoServicio;
import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.patterns.estructural.MerchandisingDecorator;
import com.ticketflow.patterns.estructural.ParqueaderoDecorator;
import com.ticketflow.patterns.estructural.SeguroDecorator;
import com.ticketflow.patterns.estructural.VIPDecorator;
import com.ticketflow.patterns.estructural.TicketComponent;
import com.ticketflow.patterns.estructural.EntradaBase;
import com.ticketflow.util.NavigationManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

public class CarritoCompraController {
    public static Evento eventoActual;
    public static Zona zonaSeleccionada;
    public static List<Asiento> asientosSeleccionados; // Si es null, es zona sin asientos

    @FXML
    private Label lblEventoDetalle;
    @FXML
    private Label lblTotal;
    @FXML
    private CheckBox chkSeguro;
    @FXML
    private CheckBox chkVip;
    @FXML
    private CheckBox chkMerch;
    @FXML
    private CheckBox chkParqueadero;

    private List<ItemCompra> itemsParaCompra = new ArrayList<>();
    private List<ServicioAdicional> serviciosElegidos = new ArrayList<>();
    private double totalCalculado = 0;

    @FXML
    public void initialize() {
        if (eventoActual == null || zonaSeleccionada == null)
            return;

        lblEventoDetalle.setText("Evento: " + eventoActual.getNombre() +
                "\nZona: " + zonaSeleccionada.getNombre() +
                "\nAsientos seleccionados: "
                + (asientosSeleccionados != null ? asientosSeleccionados.size() : "1 Configurado"));

        prepararItems();
        calcularTotal();
    }

    private void prepararItems() {
        itemsParaCompra.clear();
        if (asientosSeleccionados != null && !asientosSeleccionados.isEmpty()) {
            for (Asiento a : asientosSeleccionados) {
                itemsParaCompra.add(new ItemCompra(zonaSeleccionada, a, zonaSeleccionada.getPrecioBase(), 1));
            }
        } else {
            // Zona general, 1 ticket por defecto. Para más, se necesitaría un input de
            // cantidad (Sipmlificado aquí)
            itemsParaCompra.add(new ItemCompra(zonaSeleccionada, null, zonaSeleccionada.getPrecioBase(), 1));
        }
    }

    @FXML
    public void onServicioCambiado() {
        serviciosElegidos.clear();
        if (chkSeguro.isSelected())
            serviciosElegidos.add(new ServicioAdicional(TipoServicio.SEGURO, "Seguro de Cancelación", 15000));
        if (chkVip.isSelected())
            serviciosElegidos.add(new ServicioAdicional(TipoServicio.VIP, "Upgrade VIP", 50000));
        if (chkMerch.isSelected())
            serviciosElegidos.add(new ServicioAdicional(TipoServicio.MERCHANDISING, "Paquete Merchandising", 25000));
        if (chkParqueadero.isSelected())
            serviciosElegidos.add(new ServicioAdicional(TipoServicio.PARQUEADERO, "Parqueadero", 20000));
        calcularTotal();
    }

    private void calcularTotal() {
        // En un caso real usaríamos el CompraBuilder, pero aquí simulamos el total para
        // la UI
        totalCalculado = 0;
        for (ItemCompra item : itemsParaCompra) {
            Entrada simulada = new Entrada(item.getZona(), item.getAsiento(), item.getPrecioUnitario());
            TicketComponent tc = new EntradaBase(simulada);

            if (chkVip.isSelected())
                tc = new VIPDecorator(tc);
            if (chkSeguro.isSelected())
                tc = new SeguroDecorator(tc);
            if (chkMerch.isSelected())
                tc = new MerchandisingDecorator(tc);
            if (chkParqueadero.isSelected())
                tc = new ParqueaderoDecorator(tc);

            totalCalculado += tc.getPrecio();
        }
        lblTotal.setText("Total: $" + String.format("%.2f", totalCalculado));
    }

    @FXML
    public void onProcederPago() {
        Usuario u = GestorSesion.getInstance().getUsuarioActual();
        if (u == null) {
            new Alert(Alert.AlertType.ERROR, "Debe iniciar sesión.").show();
            return;
        }

        try {
            // Se usa el service (que podría usar CompraBuilder / Facade por dentro)
            Compra c = Main.compraService.crearCompra(u.getIdUsuario(), eventoActual.getIdEvento(), itemsParaCompra);

            if (!serviciosElegidos.isEmpty()) {
                Main.compraService.modificarCompra(c.getIdCompra(), itemsParaCompra, serviciosElegidos);
                c = Main.compraService.obtenerDetalle(c.getIdCompra());
            }

            PagoController.compraActual = c;
            NavigationManager.getInstance().navigateTo("/com/ticketflow/view/PagoView.fxml", "TicketFlow - Pago");
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    public void onCancelar() {
        NavigationManager.getInstance().goBack();
    }
}
