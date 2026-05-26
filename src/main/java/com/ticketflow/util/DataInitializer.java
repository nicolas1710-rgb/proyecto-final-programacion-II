package com.ticketflow.util;

import com.ticketflow.model.*;
import com.ticketflow.enums.*;
import com.ticketflow.repository.*;
import com.ticketflow.service.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataInitializer {
    public static void inicializarDatos(
            UsuarioRepository usuarioRepo,
            AdministradorRepository adminRepo,
            EventoRepository eventoRepo,
            RecintoRepository recintoRepo,
            ZonaRepository zonaRepo,
            AsientoRepository asientoRepo,
            ICompraService compraService) {

        System.out.println("--- INICIALIZANDO DATOS DE PRUEBA ---");

        
        Administrador admin = new Administrador("Admin Principal", "admin@gmail.com", "admin123");
        adminRepo.save(admin);

        
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Usuario u = new Usuario("Usuario " + i, "usuario" + i + "@gmail.com", "555-000" + i, "contraseña" + i);
            u.getMetodosDePago().add(
                    new MetodoPago(TipoMetodoPago.TARJETA_CREDITO, "**** **** **** 123" + i, u.getNombreCompleto()));
            usuarioRepo.save(u);
            usuarios.add(u);
        }

        
        List<Recinto> recintos = new ArrayList<>();
        recintos.add(new Recinto("Estadio Centenario", "Calle 1 # 2-3", "Bogotá"));
        recintos.add(new Recinto("Teatro Municipal", "Carrera 4 # 5-6", "Medellín"));
        recintos.add(new Recinto("Centro de Convenciones", "Avenida 7 # 8-9", "Cali"));

        for (Recinto r : recintos) {
            recintoRepo.save(r);

            Zona vip = new Zona("VIP " + r.getNombre(), 50, 200000.0, TipoZona.VIP);
            Zona pref = new Zona("Preferencial " + r.getNombre(), 100, 100000.0, TipoZona.PREFERENCIAL);
            Zona gen = new Zona("General " + r.getNombre(), 200, 50000.0, TipoZona.GENERAL);

            List<Zona> zonas = List.of(vip, pref, gen);
            for (Zona z : zonas) {
                zonaRepo.save(z);
                r.getZonas().add(z);

                
                if (z.getTipoZona() != TipoZona.GENERAL) {
                    char[] filas = { 'A', 'B', 'C', 'D', 'E' };
                    for (char fila : filas) {
                        for (int num = 1; num <= 10; num++) {
                            Asiento asiento = new Asiento(String.valueOf(fila), num, z);
                            asientoRepo.save(asiento);
                            z.getAsientos().add(asiento);
                        }
                    }
                }
            }
            recintoRepo.update(r);
        }

        
        List<Evento> eventos = new ArrayList<>();
        eventos.add(new Evento("Gran Concierto Rock", CategoriaEvento.CONCIERTO, "El mejor concierto del año",
                recintos.get(0).getCiudad(), LocalDateTime.now().plusDays(30), recintos.get(0)));
        eventos.add(new Evento("Obra Clásica", CategoriaEvento.TEATRO, "Una obra inolvidable",
                recintos.get(1).getCiudad(), LocalDateTime.now().plusDays(15), recintos.get(1)));
        eventos.add(new Evento("Tech Conf 2026", CategoriaEvento.CONFERENCIA, "Tecnología y futuro",
                recintos.get(2).getCiudad(), LocalDateTime.now().plusDays(45), recintos.get(2)));
        eventos.add(new Evento("Partido Final", CategoriaEvento.DEPORTE, "La gran final", recintos.get(0).getCiudad(),
                LocalDateTime.now().plusDays(10), recintos.get(0)));
        eventos.add(new Evento("Festival Culinario", CategoriaEvento.OTRO, "Sabores del mundo",
                recintos.get(2).getCiudad(), LocalDateTime.now().plusDays(60), recintos.get(2)));
        eventos.add(new Evento("Estreno: Sci-Fi Odyssey", CategoriaEvento.CINE,
                "La película más esperada de ciencia ficción",
                recintos.get(1).getCiudad(), LocalDateTime.now().plusDays(20), recintos.get(1)));
        eventos.add(new Evento("Stand-up Comedy Special", CategoriaEvento.STANDUP,
                "Risas garantizadas con los mejores comediantes",
                recintos.get(1).getCiudad(), LocalDateTime.now().plusDays(5), recintos.get(1)));
        eventos.add(new Evento("Festival de Música Estelar", CategoriaEvento.FESTIVAL,
                "Festival con bandas nacionales e internacionales",
                recintos.get(0).getCiudad(), LocalDateTime.now().plusDays(50), recintos.get(0)));
        eventos.add(new Evento("Galería de Arte Moderno", CategoriaEvento.EXPOSICION,
                "Muestra pictórica y de esculturas contemporáneas",
                recintos.get(2).getCiudad(), LocalDateTime.now().plusDays(25), recintos.get(2)));

        eventos.get(0).setEstadoEvento(EstadoEvento.PUBLICADO);
        eventos.get(1).setEstadoEvento(EstadoEvento.PUBLICADO);
        eventos.get(2).setEstadoEvento(EstadoEvento.PAUSADO);
        eventos.get(3).setEstadoEvento(EstadoEvento.CANCELADO);
        eventos.get(4).setEstadoEvento(EstadoEvento.BORRADOR);
        eventos.get(5).setEstadoEvento(EstadoEvento.PUBLICADO);
        eventos.get(6).setEstadoEvento(EstadoEvento.PUBLICADO);
        eventos.get(7).setEstadoEvento(EstadoEvento.PUBLICADO);
        eventos.get(8).setEstadoEvento(EstadoEvento.PUBLICADO);

        for (Evento e : eventos) {
            eventoRepo.save(e);
        }

        
        
        
        
        try {
            Usuario u1 = usuarios.get(0);
            Evento e1 = eventos.get(0); 
            Zona zVip = e1.getRecinto().getZonas().get(0);

            List<ItemCompra> items = new ArrayList<>();
            if (!zVip.getAsientos().isEmpty()) {
                items.add(new ItemCompra(zVip, zVip.getAsientos().get(0), zVip.getPrecioBase(), 1));
                items.add(new ItemCompra(zVip, zVip.getAsientos().get(1), zVip.getPrecioBase(), 1));

                Compra c = compraService.crearCompra(u1.getIdUsuario(), e1.getIdEvento(), items);
                compraService.confirmarPago(c.getIdCompra(), u1.getMetodosDePago().get(0));
            }

            System.out.println("Compras de prueba creadas.");
        } catch (Exception ex) {
            System.err.println("Error inicializando compras: " + ex.getMessage());
        }

        System.out.println("--- DATOS INICIALIZADOS CORRECTAMENTE ---");
    }
}
