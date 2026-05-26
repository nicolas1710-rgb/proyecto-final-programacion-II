package com.ticketflow.patterns.estructural;

import com.ticketflow.model.Entrada;


public class EntradaBase implements TicketComponent {
    private final Entrada entrada;

    public EntradaBase(Entrada entrada) {
        this.entrada = entrada;
    }

    @Override
    public double getPrecio() {
        return entrada.getPrecioFinal();
    }

    @Override
    public String getDescripcion() {
        return "Entrada " + entrada.getZona().getNombre() + " (QR: " + entrada.getCodigoQR().substring(0, 12) + "...)";
    }
}
