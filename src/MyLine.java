import javafx.scene.canvas.GraphicsContext;

class MyLine extends MyShape {

    private final MyPoint p2;

    MyLine(MyPoint p1, MyPoint p2, MyColor color) {
        super(p1, color);
        this.p2 = p2;
    }

    public int length() {
        return (int) (Math.sqrt(Math.pow(p.getX() - p2.getX(), 2) + Math.pow(p.getY() - p2.getY(), 2)));
    }

    public double angleX() {
        return Math.toDegrees(Math.atan2((p2.getX() - p.getX()), (p2.getY() - p.getY())));
    }

    @Override
    public int area() {
        return 0;
    }

    @Override
    public int perimeter() {
        return length();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color.getJavaFXColor());
        gc.strokeLine(p.getX(), p.getY(), p2.getX(), p2.getY());
    }

    public String toString() {
        return "Line: [(" + p.getX() + ", " + p.getY() + "), (" + p2.getX() + ", " + p2.getY() + ")] Length: " + length() + " Angle: " + angleX();
    }

    @Override
    public MyRectangle getMyBoundingRectangle(MyColor color) {
        return new MyRectangle(p, p2.getX() - p.getX(), p2.getY() - p.getY(), color);
    }

    @Override
    public boolean pointInMyShape(MyPoint point) {
        double angle = Math.toDegrees(Math.atan2((p2.getX() - point.getX()), ((p2.getY() - point.getY()))));
        return (angle == angleX() && (point.getX() >= p.getX() && point.getY() >= p.getY()) ||
                (point.getX() == p2.getX() && point.getY() == p2.getY()));
    }
}