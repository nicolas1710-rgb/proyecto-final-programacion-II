package com.ticketflow.patterns.estructural;

import com.ticketflow.model.*;
import com.ticketflow.enums.TipoMetodoPago;
import com.ticketflow.service.*;














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

    



    public CompraResultado realizarCompraCompleta(java.util.UUID idUsuario, java.util.UUID idEvento,
            java.util.List<ItemCompra> items,
            java.util.List<ServicioAdicional> servicios,
            MetodoPago metodoPago) {
        try {
            
            Compra compra = compraService.crearCompra(idUsuario, idEvento, items);

            
            if (servicios != null && !servicios.isEmpty()) {
                compraService.modificarCompra(compra.getIdCompra(), items, servicios);
                compra = compraService.obtenerDetalle(compra.getIdCompra());
            }

            
            Pago pago = compraService.confirmarPago(compra.getIdCompra(), metodoPago);

            
            java.util.List<Entrada> entradas = entradaService.generarEntradas(compra);

            System.out.println("[PlataformaFacade] Compra completa. Entradas generadas: " + entradas.size());
            return new CompraResultado(true, compra, entradas, "Compra realizada exitosamente");
        } catch (Exception e) {
            System.err.println("[PlataformaFacade] Error en compra: " + e.getMessage());
            return new CompraResultado(false, null, null, "Error: " + e.getMessage());
        }
    }

    
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
