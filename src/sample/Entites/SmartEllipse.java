package sample.Entites;

import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class SmartEllipse extends Circle {

    private double dx;
    private double dy;

    public SmartEllipse(double x, double y, double size) {
        super(x, y, size);
    }

    public abstract boolean checkCollision(SmartEllipse shape);

    public abstract void manageCollision(SmartEllipse shape);

    public abstract ArrayList<Node> getShapes();

    public abstract void update();

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public abstract void addVelocity(double dx, double dy);


}
