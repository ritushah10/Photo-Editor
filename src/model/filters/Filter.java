package model.filters;

import model.Pixel;

/**
 * Represents an image filter to be used on a given image's pixels.
 */
public interface Filter {
  /**
   * Filters the given array of Pixels for an image and returns a new array of Pixels that has
   * been filtered.
   * @return an array of Pixels which have been filtered
   */
  Pixel[][] filter();
}
