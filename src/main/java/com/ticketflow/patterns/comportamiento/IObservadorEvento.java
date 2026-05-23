package com.ticketflow.patterns.comportamiento;

/**
 * PATRÓN OBSERVER — NotificadorEventos
 *
 * PROBLEMA: Cuando un evento cambia de estado (cancelado, pausado) o una compra
 * cambia de estado, los usuarios afectados deben ser notificados. Acoplar esta
 * lógica al servicio viola SRP y OCP.
 *
 * PROPÓSITO: Definir una dependencia uno-a-muchos entre objetos de forma que
 * cuando un objeto cambia de estado, todos sus dependientes son notificados
 * automáticamente.
 *
 * SOLUCIÓN: IObservadorEvento define el contrato del observador. EventoSubject
 * mantiene la lista de observadores y notifica cuando varía el estado.
 */
public interface IObservadorEvento {
    void actualizar(String mensaje, Object entidad);
}
