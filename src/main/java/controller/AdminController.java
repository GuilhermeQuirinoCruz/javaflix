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
import javafx.scene.control.SplitPane;
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
        SplitPane spGenero = (SplitPane) App.loadFXML("genero");
        anchorPane.getChildren().setAll(spGenero);
        
        AnchorPane.setBottomAnchor(spGenero, 0d);
        AnchorPane.setTopAnchor(spGenero, 0d);
        AnchorPane.setLeftAnchor(spGenero, 0d);
        AnchorPane.setRightAnchor(spGenero, 0d);
    }

}
