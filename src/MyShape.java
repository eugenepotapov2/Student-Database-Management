import javafx.scene.canvas.GraphicsContext;

abstract class MyShape implements MyShapeInterface {
    protected MyColor color;
    protected MyPoint p;

    MyShape(MyPoint p, MyColor color) {
        this.p = p;
        this.color = color;
    }

    public int getX() {
        return p.getX();
    }

    public int getY() {
        return p.getY();
    }

    public abstract int area();

    public abstract int perimeter();

    public abstract void draw(GraphicsContext gc);

}