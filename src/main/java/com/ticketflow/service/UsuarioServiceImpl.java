package com.ticketflow.service;

import com.ticketflow.model.*;
import com.ticketflow.repository.CompraRepository;
import com.ticketflow.repository.UsuarioRepository;
import java.util.List;
import java.util.UUID;






public class UsuarioServiceImpl implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final CompraRepository compraRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, CompraRepository compraRepository) {
        this.usuarioRepository = usuarioRepository;
        this.compraRepository = compraRepository;
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        if (usuario.getCorreoElectronico() == null || usuario.getCorreoElectronico().isBlank()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
        if (usuarioRepository.findByCorreo(usuario.getCorreoElectronico()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico.");
        }
        usuarioRepository.save(usuario);
        System.out.println("[UsuarioService] Usuario registrado: " + usuario.getCorreoElectronico());
        return usuario;
    }

    @Override
    public Usuario iniciarSesion(String correo, String contrasena) {
        return usuarioRepository.findByCorreo(correo)
                .filter(u -> u.getContrasena().equals(contrasena))
                .orElse(null);
    }

    @Override
    public void actualizarPerfil(Usuario usuario) {
        usuarioRepository.update(usuario);
        System.out.println("[UsuarioService] Perfil actualizado: " + usuario.getIdUsuario());
    }

    @Override
    public void eliminar(UUID idUsuario) {
        usuarioRepository.delete(idUsuario);
    }

    @Override
    public void agregarMetodoPago(UUID idUsuario, MetodoPago metodoPago) {
        usuarioRepository.findById(idUsuario).ifPresent(u -> {
            u.getMetodosDePago().add(metodoPago);
            usuarioRepository.update(u);
        });
    }

    @Override
    public void eliminarMetodoPago(UUID idUsuario, UUID idMetodoPago) {
        usuarioRepository.findById(idUsuario).ifPresent(u -> {
            u.getMetodosDePago().removeIf(mp -> mp.getIdMetodoPago().equals(idMetodoPago));
            usuarioRepository.update(u);
        });
    }

    @Override
    public List<Compra> obtenerHistorialCompras(UUID idUsuario) {
        return compraRepository.findByUsuario(idUsuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorId(UUID idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }
}
