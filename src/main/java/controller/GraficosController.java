package controller;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import service.GeneroService;
import service.MidiaService;

public class GraficosController implements Initializable {

    @FXML
    private AnchorPane apGrafico;
    @FXML
    private ComboBox<String> cmbTipo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.getItems().addAll(
                "Favoritos por mídia",
                "Favoritos por gênero",
                "Mídias cadastradas por gênero");
        
        cmbTipo.getSelectionModel().selectFirst();
    }
    
    private void AjustarGrafico(Node grafico) {
        AnchorPane.setBottomAnchor(grafico, 0d);
        AnchorPane.setTopAnchor(grafico, 0d);
        AnchorPane.setLeftAnchor(grafico, 0d);
        AnchorPane.setRightAnchor(grafico, 0d);
    }
    
    private void GraficoFavoritosPorMidia() {
        MidiaService midiaService = new MidiaService();
        
        CategoryAxis eixoMidias = new CategoryAxis();
        eixoMidias.setLabel("Mídias");
        
        NumberAxis eixoFavoritos = new NumberAxis(); 
        eixoFavoritos.setLabel("Favoritos");
        eixoFavoritos.setMinorTickVisible(false);
        
        BarChart<String, Number> graficoFavoritos = new BarChart<>(eixoMidias, eixoFavoritos);
        graficoFavoritos.setTitle("Favoritos por Mídia");
        
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        
        Map<String, Number> favoritos = midiaService.FavoritosPorMidia();
        favoritos.forEach((k, v) -> {
            serie.getData().add(new XYChart.Data<>(k, v));
        });
        
        graficoFavoritos.getData().add(serie);
        graficoFavoritos.setLegendVisible(false);
        
        apGrafico.getChildren().setAll(graficoFavoritos);
        AjustarGrafico(graficoFavoritos);
    }
    
    private void GraficoFavoritosPorGenero() {
        GeneroService generoService = new GeneroService();
        
        Map<String, Number> midias = generoService.FavoritosPorGenero();
        ObservableList<PieChart.Data> dadosMidias = FXCollections.observableArrayList();
        midias.forEach((k, v) -> {
            dadosMidias.add(new PieChart.Data(k, v.doubleValue()));
        });
        
        PieChart graficoFavoritos = new PieChart(dadosMidias);
        
        dadosMidias.forEach(data -> data.nameProperty().bind(
            Bindings.concat(
                data.getName(),
                " (",
                Math.round(data.pieValueProperty().getValue()),
                ")")
            )
        );
        
        apGrafico.getChildren().setAll(graficoFavoritos);
        AjustarGrafico(graficoFavoritos);
    }
    
    private void GraficoMidiasPorGenero() {
        GeneroService generoService = new GeneroService();
        
        Map<String, Number> midias = generoService.MidiasPorGenero();
        ObservableList<PieChart.Data> dadosMidias = FXCollections.observableArrayList();
        midias.forEach((k, v) -> {
            dadosMidias.add(new PieChart.Data(k, v.doubleValue()));
        });
        
        PieChart graficoMidias = new PieChart(dadosMidias);
        
        dadosMidias.forEach(data -> data.nameProperty().bind(
            Bindings.concat(
                data.getName(), 
                " (",
                Math.round(data.pieValueProperty().getValue()),
                ")")
            )
        );
        
        apGrafico.getChildren().setAll(graficoMidias);
        AjustarGrafico(graficoMidias);
    }
    
    @FXML
    private void GerarGrafico() {
        int indice = cmbTipo.getSelectionModel().selectedIndexProperty().getValue();
        switch (indice) {
            case 0:
                GraficoFavoritosPorMidia();
                break;
            case 1:
                GraficoFavoritosPorGenero();
                break;
            case 2:
                GraficoMidiasPorGenero();
                break;
        }
    }
}
