package com.ticketflow.app;

import com.ticketflow.util.DataInitializer;
import com.ticketflow.util.NavigationManager;
import com.ticketflow.repository.*;
import com.ticketflow.service.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    //Repositorios estáticos para compartirlos entre controladores a
    public static UsuarioRepository usuarioRepo = new UsuarioRepository();
    public static AdministradorRepository adminRepo = new AdministradorRepository();
    public static EventoRepository eventoRepo = new EventoRepository();
    public static RecintoRepository recintoRepo = new RecintoRepository();
    public static ZonaRepository zonaRepo = new ZonaRepository();
    public static AsientoRepository asientoRepo = new AsientoRepository();
    public static CompraRepository compraRepo = new CompraRepository();
    public static EntradaRepository entradaRepo = new EntradaRepository();
    public static PagoRepository pagoRepo = new PagoRepository();
    public static IncidenciaRepository incidenciaRepo = new IncidenciaRepository();

    // Servicios estáticos
    public static IUsuarioService usuarioService = new UsuarioServiceImpl(usuarioRepo, compraRepo);
    public static IAsientoService asientoService = new AsientoServiceImpl(asientoRepo);
    public static ICompraService compraService = new CompraServiceImpl(compraRepo, usuarioRepo, eventoRepo,
            asientoService, entradaRepo, pagoRepo);
    public static IEventoService eventoService = new EventoServiceImpl(eventoRepo, entradaRepo);
    public static IRecintoService recintoService = new RecintoServiceImpl(recintoRepo);
    public static IEntradaService entradaService = new EntradaServiceImpl(entradaRepo);
    public static IIncidenciaService incidenciaService = new IncidenciaServiceImpl(incidenciaRepo);
    public static IMetricasService metricasService = new MetricasServiceImpl(compraRepo, entradaRepo, eventoRepo);

    @Override
    public void start(Stage primaryStage) {
        // Inicializar datos falsos de base de datos
        DataInitializer.inicializarDatos(
                usuarioRepo, adminRepo, eventoRepo, recintoRepo,
                zonaRepo, asientoRepo, compraService);

        // Config
        //urar NavigationManager
        NavigationManager.getInstance().setStage(primaryStage);

        // Iniciar en pantalla de Login
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/LoginView.fxml",
                "TicketFlow - Iniciar Sesión");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
