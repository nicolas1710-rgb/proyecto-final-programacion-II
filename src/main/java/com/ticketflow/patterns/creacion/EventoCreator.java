package com.ticketflow.patterns.creacion;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;

/**
 * PATRÓN FACTORY METHOD — EventoFactory
 *
 * PROBLEMA: Crear eventos de distintos tipos (Concierto, Teatro, Conferencia)
 * con configuraciones por defecto distintas (políticas, zonas predefinidas).
 * El código cliente no debe conocer los detalles de construcción.
 *
 * PROPÓSITO: Delegar la creación del objeto a subclases o implementaciones
 * específicas, permitiendo extensión sin modificar el código cliente. (OCP)
 *
 * SOLUCIÓN: Interfaz EventoCreator con método crearEvento(), con
 * implementaciones
 * ConciertoFactory, TeatroFactory, ConferenciaFactory que configuran el evento.
 */
public interface EventoCreator {
    Evento crearEvento(String nombre, String descripcion, String ciudad, java.time.LocalDateTime fechaHora,
            Recinto recinto);
}
