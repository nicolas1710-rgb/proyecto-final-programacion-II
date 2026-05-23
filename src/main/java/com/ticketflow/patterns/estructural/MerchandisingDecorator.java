package com.ticketflow.patterns.estructural;

/** Decorador Merchandising: agrega el costo de merchandising. */
public class MerchandisingDecorator extends ServicioAdicionalDecorator {
    private static final double PRECIO = 25000.0;

    public MerchandisingDecorator(TicketComponent componente) {
        super(componente);
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() + PRECIO;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " + Merchandising ($25,000)";
    }
}
