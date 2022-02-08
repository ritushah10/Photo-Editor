package model.colortransform;

import model.Pixel;

/**
 * Represents an image color transformation to greyscale. Provides the superclass with the given
 * matrix of values to conduct the operation with.
 */
public class Greyscale extends AColorTransform {
  private static final float[][] MATRIX = {{0.2126f, 0.7152f, 0.0722f},
      {0.2126f, 0.7152f, 0.0722f},
      {0.2126f, 0.7152f, 0.0722f}};

  /**
   * Constructs a new Greyscale color transformation function object.
   * @param image the array of Pixels to be operated on.
   */
  public Greyscale(Pixel[][] image) {
    super(MATRIX, image);
  }
}
