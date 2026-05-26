package com.ticketflow.service;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import com.ticketflow.repository.*;
import java.util.List;
import java.util.UUID;






public class CompraServiceImpl implements ICompraService {
    private final CompraRepository compraRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final IAsientoService asientoService;
    private final EntradaRepository entradaRepository;
    private final PagoRepository pagoRepository;

    public CompraServiceImpl(CompraRepository compraRepository,
            UsuarioRepository usuarioRepository,
            EventoRepository eventoRepository,
            IAsientoService asientoService,
            EntradaRepository entradaRepository,
            PagoRepository pagoRepository) {
        this.compraRepository = compraRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
        this.asientoService = asientoService;
        this.entradaRepository = entradaRepository;
        this.pagoRepository = pagoRepository;
    }

    @Override
    public Compra crearCompra(UUID idUsuario, UUID idEvento, List<ItemCompra> items) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + idUsuario));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new EventoNoDisponibleException("Evento no encontrado: " + idEvento));
        if (evento.getEstadoEvento() != EstadoEvento.PUBLICADO) {
            throw new EventoNoDisponibleException(
                    "El evento no está disponible para compra: " + evento.getEstadoEvento());
        }

        
        for (ItemCompra item : items) {
            if (item.getAsiento() != null) {
                Asiento asiento = item.getAsiento();
                if (asiento.getEstadoAsiento() != EstadoAsiento.DISPONIBLE) {
                    throw new AsientoYaReservadoException(
                            "El asiento " + asiento.getFila() + asiento.getNumero() + " no está disponible.");
                }
                asientoService.cambiarEstado(asiento.getIdAsiento(), EstadoAsiento.RESERVADO);
            }
        }

        Compra compra = new Compra(usuario, evento);
        compra.setItems(items);
        compra.calcularTotal();
        compraRepository.save(compra);
        System.out.println("[CompraService] Compra creada: " + compra.getIdCompra());
        return compra;
    }

    @Override
    public Compra modificarCompra(UUID idCompra, List<ItemCompra> items, List<ServicioAdicional> servicios) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + idCompra));
        if (compra.getEstadoCompra() != EstadoCompra.CREADA) {
            throw new CompraNoModificableException("Solo se puede modificar una compra en estado CREADA.");
        }
        compra.setItems(items);
        compra.setServiciosAdicionales(servicios);
        compra.calcularTotal();
        compraRepository.update(compra);
        return compra;
    }

    @Override
    public boolean cancelarCompra(UUID idCompra) {
        Compra compra = compraRepository.findById(idCompra).orElse(null);
        if (compra == null)
            return false;
        if (compra.getEstadoCompra() == EstadoCompra.CANCELADA
                || compra.getEstadoCompra() == EstadoCompra.REEMBOLSADA) {
            return false;
        }
        
        compra.getItems().forEach(item -> {
            if (item.getAsiento() != null) {
                asientoService.cambiarEstado(item.getAsiento().getIdAsiento(), EstadoAsiento.DISPONIBLE);
            }
        });
        
        entradaRepository.findByCompra(idCompra).forEach(e -> {
            e.setEstadoEntrada(EstadoEntrada.ANULADA);
            entradaRepository.update(e);
        });
        compra.setEstadoCompra(EstadoCompra.CANCELADA);
        compraRepository.update(compra);
        System.out.println("[CompraService] Compra cancelada: " + idCompra);
        return true;
    }

    @Override
    public Pago confirmarPago(UUID idCompra, MetodoPago metodoPago) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + idCompra));

        Pago pago = new Pago(metodoPago, compra.getTotal());
        
        if (Math.random() > 0.1) {
            pago.setEstadoPago(EstadoPago.APROBADO);
            compra.setEstadoCompra(EstadoCompra.PAGADA);
            compra.setPago(pago);
            
            compra.getItems().forEach(item -> {
                if (item.getAsiento() != null) {
                    asientoService.cambiarEstado(item.getAsiento().getIdAsiento(), EstadoAsiento.VENDIDO);
                }
            });
        } else {
            pago.setEstadoPago(EstadoPago.RECHAZADO);
            throw new PagoRechazadoException("El pago fue rechazado por la pasarela.");
        }
        pagoRepository.save(pago);
        compraRepository.update(compra);
        return pago;
    }

    @Override
    public Compra obtenerDetalle(UUID idCompra) {
        return compraRepository.findById(idCompra).orElse(null);
    }

    @Override
    public List<Compra> obtenerHistorial(UUID idUsuario, EstadoCompra filtroEstado) {
        return compraRepository.findByUsuario(idUsuario).stream()
                .filter(c -> filtroEstado == null || c.getEstadoCompra() == filtroEstado)
                .toList();
    }

    @Override
    public boolean reasignarAsiento(UUID idCompra, UUID idEntrada, UUID idNuevoAsiento) {
        Entrada entrada = entradaRepository.findById(idEntrada).orElse(null);
        if (entrada == null || entrada.getEstadoEntrada() != EstadoEntrada.ACTIVA)
            return false;

        Asiento nuevoAsiento = null;
        try {
            nuevoAsiento = asientoService.listarPorZona(entrada.getZona().getIdZona()).stream()
                    .filter(a -> a.getIdAsiento().equals(idNuevoAsiento))
                    .findFirst().orElse(null);
        } catch (Exception e) {
            return false;
        }

        if (nuevoAsiento == null || nuevoAsiento.getEstadoAsiento() != EstadoAsiento.DISPONIBLE)
            return false;

        
        if (entrada.getAsiento() != null) {
            asientoService.cambiarEstado(entrada.getAsiento().getIdAsiento(), EstadoAsiento.DISPONIBLE);
        }
        
        asientoService.cambiarEstado(idNuevoAsiento, EstadoAsiento.VENDIDO);
        entrada.setAsiento(nuevoAsiento);
        entradaRepository.update(entrada);
        return true;
    }

    @Override
    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    @Override
    public List<Compra> listarCompras(UUID idUsuario) {
        return compraRepository.findByUsuario(idUsuario);
    }
}
