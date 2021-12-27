import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Slice {
    MyPoint pCenter;
    int radius;
    double startAngle;
    double angle;
    double rStartAngle, rAngle, rEndAngle;
    String description;

    MyColor color;

    Slice(MyPoint p, int r, double startAngle, double angle, MyColor color, Character c) {
        this.pCenter = p;
        this.radius = r;
        this.startAngle = startAngle;
        this.angle = angle;
        this.color = color;
        this.rAngle = Math.toRadians(angle);
        description = Character.toString(c);
    }

    Slice(Slice s) {
        this.pCenter = s.getCenter();
        this.radius = s.getRadius();
        this.startAngle = s.getStartAngle();
        this.angle = s.getAngle();
        this.rAngle = Math.toRadians(angle);
    }

    public MyPoint getCenter() {
        return pCenter;
    }

    public int getRadius() {
        return radius;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double getAngle() {
        return angle;
    }

    public double getArcLength() {
        return (double) radius * rAngle;
    }

    public MyColor getColor() {
        return color;
    }

    public double area() {
        return 0.5 * rAngle * Math.pow(radius, 2);
    }

    public double perimeter() {
        return getArcLength();
    }

    public String toString() {
        return "Slice: Center(" + pCenter.getX() + ", " + pCenter.getY() + ") Radius " + radius +
                " (Starting Angle, Angle): (" + startAngle + ", " + angle + "), " +
                color.getJavaFXColor();
    }

    public int draw(GraphicsContext gc, int v1, int v2, int n) {
        gc.setFill(color.getJavaFXColor());
        gc.fillArc(pCenter.getX() - radius, pCenter.getY() - radius, 2 * radius, 2 * radius, startAngle,
                angle, ArcType.ROUND);
        gc.fillRect(v1, v2, 20, 20);
        gc.setFill(MyColor.BLACK.getJavaFXColor());
        if (description.equals("+")) {
            description = "other chars";
        }
        gc.fillText(description, v1 + 25, v2 + 15);
        String s = String.valueOf(n) + " students";
        if (n == 1) {
            s = String.valueOf(n) + " student";
        }
        gc.fillText(s, v1, v2 + 35);
        return 0;
    }
}
