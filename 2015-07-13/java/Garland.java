import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.StringJoiner;

public class Garland {

  public boolean verbose = true;

  public static void main(String[] args) throws IOException {
    Garland instance = new Garland();

    Arrays.asList(new String[]{"programmer", "ceramic", "onion", "alfalfa"})
            .stream().forEach((final String input) -> System.out.println("garland(\"" + input + "\") -> " + instance.garland(input)));

    instance.verbose = false;
    GarlandWithDegree largest = Files.readAllLines(Paths.get("enable1.txt"))
            .stream()
            .map(word -> new GarlandWithDegree(word, instance.garland(word)))
            .sorted((o1, o2) -> o2.degree - o1.degree).findFirst().get();
    System.out.println("largest garland: '" + largest.string + "' with degree of " + largest.degree);
  }

  public int garland(final String string) {
    int size = string.length();
    for (int i = size - 1; i > 0; i--) {
      if (string.endsWith(string.substring(0, i))) {
        if (verbose) {
          printGarlandChain(string, i);
        }
        return i;
      }
    }
    return 0;
  }

  private void printGarlandChain(String string, int degree) {
    String prefix = string.substring(0, string.length() - degree);
    String suffix = string.substring(string.length() - degree, string.length());
    StringJoiner joiner = new StringJoiner("", "", suffix);
    for (int i = 0; i < 10; i++) {
      joiner.add(prefix);
    }
    System.out.println(joiner.toString());
  }

  private static class GarlandWithDegree {

    String string;
    int degree;

    public GarlandWithDegree(String string, int degree) {
      this.string = string;
      this.degree = degree;
    }
  }
}
