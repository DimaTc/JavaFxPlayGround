package sample.Panes;

import javafx.scene.input.MouseEvent;
import sample.Entites.BulletCircle;
import sample.Entites.RectCircle;
import sample.Entites.Shapes;
import sample.Entites.SmartEllipse;

import java.util.ArrayList;
import java.util.HashMap;

public class ShapePanel extends InteractivePane {
    private ArrayList<SmartEllipse> items;
    private Shapes currentShape;

    public ShapePanel() {
        currentShape = Shapes.values()[0];
        items = new ArrayList<>();
        addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!e.getTarget().getClass().getName().equals(getClass().getName()))
                return;
//            SmartEllipse item = createItem(e.getX(), e.getY(), 20 + new Random().nextInt(30));
            SmartEllipse item = createItem(e.getX(), e.getY(), 50);
            items.add(item);
            getChildren().addAll(item.getShapes());
        });

    }

    public void setCurrentShape(Shapes currentShape) {
        this.currentShape = currentShape;
    }

    private SmartEllipse createItem(double x, double y, double size) {
        SmartEllipse newItem;
        switch (currentShape) {
            case RectCircle:
                newItem = new RectCircle(x, y, size);
                break;
            case BulletCircle:
                newItem = new BulletCircle(x, y, size);
                break;
            default:
                newItem = null;
        }
        return newItem;
    }

    @Override
    public void init() {

    }

    public void update() {
        for (SmartEllipse item : items)
            item.update();
        checkCollisions();
    }

    private void checkCollisions() {
        for (int i = 0; i < items.size(); i++) {
            boolean changed = false;
            for (int j = i + 1; j < items.size(); j++) {
                boolean res = items.get(i).checkCollision(items.get(j));
                if (res && !changed) {
                    changed = true;
                    items.get(i).manageCollision(items.get(j));
                    items.get(j).manageCollision(items.get(i));
                }
            }
        }
    }
}
