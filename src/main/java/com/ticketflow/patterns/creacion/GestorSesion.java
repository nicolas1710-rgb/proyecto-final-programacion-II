package com.ticketflow.patterns.creacion;

import com.ticketflow.model.Administrador;
import com.ticketflow.model.Usuario;
import com.ticketflow.enums.Rol;

/**
 * PATRÓN SINGLETON — GestorSesion
 *
 * PROBLEMA: Se necesita gestionar la sesión activa (usuario o admin logueado)
 * con una sola instancia durante toda la ejecución de la aplicación.
 * Múltiples instancias causarían inconsistencia de estado.
 *
 * PROPÓSITO: Garantizar una única instancia de GestorSesion accesible
 * globalmente.
 *
 * SOLUCIÓN: Constructor privado + método estático getInstance() que crea
 * la instancia solo la primera vez (lazy initialization thread-safe).
 */
public class GestorSesion {
    private static volatile GestorSesion instancia;

    private Usuario usuarioActual;
    private Administrador adminActual;
    private Rol rolActivo;

    // Constructor privado para prevenir instanciación externa
    private GestorSesion() {
    }

    /**
     * Retorna la única instancia de GestorSesion.
     * Thread-safe con double-checked locking.
     */
    public static GestorSesion getInstance() {
        if (instancia == null) {
            synchronized (GestorSesion.class) {
                if (instancia == null) {
                    instancia = new GestorSesion();
                }
            }
        }
        return instancia;
    }

    public void loginUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        this.adminActual = null;
        this.rolActivo = Rol.USUARIO;
        System.out.println("[GestorSesion] Login usuario: " + usuario.getCorreoElectronico());
    }

    public void loginAdmin(Administrador admin) {
        this.adminActual = admin;
        this.usuarioActual = null;
        this.rolActivo = Rol.ADMIN;
        System.out.println("[GestorSesion] Login admin: " + admin.getCorreoElectronico());
    }

    public void logout() {
        System.out.println("[GestorSesion] Logout de: " +
                (rolActivo == Rol.ADMIN ? adminActual.getCorreoElectronico() : usuarioActual.getCorreoElectronico()));
        this.usuarioActual = null;
        this.adminActual = null;
        this.rolActivo = null;
    }

    public boolean isLoggedIn() {
        return rolActivo != null;
    }

    public boolean isAdmin() {
        return rolActivo == Rol.ADMIN;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public Administrador getAdminActual() {
        return adminActual;
    }

    public Rol getRolActivo() {
        return rolActivo;
    }
}
