package com.ticketflow.patterns.comportamiento;

import java.util.ArrayList;
import java.util.List;


public class EventoSubject {
    private final List<IObservadorEvento> observadores = new ArrayList<>();

    public void suscribir(IObservadorEvento observador) {
        observadores.add(observador);
    }

    public void desuscribir(IObservadorEvento observador) {
        observadores.remove(observador);
    }

    public void notificar(String mensaje, Object entidad) {
        for (IObservadorEvento obs : observadores) {
            obs.actualizar(mensaje, entidad);
        }
    }
}
