import javafx.scene.canvas.GraphicsContext;

class MyRectangle extends MyShape {

    private final int width, height;

    MyRectangle(MyPoint p, int w, int h, MyColor color) {
        super(p, color);
        this.width = w;
        this.height = h;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    MyOval getInnerOval(MyColor color) {
        return new MyOval(p, width, height, color);
    }

    public MyPolygon getInnerPolygon(int n, MyColor color) {
        int r, x1, y1;
        if (width <= height) {
            r = width / 2;
            x1 = getX();
            y1 = getY() + height / 2 - r;
        } else {
            r = height / 2;
            x1 = getX() + width / 2 - r;
            y1 = getY();
        }
        return new MyPolygon(new MyPoint(x1, y1, color), n, r, color);
    }

    @Override
    public int area() {
        return width * height;
    }

    @Override
    public int perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (width > gc.getCanvas().getWidth() - p.getX() || height - p.getY() > gc.getCanvas().getHeight() - p.getY()) {
            System.out.println("Rectangle size exceeds the size of canvas.");
            System.exit(0);
        } else
            gc.setFill(color.getJavaFXColor());
        gc.fillRect(p.getX(), p.getY(), width, height);
    }

    public String toString() {
        return "Rectangle: Top Left Corner: (" + p.getX() + ", " + p.getY() + ")" +
                " Width: " + getWidth() + " Height: " + getHeight() +
                " Perimeter: " + perimeter() + " Area: " + area();
    }

    @Override
    public MyRectangle getMyBoundingRectangle(MyColor color) {
        return new MyRectangle(p, width, height, color);
    }

    @Override
    public boolean pointInMyShape(MyPoint point) {
        return (p.getX() <= point.getX() && point.getX() <= p.getX() + getWidth()) &&
                (p.getY() <= point.getY() && point.getY() <= p.getY() + getHeight());
    }
}