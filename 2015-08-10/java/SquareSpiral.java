public class SquareSpiral {

  public static void main(String[] args) {
    switch (args.length) {
      case 2:
        System.out.println(new SquareSpiral(Integer.parseInt(args[0])).buildSpiral(Integer.parseInt(args[1]), -2, -2));
        break;
      case 3:
        System.out.println(new SquareSpiral(Integer.parseInt(args[0])).buildSpiral(-1, Integer.parseInt(args[1]), Integer.parseInt(args[2])));
        break;
      default:
        throw new IllegalStateException("invalid arguments");
    }
  }

  private final long[][] square;
  private final int size;

  public SquareSpiral(int size) {
    if (size % 2 == 0) {
      throw new IllegalStateException("expected odd size");
    }
    square = new long[size][size];
    this.size = size;
  }

  private String buildSpiral(int destPos, int destX, int destY) {
    long currentNo = 0;
    int currentDirection = 0;
    int x = (size - 1) / 2;
    int y = x;

    square[y][x] = ++currentNo;

    while (currentNo <= size * size) {
      if (destPos == currentNo) {
        return (x + 1) + " " + (y + 1);
      }
      if (destX == x + 1 && destY == y + 1) {
        return currentNo + "";
      }
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
      square[y][x] = ++currentNo;
      if (square[adjacentY][adjacentX] == 0) {
        currentDirection = (currentDirection + 1) % 4;
      }
    }
    return "what? :)";
  }

}
