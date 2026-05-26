package com.ticketflow.patterns.comportamiento;

import com.ticketflow.service.ICompraService;
import java.util.UUID;





public class ComandoCancelarCompra implements IComando {
    private final ICompraService compraService;
    private final UUID idCompra;
    private boolean ejecutado;

    public ComandoCancelarCompra(ICompraService compraService, UUID idCompra) {
        this.compraService = compraService;
        this.idCompra = idCompra;
        this.ejecutado = false;
    }

    @Override
    public void ejecutar() {
        if (!ejecutado) {
            boolean exito = compraService.cancelarCompra(idCompra);
            if (exito) {
                System.out.println("[Comando] Cancelación ejecutada para compra: " + idCompra);
                ejecutado = true;
            } else {
                System.out.println("[Comando] No se pudo cancelar la compra.");
            }
        }
    }

    @Override
    public void deshacer() {
        if (ejecutado) {
            
            
            System.out.println("[Comando] Operación no soportada todavía: Deshacer cancelación.");
            ejecutado = false;
        }
    }
}
