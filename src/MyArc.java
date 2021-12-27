import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

class MyArc extends MyShape {
    MyPoint pCenter;
    MyPoint p1, p2;
    double startAngle;
    double angle;
    double rStartAngle, rAngle, rEndAngle;

    MyOval o;
    int a, b;
    int x, y;
    int x1, y1, x2, y2;

    MyArc(MyPoint p, int a, int b, double startAngle, double angle, MyColor color) {
        super(new MyPoint(0, 0, null), color);
        this.pCenter = p;
        this.a = a;
        this.b = b;
        this.startAngle = startAngle;
        this.angle = angle;
        this.color = MyColor.RANDOM;
        this.rStartAngle = Math.toRadians(startAngle);
        this.rAngle = Math.toRadians(angle);
        this.rEndAngle = Math.toRadians(startAngle + angle);
        this.x = pCenter.getX();
        this.y = pCenter.getY();
        this.x1 = (int) (x + (double) (a * b) / Math.sqrt(Math.pow(b, 2) + Math.pow(a * Math.tan(rStartAngle), 2)));
        this.y1 = (int) (y + (double) (a * b * Math.tan(rStartAngle)) / Math.sqrt(Math.pow(b, 2) + Math.pow(a * Math.tan(rStartAngle), 2)));
        this.x2 = (int) (x + (double) (a * b) / Math.sqrt(Math.pow(b, 2) + Math.pow(a * Math.tan(rEndAngle), 2)));
        this.y2 = (int) (y + (double) (a * b * Math.tan(rEndAngle)) / Math.sqrt(Math.pow(b, 2) + Math.pow(a * Math.tan(rEndAngle), 2)));
        this.p1 = new MyPoint(x1, y1, color);
        this.p2 = new MyPoint(x2, y2, color);
        this.o = new MyOval(pCenter, a, b, color);
    }

    public MyPoint getCenter() {
        return pCenter;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double getAngle() {
        return angle;
    }

    public MyColor getColor() {
        return color;
    }

    @Override
    public int area() {
        double HpW = (double) (b + a);
        double HmW = (double) (b - a);
        return (int) (0.5 * a * b * (rAngle - (Math.atan((HmW * Math.sin(2.0 * rEndAngle)) / (HpW + HmW * Math.cos(2.0 * rEndAngle)))
                - Math.atan((HmW * Math.sin(2.0 * rStartAngle)) / (HpW + HmW * Math.cos(2.0 * rStartAngle))))));
    }

    @Override
    public int perimeter() {
        return (int) (0.5 * Math.PI / Math.sqrt(2) * p1.distanceBetweenPoints(p2));
    }

    public MyRectangle getMyBoundingRectangle(MyColor color) {
        return o.getMyBoundingRectangle(color);
    }

    public boolean pointInMyShape(MyPoint p) {
        double pAngle = startAngle;
        int dx = pCenter.getX() - p.getX();
        int dy = pCenter.getY() - p.getY();
        return Math.pow(b * dx, 2) + Math.pow(a * dy, 2) <= Math.pow(a * b, 2) &&
                pAngle >= startAngle && pAngle <= startAngle + angle;
    }

    public void stroke(GraphicsContext gc) {
        gc.setStroke(color.getJavaFXColor());
        gc.strokeArc(x - a, y - b, 2 * a, 2 * b, startAngle, angle, ArcType.ROUND);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color.getJavaFXColor());
        gc.fillArc(x - a, y - b, 2 * a, 2 * b, startAngle, angle, ArcType.ROUND);
    }

    @Override
    public String toString() {
        return "Arc: Center " + pCenter + " Oval Width " + 2 * a + " Oval Height " + 2 * b +
                " (StartAngle, Angle): (" + startAngle + ", " + angle + "),  " +
                "Perimeter " + perimeter() + "Area " + area() +
                " " + color.getJavaFXColor();
    }
}
