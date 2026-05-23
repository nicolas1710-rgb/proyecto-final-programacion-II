package com.ticketflow.patterns.estructural;

import com.ticketflow.model.*;
import com.ticketflow.enums.TipoMetodoPago;
import com.ticketflow.service.*;

/**
 * PATRÓN FACADE — PlataformaFacade
 *
 * PROBLEMA: Un controlador JavaFX necesita coordinar múltiples servicios
 * (CompraService + EntradaService + PagoService + IncidenciaService) para
 * completar una compra. Esta orquestación crea acoplamiento en el controlador.
 *
 * PROPÓSITO: Proveer una interfaz simplificada a un conjunto complejo de
 * subsistemas, reduciendo el acoplamiento con el código cliente.
 *
 * SOLUCIÓN: PlataformaFacade expone métodos de alto nivel que internamente
 * coordinan todos los servicios necesarios.
 */
public class PlataformaFacade {
    private final ICompraService compraService;
    private final IEntradaService entradaService;
    private final IIncidenciaService incidenciaService;

    public PlataformaFacade(ICompraService compraService,
            IEntradaService entradaService,
            IIncidenciaService incidenciaService) {
        this.compraService = compraService;
        this.entradaService = entradaService;
        this.incidenciaService = incidenciaService;
    }

    /**
     * Orquesta el flujo completo de compra: crear, confirmar pago, generar
     * entradas.
     */
    public CompraResultado realizarCompraCompleta(java.util.UUID idUsuario, java.util.UUID idEvento,
            java.util.List<ItemCompra> items,
            java.util.List<ServicioAdicional> servicios,
            MetodoPago metodoPago) {
        try {
            // 1. Crear compra
            Compra compra = compraService.crearCompra(idUsuario, idEvento, items);

            // 2. Agregar servicios adicionales si hay
            if (servicios != null && !servicios.isEmpty()) {
                compraService.modificarCompra(compra.getIdCompra(), items, servicios);
                compra = compraService.obtenerDetalle(compra.getIdCompra());
            }

            // 3. Confirmar pago
            Pago pago = compraService.confirmarPago(compra.getIdCompra(), metodoPago);

            // 4. Generar entradas
            java.util.List<Entrada> entradas = entradaService.generarEntradas(compra);

            System.out.println("[PlataformaFacade] Compra completa. Entradas generadas: " + entradas.size());
            return new CompraResultado(true, compra, entradas, "Compra realizada exitosamente");
        } catch (Exception e) {
            System.err.println("[PlataformaFacade] Error en compra: " + e.getMessage());
            return new CompraResultado(false, null, null, "Error: " + e.getMessage());
        }
    }

    /** DTO de resultado del flujo de compra. */
    public static class CompraResultado {
        public final boolean exitoso;
        public final Compra compra;
        public final java.util.List<Entrada> entradas;
        public final String mensaje;

        public CompraResultado(boolean exitoso, Compra compra, java.util.List<Entrada> entradas, String mensaje) {
            this.exitoso = exitoso;
            this.compra = compra;
            this.entradas = entradas;
            this.mensaje = mensaje;
        }
    }
}
