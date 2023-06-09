package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private TableView tvGeneros;
    @FXML
    private TableColumn<Genero, String> tcId;
    @FXML
    private TableColumn<Genero, String> tcNome;
    @FXML
    private TableColumn<Genero, Void> tcEditar;
    @FXML
    private TableColumn<Genero, Void> tcExcluir;
    
    private GeneroService generoService;
    private Stage stageGenero;
    GeneroController generoController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generoService = new GeneroService();
        
        CarregarTableView();
    }
    
    private void CarregarTableView() {
        ArrayList<Genero> generos = generoService.Listar();
        
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        tcEditar.setCellFactory(col -> new TableCell<Genero, Void>() {
            private final HBox container;
            private final Button btnEditar = HBoxCell.getHBoxButton("");
            
            {
                btnEditar.setGraphic(SVGIcon.getIcon("Editar", "#0000FF"));
                btnEditar.setOnAction(e -> {
                    Genero genero = getTableView().getItems().get(getIndex());
                    try {
                        AbrirAtualizacao(genero);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                container = new HBox(10, btnEditar);
                container.setAlignment(Pos.CENTER);
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                btnEditar.disableProperty().unbind();
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
        
        tcExcluir.setCellFactory(col -> new TableCell<Genero, Void>() {
            private final HBox container;
            private final Button btnExcluir = HBoxCell.getHBoxButton("");
            
            {
                btnExcluir.setGraphic(SVGIcon.getIcon("Excluir", "#FF0000"));
                btnExcluir.setOnAction(e -> {
                    Genero genero = getTableView().getItems().get(getIndex());
                    ExcluirGenero(genero.getId());
                });
                container = new HBox(10, btnExcluir);
                container.setAlignment(Pos.CENTER);
            }

            @Override
            public void updateItem(Void item, boolean empty) {
                btnExcluir.disableProperty().unbind();
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
        
        tvGeneros.getItems().setAll(FXCollections.observableList(generos));
        tvGeneros.setSelectionModel(new NoSelectionModel(tvGeneros));
    }
        
    private void AbrirTelaCadastro(Genero genero, boolean editar, ArrayList<Button> botoes) throws IOException {
        FXMLLoader loader = App.newFXML("genero");
        AnchorPane apGenero = (AnchorPane) loader.load();
        
        Scene sceneGenero = App.newScene(apGenero, 400, 300);
        stageGenero = App.newWindow(sceneGenero);
        stageGenero.initModality(Modality.APPLICATION_MODAL);
        
        generoController = loader.getController();
        generoController.Carregar(genero, editar, botoes);
        
        stageGenero.show();
    }
    
    @FXML
    private void AbrirCadastro() throws IOException {
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnCadastrar = HBoxCell.getHBoxButton("Cadastrar");
        botoes.add(btnCadastrar);
        btnCadastrar.setOnAction(e -> {
            CadastrarGenero();
        });
        
        AbrirTelaCadastro(null, false, botoes);
    }
    
    private void AbrirAtualizacao(Genero genero) throws IOException {
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnAtualizar = HBoxCell.getHBoxButton("Atualizar");
        botoes.add(btnAtualizar);
        btnAtualizar.setOnAction(e -> {
            AtualizarGenero();
        });
        
        AbrirTelaCadastro(genero, true, botoes);
    }
    
    private void CadastrarGenero() {
        this.generoService.Inserir(generoController.getGenero());
        stageGenero.close();
        CarregarTableView();
    }
    
    private void AtualizarGenero() {
        this.generoService.Atualizar(generoController.getGenero());
        stageGenero.close();
        CarregarTableView();
    }
    
    private void ExcluirGenero(int idGenero) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirme a exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja realmente excluir?");
        
        Optional<ButtonType> result = confirmacao.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.generoService.Excluir(idGenero);
            CarregarTableView();
        }
    }
}
