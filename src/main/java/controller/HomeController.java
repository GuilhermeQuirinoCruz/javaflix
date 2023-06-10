package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import main.App;
import model.Genero;
import model.Midia;
import model.Usuario;
import service.GeneroService;
import service.MidiaService;
import service.UsuarioService;
import ui.ListViewNoSelectionModel;

public class HomeController implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgUser;
    @FXML
    private MenuButton mbConta;
    @FXML
    private AnchorPane apHome;
    
    private GeneroService generoService;
    private MidiaService midiaService;
    private UsuarioService usuarioService;
    private FXMLLoader midiaCardRowLoader;
    private Usuario usuario;
    private boolean favoritos;

    private final String URL_LOGO = "https://firebasestorage.googleapis.com/v0/b/javaflix-7d4ee.appspot.com/o/logo.png?alt=media&token=b8a3aa5d-e405-40aa-a5b0-a5be6aa241ae&_gl=1*690tts*_ga*MTgxMjY2Nzg3LjE2ODQyNzc2OTE.*_ga_CW55HF8NVT*MTY4NjE0MTgzMy42LjEuMTY4NjE0NzA5Mi4wLjAuMA..";
    private final String URL_USER = "https://cdn-icons-png.flaticon.com/512/8792/8792047.png";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("JAVAFLIX");
        
        generoService = new GeneroService();
        midiaService = new MidiaService();
        usuarioService = new UsuarioService();

        Platform.runLater(new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                imgLogo.setImage(new Image(URL_LOGO));
                imgUser.setImage(new Image(URL_USER));

                return null;
            }
        }));

        Platform.runLater(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
//                Thread.sleep(10);
                CarregarListViewMidias();

                return null;
            }
        });
    }
    
    private void SetAnchor(Node node)
    {
        AnchorPane.setBottomAnchor(node, 0d);
        AnchorPane.setTopAnchor(node, 0d);
        AnchorPane.setLeftAnchor(node, 0d);
        AnchorPane.setRightAnchor(node, 0d);
    }
    
    @FXML
    private void CarregarListViewMidias() throws IOException {
        ListView lvMidias = new ListView();
        apHome.getChildren().setAll(lvMidias);
        SetAnchor(lvMidias);
        
        lvMidias.setSelectionModel(new ListViewNoSelectionModel());
        lvMidias.setFocusTraversable(false);
        lvMidias.getItems().clear();
        
        final HomeController homeController = this;
        ArrayList<Genero> generos = generoService.Listar();
        for (Genero genero : generos) {
            ArrayList<Midia> midias = midiaService.ListarPorGenero(genero.getId());
            if (midias != null && !midias.isEmpty()) {
                Label lblGenero = new Label(genero.getNome());
                lvMidias.getItems().add(lblGenero);
                
                midiaCardRowLoader = App.newFXML("midiaCardRow");
                ListView lvCards = (ListView) midiaCardRowLoader.load();
                
                MidiaCardRowController midiaCardRowController = midiaCardRowLoader.getController();
                midiaCardRowController.CarregarCards(genero, homeController);
                
                lvMidias.getItems().add(lvCards);
            }
        }
        
        favoritos = false;
    }
    
    public void PlayMidia(Midia midia) throws IOException {
        FXMLLoader loader = App.newFXML("midiaPlayer");
        AnchorPane apMediaPlayer = (AnchorPane) loader.load();
        
        Scene sceneGenero = App.newScene(apMediaPlayer, 600, 400);
        Stage stageMidiaPlayer = App.newWindow(sceneGenero);
        stageMidiaPlayer.setTitle(midia.getTitulo());
        stageMidiaPlayer.initModality(Modality.APPLICATION_MODAL);
        
        MidiaPlayerController midiaPlayerController = loader.getController();
        midiaPlayerController.SetMidia(midia);
        stageMidiaPlayer.show();
    }
    
    public void SetUsuario(Usuario usuario){
        this.usuario = usuario;
        mbConta.setText(this.usuario.getNome());
    }
    
    @FXML
    private void AbrirConta() throws IOException {
        FXMLLoader loader = App.newFXML("usuario");
        AnchorPane apUsuario = (AnchorPane) loader.load();
        
        UsuarioController usuarioController = loader.getController();
        usuarioController.SetUsuario(this.usuario);
        usuarioController.SetHomeController(this);
        
        Stage stageCadastro = App.newWindow(App.newScene(apUsuario, 600, 400));
        
        stageCadastro.initModality(Modality.APPLICATION_MODAL);
        stageCadastro.show();
    }
    
    @FXML
    private void CarregarFavoritos() throws IOException {
        ScrollPane spFavoritos = new ScrollPane();
        GridPane gpFavoritos = new GridPane();
        final int colunas = 3;
        
        double colWidth = 100.0 / colunas;
        for (int i = 0; i < colunas; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(colWidth);
            gpFavoritos.getColumnConstraints().add(col);
        }
        
        apHome.getChildren().setAll(spFavoritos);
        
        SetAnchor(spFavoritos);
        spFavoritos.setContent(gpFavoritos);
        spFavoritos.fitToWidthProperty().set(true);
        spFavoritos.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
        gpFavoritos.setHgap(10);
        gpFavoritos.setVgap(10);
        
        int i = 0;
        int j = 0;
        ArrayList<Midia> midias = usuarioService.ListaMidiasFavoritadas(usuario.getId());
        for (Midia midia : midias) {
            final FXMLLoader midiaCardLoader = App.newFXML("midiaCard");
            final AnchorPane apMidiaCard = (AnchorPane) midiaCardLoader.load();
            
            MidiaCardController midiaCardController = midiaCardLoader.getController();
            midiaCardController.BuildCard(midia, this);

            gpFavoritos.add(apMidiaCard, i, j);
            i += 1;
            if (i == colunas) {
                i = 0;
                j += 1;
            }
        }
        
        favoritos = true;
    }
    
    public void FavoritarMidia(int idMidia, String titulo) throws IOException {
        if(usuarioService.IsMidiaFavoritada(usuario.getId(), idMidia)) {
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Removendo favorito");
            confirmacao.setHeaderText(null);
            confirmacao.setContentText("Deseja realmente remover '"
                + titulo 
                + "' dos seus favoritos?");

            Optional<ButtonType> result = confirmacao.showAndWait();
            if (result.get() == ButtonType.OK) {
                usuarioService.DesfavoritaMidia(usuario.getId(), idMidia);
                
                if (favoritos) {
                    CarregarFavoritos();
                }
            }
        } else {
            usuarioService.FavoritaMidia(usuario.getId(), idMidia);
        }
    }
    
    public void ExibirDetalhesMidia(Midia midia) throws IOException {
        FXMLLoader loader = App.newFXML("detalhes");
        AnchorPane apDetalhes = (AnchorPane) loader.load();
        
        DetalhesController detalhesController = loader.getController();
        detalhesController.CarregarMidia(midia);
        detalhesController.SetHomeController(this);
        
        Stage stageDetalhes = App.newWindow(App.newScene(apDetalhes, 600, 500));
        
        stageDetalhes.initModality(Modality.APPLICATION_MODAL);
        stageDetalhes.show();
    }
    
    @FXML
    public void Sair() throws IOException {
        App.changeScene(App.newScene(App.newFXML("login"), 800, 600));
    }
    
    public boolean isFavorited(int idMidia){
        return true;
    }
}
