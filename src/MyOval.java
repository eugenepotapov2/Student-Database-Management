import javafx.scene.canvas.GraphicsContext;

class MyOval extends MyShape {

    private final int axisA, axisB;

    MyOval(MyPoint p, int w, int h, MyColor color) {
        super(p, color);
        this.axisA = w;
        this.axisB = h;
    }

    @Override
    public int getX() {
        return p.getX() + getA();
    }

    @Override
    public int getY() {
        return p.getY() + getB();
    }

    public int getA() {
        return axisA / 2;
    }

    public int getB() {
        return axisB / 2;
    }

    public int getWidth() {
        return axisA;
    }

    public int getHeight() {
        return axisB;
    }

    public MyPoint getCenter() {
        return new MyPoint(getX(), getY(), null);
    }

    public MyRectangle getInnerRect(MyColor color) {
        return new MyRectangle(
                new MyPoint(getX() - (int) (getA() * Math.sqrt(2) / 2),
                        getY() - (int) (getB() * Math.sqrt(2) / 2), color),
                (int) (getA() * Math.sqrt(2)),
                (int) (getB() * Math.sqrt(2)),
                color
        );
    }

    public MyPolygon getInnerPolygon(int n, MyColor color) {
        int r = Math.min(getA(), getB());
        return new MyPolygon(new MyPoint(getX() - r, getY() - r, color), n, r, color);
    }

    @Override
    public int area() {
        return (int) Math.PI * getA() * getB();
    }

    @Override
    public int perimeter() {
        return 2 * (int) Math.PI * (int) Math.sqrt((Math.pow(getA(), 2) + Math.pow(getB(), 2)) / 2);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (axisA > gc.getCanvas().getWidth() - p.getX() || axisB > gc.getCanvas().getHeight() - p.getY()) {
            System.out.println("Oval size exceeds the size of canvas.");
            System.exit(0);
        } else
            gc.setFill(color.getJavaFXColor());
        gc.fillOval(p.getX(), p.getY(), axisA, axisB);
    }

    public String toString() {
        return "Oval: Center: (" + getX() + ", " + getY() + ") Major Axis: " + axisA + " Minor Axis: " + axisB +
                " Perimeter: " + perimeter() + " Area: " + area();
    }

    @Override
    public MyRectangle getMyBoundingRectangle(MyColor color) {
        return new MyRectangle(p, axisA, axisB, color);
    }

    @Override
    public boolean pointInMyShape(MyPoint point) {
        return (Math.pow(point.getX() - getX(), 2) / Math.pow(getA(), 2) +
                Math.pow(point.getY() - getY(), 2) / Math.pow(getB(), 2) <= 1);
    }
}