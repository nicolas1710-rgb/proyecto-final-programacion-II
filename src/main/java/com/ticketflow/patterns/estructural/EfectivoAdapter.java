package com.ticketflow.patterns.estructural;

import com.ticketflow.model.Pago;
import com.ticketflow.enums.EstadoPago;
import java.util.UUID;


public class EfectivoAdapter implements IPasarelaPago {
    @Override
    public ResultadoPago procesar(Pago pago) {
        System.out.println("[EfectivoAdapter] Registro de pago en efectivo por $" + pago.getMonto());
        pago.setEstadoPago(EstadoPago.APROBADO);
        String codigo = "CASH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return new ResultadoPago(true, "Pago en efectivo registrado", codigo);
    }
}
