package ui;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HBoxCell extends HBox {
    
    private ArrayList<Node> nodes;

    public HBoxCell(ArrayList<Node> nodes, double spacing) {
        super();
        
        this.setFillHeight(true);
        this.setSpacing(spacing);
        
        this.nodes = nodes;
        this.getChildren().addAll(nodes);
    }
    
    public static Button getHBoxButton(String textButton) {
        Button button = new Button();
        if (!textButton.equals("")) {
            button.setText(textButton);
        }
        button.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(button, Priority.NEVER);
        
        return button;
    }
    
    public static Label getHBoxLabel(String textLabel, Pos alg, Priority prt) {
        Label label = new Label(textLabel);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(alg);
        HBox.setHgrow(label, prt);
        
        return label;
    }
    
    public static ImageView getHBoxImageView(String path) {
        ImageView imageView = new ImageView(new Image(path));
//        HBox.setHgrow(imageView, Priority.ALWAYS);
        return imageView;
    }
    
    public static TextField getHBoxTextField() {
        TextField textField = new TextField();
        HBox.setHgrow(textField, Priority.ALWAYS);
        
        return textField;
    }
    
    public Node getNode(int index) {
        return this.nodes.get(index);
    }
}
