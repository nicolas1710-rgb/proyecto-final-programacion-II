package com.ticketflow.patterns.creacion;

import com.ticketflow.model.Administrador;
import com.ticketflow.model.Usuario;
import com.ticketflow.enums.Rol;














public class GestorSesion {
    private static volatile GestorSesion instancia;

    private Usuario usuarioActual;
    private Administrador adminActual;
    private Rol rolActivo;

    
    private GestorSesion() {
    }

    



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
