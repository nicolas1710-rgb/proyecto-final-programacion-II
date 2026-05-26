package com.ticketflow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




public class Recinto {
    private UUID idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Zona> zonas;

    public Recinto() {
        this.idRecinto = UUID.randomUUID();
        this.zonas = new ArrayList<>();
    }

    public Recinto(String nombre, String direccion, String ciudad) {
        this();
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }

    public UUID getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(UUID idRecinto) {
        this.idRecinto = idRecinto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public List<Zona> getZonas() {
        return zonas;
    }

    public void setZonas(List<Zona> zonas) {
        this.zonas = zonas;
    }

    @Override
    public String toString() {
        return "Recinto{nombre='" + nombre + "', ciudad='" + ciudad + "'}";
    }
}
