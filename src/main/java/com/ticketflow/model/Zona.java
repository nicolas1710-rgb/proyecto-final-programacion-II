package com.ticketflow.model;

import com.ticketflow.enums.TipoZona;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




public class Zona {
    private UUID idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;
    private TipoZona tipoZona;
    private List<Asiento> asientos;

    public Zona() {
        this.idZona = UUID.randomUUID();
        this.asientos = new ArrayList<>();
    }

    public Zona(String nombre, int capacidad, double precioBase, TipoZona tipoZona) {
        this();
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.tipoZona = tipoZona;
    }

    public UUID getIdZona() {
        return idZona;
    }

    public void setIdZona(UUID idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public TipoZona getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(TipoZona tipoZona) {
        this.tipoZona = tipoZona;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    @Override
    public String toString() {
        return "Zona{nombre='" + nombre + "', tipo=" + tipoZona + ", capacidad=" + capacidad + ", precio=" + precioBase
                + "}";
    }
}
