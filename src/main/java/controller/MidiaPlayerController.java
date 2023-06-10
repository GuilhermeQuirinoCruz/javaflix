package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import model.Midia;
import ui.SVGIcon;

public class MidiaPlayerController implements Initializable {

    @FXML
    private AnchorPane apMediaView;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider sVolume;
    @FXML
    private ProgressBar pgBar;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnFullscreen;
    @FXML
    private HBox hBoxControles;
    
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Platform.runLater(() -> {
            ((Stage)apMediaView.getScene().getWindow()).setOnHiding( e -> {
                mediaPlayer.dispose();
            });
            
            btnPlay.setGraphic(SVGIcon.getIcon("Play", "#000000"));
            btnFullscreen.setGraphic(SVGIcon.getIcon("Fullscreen", "#000000"));
        });
    }
    
    public void SetMidia(Midia midia) {
        Platform.runLater(() -> {
            if (!midia.getVideo().equals(""))
            {
                mediaPlayer = new MediaPlayer(new Media(midia.getVideo()));
            } else {
                mediaPlayer = new MediaPlayer(new Media("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
            }

            mediaPlayer.setVolume(0.5);
            mediaView.setMediaPlayer(mediaPlayer);

            BindProgress();
            BindSize();
            BindVolume();
            AddSeekBehavior();
            
            mediaPlayer.setOnError(() -> {
                System.out.println("Erro ao carregar vídeo. Tentando novamente...");
                SetMidia(midia);
            });
        });
    }
    
    @FXML
    private void PlayMidia() {
        var status = mediaPlayer.getStatus();
        if (status.equals(MediaPlayer.Status.PAUSED) || status.equals(MediaPlayer.Status.READY))
        {
            btnPlay.setGraphic(SVGIcon.getIcon("Pause", "#000000"));
            mediaPlayer.play();
        } else {
            btnPlay.setGraphic(SVGIcon.getIcon("Play", "#000000"));
            mediaPlayer.pause();
        }
    }
    
    private void BindVolume() {
        sVolume.valueProperty().addListener((Observable observable) -> {
            mediaPlayer.setVolume(sVolume.getValue() / 100);
        });
        
        sVolume.setValue(mediaPlayer.getVolume() * 100);
    }
    
    private void BindProgress() {
        var binding =
        Bindings.createDoubleBinding(
            () -> {
              var currentTime = mediaPlayer.getCurrentTime();
              var duration = mediaPlayer.getMedia().getDuration();
              if (IsValidDuration(currentTime) && IsValidDuration(duration)) {
                return currentTime.toMillis() / duration.toMillis();
              }
              return ProgressBar.INDETERMINATE_PROGRESS;
            },  mediaPlayer.currentTimeProperty(),
                mediaPlayer.getMedia().durationProperty());
        
        pgBar.progressProperty().bind(binding);
    }
    
    private boolean IsValidDuration(Duration d) {
        return d != null && !d.isIndefinite() && !d.isUnknown();
    }
    
    private void BindSize() {
        mediaView.fitWidthProperty().bind(apMediaView.widthProperty());
        mediaView.fitHeightProperty().bind(apMediaView.heightProperty());
    }
    
    private void AddSeekBehavior() {
        EventHandler<MouseEvent> onClickAndOnDragHandler =
        e -> {
          var duration = mediaPlayer.getMedia().getDuration();
          if (IsValidDuration(duration)) {
            var seekTime = duration.multiply(e.getX() / pgBar.getWidth());
            mediaPlayer.seek(seekTime);
            e.consume();
          }
        };
        
        pgBar.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickAndOnDragHandler);
        pgBar.addEventHandler(MouseEvent.MOUSE_DRAGGED, onClickAndOnDragHandler);
    }
    
    @FXML
    private void Fullscreen() {
        Stage stage = ((Stage)apMediaView.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }
    
    @FXML
    private void ExibirControles() {
        hBoxControles.setVisible(true);
    }
    
    @FXML
    private void OcultarControles() {
        hBoxControles.setVisible(false);
    }
}
