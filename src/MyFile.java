import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MyFile {
    static String fileName = "Alice in Wonderland.txt";
    static Scanner input;

    public static void openFile() {
        try {
            input = new Scanner(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("File is not found");
        }
    }

    public static void readFile() {
        try {
            String w = "";
            while (input.hasNext()) {
                w += input.nextLine().replaceAll("[^a-zA-Z]", "").toLowerCase();
            }

            System.out.println("\nNumber of characters: " + w.length() + "\n");

            HistogramAlphaBet H = new HistogramAlphaBet(w);
            Map<Character, Integer> sortedFrequency = H.sortDownFrequency();

            sortedFrequency.forEach((K, V) -> System.out.println(K + ": " + V));
            System.out.println("\nCumulative Frequency: " + H.getCumulativeFrequency() + "\n");

            System.out.println(H.getProbability());
            System.out.println("\nSum of Probabilities: " + H.getSumOfProbability() + "\n");
        } catch (NoSuchElementException e) {
            System.err.println("Invalid input! Terminating...");
        } catch (IllegalStateException e) {
            System.out.println("Error processing file! Terminating...");
        }
    }

    public static void closeFile() {
        if (input != null) input.close();
    }
}
