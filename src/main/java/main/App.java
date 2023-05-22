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
        changeScene(newScene(newFXML("login"), 800, 600));
    }

    public static void changeRoot(String fxml) throws IOException {
        currentScene.setRoot(newFXML(fxml).load());
    }
    
    public static void changeTitle(String title) {
        window.setTitle(title);
    }
    
    public static void changeScene(Scene scene) throws IOException {
        currentScene = scene;
        window.setScene(currentScene);
        window.show();
    }
    
    public static Stage newWindow(Scene scene) {
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }
    
    public static Scene newScene(Parent parent, int width, int height) throws IOException {
        return new Scene(parent, width, height);
    }
    
    public static Scene newScene(FXMLLoader loader, int width, int height) throws IOException {
        return new Scene(loader.load(), width, height);
    }

    public static FXMLLoader newFXML(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource("/view/" + fxml + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }
}