package com.ticketflow.model;

import com.ticketflow.enums.TipoServicio;
import java.util.UUID;




public class ServicioAdicional {
    private UUID idServicio;
    private TipoServicio tipo;
    private String descripcion;
    private double precio;

    public ServicioAdicional() {
        this.idServicio = UUID.randomUUID();
    }

    public ServicioAdicional(TipoServicio tipo, String descripcion, double precio) {
        this();
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public UUID getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(UUID idServicio) {
        this.idServicio = idServicio;
    }

    public TipoServicio getTipo() {
        return tipo;
    }

    public void setTipo(TipoServicio tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ServicioAdicional{tipo=" + tipo + ", precio=" + precio + "}";
    }
}
