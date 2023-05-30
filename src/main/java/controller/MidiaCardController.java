package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
    
    private HomeController homeController;
    private Midia midia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void BuildCard(Midia midia, HomeController homeController) {
        this.homeController = homeController;
        this.midia = midia;
        
        System.out.println(this.midia.getTitulo());
    }
    
    private void PlayMidia() {
        homeController.CarregarMidia(this.midia);
    }
}
