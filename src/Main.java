import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
//        launch(args);
        MyFile.openFile();
        MyFile.readFile();
        MyFile.closeFile();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Canvas");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        MyPoint point1 = new MyPoint(0, 0, MyColor.RANDOM);
        MyPoint point2 = new MyPoint(800, 800, MyColor.RANDOM);
        MyPoint point3 = new MyPoint(200, 200, MyColor.RANDOM);
        MyPoint point4 = new MyPoint(450, 425, MyColor.WHITE);
        MyRectangle rectangle1 = new MyRectangle(point3, 400, 400, MyColor.RANDOM);
        MyOval oval1 = rectangle1.getInnerOval(MyColor.GREEN);
        MyRectangle rectangle2 = oval1.getInnerRect(MyColor.BLUE);
        MyOval oval2 = rectangle2.getInnerOval(MyColor.RANDOM);
        MyRectangle rectangle3 = oval2.getInnerRect(MyColor.RANDOM);
        MyOval oval3 = rectangle3.getInnerOval(MyColor.RED);
        MyLine line1 = new MyLine(point1, point2, MyColor.BLACK);
        MyRectangle rectangle4 = line1.getMyBoundingRectangle(MyColor.WHITE);
        MyPolygon polygon1 = oval3.getInnerPolygon(8, MyColor.RANDOM);
        MyPolygon polygon2 = rectangle1.getInnerPolygon(14, MyColor.BLACK);

        MyShape[] myShapes = new MyShape[]{
                rectangle4,
                rectangle1,
                oval1,
                polygon2,
                rectangle2,
                oval2,
                rectangle3,
                oval3,
                polygon1,
                line1,
        };

        MyPoint[] myPoints = new MyPoint[]{
                point1,
                point2,
                point3,
                point4,
        };

        for (MyShape myShape : myShapes) {
            myShape.draw(gc);
            System.out.println(myShape);
        }
        for (MyPoint myPoint : myPoints) {
            myPoint.draw(gc);
            System.out.println(myPoint);
        }
        System.out.println("Distance between " + point2 + " & " + point3 + ": " + point2.distanceBetweenPoints(point3));
        System.out.println("Distance of " + point3 + " from origin: " + point3.distanceFromOrigin());
        System.out.println(point4 + " inside of shape: " + oval2.pointInMyShape(point4));
    }
}