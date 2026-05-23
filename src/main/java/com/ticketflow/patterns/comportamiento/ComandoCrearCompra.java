package com.ticketflow.patterns.comportamiento;

import com.ticketflow.model.Compra;
import com.ticketflow.service.ICompraService;

/** Comando concreto para crear una compra. */
public class ComandoCrearCompra implements IComando {
    private final ICompraService compraService;
    private final Compra compra;
    private boolean ejecutado;

    public ComandoCrearCompra(ICompraService compraService, Compra compra) {
        this.compraService = compraService;
        this.compra = compra;
        this.ejecutado = false;
    }

    @Override
    public void ejecutar() {
        if (!ejecutado) {
            compraService.crearCompra(compra.getUsuario().getIdUsuario(), compra.getEvento().getIdEvento(),
                    compra.getItems());
            System.out.println("[Comando] Compra ejecutada: " + compra.getIdCompra());
            ejecutado = true;
        }
    }

    @Override
    public void deshacer() {
        if (ejecutado) {
            compraService.cancelarCompra(compra.getIdCompra());
            System.out.println("[Comando] Compra deshecha (cancelada): " + compra.getIdCompra());
            ejecutado = false;
        }
    }
}
