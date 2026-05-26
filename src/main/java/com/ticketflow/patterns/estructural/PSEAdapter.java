package com.ticketflow.patterns.estructural;

import com.ticketflow.model.Pago;
import com.ticketflow.enums.EstadoPago;
import java.util.UUID;


public class PSEAdapter implements IPasarelaPago {
    @Override
    public ResultadoPago procesar(Pago pago) {
        System.out.println("[PSEAdapter] Procesando pago PSE por $" + pago.getMonto());
        
        boolean aprobado = Math.random() > 0.05;
        pago.setEstadoPago(aprobado ? EstadoPago.APROBADO : EstadoPago.RECHAZADO);
        String codigo = "PSE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new ResultadoPago(aprobado, aprobado ? "Pago PSE aprobado" : "Pago PSE rechazado por el banco", codigo);
    }
}
