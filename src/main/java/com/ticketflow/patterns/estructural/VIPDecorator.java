package com.ticketflow.patterns.estructural;

/** Decorador VIP: agrega el costo de upgrade VIP al precio base. */
public class VIPDecorator extends ServicioAdicionalDecorator {
    private static final double PRECIO_VIP = 50000.0;

    public VIPDecorator(TicketComponent componente) {
        super(componente);
    }

    @Override
    public double getPrecio() {
        return componente.getPrecio() + PRECIO_VIP;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " + VIP Upgrade ($50,000)";
    }
}
