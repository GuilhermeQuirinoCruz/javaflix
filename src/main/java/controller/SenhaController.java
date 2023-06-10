package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class SenhaController implements Initializable {

    @FXML
    private PasswordField pfAtual;
    @FXML
    private PasswordField pfNova;
    @FXML
    private PasswordField pfConfirmar;
    
    private UsuarioController usuarioController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void SetUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }
    
    private void Fechar() {
        ((Stage)pfAtual.getScene().getWindow()).close();
    }
    
    @FXML
    private void Confirmar() throws IOException {
        if (!pfNova.getText().equals(pfConfirmar.getText())) {
            Alert erroRedefinir = new Alert(Alert.AlertType.ERROR);
            erroRedefinir.setTitle("Erro ao redefinir a senha");
            erroRedefinir.setHeaderText(null);
            erroRedefinir.setContentText("Senhas não correspondem!");
            erroRedefinir.show();
            
            return;
        }
        
        if (usuarioController.RedefinirSenha(pfAtual.getText(), pfNova.getText())) {
            Fechar();
        } else {
            Alert erroRedefinir = new Alert(Alert.AlertType.ERROR);
            erroRedefinir.setTitle("Erro ao redefinir a senha");
            erroRedefinir.setHeaderText(null);
            erroRedefinir.setContentText("Senha Incorreta!");
            erroRedefinir.show();
        }
    }
    
    @FXML
    private void Cancelar() {
        Fechar();
    }
}
