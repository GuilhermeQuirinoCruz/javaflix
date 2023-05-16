package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene currentScene;
    private static Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        window = stage;
        changeScene("login");
    }

    public static void changeRoot(String fxml) throws IOException {
        currentScene.setRoot(loadFXML(fxml));
    }
    
    public static void changeTitle(String title) {
        window.setTitle(title);
    }
    
    public static void changeScene(String scene) throws IOException {
        currentScene = new Scene(loadFXML(scene), 800, 600);
        window.setScene(currentScene);
        window.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}