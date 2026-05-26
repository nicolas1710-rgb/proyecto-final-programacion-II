package com.ticketflow.report;

import com.opencsv.CSVWriter;
import com.ticketflow.model.Evento;
import com.ticketflow.model.Zona;
import com.ticketflow.enums.TipoServicio;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class ReporteCSV {

    public static void ventasPorPeriodo(Map<LocalDate, Double> datos, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[] { "Fecha", "Total Ventas ($)" });
            for (Map.Entry<LocalDate, Double> entry : datos.entrySet()) {
                writer.writeNext(new String[] { entry.getKey().toString(), String.format("%.2f", entry.getValue()) });
            }
            System.out.println("Reporte CSV generado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ocupacionPorZona(Map<Zona, Double> datos, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[] { "Zona", "Capacidad", "Ocupación (%)" });
            for (Map.Entry<Zona, Double> entry : datos.entrySet()) {
                writer.writeNext(new String[] {
                        entry.getKey().getNombre(),
                        String.valueOf(entry.getKey().getCapacidad()),
                        String.format("%.2f%%", entry.getValue())
                });
            }
            System.out.println("Reporte CSV generado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ingresosPorServicios(Map<TipoServicio, Double> datos, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[] { "Tipo Servicio", "Ingresos Totales ($)" });
            for (Map.Entry<TipoServicio, Double> entry : datos.entrySet()) {
                writer.writeNext(
                        new String[] { entry.getKey().getDescripcion(), String.format("%.2f", entry.getValue()) });
            }
            System.out.println("Reporte CSV generado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void topEventos(List<Evento> eventos, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[] { "Ranking", "Evento", "Ciudad", "Categoría" });
            int rank = 1;
            for (Evento e : eventos) {
                writer.writeNext(new String[] {
                        String.valueOf(rank++),
                        e.getNombre(),
                        e.getCiudad(),
                        e.getCategoria().getDescripcion()
                });
            }
            System.out.println("Reporte CSV generado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
