package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Usuario;
import com.ticketflow.patterns.creacion.GestorSesion;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class PerfilController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtTelefono;
    @FXML
    private PasswordField txtNuevaContrasena;
    @FXML
    private Label lblMensaje;

    @FXML
    public void initialize() {
        Usuario u = GestorSesion.getInstance().getUsuarioActual();
        if (u != null) {
            txtNombre.setText(u.getNombreCompleto());
            txtCorreo.setText(u.getCorreoElectronico());
            txtTelefono.setText(u.getTelefono());
        }
    }

    @FXML
    public void onGuardar() {
        Usuario u = GestorSesion.getInstance().getUsuarioActual();
        if (u == null)
            return;

        String nombre = txtNombre.getText().trim();
        String tel = txtTelefono.getText().trim();
        String pass = txtNuevaContrasena.getText().trim();

        if (nombre.isEmpty()) {
            lblMensaje.setText("El nombre no puede estar vacío.");
            return;
        }

        u.setNombreCompleto(nombre);
        u.setTelefono(tel);
        if (!pass.isEmpty()) {
            u.setContrasena(pass);
        }

        Main.usuarioRepo.update(u);
        lblMensaje.setStyle("-fx-text-fill: #4CAF50;");
        lblMensaje.setText("Perfil actualizado correctamente.");
        txtNuevaContrasena.clear();
    }
}
