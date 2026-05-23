package com.ticketflow.patterns.comportamiento;

/** Observador que imprime notificaciones en consola. */
public class NotificadorConsola implements IObservadorEvento {
    @Override
    public void actualizar(String mensaje, Object entidad) {
        System.out.println("[NOTIFICADOR] " + mensaje + " | Entidad: " +
                (entidad != null ? entidad.getClass().getSimpleName() : "N/A"));
    }
}
