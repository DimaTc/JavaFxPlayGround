package sample2;

import javafx.scene.shape.Polyline;

public interface LineListener {

	void lineAddListener(Polyline line);

	void lineSelectListener(Polyline line);

	void lineHoverListener(Polyline line);
}
