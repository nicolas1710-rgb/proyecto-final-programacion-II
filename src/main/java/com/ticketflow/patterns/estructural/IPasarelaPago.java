package com.ticketflow.patterns.estructural;

import com.ticketflow.model.Pago;
import com.ticketflow.enums.EstadoPago;













public interface IPasarelaPago {
    ResultadoPago procesar(Pago pago);
}
