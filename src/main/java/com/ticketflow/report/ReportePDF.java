package com.ticketflow.report;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import com.ticketflow.model.Evento;
import com.ticketflow.model.Zona;
import com.ticketflow.enums.TipoServicio;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public class ReportePDF {
    private static final PDType1Font FONT_BOLD = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private static final PDType1Font FONT_NORMAL = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    private static void writeHeader(PDPageContentStream contentStream, String titulo, String rango) throws IOException {
        contentStream.setFont(FONT_BOLD, 20);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 750);
        contentStream.showText("TICKETFLOW - Reporte del Sistema");
        contentStream.endText();

        contentStream.setFont(FONT_NORMAL, 14);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 720);
        contentStream.showText("Título: " + titulo);
        contentStream.endText();

        contentStream.setFont(FONT_NORMAL, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 700);
        if (rango != null)
            contentStream.showText("Rango Temporal: " + rango);
        contentStream.endText();
    }

    private static void writeFooter(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(FONT_NORMAL, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 50);
        contentStream.showText("Generado el: " + LocalDate.now().toString());
        contentStream.endText();
    }

    public static void generarVentasPorPeriodo(Map<LocalDate, Double> datos, String filePath) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                writeHeader(content, "Ventas Por Período", "Histórico");
                int y = 650;
                content.setFont(FONT_BOLD, 12);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText(String.format("%-20s | %-20s", "Fecha", "Ventas ($)"));
                content.endText();

                content.setFont(FONT_NORMAL, 12);
                for (Map.Entry<LocalDate, Double> entry : datos.entrySet()) {
                    y -= 20;
                    content.beginText();
                    content.newLineAtOffset(50, y);
                    content.showText(entry.getKey().toString() + "   |   $" + String.format("%.2f", entry.getValue()));
                    content.endText();
                }
                writeFooter(content);
            }
            doc.save(filePath);
            System.out.println("Reporte PDF generado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generarTopEventos(List<Evento> eventos, String filePath) {
        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(doc, page)) {
                writeHeader(content, "Top Eventos de TicketFlow", "Todos");
                int y = 650;
                content.setFont(FONT_BOLD, 12);
                content.beginText();
                content.newLineAtOffset(50, y);
                content.showText("Ranking   |   Evento                     |   Categoría");
                content.endText();

                content.setFont(FONT_NORMAL, 12);
                int r = 1;
                for (Evento e : eventos) {
                    y -= 20;
                    content.beginText();
                    content.newLineAtOffset(50, y);
                    content.showText(
                            String.format("%-10d| %-25s| %s", r++, e.getNombre(), e.getCategoria().getDescripcion()));
                    content.endText();
                }
                writeFooter(content);
            }
            doc.save(filePath);
            System.out.println("Reporte PDF generado: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
