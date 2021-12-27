import javafx.scene.paint.Color;

import java.util.Random;

enum MyColor {
    RED(255, 0, 0, 255),
    GREEN(0, 255, 0, 255),
    BLUE(0, 0, 255, 255),
    WHITE(255, 255, 255, 255),
    BLACK(0, 0, 0, 255),
    RANDOM(0, 0, 0, 255);

    private int r, g, b, a;

    MyColor(int r, int g, int b, int a) {
        if (r >= 0 && r <= 255) this.r = r;
        if (g >= 0 && g <= 255) this.g = g;
        if (b >= 0 && b <= 255) this.b = b;
        if (a >= 0 && a <= 255) this.a = a;
    }

    public Color getJavaFXColor() {
        if (this == MyColor.RANDOM) {
            Random rand = new Random();
            return Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255), (double) a / 255.0);
        } else {
            return Color.rgb(r, g, b, (double) a / 255.0);
        }
    }
}
