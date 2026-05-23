package com.ticketflow.enums;

public enum CategoriaEvento {
    CONCIERTO("Concierto"),
    TEATRO("Teatro"),
    CONFERENCIA("Conferencia"),
    DEPORTE("Deporte"),
    CINE("Cine"),
    STANDUP("Stand-up Comedy"),
    FESTIVAL("Festival"),
    EXPOSICION("Exposición"),
    OTRO("Otro");

    private final String descripcion;

    CategoriaEvento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
