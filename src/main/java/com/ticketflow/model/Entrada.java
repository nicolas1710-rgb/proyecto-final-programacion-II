package com.ticketflow.model;

import com.ticketflow.enums.EstadoEntrada;
import java.util.UUID;





public class Entrada {
    private UUID idEntrada;
    private Compra compra;
    private Zona zona;
    private Asiento asiento; 
    private double precioFinal;
    private EstadoEntrada estadoEntrada;
    private String codigoQR;

    public Entrada() {
        this.idEntrada = UUID.randomUUID();
        this.estadoEntrada = EstadoEntrada.ACTIVA;
        this.codigoQR = "QR-" + UUID.randomUUID().toString().toUpperCase();
    }

    public Entrada(Compra compra, Zona zona, Asiento asiento, double precioFinal) {
        this();
        this.compra = compra;
        this.zona = zona;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
    }

    public Entrada(Zona zona, Asiento asiento, double precioFinal) {
        this();
        this.zona = zona;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
    }

    public UUID getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(UUID idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public EstadoEntrada getEstadoEntrada() {
        return estadoEntrada;
    }

    public void setEstadoEntrada(EstadoEntrada estadoEntrada) {
        this.estadoEntrada = estadoEntrada;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    @Override
    public String toString() {
        return "Entrada{id=" + idEntrada + ", qr=" + codigoQR + ", zona=" +
                (zona != null ? zona.getNombre() : "N/A") + ", estado=" + estadoEntrada + "}";
    }
}
