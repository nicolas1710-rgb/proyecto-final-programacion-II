package com.ticketflow.controller;

import com.ticketflow.model.Evento;
import com.ticketflow.enums.CategoriaEvento;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.time.format.DateTimeFormatter;

public class EventoCardController {

    @FXML
    private VBox cardRoot;

    @FXML
    private StackPane imgContainer;

    @FXML
    private ImageView imgEvento;

    @FXML
    private Label lblIconoPlaceholder;

    @FXML
    private Label lblCategoria;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblFechaHora;

    @FXML
    private Label lblUbicacion;

    @FXML
    private Button btnDetalles;

    private Evento evento;
    private Runnable onVerDetalles;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMM, yyyy - HH:mm");

    @FXML
    public void initialize() {
        // Configuramos doble clic en la tarjeta raíz
        cardRoot.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && onVerDetalles != null) {
                onVerDetalles.run();
            }
        });
    }

    public void setEvento(Evento evento, Runnable onVerDetalles) {
        this.evento = evento;
        this.onVerDetalles = onVerDetalles;

        lblTitulo.setText(evento.getNombre());
        lblCategoria.setText(evento.getCategoria().getDescripcion().toUpperCase());
        
        if (evento.getFechaHora() != null) {
            lblFechaHora.setText(evento.getFechaHora().format(FORMATTER));
        } else {
            lblFechaHora.setText("Fecha no programada");
        }

        String ubicacion = evento.getCiudad();
        if (evento.getRecinto() != null) {
            ubicacion += " - " + evento.getRecinto().getNombre();
        }
        lblUbicacion.setText(ubicacion);

        // Estilo dinámico y Emoji según Categoría
        CategoriaEvento cat = evento.getCategoria();
        String emoji = "📅";
        String gradientStyle = "-fx-background-color: ";

        switch (cat) {
            case CONCIERTO:
                emoji = "🎤";
                // Rosa / Morado a Azul oscuro
                gradientStyle += "linear-gradient(to bottom right, #ff007f, #7b2cbf, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #ff007f; -fx-border-color: #ff007f;");
                break;
            case TEATRO:
                emoji = "🎭";
                // Naranja / Rojo a Azul oscuro
                gradientStyle += "linear-gradient(to bottom right, #ff4d6d, #ff85a1, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #ff4d6d; -fx-border-color: #ff4d6d;");
                break;
            case CONFERENCIA:
                emoji = "💼";
                // Cyan / Azul a Azul oscuro
                gradientStyle += "linear-gradient(to bottom right, #00b4d8, #0077b6, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #00b4d8; -fx-border-color: #00b4d8;");
                break;
            case DEPORTE:
                emoji = "⚽";
                // Verde neón / Amarillo a Azul oscuro
                gradientStyle += "linear-gradient(to bottom right, #3DDB84, #aacc00, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #3DDB84; -fx-border-color: #3DDB84;");
                break;
            case CINE:
                emoji = "🎬";
                // Rojo brillante a Naranja oscuro
                gradientStyle += "linear-gradient(to bottom right, #ff2200, #7a1a00, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #ff2200; -fx-border-color: #ff2200;");
                break;
            case STANDUP:
                emoji = "🎙️";
                // Amarillo a Naranja vibrante
                gradientStyle += "linear-gradient(to bottom right, #ffb703, #fb8500, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #ffb703; -fx-border-color: #ffb703;");
                break;
            case FESTIVAL:
                emoji = "🎪";
                // Púrpura a Azul eléctrico
                gradientStyle += "linear-gradient(to bottom right, #7209b7, #3f37c9, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #7209b7; -fx-border-color: #7209b7;");
                break;
            case EXPOSICION:
                emoji = "🖼️";
                // Verde esmeralda a Azul cian
                gradientStyle += "linear-gradient(to bottom right, #06d6a0, #118ab2, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #06d6a0; -fx-border-color: #06d6a0;");
                break;
            default:
                emoji = "📅";
                // Gris / Azulado a Azul oscuro
                gradientStyle += "linear-gradient(to bottom right, #8B9AB5, #495874, #0F1E35);";
                lblCategoria.setStyle("-fx-text-fill: #8B9AB5; -fx-border-color: #8B9AB5;");
                break;
        }

        lblIconoPlaceholder.setText(emoji);
        imgContainer.setStyle(gradientStyle + " -fx-background-radius: 12px 12px 0 0;");

        // Acción del botón
        btnDetalles.setOnAction(e -> {
            if (this.onVerDetalles != null) {
                this.onVerDetalles.run();
            }
        });
    }
}
