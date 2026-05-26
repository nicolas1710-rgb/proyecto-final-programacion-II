package com.ticketflow.patterns.creacion;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import java.time.LocalDateTime;




public class ConciertoFactory implements EventoCreator {
    @Override
    public Evento crearEvento(String nombre, String descripcion, String ciudad, LocalDateTime fechaHora,
            Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.CONCIERTO, descripcion, ciudad, fechaHora, recinto);
        evento.setPoliticaCancelacion("Cancelación gratuita hasta 48h antes del evento.");
        evento.setPoliticaReembolso("Reembolso del 80% si se cancela antes de 7 días.");
        return evento;
    }
}
