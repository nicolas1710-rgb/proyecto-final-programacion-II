package com.ticketflow.patterns.estructural;


public class SeguroDecorator extends ServicioAdicionalDecorator {
    private static final double PRECIO_SEGURO = 15000.0;

    public SeguroDecorator(TicketComponent componente) {
        super(componente);
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() + PRECIO_SEGURO;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " + Seguro Cancelación ($15,000)";
    }
}
