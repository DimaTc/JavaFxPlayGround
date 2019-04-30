package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    private ArrayList<RectCircle> items;

    @Override
    public void start(Stage primaryStage) throws Exception {
        items = new ArrayList<>();
        Pane pane = new Pane();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            RectCircle item = new RectCircle(event.getX(), event.getY(), 20 + new Random().nextInt(30));
            items.add(item);
            pane.getChildren().addAll(item.getEllipses());
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
