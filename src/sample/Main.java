package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Entites.RectCircle;
import sample.Entites.Shapes;

import java.util.ArrayList;


public class Main extends Application {

    private static final double UPDATE_RATE = 60;
    private ArrayList<RectCircle> items;
    private ShapePanel shapePanel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        shapePanel = new ShapePanel();
        VBox buttonPane = new VBox();
        buttonPane.setAlignment(Pos.CENTER_LEFT);
        buttonPane.setPadding(new Insets(10, 10, 10, 10));
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new RadioChangeListener());
        shapePanel.setStyle("-fx-border-color:black; -fx-border-width:2px;-fx-background-color:#f5f5f5;");
        buttonPane.setStyle("-fx-border-color:black; -fx-border-width:2px;-fx-background-color:#f1f1f1;");
        for (Shapes shapeType : Shapes.values()) {
            RadioButton rd = new RadioButton(shapeType.name());
            rd.setId(shapeType.name());
            rd.setToggleGroup(toggleGroup);
            buttonPane.getChildren().add(rd);
        }
        ((RadioButton) buttonPane.getChildren().get(0)).fire();

        mainPane.setCenter(shapePanel);
        mainPane.setRight(buttonPane);
        Scene scene = new Scene(mainPane, 800, 800);
        primaryStage.setTitle("Dima's World");
        primaryStage.setScene(scene);
        primaryStage.show();
        startTimer();
    }

    private void update() {
        shapePanel.update();
    }

    private void startTimer() {
        new AnimationTimer() {
            private long lastTime = System.nanoTime();
            private double delta = 0;
            private int ups = 0;
            private double lastPrint = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                delta += (now - lastTime) / (1_000_000_000D);
                lastTime = now;
                while (delta >= 1 / UPDATE_RATE) {
                    delta -= 1 / UPDATE_RATE;
                    update();
                    ups++;
                }
                if (System.currentTimeMillis() - lastPrint >= 1000) {
                    lastPrint = System.currentTimeMillis();
                    ups = 0;
                }
            }
        }.start();
    }

    private class RadioChangeListener implements ChangeListener<Toggle> {

        @Override
        public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
            shapePanel.setCurrentShape(Shapes.valueOf(((RadioButton) newValue.getToggleGroup().getSelectedToggle()).getId()));
        }

    }

}
