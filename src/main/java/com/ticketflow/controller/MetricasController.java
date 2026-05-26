package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Evento;
import com.ticketflow.model.Zona;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class MetricasController {
    @FXML
    private Label lblTasaCancelacion;
    @FXML
    private BarChart<String, Number> chartVentas;
    @FXML
    private PieChart chartRecintos; 

    @FXML
    public void initialize() {
        cargarMetricas();
    }

    private void cargarMetricas() {
        
        double tasa = Main.metricasService.tasaCancelacion();
        lblTasaCancelacion.setText(String.format("%.1f%%", tasa));

        
        LocalDate hoy = LocalDate.now();
        Map<LocalDate, Double> ventas = Main.metricasService.ventasPorPeriodo(hoy.minusDays(30), hoy);

        XYChart.Series<String, Number> seriesVentas = new XYChart.Series<>();
        seriesVentas.setName("Ventas (Últimos 30 días)");
        for (Map.Entry<LocalDate, Double> v : ventas.entrySet()) {
            seriesVentas.getData().add(new XYChart.Data<>(v.getKey().toString(), v.getValue()));
        }
        chartVentas.getData().add(seriesVentas);

        
        List<Evento> top = Main.metricasService.topEventos(5);
        for (Evento e : top) {
            
            
            double valorSimulado = Math.random() * 100 + 50;
            chartRecintos.getData().add(new PieChart.Data(e.getNombre(), valorSimulado));
        }
    }
}
