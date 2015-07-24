import java.util.LinkedList;
import java.util.List;

public class Langford2 {

  public static void main(String[] args) {

    for (int order : new int[]{3, 4, 8}) {
      System.out.println("Langford string of order " + order);
      new Langford2(order).calculateAndPrint();
    }
  }

  private final int order;
  private final int length;
  private final List<String> strings;
  //
  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final char FILLER = '#';

  public Langford2(int order) {
    this.order = order;
    this.length = order * 2;
    this.strings = new LinkedList<>();
  }

  public void calculateAndPrint() {

    long start = System.currentTimeMillis();

    //fill with a's, then go on
    int index = 0;
    char letter = LETTERS.charAt(index);
    int spacing = index + 2;

    for (int n = 0; n < length - spacing; n++) {
      char[] string = createFilledCharArray();
      string[n] = letter;
      string[n + spacing] = letter;
      addLetters(string, index + 1);
    }

    System.out.println(" -> " + strings.size() + "results (took " + (System.currentTimeMillis() - start) + "ms)");
    strings.stream().sorted().forEach(string -> System.out.println(string));

  }

  private void addLetters(char[] string, int index) {
    char letter = LETTERS.charAt(index);
    int spacing = index + 2;
    for (int n = 0; n < length - spacing; n++) {
      if (string[n] != FILLER || string[n + spacing] != FILLER) {
        continue;
      }
      char[] copy = copy(string);
      copy[n] = letter;
      copy[n + spacing] = letter;
      if (index == order - 1) {
        strings.add(new String(copy));
        return;
      }
      if (index < order - 1) {
        addLetters(copy, index + 1);
      }
    }
  }

  private char[] createFilledCharArray() {
    char[] string = new char[length];
    for (int n = 0; n < length; n++) {
      string[n] = FILLER;
    }
    return string;
  }

  private char[] copy(char[] string) {
    int size = string.length;
    char[] copy = new char[size];
    for (int n = 0; n < size; n++) {
      copy[n] = string[n];
    }
    return copy;
  }
}
