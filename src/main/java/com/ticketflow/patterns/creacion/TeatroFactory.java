package com.ticketflow.patterns.creacion;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import java.time.LocalDateTime;


public class TeatroFactory implements EventoCreator {
    @Override
    public Evento crearEvento(String nombre, String descripcion, String ciudad, LocalDateTime fechaHora,
            Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.TEATRO, descripcion, ciudad, fechaHora, recinto);
        evento.setPoliticaCancelacion("No se admiten cancelaciones las 24h previas.");
        evento.setPoliticaReembolso("Reembolso del 100% si se cancela con más de 10 días de antelación.");
        return evento;
    }
}
