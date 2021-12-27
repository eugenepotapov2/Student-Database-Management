import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.stream.Collectors;

class HistogramAlphaBet {
    Map<Character, Integer> frequency = new HashMap<Character, Integer>();

    HistogramAlphaBet() {
    }

    HistogramAlphaBet(Map<Character, Integer> m) {
        frequency.putAll(m);
    }

    HistogramAlphaBet(String text) {
        String w = text.replaceAll("[^a-zA-Z]", "").toLowerCase();
        for (int i = 0; i < w.length(); i++) {
            Character key = w.charAt(i);
            incrementFrequency(frequency, key);
        }
    }

    private static <K> void incrementFrequency(Map<K, Integer> m, K Key) {
        m.putIfAbsent(Key, 0);
        m.put(Key, m.get(Key) + 1);
    }

    public Map<Character, Integer> getFrequency() {
        return frequency;
    }

    public Map<Character, Integer> sortUpFrequency() {
        return frequency.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public Map<Character, Integer> sortDownFrequency() {
        return frequency.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public Integer getCumulativeFrequency() {
        return frequency.values().stream().reduce(0, Integer::sum);
    }

    public String toString() {
        String output = "Frequency of Characters\n";
        for (Character K : frequency.keySet()) {
            output += K + ": " + frequency.get(K) + "\n";
        }
        return output;
    }

    class MyPieChart {
        Map<Character, Double> probability = new HashMap<Character, Double>();
        Map<Character, Slice> slices = new HashMap<Character, Slice>();
        Map<Character, Double> tempProbability = new HashMap<Character, Double>();
        Map<Character, Integer> gradesMap = new HashMap<Character, Integer>();

        int N;
        MyPoint pCenter;
        int radius;
        double rotateAngle;
        double startAngle = 90.0;
        double restProb = 1.0;

        MyPieChart(Map<Character, Integer> gradesMap, MyPoint p, int r, double rotateAngle) {
            this.gradesMap = gradesMap;
            this.pCenter = p;
            this.radius = r;
            this.rotateAngle = Optional.ofNullable(rotateAngle).orElse(0.0);
            probability = getProbability();
            slices = getMyPieChart();
        }

        public Map<Character, Double> getProbability() {
            double inverseCumulativeFrequency = 1.0 / (gradesMap.values().stream().reduce(0, Integer::sum));
            for (Character Key : gradesMap.keySet()) {
                probability.put(Key, (double) gradesMap.get(Key) * inverseCumulativeFrequency);
            }
            return probability.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        }

        public Double getSumOfProbability() {
            return probability.values().stream().reduce(0.0, Double::sum);
        }

        public Map<Character, Slice> getMyPieChart() {

            for (Character Key : gradesMap.keySet()) {
                double angle = 360.0 * probability.get(Key);
                slices.put(Key, new Slice(pCenter, radius, startAngle, angle, MyColor.RANDOM, Key));
                startAngle += angle;
            }
            return slices;
        }

        public void draw(GraphicsContext gc) {
            int v1 = 650, v2 = 200;
            for (Character Key : gradesMap.keySet()) {
                slices.get(Key).draw(gc, v1, v2, gradesMap.get(Key));
                v2 += 50;
            }
        }
    }
}
