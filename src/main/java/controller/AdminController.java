package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import main.App;

public class AdminController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imgLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Admin");
        
        Platform.runLater(() -> {
            imgLogo.setImage(new Image("https://firebasestorage.googleapis.com/v0/b/javaflix-7d4ee.appspot.com/o/logo.png?alt=media&token=b8a3aa5d-e405-40aa-a5b0-a5be6aa241ae&_gl=1*690tts*_ga*MTgxMjY2Nzg3LjE2ODQyNzc2OTE.*_ga_CW55HF8NVT*MTY4NjE0MTgzMy42LjEuMTY4NjE0NzA5Mi4wLjAuMA.."));
            try {
                AbrirMidias();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private void SetAnchor(AnchorPane anchorPane)
    {
        AnchorPane.setBottomAnchor(anchorPane, 0d);
        AnchorPane.setTopAnchor(anchorPane, 0d);
        AnchorPane.setLeftAnchor(anchorPane, 0d);
        AnchorPane.setRightAnchor(anchorPane, 0d);
    }
    
    @FXML
    private void AbrirGeneros() throws IOException {
        AnchorPane apGenero = (AnchorPane) App.newFXML("generoList").load();
        anchorPane.getChildren().setAll(apGenero);
        
        SetAnchor(apGenero);
    }
    
    @FXML
    private void AbrirMidias() throws IOException {
        AnchorPane apMidia = (AnchorPane) App.newFXML("midiaList").load();
        anchorPane.getChildren().setAll(apMidia);
        
        SetAnchor(apMidia);
    }
    
    @FXML
    private void SignOut() throws IOException{
        App.changeScene(App.newScene(App.newFXML("login"), 800, 600));
    }
}
