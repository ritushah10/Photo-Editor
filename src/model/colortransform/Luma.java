package model.colortransform;

import model.Pixel;

/**
 * Represents an image color transformation to luma. Extends the Greyscale class as it uses the
 * same matrix to conduct operations with.
 */
public class Luma extends Greyscale {
  /**
   * Constructs a new Luma color transformation function object.
   * @param image  the array of Pixels to be operated on.
   */
  public Luma(Pixel[][] image) {
    super(image);
  }
}
