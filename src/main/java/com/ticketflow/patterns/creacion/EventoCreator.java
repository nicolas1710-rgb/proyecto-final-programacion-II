package com.ticketflow.patterns.creacion;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;















public interface EventoCreator {
    Evento crearEvento(String nombre, String descripcion, String ciudad, java.time.LocalDateTime fechaHora,
            Recinto recinto);
}
