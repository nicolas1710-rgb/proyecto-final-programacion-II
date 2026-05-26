package com.ticketflow.patterns.comportamiento;















public interface IEstrategiaDescuento {
    double aplicar(double precioOriginal);

    String getNombre();
}
