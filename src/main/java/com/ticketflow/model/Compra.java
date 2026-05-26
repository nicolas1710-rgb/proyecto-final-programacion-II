package com.ticketflow.model;

import com.ticketflow.enums.EstadoCompra;
import com.ticketflow.enums.TipoServicio;
import com.ticketflow.patterns.estructural.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;





public class Compra {
    private UUID idCompra;
    private Usuario usuario;
    private Evento evento;
    private LocalDateTime fechaCreacion;
    private double total;
    private EstadoCompra estadoCompra;
    private List<ItemCompra> items;
    private List<ServicioAdicional> serviciosAdicionales;
    private Pago pago;

    public Compra() {
        this.idCompra = UUID.randomUUID();
        this.fechaCreacion = LocalDateTime.now();
        this.estadoCompra = EstadoCompra.CREADA;
        this.items = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
    }

    public Compra(Usuario usuario, Evento evento) {
        this();
        this.usuario = usuario;
        this.evento = evento;
    }

    public void calcularTotal() {
        double totalCalculado = 0;
        boolean tieneVip = serviciosAdicionales.stream().anyMatch(s -> s.getTipo() == TipoServicio.VIP);
        boolean tieneSeguro = serviciosAdicionales.stream().anyMatch(s -> s.getTipo() == TipoServicio.SEGURO);
        boolean tieneMerch = serviciosAdicionales.stream().anyMatch(s -> s.getTipo() == TipoServicio.MERCHANDISING);
        boolean tieneParqueadero = serviciosAdicionales.stream().anyMatch(s -> s.getTipo() == TipoServicio.PARQUEADERO);

        for (ItemCompra item : items) {
            Entrada simulada = new Entrada(item.getZona(), item.getAsiento(), item.getPrecioUnitario());
            TicketComponent tc = new EntradaBase(simulada);

            if (tieneVip)
                tc = new VIPDecorator(tc);
            if (tieneSeguro)
                tc = new SeguroDecorator(tc);
            if (tieneMerch)
                tc = new MerchandisingDecorator(tc);
            if (tieneParqueadero)
                tc = new ParqueaderoDecorator(tc);

            totalCalculado += tc.getPrecio() * item.getCantidad();
        }
        this.total = totalCalculado;
    }

    public UUID getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(UUID idCompra) {
        this.idCompra = idCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoCompra getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(EstadoCompra estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public List<ItemCompra> getItems() {
        return items;
    }

    public void setItems(List<ItemCompra> items) {
        this.items = items;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(List<ServicioAdicional> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Compra{id=" + idCompra + ", usuario=" + (usuario != null ? usuario.getNombreCompleto() : "N/A") +
                ", evento=" + (evento != null ? evento.getNombre() : "N/A") +
                ", total=" + total + ", estado=" + estadoCompra + "}";
    }
}
