package com.ticketflow.patterns.comportamiento;


public class SinDescuento implements IEstrategiaDescuento {
    @Override
    public double aplicar(double precioOriginal) {
        return precioOriginal;
    }

    @Override
    public String getNombre() {
        return "Sin descuento";
    }
}
