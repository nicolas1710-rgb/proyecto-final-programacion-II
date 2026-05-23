package com.ticketflow.patterns.comportamiento;

/**
 * PATRÓN STRATEGY — EstrategiaDescuento
 *
 * PROBLEMA: Distintos tipos de descuento según el rol del usuario o tipo de
 * evento.
 * Si la lógica de descuento está dentro de CompraService, se viola OCP al
 * agregar nuevos tipos.
 *
 * PROPÓSITO: Encapsular algoritmos de descuento en clases intercambiables.
 *
 * SOLUCIÓN: IEstrategiaDescuento define el contrato. Cada estrategia concreta
 * implementa el cálculo. CompraService recibe la estrategia por inyección
 * (DIP).
 */
public interface IEstrategiaDescuento {
    double aplicar(double precioOriginal);

    String getNombre();
}
