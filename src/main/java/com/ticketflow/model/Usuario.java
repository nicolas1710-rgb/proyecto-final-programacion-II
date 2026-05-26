package com.ticketflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




public class Usuario {
    private UUID idUsuario;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private String contrasena;
    private List<MetodoPago> metodosDePago;
    private List<Compra> historialCompras;

    public Usuario() {
        this.idUsuario = UUID.randomUUID();
        this.metodosDePago = new ArrayList<>();
        this.historialCompras = new ArrayList<>();
    }

    public Usuario(String nombreCompleto, String correoElectronico, String telefono, String contrasena) {
        this();
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }

    
    public UUID getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(UUID idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<MetodoPago> getMetodosDePago() {
        return metodosDePago;
    }

    public void setMetodosDePago(List<MetodoPago> metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    public List<Compra> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<Compra> historialCompras) {
        this.historialCompras = historialCompras;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", nombre='" + nombreCompleto + "', correo='" + correoElectronico + "'}";
    }
}
