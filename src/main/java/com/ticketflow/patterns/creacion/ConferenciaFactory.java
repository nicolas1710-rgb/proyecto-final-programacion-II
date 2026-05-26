package com.ticketflow.patterns.creacion;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import java.time.LocalDateTime;


public class ConferenciaFactory implements EventoCreator {
    @Override
    public Evento crearEvento(String nombre, String descripcion, String ciudad, LocalDateTime fechaHora,
            Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.CONFERENCIA, descripcion, ciudad, fechaHora, recinto);
        evento.setPoliticaCancelacion("Posible cancelación hasta 72h antes del evento.");
        evento.setPoliticaReembolso("Reembolso del 100% en caso de cancelación del organizador.");
        return evento;
    }
}
