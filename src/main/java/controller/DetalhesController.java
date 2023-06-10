package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import model.Midia;
import ui.SVGIcon;

public class DetalhesController implements Initializable {
    
    @FXML
    private AnchorPane apMediaView;
    @FXML
    private MediaView mvTrailer;
    @FXML
    private Button btnPlay;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDescricao;
    
    private MediaPlayer mediaPlayer;
    private HomeController homeController;
    private Midia midia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            ((Stage)apMediaView.getScene().getWindow()).setOnHiding( e -> {
                mediaPlayer.dispose();
            });
            
            btnPlay.setGraphic(SVGIcon.getIcon("Play", "#000000"));
        });
    }
    
    public void SetHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
    
    public void CarregarMidia(Midia midia) {
        this.midia = midia;
        lblTitulo.setText(midia.getTitulo());
        lblDescricao.setText("Gênero: " + midia.getGenero().getNome()
                + "\nDescrição: " + midia.getDescricao());
//        lblDescricao.setText(midia.getDescricao());
        
        Platform.runLater(() ->{
            if (!midia.getTrailer().equals(""))
            {
                mediaPlayer = new MediaPlayer(new Media(midia.getTrailer()));
            } else {
                mediaPlayer = new MediaPlayer(new Media("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
            }

            mediaPlayer.setVolume(0.1);
            mvTrailer.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            });

            mvTrailer.fitWidthProperty().bind(apMediaView.widthProperty());
            mvTrailer.fitHeightProperty().bind(apMediaView.heightProperty());
        });
    }
    
    @FXML
    private void Assistir() throws IOException {
        homeController.PlayMidia(this.midia);
        ((Stage)apMediaView.getScene().getWindow()).close();
    }
}
