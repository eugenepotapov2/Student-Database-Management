import javafx.scene.canvas.GraphicsContext;

public class MyPolygon extends MyShape {
    private final int n, r;

    public MyPolygon(MyPoint p, int n, int r, MyColor color) {
        super(p, color);
        this.n = n;
        this.r = r;
    }

    public double getAngle() {
        return (double) 180 * (n - 2) / n;
    }

    public MyPoint getCenter() {
        return new MyPoint(getX() + r, getY() + r, color);
    }

    public double getSide() {
        return (2 * r * Math.sin(Math.PI / n));
    }

    public String toString() {
        return "Polygon: Center: (" + getCenter().getX() + ", " + getCenter().getY() + ") Radius: " + r + " Number of sides: " + n +
                " Side size: " + getSide() + " Interior angle: " + getAngle() + " Perimeter: " + perimeter() + " Area: " + area();
    }

    @Override
    public int area() {
        return (int) (Math.pow(getSide(), 2) * n / 4 * (Math.cos(Math.PI / n) / Math.sin(Math.PI / n)));
    }

    @Override
    public int perimeter() {
        return (int) (n * getSide());
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color.getJavaFXColor());
        double[] x_vertices = new double[n];
        double[] y_vertices = new double[n];
        double angle = (n - 1) * getAngle();
        double angle_increment = (2 * Math.PI) / n;
        int i;
        for (i = 0; i < n; i++) {
            x_vertices[i] = (int) (r * Math.cos(angle)) + (getCenter().getX());
            y_vertices[i] = (int) (r * Math.sin(angle)) + (getCenter().getY());
            angle += angle_increment;
        }
        gc.fillPolygon(x_vertices, y_vertices, n);
    }

    @Override
    public boolean pointInMyShape(MyPoint point) {
        return (Math.pow(point.getX() - getX(), 2) / Math.pow(r, 2) +
                Math.pow(point.getY() - getY(), 2) / Math.pow(r, 2) <= 1);
    }

    @Override
    public MyRectangle getMyBoundingRectangle(MyColor color) {
        return new MyRectangle(p, 2 * r, 2 * r, color);
    }
}
