package sample2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ListPane extends VBox {

	private ArrayList<Polyline> lines;
	private int selectedLine = -1;
	private LineListener callback;

	public ListPane() {
		lines = new ArrayList<>();
		setAlignment(Pos.TOP_CENTER);
	}

	public void addLine(Polyline line) {
		lines.add(line);
		HBox pane = new HBox();
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setAlignment(Pos.CENTER);
		pane.setOnMouseClicked(e -> {
			if (callback != null)
				callback.lineSelectListener(line);
		});
		pane.setOnMouseEntered(e ->{
			if(callback!=null)
				callback.lineHoverListener(line);
		});
		pane.setOnMouseExited(e ->{
			if(callback!=null)
				callback.lineHoverListener(null);
		});
		Text text = new Text("Line #" + lines.size());
		pane.getChildren().add(text);
		pane.setStyle("-fx-border-width:1px; -fx-border-color:black; -fx-border-style:solid;");
		getChildren().add(pane);

	}

	public void setOnLineSelectListener(LineListener callback) {
		this.callback = callback;
	}

	public int getSelectedLine() {
		return selectedLine;
	}

}
