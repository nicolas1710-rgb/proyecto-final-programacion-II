package com.ticketflow.patterns.comportamiento;

/** Descuento 20% para compra anticipada (early bird). */
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
