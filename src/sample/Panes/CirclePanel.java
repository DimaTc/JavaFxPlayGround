package sample.Panes;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class CirclePanel extends InteractivePane {
    private final static int LAYERS = 10;
    private final static int CIRCLE_PER_LAYER = 15;
    private final static int DIST_FROM_CENTER = 80;
    private final static int MAX_MOVEMENT = 80;
    private final static int MASS = 1_000_000; //in grams
    private ArrayList<Circle> circles;
//    private final static int SENSITIVE_DISTANCE = 300;

    public CirclePanel() {
        super();
        circles = new ArrayList<>();
        addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            for (Circle circle : circles)
                moveCircle(event.getX(), event.getY(), circle);
        });

    }

    public void init() {
        double radius =  DIST_FROM_CENTER * Math.PI / (CIRCLE_PER_LAYER );
        for (int layer = 0; layer < LAYERS; layer++) {
            int numBalls = CIRCLE_PER_LAYER * (layer + 1);
            for (int i = 0; i <= numBalls; i++) {
                double deg = i / (double) (numBalls + 1) * 2 * Math.PI;
                Circle c = new Circle(radius, getRandomColor());
                c.centerXProperty().bind(widthProperty().divide(2)
                        .add(((DIST_FROM_CENTER + 1) * (layer + 1) * Math.cos(deg))));
                c.centerYProperty().bind(heightProperty().divide(2)
                        .add(((DIST_FROM_CENTER + 1) * (layer + 1) * Math.sin(deg))));
                circles.add(c);

            }
        }
        getChildren().addAll(circles);

    }

    private void moveCircle(double mx, double my, Circle circle) {
        int circleX = (int) circle.getCenterX();
        int circleY = (int) circle.getCenterY();
        double distance = Math.sqrt(Math.pow(circleX - mx, 2) + Math.pow(circleY - my, 2));
        double force = MASS / Math.pow(distance, 1.8);    //Calculation of gravity
        double movement = -(force > MAX_MOVEMENT ? MAX_MOVEMENT : force);
//        movement = -force;


        double deg = Math.atan2(circle.getCenterY() - my, circle.getCenterX() - mx);
        circle.translateXProperty().set(movement * Math.cos(deg));
        circle.translateYProperty().set(movement * Math.sin(deg));

    }

    private Color getRandomColor() {
        Random rand = new Random();
        double r = rand.nextDouble();
        double g = rand.nextDouble();
        double b = rand.nextDouble();

        return new Color(r, g, b, 1);
    }

    public void update() {
    }
}
