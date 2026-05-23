package com.ticketflow.service;

import com.ticketflow.model.*;
import com.ticketflow.enums.EstadoEntrada;
import com.ticketflow.enums.TipoServicio;
import com.ticketflow.patterns.estructural.*;
import com.ticketflow.repository.EntradaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntradaServiceImpl implements IEntradaService {
    private final EntradaRepository entradaRepository;

    public EntradaServiceImpl(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    @Override
    public List<Entrada> generarEntradas(Compra compra) {
        List<Entrada> entradas = new ArrayList<>();
        boolean tieneVip = compra.getServiciosAdicionales().stream().anyMatch(s -> s.getTipo() == TipoServicio.VIP);
        boolean tieneSeguro = compra.getServiciosAdicionales().stream().anyMatch(s -> s.getTipo() == TipoServicio.SEGURO);
        boolean tieneMerch = compra.getServiciosAdicionales().stream().anyMatch(s -> s.getTipo() == TipoServicio.MERCHANDISING);
        boolean tieneParqueadero = compra.getServiciosAdicionales().stream().anyMatch(s -> s.getTipo() == TipoServicio.PARQUEADERO);

        for (ItemCompra item : compra.getItems()) {
            if (item.getAsiento() != null) {
                Entrada entrada = new Entrada(compra, item.getZona(), item.getAsiento(), item.getPrecioUnitario());
                TicketComponent tc = new EntradaBase(entrada);
                if (tieneVip)
                    tc = new VIPDecorator(tc);
                if (tieneSeguro)
                    tc = new SeguroDecorator(tc);
                if (tieneMerch)
                    tc = new MerchandisingDecorator(tc);
                if (tieneParqueadero)
                    tc = new ParqueaderoDecorator(tc);

                entrada.setPrecioFinal(tc.getPrecio());
                entradaRepository.save(entrada);
                entradas.add(entrada);
            } else {
                for (int i = 0; i < item.getCantidad(); i++) {
                    Entrada entrada = new Entrada(compra, item.getZona(), null, item.getPrecioUnitario());
                    TicketComponent tc = new EntradaBase(entrada);
                    if (tieneVip)
                        tc = new VIPDecorator(tc);
                    if (tieneSeguro)
                        tc = new SeguroDecorator(tc);
                    if (tieneMerch)
                        tc = new MerchandisingDecorator(tc);
                    if (tieneParqueadero)
                        tc = new ParqueaderoDecorator(tc);

                    entrada.setPrecioFinal(tc.getPrecio());
                    entradaRepository.save(entrada);
                    entradas.add(entrada);
                }
            }
        }
        System.out.println(
                "[EntradaService] Generadas " + entradas.size() + " entradas para compra: " + compra.getIdCompra());
        return entradas;
    }

    @Override
    public void anularEntrada(UUID idEntrada) {
        entradaRepository.findById(idEntrada).ifPresent(e -> {
            e.setEstadoEntrada(EstadoEntrada.ANULADA);
            entradaRepository.update(e);
        });
    }

    @Override
    public List<Entrada> obtenerPorCompra(UUID idCompra) {
        return entradaRepository.findByCompra(idCompra);
    }

    @Override
    public List<Entrada> obtenerPorEvento(UUID idEvento) {
        return entradaRepository.findByEvento(idEvento);
    }
}
