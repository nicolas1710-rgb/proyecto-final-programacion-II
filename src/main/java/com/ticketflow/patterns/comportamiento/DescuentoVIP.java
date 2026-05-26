package com.ticketflow.patterns.comportamiento;


public class DescuentoVIP implements IEstrategiaDescuento {
    @Override
    public double aplicar(double p) {
        return p * 0.90;
    }

    @Override
    public String getNombre() {
        return "Descuento VIP 10%";
    }
}
