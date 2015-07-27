import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SudokuChecker {

  public static void main(String[] args) throws IOException {
    for (String file : args) {
      System.out.println(file + " is valid solution: " + new SudokuChecker(file).isValid());
    }
  }

  public SudokuChecker(String file) throws IOException {
    grid = new ArrayList<>(9);
    Files.readAllLines(Paths.get(file))
            .stream()
            .forEach(line -> grid.add(Arrays.asList(line.split(" "))
                            .stream()
                            .map(Integer::parseInt)
                            .collect(Collectors.toList())));
  }

  private final List<List<Integer>> grid;

  public boolean isValid() {
    boolean rowsOk = grid.stream().filter(line -> line.stream().distinct().count() != 9).count() == 0;
    System.out.println("rows " + (rowsOk ? "" : "not") + " ok");

    boolean columnsOk = true;
    for (int n = 0; n < 9; n++) {
      final int column = n;
      columnsOk &= grid.stream().map(row -> row.stream().skip(column).limit(1)).distinct().count() == 9;
    }
    System.out.println("columns " + (columnsOk ? "" : "not") + " ok");

    boolean boxesOk = true;
    for (int col = 0; col < 9; col += 3) {
      final int colNo = col;
      for (int rowNo = 0; rowNo < 9; rowNo += 3) {
        boxesOk &= grid.stream().skip(rowNo).limit(rowNo + 3).flatMap(row -> row.stream().skip(colNo).limit(colNo + 3).distinct()).distinct().count() == 9;
      }
    }
    System.out.println("boxes " + (boxesOk ? "" : "not") + " ok");

    return rowsOk && columnsOk && boxesOk;
  }
}

