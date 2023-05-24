package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.App;
import model.Genero;
import service.GeneroService;
import ui.HBoxCell;
import ui.NoSelectionModel;
import ui.SVGIcon;

public class GeneroListController implements Initializable {
    
    @FXML
    private ListView lvGeneros;
    
    private ArrayList<Genero> generos;
    private GeneroService generoService;
    
    private TextField txtNome;
    private Label lblId;
    private Stage stageGenero;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generoService = new GeneroService();
        
        CarregarListViewGenero();
    }
    
    private void CarregarListViewGenero() {
        generos = generoService.Listar();
        
        ArrayList<HBoxCell> hBoxCells = new ArrayList<>();
        for (Genero genero : generos) {
            ArrayList<Node> nodes = new ArrayList<>();
            
            Label lblId = HBoxCell.getHBoxLabel(String.valueOf(genero.getId()), Pos.CENTER_LEFT, Priority.NEVER);
            lblId.setTooltip(new Tooltip("ID"));
            
            Label lblNome = HBoxCell.getHBoxLabel(genero.getNome(), Pos.CENTER_LEFT, Priority.ALWAYS);
            lblId.setTooltip(new Tooltip("Nome"));
            
            Button btnEditar = HBoxCell.getHBoxButton("");
            btnEditar.setGraphic(SVGIcon.getIcon("Editar", "#0000FF"));
            btnEditar.setOnAction(e -> { 
                try {
                    AbrirAtualizacao(genero);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            
            Button btnExcluir = HBoxCell.getHBoxButton("");
            btnExcluir.setGraphic(SVGIcon.getIcon("Excluir", "#FF0000"));
            btnExcluir.setOnAction(e -> {
                ExcluirGenero(genero);
            });
            
            nodes.add(lblId);
            nodes.add(lblNome);
            nodes.add(btnEditar);
            nodes.add(btnExcluir);
            
            hBoxCells.add(new HBoxCell(nodes, 10));
        }
        
        lvGeneros.setItems(FXCollections.observableList(hBoxCells));
        lvGeneros.setSelectionModel(new NoSelectionModel());
    }
    
    private void AbrirTelaCadastro(ArrayList<HBoxCell> cells, ArrayList<Button> botoes, String titulo) throws IOException {
        FXMLLoader loader = App.newFXML("genero");
        AnchorPane apGenero = (AnchorPane) loader.load();
        
        Scene sceneGenero = App.newScene(apGenero, 400, 300);
        stageGenero = App.newWindow(sceneGenero);
        stageGenero.initModality(Modality.APPLICATION_MODAL);
        
        GeneroController generoController = loader.getController();
        
        generoController.Carregar(titulo, cells, botoes);
        
        stageGenero.show();
    }
    
    @FXML
    private void AbrirCadastro() throws IOException {
        ArrayList<Node> nodesNome = new ArrayList<>();
        nodesNome.add(HBoxCell.getHBoxLabel("Nome", Pos.CENTER, Priority.NEVER));
        txtNome = HBoxCell.getHBoxTextField();
        nodesNome.add(txtNome);
        HBoxCell cellNome = new HBoxCell(nodesNome, 10);
        ArrayList<HBoxCell> cells = new ArrayList<>();
        cells.add(cellNome);
        
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnCadastrar = HBoxCell.getHBoxButton("Cadastrar");
        botoes.add(btnCadastrar);
        btnCadastrar.setOnAction(e -> {
            CadastrarGenero();
        });
        
        AbrirTelaCadastro(cells, botoes, "Cadastro de Gênero");
    }
    
    private void AbrirAtualizacao(Genero genero) throws IOException {
        ArrayList<Node> nodesId = new ArrayList<>();
        nodesId.add(HBoxCell.getHBoxLabel("ID", Pos.CENTER, Priority.NEVER));
        lblId = HBoxCell.getHBoxLabel(String.valueOf(genero.getId()), Pos.CENTER, Priority.NEVER);
        nodesId.add(lblId);
        HBoxCell cellId = new HBoxCell(nodesId, 10);
        
        ArrayList<Node> nodesNome = new ArrayList<>();
        nodesNome.add(HBoxCell.getHBoxLabel("Nome", Pos.CENTER, Priority.NEVER));
        txtNome = HBoxCell.getHBoxTextField();
        txtNome.setText(genero.getNome());
        nodesNome.add(txtNome);
        HBoxCell cellNome = new HBoxCell(nodesNome, 10);
        
        ArrayList<HBoxCell> cells = new ArrayList<>();
        cells.add(cellId);
        cells.add(cellNome);
        
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnAtualizar = HBoxCell.getHBoxButton("Editar");
        botoes.add(btnAtualizar);
        btnAtualizar.setOnAction(e -> {
            AtualizarGenero();
        });
        
        AbrirTelaCadastro(cells, botoes, "Atualizar Gênero");
    }
    
    private void CadastrarGenero() {
        this.generoService.Inserir(new Genero(txtNome.getText()));
        stageGenero.close();
        CarregarListViewGenero();
    }
    
    private void AtualizarGenero() {
        this.generoService.Atualizar(new Genero(Integer.parseInt(lblId.getText()), txtNome.getText()));
        stageGenero.close();
        CarregarListViewGenero();
    }
    
    private void ExcluirGenero(Genero genero) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirme a exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja realmente excluir?");
        
        Optional<ButtonType> result = confirmacao.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.generoService.Excluir(genero);
            CarregarListViewGenero();
        }
    }
}
