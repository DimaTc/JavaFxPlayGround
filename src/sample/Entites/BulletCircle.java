package sample.Entites;

import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Random;


public class BulletCircle extends SmartEllipse {
    private Timeline timeline;
    private Circle circle;
    private Line guideLine;
    private ArrayList<Node> items;

    public BulletCircle(double x, double y, double radius) {
        super(x, y, radius);
        items = new ArrayList<>();
        circle = new Circle(x, y, radius);
        circle.centerXProperty().bindBidirectional(centerXProperty());
        circle.centerYProperty().bindBidirectional(centerYProperty());
        guideLine = new Line();
//        guideLine.setFill(Color.GRAY);
        guideLine.setStroke(Color.TRANSPARENT);
        guideLine.setStyle("-fx-stroke-dash-array:10");
//        guideLine.set
        Random r = new Random();
        circle.setFill(new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 1));
        circle.setStyle("-fx-cursor:hand");
        circle.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            guideLine.setStartX(e.getX());
            guideLine.setStartY(e.getY());
            guideLine.setEndX(e.getX());
            guideLine.setEndY(e.getY());
            setDx(0);
            setDy(0);

        });
        circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
            if (guideLine.getStroke() == Color.TRANSPARENT)
                guideLine.setStroke(Color.GRAY);
            guideLine.setEndX(e.getX());
            guideLine.setEndY(e.getY());
            circle.centerXProperty().set(e.getX());
            circle.centerYProperty().set(e.getY());

        });

        circle.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            double dx = guideLine.getStartX() - guideLine.getEndX();
            double dy = guideLine.getStartY() - guideLine.getEndY();
            dx /= 10;
            dy /= 10;
            setDx(dx);
            setDy(dy);
            makeTheLineGo();
        });

        items.add(guideLine);
        items.add(circle);
    }

    private void makeTheLineGo() {
        guideLine.setStartX(0);
        guideLine.setStartY(0);
        guideLine.setEndX(0);
        guideLine.setEndY(0);
        guideLine.setStroke(Color.TRANSPARENT);
    }

    @Override
    public boolean checkCollision(SmartEllipse shape) {
        Shape intersect = Shape.intersect(circle, shape);
        return intersect.getBoundsInLocal().getWidth() != -1;
//        return circle.getBoundsInParent().intersects(shape.getBoundsInParent());
    }

    /**
     * Need to to give some mass and think of the way to calculate it correctly
     * @param shape
     */
    @Override
    public void manageCollision(SmartEllipse shape) {
        double speedSize = Math.sqrt(getDx() * getDx() + getDy() * getDy());
        double hitAngle;
        if (shape.getCenterX() == getCenterX())
            hitAngle = 0;
        else
            hitAngle = Math.atan((shape.getCenterY() - getCenterY()) / (shape.getCenterX() - getCenterX()));
        System.out.println(hitAngle);
        setDx(speedSize * Math.sin(hitAngle));
        setDy(speedSize * Math.cos(hitAngle));
//
//        setDy(-getDy());
//        setDx(-getDx());

    }

    @Override
    public ArrayList<Node> getShapes() {
        return items;
    }

    private void checkAndUpdateWallCollision() {
        double h = circle.getParent().getLayoutBounds().getHeight();
        double w = circle.getParent().getLayoutBounds().getWidth();
        double cRight = circle.getCenterX() + circle.getRadius();
        double cLeft = circle.getCenterX() - circle.getRadius();
        double cTop = circle.getCenterY() - circle.getRadius();
        double cBottom = circle.getCenterY() + circle.getRadius();
        //Simple to change speed without control (very buggy and should not stay)
        if (cRight >= w || cLeft <= 0)
            setDx(-getDx());
        else if (cBottom >= h || cTop <= 0)
            setDy(-getDy());
    }

    @Override
    public void update() {
        circle.setCenterX(circle.getCenterX() + getDx());
        circle.setCenterY(circle.getCenterY() + getDy());
        checkAndUpdateWallCollision();
    }
}
