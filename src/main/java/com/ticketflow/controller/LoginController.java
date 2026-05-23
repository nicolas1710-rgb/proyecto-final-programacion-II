package com.ticketflow.controller;

import com.ticketflow.app.Main;
import com.ticketflow.model.Usuario;
import com.ticketflow.model.Administrador;
import com.ticketflow.patterns.creacion.GestorSesion;
import com.ticketflow.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblError;

    @FXML
    public void initialize() { lblError.setText(""); }

    @FXML
    public void onLogin() {
        String correo = txtCorreo.getText().trim();
        String pass = txtContrasena.getText().trim();

        if (correo.isEmpty() || pass.isEmpty()) {
            lblError.setText("Por favor ingrese correo y contraseña");
            return;
        }

        // Buscar en admin
        Administrador asAdmin = Main.adminRepo.findByCorreo(correo).orElse(null);
        if (asAdmin != null && asAdmin.getContrasena().equals(pass)) {
            GestorSesion.getInstance().loginAdmin(asAdmin);
            NavigationManager.getInstance().navigateTo("/com/ticketflow/view/DashboardAdminView.fxml", "TicketFlow - Admin");
            return;
        }

        // Buscar en usuario
        Usuario asUser = Main.usuarioService.iniciarSesion(correo, pass);
        if (asUser != null) {
            GestorSesion.getInstance().loginUsuario(asUser);
            NavigationManager.getInstance().navigateTo("/com/ticketflow/view/DashboardUsuarioView.fxml", "TicketFlow - Usuario");
            return;
        }

        lblError.setText("Credenciales incorrectas");
    }

    @FXML
    public void onRegistrarse() {
        NavigationManager.getInstance().navigateTo("/com/ticketflow/view/RegistroView.fxml", "TicketFlow - Registro");
    }
}
