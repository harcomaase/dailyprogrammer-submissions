import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Shuffle<T> {

  public static void main(String[] args) {
    new Shuffle(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8})
            .faro();
    new Shuffle(new String[]{"raspberry", "blackberry", "nectarine", "kumquat", "grapefruit", "cherry", "raspberry", "apple", "mango", "persimmon", "dragonfruit"})
            .faro();
    new Shuffle(new String[]{"e", "a", "i", "o", "u"})
            .faro();
  }

  private final List<T> toShuffle;

  public Shuffle(final T[] toShuffle) {
    this.toShuffle = Arrays.asList(toShuffle);
  }

  public void faro() {
    final int middle = toShuffle.size() / 2;
    final Queue<T> firstSlice = new ArrayDeque<>(toShuffle.subList(0, middle));
    final Queue<T> secondSlice = new ArrayDeque<>(toShuffle.subList(middle, toShuffle.size()));

    final List<T> shuffled = new ArrayList<>();
    int n=0;
    while (firstSlice.size() + secondSlice.size() > 0) {
      final Queue<T> toPoll;
      if (secondSlice.isEmpty() || firstSlice.size() > 0 && n++ % 2 == 0) {
        toPoll = firstSlice;
      } else {
        toPoll = secondSlice;
      }
      System.out.println(System.nanoTime());
      if (firstSlice.size() > 1 && secondSlice.size() > 1) {
        if (System.nanoTime() % 2L == 0L) {
          shuffled.add(toPoll.poll()); //maybe put down 2 cards :)
        }
      }
      shuffled.add(toPoll.poll());
    }

    System.out.println(toShuffle);
    System.out.println(" -> " + shuffled);
  }
}

