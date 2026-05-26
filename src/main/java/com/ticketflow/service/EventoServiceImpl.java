package com.ticketflow.service;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import com.ticketflow.repository.EventoRepository;
import com.ticketflow.repository.EntradaRepository;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;





public class EventoServiceImpl implements IEventoService {
    private final EventoRepository eventoRepository;
    private final EntradaRepository entradaRepository;

    public EventoServiceImpl(EventoRepository eventoRepository, EntradaRepository entradaRepository) {
        this.eventoRepository = eventoRepository;
        this.entradaRepository = entradaRepository;
    }

    @Override
    public Evento crear(Evento evento) {
        if (evento.getNombre() == null || evento.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del evento es obligatorio.");
        }
        eventoRepository.save(evento);
        System.out.println("[EventoService] Evento creado: " + evento.getNombre());
        return evento;
    }

    @Override
    public void actualizar(Evento evento) {
        eventoRepository.update(evento);
    }

    @Override
    public void eliminar(UUID idEvento) {
        eventoRepository.delete(idEvento);
    }

    @Override
    public void publicarEvento(UUID idEvento) {
        cambiarEstado(idEvento, EstadoEvento.PUBLICADO, EstadoEvento.BORRADOR, EstadoEvento.PAUSADO);
    }

    @Override
    public void pausar(UUID idEvento) {
        cambiarEstado(idEvento, EstadoEvento.PAUSADO, EstadoEvento.PUBLICADO);
    }

    @Override
    public void cancelarEvento(UUID idEvento) {
        cambiarEstado(idEvento, EstadoEvento.CANCELADO,
                EstadoEvento.BORRADOR, EstadoEvento.PUBLICADO, EstadoEvento.PAUSADO);
    }

    @Override
    public void finalizar(UUID idEvento) {
        cambiarEstado(idEvento, EstadoEvento.FINALIZADO, EstadoEvento.PUBLICADO);
    }

    private void cambiarEstado(UUID idEvento, EstadoEvento nuevoEstado, EstadoEvento... estadosPermitidos) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new EventoNoDisponibleException("Evento no encontrado: " + idEvento));
        boolean estadoPermitido = Arrays.asList(estadosPermitidos).contains(evento.getEstadoEvento());
        if (!estadoPermitido) {
            throw new EventoNoDisponibleException("No se puede cambiar al estado " + nuevoEstado +
                    " desde " + evento.getEstadoEvento());
        }
        evento.setEstadoEvento(nuevoEstado);
        eventoRepository.update(evento);
        System.out.println("[EventoService] Evento " + evento.getNombre() + " cambió a: " + nuevoEstado);
    }

    @Override
    public List<Evento> buscarConFiltros(LocalDateTime fecha, String ciudad, CategoriaEvento categoria,
            Double precioMax) {
        return eventoRepository.findWithFilters(fecha, ciudad, categoria, precioMax);
    }

    @Override
    public Map<Zona, Integer> obtenerDisponibilidad(UUID idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        if (evento == null || evento.getRecinto() == null)
            return new HashMap<>();

        Map<Zona, Integer> disponibilidad = new LinkedHashMap<>();
        List<Entrada> entradasEvento = entradaRepository.findByEvento(idEvento);

        for (Zona zona : evento.getRecinto().getZonas()) {
            long vendidos = entradasEvento.stream()
                    .filter(e -> e.getZona() != null && e.getZona().getIdZona().equals(zona.getIdZona()))
                    .filter(e -> e.getEstadoEntrada() == EstadoEntrada.ACTIVA
                            || e.getEstadoEntrada() == EstadoEntrada.USADA)
                    .count();
            int disponibles = zona.getCapacidad() - (int) vendidos;
            disponibilidad.put(zona, Math.max(0, disponibles));
        }
        return disponibilidad;
    }

    @Override
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    @Override
    public Evento obtenerPorId(UUID idEvento) {
        return eventoRepository.findById(idEvento).orElse(null);
    }
}
