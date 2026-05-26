package com.ticketflow.model;

import com.ticketflow.enums.TipoMetodoPago;
import java.util.UUID;




public class MetodoPago {
    private UUID idMetodoPago;
    private TipoMetodoPago tipo;
    private String numeroEnmascarado;
    private String titular;
    private boolean activo;

    public MetodoPago() {
        this.idMetodoPago = UUID.randomUUID();
        this.activo = true;
    }

    public MetodoPago(TipoMetodoPago tipo, String numeroEnmascarado, String titular) {
        this();
        this.tipo = tipo;
        this.numeroEnmascarado = numeroEnmascarado;
        this.titular = titular;
    }

    
    public UUID getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(UUID idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public TipoMetodoPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoMetodoPago tipo) {
        this.tipo = tipo;
    }

    public String getNumeroEnmascarado() {
        return numeroEnmascarado;
    }

    public void setNumeroEnmascarado(String numeroEnmascarado) {
        this.numeroEnmascarado = numeroEnmascarado;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDetalle() {
        return toString();
    }

    @Override
    public String toString() {
        return tipo.getDescripcion() + " - " + numeroEnmascarado + " (" + titular + ")";
    }
}
