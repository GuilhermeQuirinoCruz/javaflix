
package controller;

import java.io.IOException;
import main.App;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminController implements Initializable {

    @FXML
    private Button btnGenero;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        App.changeTitle("Admin");
    }    
    
    private void SetAnchor(AnchorPane anchorPane)
    {
        AnchorPane.setBottomAnchor(anchorPane, 0d);
        AnchorPane.setTopAnchor(anchorPane, 0d);
        AnchorPane.setLeftAnchor(anchorPane, 0d);
        AnchorPane.setRightAnchor(anchorPane, 0d);
    }
    
    @FXML
    private void OpenGenero(ActionEvent event) throws IOException {
        AnchorPane apGenero = (AnchorPane) App.newFXML("generoList").load();
        anchorPane.getChildren().setAll(apGenero);
        
        SetAnchor(apGenero);
    }
    
    @FXML
    private void OpenMidia(ActionEvent event) throws IOException {
        AnchorPane apMidia = (AnchorPane) App.newFXML("midiaList").load();
        anchorPane.getChildren().setAll(apMidia);
        
        SetAnchor(apMidia);
    }
    
    @FXML
    private void SignOut() throws IOException{
        App.changeScene(App.newScene(App.newFXML("login"), 800, 600));
        //((Stage)btnGenero.getScene().getWindow()).close();
    }

}
