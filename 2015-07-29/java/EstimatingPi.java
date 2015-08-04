import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class EstimatingPi {

  public static void main(String[] args) throws IOException {
    for (String file : new String[]{"/tmp/pi/input1.png", "/tmp/pi/input2.png"}) {
      System.out.println("PI from image '" + file + "': " + new EstimatingPi(file).estimateByPicture());
    }
  }

  private final BufferedImage image;
  private double blackPixels = 0;
  private double diameter = 0;

  public EstimatingPi(String file) throws IOException {
    image = ImageIO.read(new File(file));
  }

  public double estimateByPicture() {
    countPixels();

    // A = PI * (d/2)² -> PI = A / (d/2)²
    return blackPixels / ((diameter / 2d) * (diameter / 2d));
  }

  private void countPixels() {
    int width = image.getWidth();
    int height = image.getHeight();

    int minX = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int rgb = image.getRGB(x, y);
        if (rgb != -1) {
          blackPixels++;
          if (x < minX) {
            minX = x;
          }
          if (x > maxX) {
            maxX = x;
          }
        }
      }
    }
    diameter = maxX - minX;
  }
}
