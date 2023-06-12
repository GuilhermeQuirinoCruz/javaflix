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
import java.time.LocalDate;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;

import main.App;
import model.Usuario;
import service.UsuarioService;
import utils.PasswordHash;

public class UsuarioController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblSenha;
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
    @FXML
    private Button btnRedefinir;
    
    private UsuarioService usuarioService;
    private HomeController homeController;
    private int idUsuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Cadastro");
        usuarioService = new UsuarioService();
        lblTitulo.setText("Cadastro");
        
        btnCadastrar.setVisible(true);
        btnAtualizar.setVisible(false);
        btnExcluir.setVisible(false);
        btnRedefinir.setVisible(false);
    }
    
    public void SetUsuario(Usuario usuario) {
        idUsuario = usuario.getId();
        txtNome.setText(usuario.getNome());
        txtEmail.setText(usuario.getEmail());
        lblTitulo.setText("Atualizar");
        
        btnCadastrar.setVisible(false);
        btnAtualizar.setVisible(true);
        btnExcluir.setVisible(true);
        btnRedefinir.setVisible(true);
        lblSenha.setVisible(false);
        psSenha.setVisible(false);
    }
    
    private Usuario GetUsuario(){
        Usuario usuario = new Usuario();
        
        usuario.setId(idUsuario);
        usuario.setNome(txtNome.getText());
        usuario.setEmail(txtEmail.getText());
        usuario.setAdmin(false);
       
        usuario.setDataExpiracao(LocalDate.now());
        
        return usuario;
    }
    
    public void SetHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
    private void ExibirErroSenha() {
        
    }
    
    @FXML
    public void Cadastrar() throws IOException{
        Usuario usuario = GetUsuario();
        
        if(!psSenha.getText().equals(psConfirmaSenha.getText())){
            Alert erroCadastro = new Alert(Alert.AlertType.ERROR);
            erroCadastro.setTitle("Erro ao cadastrar");
            erroCadastro.setHeaderText(null);
            erroCadastro.setContentText("Senhas não correspodem!!");
            erroCadastro.show();
        } else {
          usuario.setSenha(PasswordHash.HashString(psSenha.getText()));

          usuarioService.Inserir(usuario);
          ((Stage)lblTitulo.getScene().getWindow()).close();
        }
    }
    
    @FXML
    private void Atualizar() {
        Usuario usuario = GetUsuario();
        
        String senhaHash = PasswordHash.HashString(psConfirmaSenha.getText());
        Usuario usuarioBD = usuarioService.RetornaUsuarioById(idUsuario);
        
        if (senhaHash.equals(usuarioBD.getSenha())) {
            usuario.setSenha(senhaHash);
            usuarioService.Atualizar(usuario);
            
            homeController.SetUsuario(usuario);
            ((Stage)lblTitulo.getScene().getWindow()).close();
        } else {
            Alert erroAtualizar = new Alert(Alert.AlertType.ERROR);
            erroAtualizar.setTitle("Erro ao atualizar os dados");
            erroAtualizar.setHeaderText(null);
            erroAtualizar.setContentText("Senha Incorreta!");
            erroAtualizar.show();
        }
    }
    
    @FXML
    private void Excluir() throws IOException {
        String senhaHash = PasswordHash.HashString(psSenha.getText());
        Usuario usuario = usuarioService.RetornaUsuarioById(idUsuario);
        
        if (senhaHash.equals(usuario.getSenha())) {
            usuarioService.Excluir(idUsuario);
            homeController.Sair();
            ((Stage)lblTitulo.getScene().getWindow()).close();
        } else {
            Alert erroExcluir = new Alert(Alert.AlertType.ERROR);
            erroExcluir.setTitle("Erro ao excluir");
            erroExcluir.setHeaderText(null);
            erroExcluir.setContentText("Senha Incorreta!");
            erroExcluir.show();
        }
    }
    
    @FXML
    private void Redefinir() throws IOException {
        FXMLLoader loader = App.newFXML("senha");
        AnchorPane apSenha = (AnchorPane) loader.load();
        
        SenhaController senhaController = loader.getController();
        senhaController.SetUsuarioController(this);
        
        Stage stageCadastro = App.newWindow(App.newScene(apSenha, 650, 300));
        
        stageCadastro.initModality(Modality.APPLICATION_MODAL);
        stageCadastro.show();
    }
    
    public boolean RedefinirSenha(String senhaAtual, String senhaNova) throws IOException {
        senhaAtual = PasswordHash.HashString(senhaAtual);
        Usuario usuario = usuarioService.RetornaUsuarioById(idUsuario);
        
        if (senhaAtual.equals(usuario.getSenha())) {
            usuario = GetUsuario();
            usuario.setSenha(PasswordHash.HashString(senhaNova));
            
            usuarioService.Atualizar(usuario);
            
            homeController.Sair();
            ((Stage)lblTitulo.getScene().getWindow()).close();
            
            return true;
        }
        
        return false;
    }
}
