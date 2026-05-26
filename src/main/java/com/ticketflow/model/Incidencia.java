package com.ticketflow.model;

import com.ticketflow.enums.TipoIncidencia;
import java.time.LocalDateTime;
import java.util.UUID;




public class Incidencia {
    private UUID idIncidencia;
    private Usuario usuario;
    private TipoIncidencia tipo;
    private String descripcion;
    private LocalDateTime fecha;
    private String entidadAfectadaTipo;
    private String entidadAfectadaId;
    private boolean resuelta;
    private LocalDateTime fechaResolucion;

    public Incidencia() {
        this.idIncidencia = UUID.randomUUID();
        this.fecha = LocalDateTime.now();
    }

    public Incidencia(TipoIncidencia tipo, String descripcion, String entidadAfectadaTipo, String entidadAfectadaId) {
        this();
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.entidadAfectadaTipo = entidadAfectadaTipo;
        this.entidadAfectadaId = entidadAfectadaId;
    }

    public Incidencia(Usuario usuario, Object entidad, TipoIncidencia tipo, String descripcion) {
        this();
        this.usuario = usuario;
        this.tipo = tipo;
        this.descripcion = descripcion;
        if (entidad != null) {
            this.entidadAfectadaTipo = entidad.getClass().getSimpleName();
            this.entidadAfectadaId = entidad.toString();
        }
    }

    public UUID getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(UUID idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public TipoIncidencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoIncidencia tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEntidadAfectadaTipo() {
        return entidadAfectadaTipo;
    }

    public void setEntidadAfectadaTipo(String entidadAfectadaTipo) {
        this.entidadAfectadaTipo = entidadAfectadaTipo;
    }

    public String getEntidadAfectadaId() {
        return entidadAfectadaId;
    }

    public void setEntidadAfectadaId(String entidadAfectadaId) {
        this.entidadAfectadaId = entidadAfectadaId;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }

    public LocalDateTime getFechaCreacion() {
        return fecha;
    }

    public void setFechaResolucion(LocalDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Incidencia{tipo=" + tipo + ", descripcion='" + descripcion + "', fecha=" + fecha + "}";
    }
}
