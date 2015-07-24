import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Langford5 {

  public static void main(String[] args) {

    for (int order : new int[]{20}) {
      System.out.println("Langford string of order " + order);
      new Langford5(order).calculateAndPrint();
    }
  }

  private final int order;
  private final int length;
  private final List<char[]> strings;
  private char[] currentWorst = null;
  private final Comparator<char[]> CHAR_COMPARATOR = new Comparator<char[]>() {

    @Override
    public int compare(char[] o1, char[] o2) {
      for (int n = 0; n < o1.length; n++) {
        if (o1[n] == o2[n]) {
          continue;
        }
        if (o1[n] < o2[n]) {
          return -1;
        }
        if (o1[n] > o2[n]) {
          return 1;
        }
      }
      return 0;
    }
  };
  //
  private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final char FILLER = '#';
  private static final int FIRST_X = 10;

  public Langford5(int order) {
    this.order = order;
    this.length = order * 2;
    this.strings = new ArrayList<>();
  }

  public void calculateAndPrint() {

    long start = System.currentTimeMillis();

    //fill with a's, then go on
    int index = 0;
    char letter = LETTERS.charAt(index);
    int spacing = index + 2;

    int n = 0;
    char[] string = createFilledCharArray();
    string[n] = letter;
    string[n + spacing] = letter;
    addLetters(string, index + 1);

    System.out.println(" -> " + strings.size() + "results (took " + (System.currentTimeMillis() - start) + "ms)");
    strings.stream().forEach(printMe -> System.out.println(new String(printMe)));

  }

  private void addLetters(char[] string, int index) {
    if (strings.size() == FIRST_X && !stringIsBetterThanWorst(string, index)) {
      return;
    }
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
        if (strings.size() < FIRST_X) {
          strings.add(copy);
          if (strings.size() == FIRST_X) {
            currentWorst = strings.get(FIRST_X - 1);
            Collections.sort(strings, CHAR_COMPARATOR);
          }
          return;
        }
        if (stringIsBetterThanWorst(copy, length)) {
          strings.set(FIRST_X - 1, copy);
          Collections.sort(strings, CHAR_COMPARATOR);
          currentWorst = strings.get(FIRST_X - 1);
        }
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
    System.arraycopy(string, 0, copy, 0, size);
    return copy;
  }

  private boolean stringIsBetterThanWorst(char[] contestant, int checkLength) {
    for (int n = 0; n < checkLength; n++) {
      if (contestant[n] == currentWorst[n]) {
        continue;
      }
      if (contestant[n] < currentWorst[n]) {
        return true;
      }
      return false;
    }
    return false;
  }
}

