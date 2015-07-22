import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Detector {

  public static void main(String[] args) throws Exception {
    List<String> input = Files.readAllLines(Paths.get("/tmp/maze.txt"));

    Detector detector = new Detector(input);
    detector.printMaze();
    System.out.println(" -> " + detector.detectFigures() + " rectangles");

    System.out.println(" challenge 1 -> " + new Detector(Files.readAllLines(Paths.get("/tmp/challenge1.txt"))).detectFigures());
    System.out.println(" extra 1 -> " + new Detector(Files.readAllLines(Paths.get("/tmp/extra1.txt"))).detectFigures());
    System.out.println(" extra 2 -> " + new Detector(Files.readAllLines(Paths.get("/tmp/extra2.txt"))).detectFigures());
  }

  private final char[][] maze;

  private Detector(List<String> input) {
    int longestLine = input.stream().mapToInt(String::length).max().getAsInt();
    maze = new char[input.size()][longestLine];
    for (int y = 0; y < input.size(); y++) {
      String line = input.get(y);
      for (int x = 0; x < line.length(); x++) {
        maze[y][x] = line.charAt(x);
      }
    }
  }

  public int detectFigures() {
    int figureCount = 0;
    for (int y = 0; y < maze.length - 1; y++) {
      for (int x = 0; x < maze[y].length - 1; x++) {
        if (maze[y][x] == '+') {
          figureCount += countFiguresWithStartingPoint(x, y);
        }
      }
    }
    return figureCount;
  }

  private int countFiguresWithStartingPoint(int startX, int startY) {
    int figureCount = 0;
    for (int x = startX + 1; x < maze[startY].length; x++) {
      if (maze[startY][x] == '+') {
        int endX = x;
        for (int y = startY + 1; y < maze.length; y++) {
          if (maze[y][x] == '+') {
            int endY = y;
            if (verifyFigure(startX, startY, endX, endY)) {
              figureCount++;
            }
          }
        }
      }
    }
    return figureCount;
  }

  private boolean verifyFigure(int startX, int startY, int endX, int endY) {
    return verifyEdges(startX, startY, endX, endY)
            && verifyHorizontalLines(startX, endX, startY)
            && verifyHorizontalLines(startX, endX, endY)
            && verifyVerticalLines(startY, endY, startX)
            && verifyVerticalLines(startY, endY, endX);
  }

  private boolean verifyVerticalLines(int startY, int endY, int x) {
    for (int y = startY + 1; y < endY; y++) {
      char c = maze[y][x];
      if (c != '|' && c != '+') {
        return false;
      }
    }
    return true;
  }

  private boolean verifyHorizontalLines(int startX, int endX, int y) {
    for (int x = startX + 1; x < endX; x++) {
      char c = maze[y][x];
      if (c != '-' && c != '+') {
        return false;
      }
    }
    return true;
  }

  private boolean verifyEdges(int startX, int startY, int endX, int endY) {
    char edge = '+';
    return maze[startY][startX] == edge
            && maze[endY][endX] == edge
            && maze[startY][endX] == edge
            && maze[endY][startX] == edge;
  }

  public void printMaze() {
    for (char[] line : maze) {
      System.out.println(line);
    }
  }
}

