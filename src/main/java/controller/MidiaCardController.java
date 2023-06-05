package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Midia;

public class MidiaCardController implements Initializable {

    @FXML
    private ImageView imgCapa;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnFavorito;
    @FXML
    private Button btnDetalhes;
    @FXML
    private HBox hBoxBotoes;
    
    private HomeController homeController;
    private Midia midia;
    
    private final String CAPA_PADRAO = "https://i.ytimg.com/vi/6kCSVT3r_Qg/hqdefault.jpg";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void BuildCard(Midia midia, HomeController homeController) {
        OcultarBotoes();
        this.homeController = homeController;
        this.midia = midia;
        
        Task taskCapa = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                if(midia.getCapa().equals("")) {
                    imgCapa.setImage(new Image(CAPA_PADRAO));
                } else {
                    imgCapa.setImage(new Image(midia.getCapa()));
                }
                return null;
            }
        };
        
        Thread threadCapa = new Thread(taskCapa);
        threadCapa.setDaemon(true);
        threadCapa.start();
    }
    
    @FXML
    private void ExibirBotoes() {
        hBoxBotoes.setVisible(true);
    }
    
    @FXML
    private void OcultarBotoes() {
        hBoxBotoes.setVisible(false);
    }
    
    @FXML
    private void PlayMidia() throws IOException {
        homeController.PlayMidia(this.midia);
    }
}
