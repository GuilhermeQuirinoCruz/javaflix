/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.App;
import model.Midia;
import model.Usuario;
import service.UsuarioService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;


/**
 * Mover para package controller esse arquivo.
 */
public class CadastroUsuarioController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField psSenha;
    @FXML
    private PasswordField psConfirmaSenha;
    @FXML
    private Button btnCadastrar;
    
    private Usuario usuario;
    private UsuarioService usuarioService;
    
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Cadastro");
        usuarioService = new UsuarioService();
        lblTitulo.setText("Cadastro");
        
        
    }  
    
    public void SetUser(){
        usuario = new Usuario();

        this.usuario.setNome(txtNome.getText());
        this.usuario.setEmail(txtEmail.getText());
        this.usuario.setAdmin(false);
       
        LocalDate date = LocalDate.now();
        
        this.usuario.setDataExpiracao(date);
        
        
        
    }
    
    @FXML
    public void Cadastrar() throws IOException{
       SetUser();
      
    if(!psSenha.getText().equals(psConfirmaSenha.getText())){
        Alert confirmacao = new Alert(Alert.AlertType.ERROR);
            confirmacao.setTitle("Erro ao cadastrar.");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Senhas não correspodem!!");
            confirmacao.show();
        
    }
    else{
        
      usuario.setSenha(HashPassword(psSenha.getText()));
      
      usuarioService.Inserir(usuario);
      ((Stage)lblTitulo.getScene().getWindow()).close();
    
    }
    
    
}
    
    public String HashPassword(String password){
        String generatedPassword = null;
        
    try 
        {
          
      // Create MessageDigest instance for MD5
      MessageDigest md = MessageDigest.getInstance("MD5");

      // Add password bytes to digest
      md.update(password.getBytes());

      // Get the hash's bytes
      byte[] bytes = md.digest();

      // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

      // Get complete hashed password in hex format
      generatedPassword = sb.toString();
     
      
      
      
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPassword;
    
        
    }
    
    
    
    
    
    
}
