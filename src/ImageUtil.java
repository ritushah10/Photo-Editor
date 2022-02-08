import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import model.Pixel;

/**
 * This class contains utility methods to read a PPM image from file and simply return its
 * contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and return the array of Pixels from that PPM file.
   *
   * @param filename the path of the file.
   * @return The array of Pixels scanned in from the file.
   */
  public static Pixel[][] readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    Pixel[][] pixels = new Pixel[height][width];

    int maxValue = sc.nextInt();

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel p = new Pixel(r, g, b);
        pixels[i][j] = p;
      }
    }
    return pixels;
  }
}

