package com.ticketflow.patterns.creacion;

import com.ticketflow.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * PATRÓN BUILDER — CompraBuilder
 *
 * PROBLEMA: Construir objetos Compra complejos paso a paso
 * (agregar ítems, servicios adicionales, método de pago) sin
 * constructores con demasiados parámetros.
 *
 * PROPÓSITO: Separar la construcción de objetos complejos de su representación.
 *
 * SOLUCIÓN: CompraBuilder con métodos fluidos (fluent API) que permiten
 * configurar la compra incrementalmente y construirla al final con build().
 */
public class CompraBuilder {
    private Usuario usuario;
    private Evento evento;
    private final List<ItemCompra> items = new ArrayList<>();
    private final List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();
    private Pago pago;

    public CompraBuilder conUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public CompraBuilder conEvento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public CompraBuilder agregarItem(ItemCompra item) {
        this.items.add(item);
        return this;
    }

    public CompraBuilder agregarServicio(ServicioAdicional servicio) {
        this.serviciosAdicionales.add(servicio);
        return this;
    }

    public CompraBuilder conPago(Pago pago) {
        this.pago = pago;
        return this;
    }

    /**
     * Construye y retorna la instancia de Compra configurada.
     */
    public Compra build() {
        if (usuario == null)
            throw new IllegalStateException("CompraBuilder: usuario es obligatorio.");
        if (evento == null)
            throw new IllegalStateException("CompraBuilder: evento es obligatorio.");

        Compra compra = new Compra(usuario, evento);
        compra.setItems(new ArrayList<>(items));
        compra.setServiciosAdicionales(new ArrayList<>(serviciosAdicionales));
        compra.calcularTotal();
        if (pago != null)
            compra.setPago(pago);
        return compra;
    }
}
