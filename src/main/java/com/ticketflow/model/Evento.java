package com.ticketflow.model;

import com.ticketflow.enums.CategoriaEvento;
import com.ticketflow.enums.EstadoEvento;
import java.time.LocalDateTime;
import java.util.UUID;




public class Evento {
    private UUID idEvento;
    private String nombre;
    private CategoriaEvento categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fechaHora;
    private EstadoEvento estadoEvento;
    private String politicaCancelacion;
    private String politicaReembolso;
    private Recinto recinto;

    public Evento() {
        this.idEvento = UUID.randomUUID();
        this.estadoEvento = EstadoEvento.BORRADOR;
    }

    public Evento(String nombre, CategoriaEvento categoria, String descripcion, String ciudad,
            LocalDateTime fechaHora, Recinto recinto) {
        this();
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fechaHora = fechaHora;
        this.recinto = recinto;
    }

    public UUID getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(UUID idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(EstadoEvento estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public String getPoliticaCancelacion() {
        return politicaCancelacion;
    }

    public void setPoliticaCancelacion(String politicaCancelacion) {
        this.politicaCancelacion = politicaCancelacion;
    }

    public String getPoliticaReembolso() {
        return politicaReembolso;
    }

    public void setPoliticaReembolso(String politicaReembolso) {
        this.politicaReembolso = politicaReembolso;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    @Override
    public String toString() {
        return "Evento{nombre='" + nombre + "', categoria=" + categoria + ", estado=" + estadoEvento + ", ciudad='"
                + ciudad + "'}";
    }
}
