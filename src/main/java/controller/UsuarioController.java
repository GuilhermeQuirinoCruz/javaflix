package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.App;
import model.Usuario;
import service.UsuarioService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class UsuarioController implements Initializable {

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
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnExcluir;
    
    private Usuario usuario = new Usuario();
    private UsuarioService usuarioService;
    private HomeController homeController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Cadastro");
        usuarioService = new UsuarioService();
        lblTitulo.setText("Cadastro");
        
        btnCadastrar.setVisible(true);
        btnAtualizar.setVisible(false);
        btnExcluir.setVisible(false);
    }  
    
    public void SetUsuario(Usuario usuario) {
        this.usuario = usuario;
        
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());
        lblTitulo.setText("Atualizar");
        
        btnCadastrar.setVisible(false);
        btnAtualizar.setVisible(true);
        btnExcluir.setVisible(true);
    }
    
    private void GetUsuario(){
        this.usuario.setNome(txtNome.getText());
        this.usuario.setEmail(txtEmail.getText());
        this.usuario.setAdmin(false);
       
        LocalDate date = LocalDate.now();
        
        this.usuario.setDataExpiracao(date);
    }
    
    public void SetHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
    @FXML
    public void Cadastrar() throws IOException{
        GetUsuario();
        
        if(!psSenha.getText().equals(psConfirmaSenha.getText())){
            Alert confirmacao = new Alert(Alert.AlertType.ERROR);
            confirmacao.setTitle("Erro ao cadastrar.");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Senhas não correspodem!!");
            confirmacao.show();
        } else {

          usuario.setSenha(HashPassword(psSenha.getText()));

          usuarioService.Inserir(usuario);
          ((Stage)lblTitulo.getScene().getWindow()).close();
        }
    }
    
    public String HashPassword(String password){
        String generatedPassword = null;
        
        try {
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
    
    @FXML
    private void Atualizar() {
        GetUsuario();
        
        String senhaHash = HashPassword(psSenha.getText());
        Usuario usuarioBD = usuarioService.RetornaUsuarioById(this.usuario.getId());
        
        if (senhaHash.equals(usuarioBD.getSenha())) {
            this.usuario.setSenha(senhaHash);
            usuarioService.Atualizar(this.usuario);
            
            homeController.SetUsuario(this.usuario);
            ((Stage)lblTitulo.getScene().getWindow()).close();
        }
    }
    
    @FXML
    private void Excluir() throws IOException {
        GetUsuario();
        
        String senhaHash = HashPassword(psSenha.getText());
        Usuario usuarioBD = usuarioService.RetornaUsuarioById(this.usuario.getId());
        
        if (senhaHash.equals(usuarioBD.getSenha())) {
            usuarioService.Excluir(this.usuario);
            homeController.Sair();
            ((Stage)lblTitulo.getScene().getWindow()).close();
        }
    }
}
