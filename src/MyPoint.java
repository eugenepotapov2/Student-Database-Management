import javafx.scene.canvas.GraphicsContext;

class MyPoint {
    protected int x, y;
    MyColor color;

    MyPoint(int x, int y, MyColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(MyPoint p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void shift(int dx, int dy) {
        setPoint(x + dx, y + dy);
    }

    public double distanceFromOrigin() {
        return Math.sqrt(x * x + y * y);
    }

    public double distanceBetweenPoints(MyPoint p) {
        int dx = x - p.getX();
        int dy = y - p.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(color.getJavaFXColor());
        gc.fillOval(x - 1, y - 1, 2, 2);
    }

    public String toString() {
        return "Point: (" + x + ", " + y + ")";
    }
}