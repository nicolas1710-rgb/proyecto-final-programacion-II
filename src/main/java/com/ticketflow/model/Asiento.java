package com.ticketflow.model;

import com.ticketflow.enums.EstadoAsiento;
import java.util.UUID;




public class Asiento {
    private UUID idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estadoAsiento;
    private Zona zona;

    public Asiento() {
        this.idAsiento = UUID.randomUUID();
        this.estadoAsiento = EstadoAsiento.DISPONIBLE;
    }

    public Asiento(String fila, int numero, Zona zona) {
        this();
        this.fila = fila;
        this.numero = numero;
        this.zona = zona;
    }

    public UUID getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(UUID idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public EstadoAsiento getEstadoAsiento() {
        return estadoAsiento;
    }

    public void setEstadoAsiento(EstadoAsiento estadoAsiento) {
        this.estadoAsiento = estadoAsiento;
    }

    public EstadoAsiento getEstado() {
        return estadoAsiento;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        return "Asiento{fila=" + fila + ", num=" + numero + ", estado=" + estadoAsiento + "}";
    }
}
