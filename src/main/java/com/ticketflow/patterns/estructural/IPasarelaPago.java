package com.ticketflow.patterns.estructural;

import com.ticketflow.model.Pago;
import com.ticketflow.enums.EstadoPago;

/**
 * PATRÓN ADAPTER — PagoAdapterSimulado
 *
 * PROBLEMA: Distintas pasarelas de pago (PSE, Tarjeta, Efectivo) tienen
 * interfaces incompatibles entre sí. El sistema necesita interactuar con
 * todas ellas de manera uniforme.
 *
 * PROPÓSITO: Convertir una interfaz en otra que el cliente espera.
 *
 * SOLUCIÓN: Interfaz IPasarelaPago unifica el procesamiento. Adaptadores
 * concretos adaptan cada pasarela a esta interfaz común.
 */
public interface IPasarelaPago {
    ResultadoPago procesar(Pago pago);
}
