package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Genero;
import service.GeneroService;


public class GeneroController implements Initializable {

    @FXML
    private TableView<Genero> tableViewGenero;
    @FXML
    private TableColumn<Genero, String> tcId;
    @FXML
    private TableColumn<Genero, String> tcNome;
    @FXML
    private Label lblId;
    @FXML
    private TextField txtNome;
    
    private ArrayList<Genero> generos;
    private GeneroService generoService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generoService = new GeneroService();
        CarregarTableViewGenero();
        
        tableViewGenero.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> SelecionarGenero(newValue));
    }
    
    private void CarregarTableViewGenero() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        generos = generoService.Listar();
        tableViewGenero.setItems(FXCollections.observableArrayList(generos));
    }
    
    @FXML
    private void Inserir(ActionEvent event) {
        this.generoService.Inserir(new Genero(txtNome.getText()));
        CarregarTableViewGenero();
        LimparCampos();
    }
    
    @FXML
    private void Atualizar(ActionEvent event) {
        this.generoService.Atualizar(new Genero(Integer.parseInt(lblId.getText()), txtNome.getText()));
        CarregarTableViewGenero();
        LimparCampos();
    }
    
    @FXML
    private void Excluir(ActionEvent event) {
        this.generoService.Excluir(new Genero(Integer.parseInt(lblId.getText())));
        CarregarTableViewGenero();
        LimparCampos();
    }
    
    private void SelecionarGenero(Genero genero) {
        if (genero == null)
            return;
        
        lblId.setText(String.valueOf(genero.getId()));
        txtNome.setText(genero.getNome());
    }
    
    private void LimparCampos() {
        lblId.setText("");
        txtNome.setText("");
    }
}
