package com.ticketflow.patterns.comportamiento;


public class DescuentoEstudiante implements IEstrategiaDescuento {
    @Override
    public double aplicar(double p) {
        return p * 0.85;
    }

    @Override
    public String getNombre() {
        return "Descuento Estudiante 15%";
    }
}
