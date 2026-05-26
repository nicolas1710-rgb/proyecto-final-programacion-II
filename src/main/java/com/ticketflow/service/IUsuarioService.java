package com.ticketflow.service;

import com.ticketflow.model.*;
import java.util.List;
import java.util.UUID;






public interface IUsuarioService {
    Usuario registrar(Usuario usuario);

    Usuario iniciarSesion(String correo, String contrasena);

    void actualizarPerfil(Usuario usuario);

    void eliminar(UUID idUsuario);

    void agregarMetodoPago(UUID idUsuario, MetodoPago metodoPago);

    void eliminarMetodoPago(UUID idUsuario, UUID idMetodoPago);

    List<Compra> obtenerHistorialCompras(UUID idUsuario);

    List<Usuario> listarTodos();

    Usuario obtenerPorId(UUID idUsuario);
}
