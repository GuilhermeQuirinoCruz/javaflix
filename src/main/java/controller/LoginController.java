package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.App;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.fxml.FXMLLoader;


import service.UsuarioService;
import model.Usuario;

public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pfPassword;
        HomeController homeController;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Login");

    }

    @FXML
    private void Entrar() throws IOException {
        Usuario usuario = new Usuario();
        
        UsuarioService usuarioService = new UsuarioService();
        String generatedPassword = null;

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(pfPassword.getText().getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            generatedPassword = sb.toString();
            // usuario.setSenha(generatedPassword);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        usuario = usuarioService.RetornaUsuarioByEmail(txtEmail.getText());

        if (usuario != null) {
            usuario = usuarioService.RetornaUsuarioById(usuario.getId());
            if (usuario.getSenha().equals(generatedPassword)) {
                if (usuario.isAdmin()) {
                    App.changeScene(App.newScene(App.newFXML("admin"), 800, 600));

                } else {
                     FXMLLoader loader = App.newFXML("home");
                     homeController = loader.getController();
                     homeController.SetUsuario(usuario);
                     
                    
                    App.changeScene(App.newScene(App.newFXML("home"), 800, 600));
                    
                        
                    }
                    // Aqui implementar a rot que vai pra home page do usuario
//                    App.changeScene(App.newScene(App.newFXML("home"), 800, 600));


                

            } else {
                System.out.println("Senha incorreta!");
                Alert confirmacao = new Alert(Alert.AlertType.ERROR);
                confirmacao.setTitle("Erro ao fazer login");
                confirmacao.setHeaderText(null);
                confirmacao.setContentText("Senha Incorreta!!");
                confirmacao.show();

            }

            // ---------------------------------------------------------------------------

        } else {
            System.out.println("nao encontrado!");
            Alert confirmacao = new Alert(Alert.AlertType.ERROR);
            confirmacao.setTitle("Erro ao fazer login");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Usuario nao encontrado!!");
            confirmacao.show();

        }
        
        // ---------------------------------------------------------------------------

    }

    @FXML
    private void AbreCadastro(ActionEvent event) throws IOException {

        Scene sceneCadastro = App.newScene(App.newFXML("cadastroUsuario"), 800, 600);
        Stage stageCadastro = App.newWindow(sceneCadastro);
        stageCadastro.initModality(Modality.APPLICATION_MODAL);
        stageCadastro.show();

    }
    
    
}

