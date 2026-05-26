package com.ticketflow.model;

import com.ticketflow.enums.EstadoPago;
import java.time.LocalDateTime;
import java.util.UUID;




public class Pago {
    private UUID idPago;
    private MetodoPago metodoPago;
    private double monto;
    private LocalDateTime fechaPago;
    private EstadoPago estadoPago;
    private String referencia;

    public Pago() {
        this.idPago = UUID.randomUUID();
        this.estadoPago = EstadoPago.PENDIENTE;
        this.fechaPago = LocalDateTime.now();
    }

    public Pago(MetodoPago metodoPago, double monto) {
        this();
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.referencia = "REF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public UUID getIdPago() {
        return idPago;
    }

    public void setIdPago(UUID idPago) {
        this.idPago = idPago;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return "Pago{ref=" + referencia + ", monto=" + monto + ", estado=" + estadoPago + "}";
    }
}
