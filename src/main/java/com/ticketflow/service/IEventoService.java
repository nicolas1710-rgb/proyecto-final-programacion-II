package com.ticketflow.service;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;





public interface IEventoService {
    Evento crear(Evento evento);

    void actualizar(Evento evento);

    void eliminar(UUID idEvento);

    void publicarEvento(UUID idEvento);

    void pausar(UUID idEvento);

    void cancelarEvento(UUID idEvento);

    void finalizar(UUID idEvento);

    List<Evento> buscarConFiltros(LocalDateTime fecha, String ciudad, CategoriaEvento categoria, Double precioMax);

    Map<Zona, Integer> obtenerDisponibilidad(UUID idEvento);

    List<Evento> listarTodos();

    Evento obtenerPorId(UUID idEvento);
}
