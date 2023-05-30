package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import main.App;
import model.Genero;
import model.Midia;
import service.GeneroService;
import service.MidiaService;

public class HomeController implements Initializable {

    @FXML
    private Label lblNome;
    @FXML
    private ListView lvMidias;
    
    private GeneroService generoService;
    private MidiaService midiaService;
    private AnchorPane apMidiaCard;
    private FXMLLoader midiaCardLoader;
    
    private ArrayList<ListView> listViewsCards;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generoService = new GeneroService();
        midiaService = new MidiaService();
        
        Platform.runLater(() -> {
            Task taskLvMidias = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Thread.sleep(100);
                    CarregarListViewMidias();

                    return null;
                }
            };
            
            Thread threadMidias = new Thread(taskLvMidias);
            threadMidias.setDaemon(true);
            threadMidias.start();
        });
    }
    
    @FXML
    private void CarregarListViewMidias() throws IOException {
        lvMidias.getItems().clear();
        
        final HomeController homeController = this;
        ArrayList<Genero> generos = generoService.Listar();
        listViewsCards = new ArrayList<>();
        
        for (Genero genero : generos) {
            Label lblGenero = new Label(genero.getNome());
            lvMidias.getItems().add(lblGenero);
            
            ArrayList<Midia> midias = midiaService.ListarPorGenero(genero.getId());
            if (midias != null) {
                ListView lvCards = new ListView();
                lvCards.setOrientation(Orientation.HORIZONTAL);
                lvCards.setPrefHeight(225);
                
                Task taskLvCards = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        for (Midia midia : midias) {
                            midiaCardLoader = App.newFXML("midiaCard");
                            apMidiaCard = (AnchorPane) midiaCardLoader.load();
//                            Thread.sleep(10);
                            MidiaCardController midiaCardController = midiaCardLoader.getController();
                            midiaCardController.BuildCard(midia, homeController);

                            Platform.runLater(() -> {
                                lvCards.getItems().add(apMidiaCard);
                            });
                        }
                        
                        return null;
                    }
                };
                
                Thread threadCards = new Thread(taskLvCards);
                threadCards.setDaemon(true);
                threadCards.start();
                
                lvMidias.getItems().add(lvCards);
                
                listViewsCards.add(lvCards);
            }
        }
    }
    
    public void CarregarMidia(Midia midia) {
        
    }
}
