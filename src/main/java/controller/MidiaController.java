package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import model.Genero;
import model.Midia;
import service.GeneroService;

public class MidiaController implements Initializable {
    @FXML
    private ComboBox<Genero> cmbGenero;
    @FXML
    private HBox hBoxBotoes;
    @FXML
    private HBox hBoxId;
    @FXML
    private Label lblId;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtCapa;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtTrailer;
    @FXML
    private TextField txtVideo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    public void Carregar(Midia midia, boolean editar, ArrayList<Button> botoes) {
        GeneroService generoService = new GeneroService();
        cmbGenero.getItems().addAll(generoService.Listar());
        
        if (midia == null) {
            lblTitulo.setText("Cadastro");
            hBoxId.setVisible(false);
            cmbGenero.getSelectionModel().selectFirst();
        } else {
            lblTitulo.setText("Visualizar/Editar");
            lblId.setText(String.valueOf(midia.getId()));
            txtTitulo.setText(midia.getTitulo());
            txtDescricao.setText(midia.getDescricao());
            txtTrailer.setText(midia.getTrailer());
            txtVideo.setText(midia.getVideo());
            txtCapa.setText(midia.getCapa());
            cmbGenero.setValue(midia.getGenero());
        }
        
        hBoxBotoes.getChildren().setAll(botoes);
    }
    
    public Midia getMidia(){
        Midia midia = new Midia();
        if(hBoxId.isVisible()) {
            midia.setId(Integer.parseInt(lblId.getText()));
        }
        midia.setTitulo(txtTitulo.getText());
        midia.setDescricao(txtDescricao.getText());
        midia.setTrailer(txtTrailer.getText());
        midia.setVideo(txtVideo.getText());
        midia.setCapa(txtCapa.getText());
        midia.setGenero(cmbGenero.getValue());

        return midia;
    }
}
