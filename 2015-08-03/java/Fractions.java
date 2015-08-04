import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Fractions {

  public static void main(String[] args) throws IOException {
    for (String file : new String[]{"input1.txt", "input2.txt", "challenge1.txt", "challenge2.txt", "challenge3.txt"}) {
      List<Fraction> addends = Files.readAllLines(Paths.get("/tmp/f", file)).stream()
              .skip(1)
              .filter(line -> line.length() > 0)
              .map(line -> new Fraction(Integer.parseInt(line.split("/")[0].trim()), Integer.parseInt(line.split("/")[1].trim())))
              .collect(Collectors.toList());
      System.out.println(addAll(addends));
    }
  }

  private static Fraction addAll(List<Fraction> addends) {
    Fraction result = new Fraction(0, 1);
    for (Fraction f : addends) {
      result = result.add(f);
    }
    return result;
  }
}