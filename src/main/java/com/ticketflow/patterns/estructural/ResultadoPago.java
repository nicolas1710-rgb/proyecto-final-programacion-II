package com.ticketflow.patterns.estructural;


public class ResultadoPago {
    private final boolean aprobado;
    private final String mensaje;
    private final String codigoTransaccion;

    public ResultadoPago(boolean aprobado, String mensaje, String codigoTransaccion) {
        this.aprobado = aprobado;
        this.mensaje = mensaje;
        this.codigoTransaccion = codigoTransaccion;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getCodigoTransaccion() {
        return codigoTransaccion;
    }
}
