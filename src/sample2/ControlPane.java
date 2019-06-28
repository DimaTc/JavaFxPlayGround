package sample2;

import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class ControlPane extends HBox {

	private Button startBtn;
	private Button pauseBtn;
	private Button stopBtn;
	private double speed = 10;
	private PathTransition pt;
	private Node node;

	public ControlPane() {
		pt = new PathTransition();
		pt.setDuration(Duration.seconds(speed));
		setAlignment(Pos.CENTER);
		pt.setInterpolator(Interpolator.LINEAR);
		pt.setOnFinished(e -> stopAnimation());
		VBox ctrlGroup = new VBox();
		ctrlGroup.setAlignment(Pos.CENTER);
		ctrlGroup.setPadding(new Insets(10, 10, 10, 10));
		startBtn = new Button("Start");
		startBtn.setOnAction(e -> startAnimation());
		startBtn.setDisable(true);
		pauseBtn = new Button("Pause");
		pauseBtn.setOnAction(e -> pauseAnimation());
		pauseBtn.setDisable(true);
		stopBtn = new Button("Stop");
		stopBtn.setOnAction(e -> stopAnimation());
		stopBtn.setDisable(true);
		ctrlGroup.getChildren().addAll(startBtn, pauseBtn, stopBtn);

		Slider speedSlider = new Slider();
		speedSlider.setMin(1);
		speedSlider.setMax(10);

		speedSlider.valueProperty().addListener(e -> {
			pt.rateProperty().bind(speedSlider.valueProperty());
		});

//		pt.setInterpolator(value);
		getChildren().addAll(ctrlGroup, speedSlider);
//		getChildren().add(pane);
	}

	public void selectLine(Polyline line) {
		if (pt.getStatus() == Status.RUNNING)
			pt.stop();
		startBtn.setDisable(false);
		pt.setPath(line);
	}

	public void setNodeForAnimation(Node node) {
		this.node = node;
	}

	private void startAnimation() {
		pt.setNode(node);
		pt.play();
		pauseBtn.setDisable(false);
		startBtn.setDisable(true);
		stopBtn.setDisable(false);
	}

	private void stopAnimation() {
		pt.stop();
		pt.setNode(null);
		startBtn.setDisable(false);
		stopBtn.setDisable(true);
		pauseBtn.setDisable(true);

	}

	private void pauseAnimation() {
		pt.pause();
		startBtn.setDisable(false);

	}
}
