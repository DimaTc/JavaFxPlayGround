package sample2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

public class Main extends Application implements LineListener {

	private BorderPane mainPane;
	private MainCanvas canvas;
	private ListPane listPane;
	private ControlPane ctrlPane;
	private Circle circle = new Circle();

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainPane = new BorderPane();
		Scene scene = new Scene(mainPane);
		mainPane.setPrefHeight(600);
		mainPane.setPrefWidth(800);

		canvas = new MainCanvas();
		canvas.getChildren().add(circle);
		mainPane.setCenter(canvas);
		canvas.setStyle("-fx-border-width:1px; -fx-border-color:black; -fx-border-style:solid;");

		listPane = new ListPane();
		listPane.prefWidthProperty().bind(mainPane.prefWidthProperty().divide(4));
		listPane.prefHeightProperty().bind(mainPane.prefHeightProperty());
		mainPane.setRight(listPane);
		listPane.setStyle("-fx-border-width:1px; -fx-border-color:black; -fx-border-style:solid;");

		ctrlPane = new ControlPane();
		ctrlPane.prefWidthProperty().bind(canvas.prefWidthProperty());
		ctrlPane.prefHeightProperty().bind(mainPane.prefHeightProperty().divide(5));
		mainPane.setBottom(ctrlPane);
		ctrlPane.setStyle("-fx-border-width:1px; -fx-border-color:black; -fx-border-style:solid;");

		canvas.setOnLineAdd(this);
		listPane.setOnLineSelectListener(this);

//		mainPane.getChildren().add(listPane);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void lineAddListener(Polyline line) {
		listPane.addLine(line);

	}

	@Override
	public void lineSelectListener(Polyline line) {
		canvas.selectLine(line);
		ctrlPane.selectLine(line);
		circle.setFill(Color.ORANGE);
		circle.setRadius(10);
		circle.setCenterX(line.getPoints().get(0));
		circle.setCenterY(line.getPoints().get(1));
		circle.setTranslateX(0);
		circle.setTranslateY(0);
		ctrlPane.setNodeForAnimation(circle);
	}

	@Override
	public void lineHoverListener(Polyline line) {
		canvas.hoverLine(line);
	}
}
