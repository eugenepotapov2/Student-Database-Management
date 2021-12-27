import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

class HistogramAlphaBet {
    Map<Character, Integer> frequency = new HashMap<Character, Integer>();
    Map<Character, Double> probability = new HashMap<Character, Double>();

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

    public Integer getCumulativeFrequency() {
        return frequency.values().stream().reduce(0, Integer::sum);
    }

    public Map<Character, Double> getProbability() {
        double inverseCumulativeFrequency = 1.0 / getCumulativeFrequency();
        for (Character Key : frequency.keySet()) {
            probability.put(Key, (double) frequency.get(Key) * inverseCumulativeFrequency);
        }
        return probability.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public Double getSumOfProbability() {
        return probability.values().stream().reduce(0.0, Double::sum);
    }

    public Map<Character, Integer> sortUpFrequency() {
        return frequency.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public Map<Character, Integer> sortDownFrequency() {
        return frequency.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public String toString() {
        String output = "Frequency of Characters\n";
        for (Character K : frequency.keySet()) {
            output += K + ": " + frequency.get(K) + "\n";
        }
        return output;
    }
}
