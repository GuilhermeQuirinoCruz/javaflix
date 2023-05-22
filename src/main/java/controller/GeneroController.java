package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.HBoxCell;

public class GeneroController implements Initializable {

    @FXML
    private AnchorPane apGenero;
    @FXML
    private Label lblTitulo;
    @FXML
    private HBox hBoxBotoes;
    @FXML
    private VBox vBoxGenero;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void Carregar(String titulo, ArrayList<HBoxCell> hBoxCells, ArrayList<Button> botoes) {
        lblTitulo.setText(titulo);
        vBoxGenero.getChildren().setAll(hBoxCells);
        hBoxBotoes.getChildren().setAll(botoes);
    }
}
