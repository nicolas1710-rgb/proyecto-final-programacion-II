package com.ticketflow.repository;

import com.ticketflow.model.Usuario;
import java.util.*;
import java.util.stream.Collectors;




public class UsuarioRepository implements CrudRepository<Usuario, UUID> {
    private final Map<UUID, Usuario> store = new HashMap<>();

    @Override
    public void save(Usuario entity) {
        store.put(entity.getIdUsuario(), entity);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Usuario entity) {
        store.put(entity.getIdUsuario(), entity);
    }

    @Override
    public void delete(UUID id) {
        store.remove(id);
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return store.values().stream()
                .filter(u -> u.getCorreoElectronico().equalsIgnoreCase(correo))
                .findFirst();
    }
}
