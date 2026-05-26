package com.ticketflow.repository;

import com.ticketflow.model.Evento;
import com.ticketflow.enums.EstadoEvento;
import com.ticketflow.enums.CategoriaEvento;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;




public class EventoRepository implements CrudRepository<Evento, UUID> {
    private final Map<UUID, Evento> store = new HashMap<>();

    @Override
    public void save(Evento entity) {
        store.put(entity.getIdEvento(), entity);
    }

    @Override
    public Optional<Evento> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Evento> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Evento entity) {
        store.put(entity.getIdEvento(), entity);
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }

    public List<Evento> findByEstado(EstadoEvento estado) {
        return store.values().stream()
                .filter(e -> e.getEstadoEvento() == estado)
                .collect(Collectors.toList());
    }

    public List<Evento> findByCiudad(String ciudad) {
        return store.values().stream()
                .filter(e -> e.getCiudad().equalsIgnoreCase(ciudad))
                .collect(Collectors.toList());
    }

    public List<Evento> findByCategoria(CategoriaEvento categoria) {
        return store.values().stream()
                .filter(e -> e.getCategoria() == categoria)
                .collect(Collectors.toList());
    }

    public List<Evento> findWithFilters(LocalDateTime fecha, String ciudad, CategoriaEvento categoria,
            Double precioMax) {
        return store.values().stream()
                .filter(e -> e.getEstadoEvento() == EstadoEvento.PUBLICADO)
                .filter(e -> fecha == null || !e.getFechaHora().isBefore(fecha))
                .filter(e -> ciudad == null || ciudad.isBlank() || e.getCiudad().equalsIgnoreCase(ciudad))
                .filter(e -> categoria == null || e.getCategoria() == categoria)
                .filter(e -> precioMax == null || (e.getRecinto() != null &&
                        e.getRecinto().getZonas().stream().anyMatch(z -> z.getPrecioBase() <= precioMax)))
                .collect(Collectors.toList());
    }
}
