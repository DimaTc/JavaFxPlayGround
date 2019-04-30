package sample;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class RectCircle {
    private ArrayList<Ellipse> ellipses;

    private int numRects = 6;
    private int deg = 0;

    public RectCircle(double x, double y, int size) {
        ellipses = new ArrayList<>();
        double red = new Random().nextDouble() / 2D;
        double green = new Random().nextDouble() / 2D;
        double blue = new Random().nextDouble() / 2D;
        int factor = (int) (Math.pow(-1, new Random().nextInt(4)));
        for (int i = 0; i < numRects; i++) {
            RotateTransition rt = new RotateTransition();
            rt.setDuration(Duration.seconds(10 - i));
            rt.setFromAngle(0);

            rt.setToAngle(360 * factor);
            ellipses.add(new Ellipse(x, y, size, size * 4));
            final Ellipse ellipse = ellipses.get(i);
            rt.setNode(ellipse);
            ellipses.get(i).fillProperty().setValue(new Color(red + (0.4 * (i / (double) numRects)),
                    green + 0.4 * (i / (double) numRects), blue + 0.4 * (i / (double) numRects), 1));
            rt.play();
            rt.setOnFinished(event -> {
                FadeTransition ft = new FadeTransition();
                ft.setNode(ellipse);
                ft.setToValue(0);
                ft.setDuration(Duration.seconds(0.5));
                ft.play();
            });
        }
    }

    public ArrayList<Ellipse> getEllipses() {
        return ellipses;
    }

    public void setNumRects(int numRects) {
        this.numRects = numRects;
    }
}
