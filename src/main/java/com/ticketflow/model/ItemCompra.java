package com.ticketflow.model;

import java.util.UUID;





public class ItemCompra {
    private UUID idItem;
    private Zona zona;
    private Asiento asiento; 
    private double precioUnitario;
    private int cantidad;

    public ItemCompra() {
        this.idItem = UUID.randomUUID();
    }

    public ItemCompra(Zona zona, Asiento asiento, double precioUnitario, int cantidad) {
        this();
        this.zona = zona;
        this.asiento = asiento;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public UUID getIdItem() {
        return idItem;
    }

    public void setIdItem(UUID idItem) {
        this.idItem = idItem;
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

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public String toString() {
        return "ItemCompra{zona=" + (zona != null ? zona.getNombre() : "N/A") +
                ", asiento=" + (asiento != null ? asiento.getFila() + asiento.getNumero() : "Libre") +
                ", precio=" + precioUnitario + ", cantidad=" + cantidad + "}";
    }
}
