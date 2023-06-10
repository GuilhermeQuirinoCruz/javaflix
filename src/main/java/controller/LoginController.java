package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import main.App;
import service.UsuarioService;
import model.Usuario;
import utils.PasswordHash;

public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField passwordField;
    
    HomeController homeController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Login");
        
        Platform.runLater(() -> {txtEmail.requestFocus();});
    }

    @FXML
    private void Entrar() throws IOException {
        Usuario usuario;
        UsuarioService usuarioService = new UsuarioService();
        
        usuario = usuarioService.RetornaUsuarioByEmail(txtEmail.getText());
        
        if (usuario == null) {
            Alert erroLogin = new Alert(Alert.AlertType.ERROR);
            erroLogin.setTitle("Erro ao fazer login");
            erroLogin.setHeaderText(null);
            erroLogin.setContentText("Usuário não encontrado!");
            erroLogin.show();
            
            return;
        }
        
        usuario = usuarioService.RetornaUsuarioById(usuario.getId());
        
        if (!usuario.getSenha().equals(PasswordHash.HashString(passwordField.getText()))) {
            Alert erroLogin = new Alert(Alert.AlertType.ERROR);
            erroLogin.setTitle("Erro ao fazer login");
            erroLogin.setHeaderText(null);
            erroLogin.setContentText("Senha Incorreta!");
            erroLogin.show();
            
            return;
        }
        
        if (usuario.isAdmin()) {
            App.changeScene(App.newScene(App.newFXML("admin"), 800, 600));
        } else {
            FXMLLoader loader = App.newFXML("home");
            AnchorPane apHome = (AnchorPane)loader.load();
            homeController = loader.getController();
            homeController.SetUsuario(usuario);

            App.changeScene(App.newScene(apHome, 800, 600));
        }
    }

    @FXML
    private void AbreCadastro(ActionEvent event) throws IOException {
        Scene sceneCadastro = App.newScene(App.newFXML("usuario"), 600, 400);
        Stage stageCadastro = App.newWindow(sceneCadastro);
        stageCadastro.initModality(Modality.APPLICATION_MODAL);
        stageCadastro.show();
    }
    
    @FXML
    void EntrarComEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            Entrar();
        }
    }
}
