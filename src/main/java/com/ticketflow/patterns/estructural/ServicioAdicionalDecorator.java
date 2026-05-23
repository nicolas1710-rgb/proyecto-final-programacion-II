package com.ticketflow.patterns.estructural;

/** Decorador abstracto base que implementa TicketComponent. */
public abstract class ServicioAdicionalDecorator implements TicketComponent {
    protected final TicketComponent componente;

    protected ServicioAdicionalDecorator(TicketComponent componente) {
        this.componente = componente;
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio();
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion();
    }
}
