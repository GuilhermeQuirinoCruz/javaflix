package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import model.Midia;

public class MidiaPlayerController implements Initializable {

    @FXML
    private AnchorPane apMediaView;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider sVolume;
    @FXML
    private ProgressBar pgBar;
    
    MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setMidia(Midia midia) {
//        if (midia.getVideo().equals(""))
//        {
//            mediaPlayer = new MediaPlayer(new Media("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
//        } else {
//            mediaPlayer = new MediaPlayer(new Media(midia.getVideo()));
//        }
        mediaPlayer = new MediaPlayer(new Media("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"));
        
        mediaPlayer.setVolume(0.5);
        mediaView.setMediaPlayer(mediaPlayer);
        
        bindProgress();
        bindSize();
        bindVolume();
        addSeekBehavior();
    }

    @FXML
    private void playVideo() {
        var status = mediaPlayer.getStatus();
        if (status.equals(MediaPlayer.Status.PAUSED) || status.equals(MediaPlayer.Status.READY))
        {
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
    }
    
    private void bindVolume() {
        sVolume.valueProperty().addListener((Observable observable) -> {
            mediaPlayer.setVolume(sVolume.getValue() / 100);
        });
        
        sVolume.setValue(mediaPlayer.getVolume() * 100);
    }
    
    private void bindProgress() {
        var binding =
        Bindings.createDoubleBinding(
            () -> {
              var currentTime = mediaPlayer.getCurrentTime();
              var duration = mediaPlayer.getMedia().getDuration();
              if (isValidDuration(currentTime) && isValidDuration(duration)) {
                return currentTime.toMillis() / duration.toMillis();
              }
              return ProgressBar.INDETERMINATE_PROGRESS;
            },  mediaPlayer.currentTimeProperty(),
                mediaPlayer.getMedia().durationProperty());
        
        pgBar.progressProperty().bind(binding);
    }
    
    private boolean isValidDuration(Duration d) {
        return d != null && !d.isIndefinite() && !d.isUnknown();
    }
    
    private void bindSize() {
        mediaView.fitWidthProperty().bind(apMediaView.widthProperty());
        mediaView.fitHeightProperty().bind(apMediaView.heightProperty());
    }
    
    private void addSeekBehavior() {
        EventHandler<MouseEvent> onClickAndOnDragHandler =
        e -> {
          var duration = mediaPlayer.getMedia().getDuration();
          if (isValidDuration(duration)) {
            var seekTime = duration.multiply(e.getX() / pgBar.getWidth());
            mediaPlayer.seek(seekTime);
            e.consume();
          }
        };
        
        pgBar.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickAndOnDragHandler);
        pgBar.addEventHandler(MouseEvent.MOUSE_DRAGGED, onClickAndOnDragHandler);
    }
}
