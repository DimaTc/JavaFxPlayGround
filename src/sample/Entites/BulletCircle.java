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
     *
     * @param shape
     */
    @Override
    public void manageCollision(SmartEllipse shape) {
        shape.addVelocity(getDx() / 2, getDy() / 2);
        setDx(getDx() / 2);
        setDy(getDy() / 2);
        double speedSize = Math.sqrt(getDx() * getDx() + getDy() * getDy());
        double totalRadius = shape.getRadius() + getRadius();
        double relRadToVel = speedSize / totalRadius;
        double hitAngle;
        if (Math.abs(shape.getCenterX() - circle.getCenterX()) <= 0.0002)
            hitAngle = 0;
        else
            hitAngle = Math.atan((shape.getCenterY() - circle.getCenterY()) / (shape.getCenterX() - circle.getCenterX()));
//        System.out.println(speedSize);
        setDx(relRadToVel * totalRadius * Math.sin(hitAngle));
        setDy(relRadToVel * totalRadius * Math.cos(hitAngle));
//        System.out.println(getDx());
    }

    @Override
    public void addVelocity(double dx, double dy) {
        setDx(getDx() + dx);
        setDy(getDy() + dy);
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
        if (cRight >= w && getDx() > 0) {
            setDx(-Math.abs(getDx()));
            setCenterX(w - circle.getRadius() - 1);
        } else if (cLeft <= 0 && getDx() < 0) {
            setDx(Math.abs(getDx()));
            setCenterX(circle.getRadius() + 1);
        } else if (cBottom >= h && getDy() > 0) {
            setDy(-Math.abs(getDy()));
            setCenterY(h - circle.getRadius() - 1);
        } else if (cTop <= 0 && getDy() < 0) {
            setDy(Math.abs(getDy()));
            setCenterY(circle.getRadius() + 1);

        }
    }

    @Override
    public void update() {
        circle.setCenterX(circle.getCenterX() + getDx());
        circle.setCenterY(circle.getCenterY() + getDy());
        checkAndUpdateWallCollision();
    }
}
