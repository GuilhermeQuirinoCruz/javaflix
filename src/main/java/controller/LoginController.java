package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.App;

public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pfPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Login");
    }
    
    @FXML
    private void Entrar() throws IOException {
        if (txtEmail.getText().equals("admin")){
            App.changeScene("admin");
        }
    }
}