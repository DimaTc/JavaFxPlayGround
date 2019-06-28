package sample2;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class MainCanvas extends Pane {

	private ArrayList<Polyline> lines;
	private LineListener callback;
	private int currentLine = -1;
	private Polyline hoveredLine;
	private Polyline selectedLine;

	public MainCanvas() {
		lines = new ArrayList<>();
		Rectangle clip = new Rectangle();
		clip.widthProperty().bind(widthProperty());
		clip.heightProperty().bind(heightProperty());
		setClip(clip);

		setOnMouseReleased(e -> {
			if (currentLine != -1)
				callback.lineAddListener(lines.get(currentLine));
			currentLine = -1;
		});
		setOnMouseDragged(e -> {
			if (currentLine == -1) {
				lines.add(new Polyline());
				currentLine = lines.size() - 1;
				getChildren().add(lines.get(currentLine));
			}
			lines.get(currentLine).getPoints().addAll(e.getX(), e.getY());
		});
	}

	public void setOnLineAdd(LineListener callback) {
		this.callback = callback;
	}

	public void selectLine(Polyline line) {
		hoveredLine = null;
		if (selectedLine != null)
			selectedLine.setStroke(Color.BLACK);
		selectedLine = line;
		selectedLine.setStroke(Color.BLUE);
	}

	public void hoverLine(Polyline line) {
		if (selectedLine != null && selectedLine == line)
			return;
		if (hoveredLine != null)
			hoveredLine.setStroke(Color.BLACK);
		if (line == null)
			return;
		hoveredLine = line;
		hoveredLine.setStroke(Color.GREEN);
	}
}
