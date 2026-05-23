package com.ticketflow.patterns.estructural;

import com.ticketflow.model.Pago;
import com.ticketflow.enums.EstadoPago;
import java.util.UUID;

/** Adaptador para pasarela de Tarjeta Crédito/Débito. */
public class TarjetaAdapter implements IPasarelaPago {
    @Override
    public ResultadoPago procesar(Pago pago) {
        System.out.println("[TarjetaAdapter] Procesando pago con tarjeta por $" + pago.getMonto());
        boolean aprobado = Math.random() > 0.08;
        pago.setEstadoPago(aprobado ? EstadoPago.APROBADO : EstadoPago.RECHAZADO);
        String codigo = "CARD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new ResultadoPago(aprobado, aprobado ? "Tarjeta aprobada" : "Tarjeta rechazada - fondos insuficientes",
                codigo);
    }
}
