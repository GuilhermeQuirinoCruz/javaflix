/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Genero;
import model.Midia;
import service.GeneroService;
import service.MidiaService;


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

    @FXML
    private VBox vBoxMidia;
    
    private Midia midia;
    private GeneroService generoService;
    private MidiaService midiaService;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    public void Carregar(Midia midia, boolean editar, ArrayList<Button> botoes) {
        this.midia = midia;
        this.generoService = new GeneroService();
        this.midiaService = new MidiaService();
        cmbGenero.getItems().addAll(generoService.Listar());
        if (this.midia == null) {
            
            lblTitulo.setText("Cadastro");
            hBoxId.setVisible(false);
            cmbGenero.getSelectionModel().selectFirst();
            this.midia = new Midia();
        } else {
            midia = midiaService.GetMidiaById(midia.getId());
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
        this.midia.setTitulo(txtTitulo.getText());
        this.midia.setDescricao(txtDescricao.getText());
        this.midia.setTrailer(txtTrailer.getText());
        this.midia.setVideo(txtVideo.getText());
        this.midia.setCapa(txtCapa.getText());
        this.midia.setGenero(cmbGenero.getValue());
        
        
        
        
        
        
        return this.midia;
    }
    
}
