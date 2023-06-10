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
import model.Midia;
import service.MidiaService;
import ui.HBoxCell;
import ui.TableViewNoSelectionModel;
import ui.SVGIcon;

public class MidiaListController implements Initializable {
    
    @FXML
    private TableView tvMidias;
    @FXML
    private TableColumn<Midia, String> tcId;
    @FXML
    private TableColumn<Midia, String> tcTitulo;
    @FXML
    private TableColumn<Midia, String> tcGenero;
    @FXML
    private TableColumn<Midia, Void> tcEditar;
    @FXML
    private TableColumn<Midia, Void> tcExcluir;
    
    private MidiaService midiaService;
    private MidiaController midiaController;
    private Stage stageMidia;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        midiaService = new MidiaService();
        CarregarTableView();
    }  
    
    private void CarregarTableView(){
        ArrayList<Midia> midias = midiaService.ListarResumido();
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        
        tcEditar.setCellFactory(col -> new TableCell<Midia, Void>() {
            private final HBox container;
            private final Button btnEditar = HBoxCell.getHBoxButton("");
            
            {
                btnEditar.setGraphic(SVGIcon.getIcon("Editar", "#0000FF"));
                btnEditar.setOnAction(e -> {
                    Midia midia = getTableView().getItems().get(getIndex());
                    try {
                        AbrirAtualizacao(midia);
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
        
        tcExcluir.setCellFactory(col -> new TableCell<Midia, Void>() {
            private final HBox container;
            private final Button btnExcluir = HBoxCell.getHBoxButton("");
            
            {
                btnExcluir.setGraphic(SVGIcon.getIcon("Excluir", "#FF0000"));
                btnExcluir.setOnAction(e -> {
                    Midia midia = getTableView().getItems().get(getIndex());
                    ExcluirMidia(midia.getId());
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
        
        tvMidias.getItems().setAll(FXCollections.observableList(midias));
        tvMidias.setSelectionModel(new TableViewNoSelectionModel(tvMidias));
    }
    
    private void AbrirTelaCadastro(Midia midia, boolean editar, ArrayList<Button> botoes) throws IOException {
        FXMLLoader loader = App.newFXML("midia");
        AnchorPane apMidia = (AnchorPane) loader.load();
        
        Scene sceneMidia = App.newScene(apMidia, 400, 500);
        stageMidia = App.newWindow(sceneMidia);
        stageMidia.initModality(Modality.APPLICATION_MODAL);
        
        midiaController = loader.getController();
        midiaController.Carregar(midia, editar, botoes);
        
        stageMidia.show();
    }
    
    @FXML
    private void AbrirCadastro() throws IOException {
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnCadastrar = HBoxCell.getHBoxButton("Cadastrar");
        botoes.add(btnCadastrar);
        btnCadastrar.setOnAction(e -> {
            CadastrarMidia();
        });
        
        AbrirTelaCadastro(null, false, botoes);
    }
    
    private void AbrirAtualizacao(Midia midia) throws IOException {
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnAtualizar = HBoxCell.getHBoxButton("Atualizar");
        botoes.add(btnAtualizar);
        btnAtualizar.setOnAction(e -> {
            AtualizarMidia();
        });
        
        AbrirTelaCadastro(midia, true, botoes);
    }
     
    private void CadastrarMidia() {
        this.midiaService.Inserir(this.midiaController.getMidia());
        stageMidia.close();
        CarregarTableView();
    }
    
    private void AtualizarMidia() {
        this.midiaService.Atualizar(this.midiaController.getMidia());
        stageMidia.close();
        CarregarTableView();
    }
     
    private void ExcluirMidia(int idMidia) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirme a exclusão");
        confirmacao.setHeaderText(null);
        confirmacao.setContentText("Deseja realmente excluir?");
        
        Optional<ButtonType> result = confirmacao.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.midiaService.Excluir(idMidia);
            CarregarTableView();
        }
    }
}
