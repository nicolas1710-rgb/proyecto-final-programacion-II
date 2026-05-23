package com.ticketflow.patterns.estructural;

/**
 * PATRÓN DECORATOR — ServicioAdicionalDecorator
 *
 * PROBLEMA: Agregar servicios adicionales (VIP, seguro, parking) a una entrada
 * base modificando el precio dinámicamente sin alterar la clase Entrada.
 *
 * PROPÓSITO: Añadir comportamiento a objetos de manera flexible y extensible,
 * sin modificar su clase original. Sigue el Principio OCP.
 *
 * SOLUCIÓN: Interfaz TicketComponent define getPrecio() y getDescripcion().
 * EntradaBase implementa la interfaz. Decoradores concretos envuelven el
 * componente
 * y suman su precio al calcular el total.
 */
public interface TicketComponent {
    double getPrecio();

    String getDescripcion();
}
