package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import main.App;
import model.Midia;
import service.MidiaService;
import ui.ListViewNoSelectionModel;

public class MidiaCardRowController implements Initializable {

    @FXML
    private ListView<AnchorPane> lvCards;
    
    private MidiaService midiaService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        midiaService = new MidiaService();
        lvCards.setSelectionModel(new ListViewNoSelectionModel());
    }
    
    public void CarregarCards(int idGenero, HomeController homeController) throws IOException {
        ArrayList<Midia> midias = midiaService.ListarPorGenero(idGenero);
        for (Midia midia : midias) {
//            final FXMLLoader midiaCardLoader = App.newFXML("midiaCard");
//            final AnchorPane apMidiaCard = (AnchorPane) midiaCardLoader.load();
//            MidiaCardController midiaCardController = midiaCardLoader.getController();
//            midiaCardController.BuildCard(midia, homeController);
//
//            Platform.runLater(() ->{
//                lvCards.getItems().add(apMidiaCard);
//            });
            
            Task taskCard = new Task<Void>(){
                @Override
                protected Void call() throws Exception {
                    final FXMLLoader midiaCardLoader = App.newFXML("midiaCard");
                    final AnchorPane apMidiaCard = (AnchorPane) midiaCardLoader.load();
                    MidiaCardController midiaCardController = midiaCardLoader.getController();
                    midiaCardController.BuildCard(midia, homeController);

                    Platform.runLater(() ->{
                        lvCards.getItems().add(apMidiaCard);
                    });
                    
                    return null;
                }
            };

            Thread threadCapa = new Thread(taskCard);
            threadCapa.setDaemon(true);
            threadCapa.start();
        }
    }
}
