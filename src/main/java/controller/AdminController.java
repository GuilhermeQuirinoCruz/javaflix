/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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

public class AdminController implements Initializable {

    @FXML
    private Button btnGenero;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    private void OpenGenero(ActionEvent event) throws IOException {
        AnchorPane apGenero = (AnchorPane) App.newFXML("generoList").load();
        anchorPane.getChildren().setAll(apGenero);
        
        AnchorPane.setBottomAnchor(apGenero, 0d);
        AnchorPane.setTopAnchor(apGenero, 0d);
        AnchorPane.setLeftAnchor(apGenero, 0d);
        AnchorPane.setRightAnchor(apGenero, 0d);
    }
    
     @FXML
    private void OpenMidia(ActionEvent event) throws IOException {
        AnchorPane apMidia = (AnchorPane) App.newFXML("midiaList").load();
        anchorPane.getChildren().setAll(apMidia);
        
        AnchorPane.setBottomAnchor(apMidia, 0d);
        AnchorPane.setTopAnchor(apMidia, 0d);
        AnchorPane.setLeftAnchor(apMidia, 0d);
        AnchorPane.setRightAnchor(apMidia, 0d);
    }

}
