
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import model.Midia;
import service.GeneroService;
import service.MidiaService;
import ui.HBoxCell;
import ui.NoSelectionModel;
import ui.SVGIcon;


public class MidiaListController implements Initializable {
    @FXML
    private ListView lvMidias;
    
    private ArrayList<Midia> midias;
    private MidiaService midiaService ;
    private GeneroService generoService;
    private TextField txtTitulo;
    private TextField txtDescricao;
    private TextField txtVideo;
    private TextField txtCapa;
    private TextField txtTrailer;
    private ComboBox cmbGenero;
    private Label lblId;
    private Label lblTitulo;
    private Label lblNomeGenero;
    private Stage stageMidia;
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        midiaService = new MidiaService();
        generoService = new GeneroService();
        CarregarListViewMidia();
        
        
        
        
    }    
    
    private void CarregarListViewMidia(){
        midias = midiaService.ListarResumido();
        ArrayList<HBoxCell> hBoxCells = new ArrayList<>();
        for (Midia midia : midias) {
            ArrayList<Node> nodes = new ArrayList<>();
            
            lblId = HBoxCell.getHBoxLabel(String.valueOf(midia.getId()), Pos.CENTER_LEFT, Priority.NEVER);
            lblId.setTooltip(new Tooltip("ID"));
            
            lblTitulo = HBoxCell.getHBoxLabel(midia.getTitulo(), Pos.CENTER_LEFT, Priority.ALWAYS);
            lblTitulo.setTooltip(new Tooltip("Titulo"));
            
            lblNomeGenero = HBoxCell.getHBoxLabel(midia.getGenero().getNome(), Pos.CENTER_LEFT, Priority.ALWAYS);
            lblNomeGenero.setTooltip(new Tooltip("Genero"));
            
            Button btnEditar = HBoxCell.getHBoxButton("");
            btnEditar.setGraphic(SVGIcon.getIcon("Editar", "#0000FF"));
            
            btnEditar.setOnAction(e -> { 
            
                //try {
//                    AbrirAtualizacao(i);
//                    System.out.println(midia.getId() + midia.getTitulo());
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
            });
            
            Button btnExcluir = HBoxCell.getHBoxButton("");
            btnExcluir.setGraphic(SVGIcon.getIcon("Excluir", "#FF0000"));
//            btnExcluir.setOnAction(e -> {
//                ExcluirMidia(midia);
//            });
            
            nodes.add(lblId);
            nodes.add(lblTitulo);
            nodes.add(lblNomeGenero);
            nodes.add(btnEditar);
            nodes.add(btnExcluir);
            
            hBoxCells.add(new HBoxCell(nodes, 10));
        }
        
        lvMidias.setItems(FXCollections.observableList(hBoxCells));
        lvMidias.setSelectionModel(new NoSelectionModel());
        
        
    }
    
    private void AbrirTelaCadastro(ArrayList<HBoxCell> cells, ArrayList<Button> botoes, String titulo) throws IOException {
        FXMLLoader loader = App.newFXML("midia");
        AnchorPane apMidia = (AnchorPane) loader.load();
        
        Scene sceneMidia = App.newScene(apMidia, 400, 300);
        stageMidia = App.newWindow(sceneMidia);
        stageMidia.initModality(Modality.APPLICATION_MODAL);
        
        MidiaController midiaController = loader.getController();
        
        midiaController.Carregar(titulo, cells, botoes);
        
        stageMidia.show();
    }
    
    
    @FXML
    private void AbrirCadastro() throws IOException {
        ArrayList<HBoxCell> cells = BuildScene(); 
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnCadastrar = HBoxCell.getHBoxButton("Cadastrar");
        botoes.add(btnCadastrar);
        btnCadastrar.setOnAction(e -> {
            CadastrarMidia();
        });
        
        AbrirTelaCadastro(cells, botoes, "Cadastro de Midias");
    }
    
    @FXML
    private void AbrirAtualizacao(int id) throws IOException {
        Midia midia = midiaService.GetMidiaById(id);
        ArrayList<HBoxCell> cells = new ArrayList<>();
        ArrayList<Node> nodesId = new ArrayList<>();
        nodesId.add(HBoxCell.getHBoxLabel("Id", Pos.CENTER, Priority.NEVER));
        lblId = HBoxCell.getHBoxLabel(String.valueOf(midia.getId()), Pos.CENTER, Priority.NEVER);
        nodesId.add(lblId);
        HBoxCell cellId = new HBoxCell(nodesId, 10);
        cells.add(cellId);
        cells.addAll(BuildScene());
        txtTitulo.setText(midia.getTitulo());
        txtDescricao.setText(midia.getDescricao());
        txtCapa.setText(midia.getCapa());
        txtVideo.setText(midia.getVideo());
        txtTrailer.setText(midia.getTrailer());
        
        
        
        ArrayList<Button> botoes = new ArrayList<>();
        Button btnAtualizar = HBoxCell.getHBoxButton("Atualizar");
        botoes.add(btnAtualizar);
        btnAtualizar.setOnAction(e -> {
            //CadastrarMidia();
        });
        
        AbrirTelaCadastro(cells, botoes, "Atualização de Midias");
    }
    
    
    private ArrayList<HBoxCell> BuildScene(){
         // Titulo
        ArrayList<Node> nodesTitulo = new ArrayList<>();
        nodesTitulo.add(HBoxCell.getHBoxLabel("Titulo", Pos.CENTER, Priority.NEVER));
        txtTitulo = HBoxCell.getHBoxTextField();
        nodesTitulo.add(txtTitulo);
        HBoxCell cellTitulo = new HBoxCell(nodesTitulo, 10);
        
        //Descricao
        ArrayList<Node> nodesDescricao = new ArrayList<>();
        nodesDescricao.add(HBoxCell.getHBoxLabel("Descricao", Pos.CENTER, Priority.NEVER));
        txtDescricao = HBoxCell.getHBoxTextField();
        nodesDescricao.add(txtDescricao);
        HBoxCell cellDescricao = new HBoxCell(nodesDescricao, 10);
        
        //Video
        ArrayList<Node> nodesVideo = new ArrayList<>();
        nodesVideo.add(HBoxCell.getHBoxLabel("Video", Pos.CENTER, Priority.NEVER));
        txtVideo = HBoxCell.getHBoxTextField();
        nodesVideo.add(txtVideo);
        HBoxCell cellVideo = new HBoxCell(nodesVideo, 10);
        
        //Capa
        ArrayList<Node> nodesCapa = new ArrayList<>();
        nodesCapa.add(HBoxCell.getHBoxLabel("Capa", Pos.CENTER, Priority.NEVER));
        txtCapa = HBoxCell.getHBoxTextField();
        nodesCapa.add(txtCapa);
        HBoxCell cellCapa = new HBoxCell(nodesCapa, 10);
        
        //Trailer 
        ArrayList<Node> nodesTrailer = new ArrayList<>();
        nodesTrailer.add(HBoxCell.getHBoxLabel("Trailer", Pos.CENTER, Priority.NEVER));
        txtTrailer = HBoxCell.getHBoxTextField();
        nodesTrailer.add(txtTrailer);
        HBoxCell cellTrailer = new HBoxCell(nodesTrailer, 10);
        
        //Genero
        ArrayList<Node> nodesGenero = new ArrayList<>();
        nodesGenero.add(HBoxCell.getHBoxLabel("Genero", Pos.CENTER, Priority.NEVER));
        cmbGenero = new ComboBox();
        ArrayList<Genero> generos = generoService.Listar();
        cmbGenero.setItems(FXCollections.observableList(generos));
        cmbGenero.getSelectionModel().selectFirst();
        
        
        
        nodesGenero.add(cmbGenero);
        HBoxCell cellGenero = new HBoxCell(nodesGenero, 10);
        ArrayList<HBoxCell> cells = new ArrayList<>();
        cells.add(cellTitulo);
        cells.add(cellDescricao);
        cells.add(cellVideo);
        cells.add(cellCapa);
        cells.add(cellTrailer);
        cells.add(cellGenero);
        return cells;
        
        
    }
    
    private void CadastrarMidia(){
        Midia midia = new Midia();
        midia.setTitulo(txtTitulo.getText());
        midia.setDescricao(txtDescricao.getText());
        midia.setVideo(txtVideo.getText());
        midia.setCapa(txtCapa.getText());
        midia.setTrailer(txtTrailer.getText());
        midia.setGenero((Genero)cmbGenero.getValue());
        midiaService.Inserir(midia);
        stageMidia.close();
        CarregarListViewMidia();
     
    }
    
    
    
    
    
    
    
    
    
}
