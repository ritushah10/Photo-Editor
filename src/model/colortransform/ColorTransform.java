package model.colortransform;

import model.Pixel;

/**
 * Represents a color transformation given an array of Pixels.
 */

public interface ColorTransform {
  /**
   * Performs a color transformation given an array of Pixels and returns a new arry
   * of pixels that have been transformed.
   * @return an array of Pixels
   */
  Pixel[][] transform();
}
