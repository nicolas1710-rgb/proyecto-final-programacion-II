package com.ticketflow.patterns.comportamiento;


public class DescuentoEarlyBird implements IEstrategiaDescuento {
    @Override
    public double aplicar(double p) {
        return p * 0.80;
    }

    @Override
    public String getNombre() {
        return "Descuento Early Bird 20%";
    }
}
