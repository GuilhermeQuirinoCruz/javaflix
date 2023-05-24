/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.HBoxCell;

public class MidiaController implements Initializable {


    @FXML
    private Label lblTitulo;
    @FXML
    private VBox vBoxMidia;
    @FXML
    private HBox hBoxBotoes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void Carregar(String titulo, ArrayList<HBoxCell> hBoxCells, ArrayList<Button> botoes){
        lblTitulo.setText(titulo);
        vBoxMidia.getChildren().setAll(hBoxCells);
        hBoxBotoes.getChildren().setAll(botoes);
        
    }
    
    
    
}
