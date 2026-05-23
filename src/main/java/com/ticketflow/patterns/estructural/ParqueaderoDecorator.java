package com.ticketflow.patterns.estructural;

/** Decorador Parqueadero: agrega el costo de parqueadero. */
public class ParqueaderoDecorator extends ServicioAdicionalDecorator {
    private static final double PRECIO = 20000.0;

    public ParqueaderoDecorator(TicketComponent componente) {
        super(componente);
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() + PRECIO;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " + Parqueadero ($20,000)";
    }
}
