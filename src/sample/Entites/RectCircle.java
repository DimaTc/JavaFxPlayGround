package sample.Entites;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class RectCircle extends SmartEllipse {
    private ArrayList<Node> ellipses;
    private int numRects = 6;

    public RectCircle(double x, double y, double size) {

        super(x, y, size);
        ellipses = new ArrayList<>();
        double red = new Random().nextDouble() / 2D;
        double green = new Random().nextDouble() / 2D;
        double blue = new Random().nextDouble() / 2D;
        int factor = (int) (Math.pow(-1, new Random().nextInt(4)));
        int scale = new Random().nextInt(4) + 1;
        for (int i = 0; i < numRects; i++) {
            RotateTransition rt = new RotateTransition();
            rt.setDuration(Duration.seconds(6 - i / 2d));
            rt.setFromAngle(0);

            rt.setToAngle(360 * scale * factor);
            ellipses.add(new Ellipse(x, y, size, size * 4));
            final Ellipse ellipse = (Ellipse) ellipses.get(i);
            rt.setNode(ellipse);
            ((Ellipse) ellipses.get(i)).fillProperty().setValue(new Color(red + (0.4 * (i / (double) numRects)),
                    green + 0.4 * (i / (double) numRects), blue + 0.4 * (i / (double) numRects), 1));
            rt.play();
            rt.setOnFinished(event -> {
                FadeTransition ft = new FadeTransition();
                ft.setNode(ellipse);
                ft.setToValue(0);
                ft.setDuration(Duration.seconds(0.5));
                ft.play();
                ft.setOnFinished(e -> {
                    //Instead of making a new Event + Event handler. I'll give the RAM to handle it
                    //In case it will be scaled up - should be made with an event handler to remove the node
                    ellipse.setCenterX(Integer.MIN_VALUE);
                    ellipse.setCenterY(Integer.MIN_VALUE);
                });
            });
        }
    }

    public void setNumRects(int numRects) {
        this.numRects = numRects;
    }

    @Override
    public boolean checkCollision(SmartEllipse shape) {
        return false;
    }

    @Override
    public void manageCollision(SmartEllipse shape) {

    }

    @Override
    public ArrayList<Node> getShapes() {
        return ellipses;
    }

    @Override
    public void update() {

    }

    @Override
    public void addVelocity(double dx, double dy) {

    }
}
