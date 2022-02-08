package model.filters;

import model.Pixel;

/**
 * Represents an image filter to blur an image which has this operation conducted upon it. Uses
 * the constant KERNEL to filter in the super abstract class.
 */
public class Blur extends AFilter {
  private static final float[][] KERNEL = {{1f / 16f, 1f / 8f, 1f / 16f},
      {1f / 8f, 1f / 4f, 1f / 8f},
      {1f / 16f, 1f / 8f, 1f / 16f}};

  /**
   * Constructs the Blur filter and passes the constant kernel and image array of Pixels to the
   * super class to filter upon.
   * @param image the image array of Pixels to be operated on.
   */
  public Blur(Pixel[][] image) {
    super(KERNEL, image);
  }
}
