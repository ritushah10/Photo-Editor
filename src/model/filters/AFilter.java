package model.filters;

import model.Pixel;

/**
 * Represents an abstract filter object to convert the RGB values of an image using the given
 * kernel.
 */
public abstract class AFilter implements Filter {
  float[][] kernel;
  int kWidth;
  int kHeight;
  Pixel[][] image;

  protected AFilter(float[][] kernel, Pixel[][] image) throws IllegalArgumentException {
    if (kernel == null || image == null) {
      throw new IllegalArgumentException("Arguments of filter must not be null");
    }

    this.kernel = kernel;
    kWidth = kernel[0].length;
    kHeight = kernel.length;
    this.image = image.clone();
  }

  /**
   * Goes through each Pixel of an image and applies a given filter to change the RGB value.
   *
   * @return a new image with converted pixels
   */
  public Pixel[][] filter() {
    // Goes through each Pixel from the image
    for (int row = 0; row < this.image.length; row++) {
      for (int col = 0; col < this.image[row].length; col++) {
        double r = 0;
        double g = 0;
        double b = 0;

        for (int i = 0; i < this.kHeight; i++) {
          for (int j = 0; j < this.kWidth; j++) {
            int newRow = row + i - (kHeight / 2);
            int newCol = col + j - (kWidth / 2);

            if (this.outOfBounds(newRow, newCol)) {
              continue;
            }

            r += this.kernel[i][j] * this.image[newRow][newCol].getRed();
            g += this.kernel[i][j] * this.image[newRow][newCol].getGreen();
            b += this.kernel[i][j] * this.image[newRow][newCol].getBlue();
          }

        }
        this.image[row][col] = new Pixel(this.colorCap((int) Math.round(r)),
            this.colorCap((int) Math.round(g)), this.colorCap((int) Math.round(b)));
      }
    }
    return this.image;
  }

  private boolean outOfBounds(int row, int col) {
    if (row > image.length - 1 || row < 0) {
      return true;
    }
    return col > this.image[row].length - 1 || col < 0;
  }

  private int colorCap(int val) {
    if (val < 0) {
      return 0;
    }
    return Math.min(val, 255);
  }
}