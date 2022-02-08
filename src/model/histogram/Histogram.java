package model.histogram;

import java.util.ArrayList;

import model.Pixel;

/**
 * Represents a model of a Histogram graph for representing the components of an image.
 */
public interface Histogram {

  /**
   * Upon the changing/editing of an image, this method will allow the program to switch the image
   * being represented by the histogram.
   * @param image the image to change the view to represent.
   */
  void setImage(Pixel[][] image);

  /**
   * Returns the ArrayList of red component pixels within the given image in order to conduct
   * operations upon the histogram with.
   * @return an ArrayList of the red component pixels within the image.
   */
  ArrayList<Integer> getRedBar();

  /**
   * Returns the ArrayList of green component pixels within the given image in order to conduct
   * operations upon the histogram with.
   * @return an ArrayList of the green component pixels within the image.
   */
  ArrayList<Integer> getGreenBar();

  /**
   * Returns the ArrayList of blue component pixels within the given image in order to conduct
   * operations upon the histogram with.
   * @return an ArrayList of the blue component pixels within the image.
   */
  ArrayList<Integer> getBlueBar();

  /**
   * Returns the ArrayList of intensity component pixels within the given image in order to conduct
   * operations upon the histogram with.
   * @return an ArrayList of the intensity component pixels within the image.
   */
  ArrayList<Integer> getIntensityBar();

  /**
   * Fills in each of the frequency lists with their appropriate values in order to be used
   * with the histogram.
   */
  void fillFrequencies();
}
