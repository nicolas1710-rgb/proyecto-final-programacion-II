package com.ticketflow.patterns.comportamiento;

/**
 * PATRÓN COMMAND — ComandoCompra
 *
 * PROBLEMA: Las acciones sobre compras (crear, cancelar, confirmar pago) deben
 * ser ejecutables y reversibles (undo), especialmente para el admin.
 *
 * PROPÓSITO: Encapsular solicitudes como objetos, permitiendo parametrizar
 * operaciones, encolar peticiones y soportar operaciones deshacibles.
 *
 * SOLUCIÓN: IComando con ejecutar() y deshacer(). Implementaciones concretas.
 * InvokerCompras mantiene historial de comandos ejecutados.
 */
public interface IComando {
    void ejecutar();

    void deshacer();
}
