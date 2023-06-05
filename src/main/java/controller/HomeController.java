package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.App;
import model.Genero;
import model.Midia;
import service.GeneroService;
import service.MidiaService;

public class HomeController implements Initializable {

    @FXML
    private Label lblNome;
    @FXML
    private ListView lvMidias;
    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView imgUser;
    
    private GeneroService generoService;
    private MidiaService midiaService;
    private ListView lvCards;
    private FXMLLoader midiaCardRowLoader;
    
    private final String URL_LOGO = "https://img.freepik.com/icones-gratis/netflix_318-566093.jpg";
    private final String URL_USER = "https://cdn-icons-png.flaticon.com/512/8792/8792047.png";
    
    private ArrayList<ListView> listViewsCards = new ArrayList<>();;
    private ObservableList<Node> nodes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("JAVAFLIX");
        
        generoService = new GeneroService();
        midiaService = new MidiaService();

        Platform.runLater(new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                imgLogo.setImage(new Image(URL_LOGO));
                imgUser.setImage(new Image(URL_USER));

                return null;
            }
        }));

        Task taskLvMidias = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
//                Thread.sleep(10);
                CarregarListViewMidias();

                return null;
            }
        };
        
        Platform.runLater(taskLvMidias);
    }
    
    @FXML
    private void CarregarListViewMidias() throws IOException {
        lvMidias.getItems().clear();
        
        final HomeController homeController = this;
        ArrayList<Genero> generos = generoService.Listar();
        listViewsCards = new ArrayList<>();
        
        for (Genero genero : generos) {
            Label lblGenero = new Label(genero.getNome());
            lvMidias.getItems().add(lblGenero);
            
            ArrayList<Midia> midias = midiaService.ListarPorGenero(genero.getId());
            if (midias != null) {
                midiaCardRowLoader = App.newFXML("midiaCardRow");
                lvCards = (ListView) midiaCardRowLoader.load();
                
                MidiaCardRowController midiaCardRowController = midiaCardRowLoader.getController();
                midiaCardRowController.CarregarCards(genero.getId(), homeController);
                
                lvMidias.getItems().add(lvCards);
                
                listViewsCards.add(lvCards);
            }
        }
    }
    
    public void PlayMidia(Midia midia) throws IOException {
        FXMLLoader loader = App.newFXML("midiaPlayer");
        AnchorPane apMediaPlayer = (AnchorPane) loader.load();
        
        Scene sceneGenero = App.newScene(apMediaPlayer, 400, 300);
        Stage stageMidiaPlayer = App.newWindow(sceneGenero);
        stageMidiaPlayer.initModality(Modality.APPLICATION_MODAL);
        
        MidiaPlayerController midiaPlayerController = loader.getController();
        midiaPlayerController.setMidia(midia);
        
        stageMidiaPlayer.show();
    }
}
