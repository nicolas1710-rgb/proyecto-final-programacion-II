package com.ticketflow.patterns.comportamiento;

import javafx.scene.control.Alert;
import javafx.application.Platform;


public class NotificadorUIJavaFX implements IObservadorEvento {
    @Override
    public void actualizar(String mensaje, Object entidad) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Notificación TicketFlow");
            alert.setHeaderText("Cambio de estado");
            alert.setContentText(mensaje);
            alert.show();
        });
    }
}
