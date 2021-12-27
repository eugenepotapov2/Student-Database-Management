import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main extends Application {
    static int N;
    static Map<Character, Integer> gradesMap = new HashMap<Character, Integer>();

    public static void main(String[] args) throws SQLException {
        Database db = new Database();
        db.connectToDatabase();

        Scanner scan = new Scanner(System.in);
        System.out.println("Start over? (Enter YES to confirm)");
        String check = scan.nextLine();
        if ("YES".equals(check)) {
            db.dropTables();
            db.createTableSchedule();
            db.bulkTableSchedule();
            db.createTableCourses();
            db.createTableStudents();
            db.createTableClasses();
        }

        System.out.println("\nEnter Student's information that you want to add as follows:\n" +
                "empId, firstName, lastName, email, gender (only M, F, U) OR write STOP to finish");
        while (true) {
            String studentData = scan.nextLine();
            if ("STOP".equals(studentData)) {
                break;
            }
            String[] studentSplit = studentData.split(", ");
            String temp0 = studentSplit[0] + ", ";
            String temp1 = "'" + studentSplit[1] + "', ";
            String temp2 = "'" + studentSplit[2] + "', ";
            String temp3 = "'" + studentSplit[3] + "', ";
            String temp4 = "'" + studentSplit[4] + "'";
            studentData = temp0 + temp1 + temp2 + temp3 + temp4;
            //System.out.println(studentData);
            db.enterStudent(studentData);
        }

//        System.out.println("\nDo you want to drop any student? (Enter YES to confirm)");
//        String check2 = scan.nextLine();
//        if ("YES".equals(check2)) {
//            System.out.println("\nEnter Student's information that you want to drop as follows:\n" +
//                    "empId, firstName, lastName, email, gender (only M, F, U) OR write STOP to finish");
//            while (true) {
//                String studentData = scan.nextLine();
//                if ("STOP".equals(studentData)) {
//                    break;
//                }
//                String[] studentSplit = studentData.split(", ");
//                String temp0 = studentSplit[0];
//                int tempInt0 = Integer.parseInt(temp0);
//                String temp1 = "'" + studentSplit[1] + "'";
//                String temp2 = "'" + studentSplit[2] + "'";
//                String temp3 = "'" + studentSplit[3] + "'";
//                String temp4 = "'" + studentSplit[4] + "'";
//                studentData = temp0 + temp1 + temp2 + temp3 + temp4;
//                //System.out.println(studentData);
//                db.dropStudent(tempInt0, temp1, temp2, temp3, temp4);
//            }
//        }


        System.out.println("\nEnter Class' information that you want to add as follows:\n" +
                "empId, sectionNumber, grade OR write STOP to finish");
        while (true) {
            String classData1 = scan.nextLine();
            if ("STOP".equals(classData1)) {
                break;
            }
            String[] classSplit = classData1.split(", ");
            String empIdString = classSplit[0];
            String sectionNumber = classSplit[1];
            String grade = classSplit[2];
            int empId = Integer.parseInt(empIdString);
            db.enterClasses(empId, sectionNumber, grade);
        }

        System.out.println("\nDo you want to drop any class? (Enter YES to confirm)");
        String check1 = scan.nextLine();
        if ("YES".equals(check1)) {
            System.out.println("\nEnter Class' information that you want to drop as follows:\n" +
                    "empId, sectionNumber, grade OR write STOP to finish");
            while (true) {
                String classData2 = scan.nextLine();
                if ("STOP".equals(classData2)) {
                    break;
                }
                String[] classSplit = classData2.split(", ");
                String empIdString = classSplit[0];
                String sectionNumber = classSplit[1];
                String grade = classSplit[2];
                int empId = Integer.parseInt(empIdString);
                db.dropClass(empId, sectionNumber, grade);
            }
        }

        System.out.println("\nDo you want to delete any grade? (Enter YES to confirm)");
        String check3 = scan.nextLine();
        if ("YES".equals(check3)) {
            System.out.println("\nEnter grade that you want to delete or write STOP to finish");
            while (true) {
                String grade = scan.nextLine();
                if ("STOP".equals(grade)) {
                    break;
                }
                db.deleteGrade(grade);
            }
        }

//        System.out.println("Enter number of slices: ");
//        N = scan.nextInt();

        scan.close();

        gradesMap = db.mapAggregateGrades();


        launch(args);
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

        String fileName = "Alice in Wonderland.txt";
        Scanner input = null;

        try {
            input = new Scanner(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("File is not found");
        }

        try {
            String w = "";
            while (input.hasNext()) {
                w += input.nextLine().replaceAll("[^a-zA-Z]", "").toLowerCase();
            }

//            System.out.println("\nNumber of characters: " + w.length() + "\n");

            HistogramAlphaBet H = new HistogramAlphaBet(w);
            Map<Character, Integer> sortedFrequency = H.sortDownFrequency();

//            sortedFrequency.forEach((K, V) -> System.out.println(K + ": " + V));
//            System.out.println("\nCumulative Frequency: " + H.getCumulativeFrequency() + "\n");

            HistogramAlphaBet.MyPieChart pieChart = H.new MyPieChart(gradesMap, new MyPoint(400, 400, null), 200, 30.0);
//            System.out.println(pieChart.getProbability());
//            System.out.println("\nSum of Probabilities: " + pieChart.getSumOfProbability() + "\n");

//            Map<Character, Slice> slices = pieChart.getMyPieChart();
//            sortedFrequency.forEach((K, V) -> System.out.println(K + ": " + slices.get(K)));
//            double sumOfAngles = 0.0;
//            for (Character Key : slices.keySet()) {
//                sumOfAngles += slices.get(Key).getAngle();
//            }
//            System.out.println("\nSum of Angles: " + sumOfAngles);

            pieChart.draw(gc);
            System.out.println("\nDone!");

        } catch (NoSuchElementException e) {
            System.err.println("Invalid input! Terminating...");
        } catch (IllegalStateException e) {
            System.out.println("Error processing file! Terminating...");
        }

        input.close();
    }
}


