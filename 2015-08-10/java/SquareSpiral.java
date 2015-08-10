public class SquareSpiral {
  
  public static void main(String[] args) {
    SquareSpiral instance = new SquareSpiral(5);
    instance.print();
  }

  private final long[][] square;
  private final int size;

  public SquareSpiral(int size) {
    if (size % 2 == 0) {
      throw new IllegalStateException("expected odd size");
    }
    square = new long[size][size];
    this.size = size;
    buildSpiral();
  }
  
  public void print() {
    for(long[] line : square) {
      StringBuilder l = new StringBuilder();
      for(long lo : line) {
        l.append(lo).append(" ");
      }
      System.out.println(l.toString());
    }
  }

  private void buildSpiral() {
    long currentNo = 1;
    int currentDirection = 0;
    int x = (size - 1) / 2;
    int y = x;

    square[y][x] = currentNo++;

    while (currentNo <= size * size) {
      int adjacentX;
      int adjacentY;
      if (currentDirection == 0) {//right
        x++;
        adjacentY = y - 1;
        adjacentX = x;
      } else if (currentDirection == 2) { //left
        x--;
        adjacentY = y + 1;
        adjacentX = x;
      } else if (currentDirection == 1) { //up
        y--;
        adjacentY = y;
        adjacentX = x - 1;
      } else { //down
        y++;
        adjacentY = y;
        adjacentX = x + 1;
      }
      square[y][x] = currentNo++;
      if (square[adjacentY][adjacentX] == 0) {
        currentDirection = (currentDirection + 1) % 4;
      }
    }
  }

}
