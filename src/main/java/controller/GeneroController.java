package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Genero;

public class GeneroController implements Initializable {

    @FXML
    private AnchorPane apGenero;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblId;
    @FXML
    private TextField txtNome;
    @FXML
    private HBox hBoxBotoes;
    @FXML
    private HBox hBoxId;
    
    private Genero genero;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void Carregar(Genero genero, boolean editar, ArrayList<Button> botoes) {
        this.genero = genero;
        
        if (this.genero == null) {
            lblTitulo.setText("Cadastro");
            hBoxId.setVisible(false);
            this.genero = new Genero();
        } else {
            lblTitulo.setText("Visualizar/Editar");
            lblId.setText(String.valueOf(genero.getId()));
            txtNome.setText(genero.getNome());
        }
        
        hBoxBotoes.getChildren().setAll(botoes);
    }
    
    public Genero getGenero(){
        this.genero.setNome(txtNome.getText());
        
        return this.genero;
    }
}
