package com.ticketflow.model;

import com.ticketflow.enums.Rol;
import java.util.UUID;





public class Administrador {
    private UUID idAdmin;
    private String nombreCompleto;
    private String correoElectronico;
    private String contrasena;
    private Rol rol;

    public Administrador() {
        this.idAdmin = UUID.randomUUID();
        this.rol = Rol.ADMIN;
    }

    public Administrador(String nombreCompleto, String correoElectronico, String contrasena) {
        this();
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public UUID getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(UUID idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Administrador{nombre='" + nombreCompleto + "', correo='" + correoElectronico + "', rol=" + rol + "}";
    }
}
