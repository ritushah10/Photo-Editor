package model;

import java.util.HashMap;
import java.util.Map;

import model.colortransform.ColorTransform;
import model.colortransform.Greyscale;
import model.colortransform.Luma;
import model.colortransform.Sepia;
import model.filters.Blur;
import model.filters.Filter;
import model.filters.Sharpen;

/**
 * Represents a SimpleImageEditor. Can load, conduct operations on, and save images. This editor
 * can also store multiple images within its memory on a single concurrent run. Then, allowing
 * the user to save any of these representations of the images to a given path.
 */
public class SimpleEditorModel implements ImageEditorModel {
  protected final Map<String, Pixel[][]> images;

  /**
   * Constructs an instance of the model to represent the image at the given file name for the
   * parameter. After checking to ensure the file name is not null, the fileName field will be
   * instantiated and the image 2D array will be instantiated as well.
   */
  public SimpleEditorModel() {
    this.images = new HashMap<>();
  }

  @Override
  public void acceptNewImage(Pixel[][] image, String name)
      throws IllegalArgumentException {
    if (image == null || name == null) {
      throw new IllegalArgumentException("Image or name accepted may not be null");
    }
    this.images.put(name, image);
  }

  @Override
  public Pixel[][] releaseImage(String name) throws IllegalStateException {
    Pixel[][] value = this.images.get(name);
    if (value == null) {
      throw new IllegalStateException("Image \"" + name + "\" not found in saved list.");
    }
    return value;
  }

  @Override
  public void component(Component type, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    if (type == null || fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("Component type and image names must not be null");
    }

    if (type == Component.LUMA) {
      ColorTransform luma = new Luma(this.releaseImage(fromImageName));
      this.images.put(toImageName, luma.transform());
      return;
    }

    Pixel[][] image = this.releaseImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel p = image[i][j].clone();
        int component;
        switch (type) {
          case RED:
            component = p.getRed();
            p.setGreen(component);
            p.setBlue(component);
            break;
          case GREEN:
            component = p.getGreen();
            p.setRed(component);
            p.setBlue(component);
            break;
          case BLUE:
            component = p.getBlue();
            p.setRed(component);
            p.setGreen(component);
            break;
          case VALUE:
            int maxVal = Math.max(Math.max(p.getRed(), p.getGreen()), p.getBlue());
            p.setRGB(maxVal, maxVal, maxVal);
            break;
          case INTENSITY:
            double avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3.0;
            int newAvg = (int) Math.round(avg);
            p.setRGB(newAvg, newAvg, newAvg);
            break;
          default:
            /*
             * No action is intended when no other case applies. The switch statement switches on
             * the model.Component ENUM, and all the options are specified above, so the program
             * will never hit the default case.
             */
            break;
        }
        result[i][j] = p;
      }
    }
    this.images.put(toImageName, result);
  }

  @Override
  public void flip(Flip type, String fromImageName, String toImageName) {
    if (fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("The image from and destination names must not be null.");
    }

    Pixel[][] image = this.releaseImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel temp = image[i][j].clone();
        if (type == Flip.HORIZONTAL) {
          result[i][j] = image[i][result[i].length - j - 1].clone();
          result[i][result[i].length - j - 1] = temp;
        } else if (type == Flip.VERTICAL) {
          result[i][j] = image[result.length - i - 1][j].clone();
          result[result.length - i - 1][j] = temp;
        }
      }
    }
    this.images.put(toImageName, result);
  }


  @Override
  public void brighten(int value, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException {
    if (fromImageName == null || toImageName == null) {
      throw new IllegalArgumentException("The image from and destination names must not be null.");
    }

    Pixel[][] image = this.releaseImage(fromImageName);
    Pixel[][] result = new Pixel[image.length][image[0].length];

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[i].length; j++) {
        Pixel p = image[i][j].clone();

        p.setRGB(this.brightenCalc(p.getRed(), value),
                 this.brightenCalc(p.getGreen(), value),
                 this.brightenCalc(p.getBlue(), value));

        result[i][j] = p;
      }
    }
    this.images.put(toImageName, result);
  }

  /**
   * Supporting method for the this.brighten() method. Given the current value and the change,
   * will cap the value at either 0 or 255 depending on whether the change is negative or
   * positive. Will then return the result.
   * @param before the value before the brighten is added/subtracted.
   * @param change the value to either add or subtract to brighten/darken the image.
   * @return the new value after adding/subtracting, capped between 0 and 255 inclusive.
   */
  private int brightenCalc(int before, int change) {
    int result = before + change;

    if (result < 0) {
      return 0;
    }
    if (result > 255) {
      return 255;
    }
    return result;
  }

  /**
   * Blurs an image given a fromImageName and a toImageName.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalStateException if the program properly get an image
   */
  @Override
  public void blur(String fromImageName, String toImageName) throws IllegalStateException {
    Filter blur = new Blur(this.releaseImage(fromImageName));
    this.images.put(toImageName, blur.filter());
  }

  /**
   * Sharpens an image given the fromImageName and toImageName.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalStateException if the program is not able to properly get an image
   */
  @Override
  public void sharpen(String fromImageName, String toImageName) throws IllegalStateException {
    Filter sharpen = new Sharpen(this.releaseImage(fromImageName));
    this.images.put(toImageName, sharpen.filter());
  }

  /**
   * Converts a colored image to only have shades of grey.
   * @param fromImageName the image to conduct this operation on, stored in memory
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalStateException if the program is not able to properly get an image
   */
  @Override
  public void greyscale(String fromImageName, String toImageName) throws IllegalStateException {
    ColorTransform greyscale = new Greyscale(this.releaseImage(fromImageName));
    this.images.put(toImageName, greyscale.transform());
  }

  /**
   * Converts a normal colored image into a sepia-toned image.
   * @param fromImageName the image to conduct this operation on, stored in memory
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalStateException if the program is not able to properly get an image
   */
  @Override
  public void sepia(String fromImageName, String toImageName) throws IllegalStateException {
    ColorTransform sepia = new Sepia(this.releaseImage(fromImageName));
    this.images.put(toImageName, sepia.transform());
  }

  @Override
  public String[] getListOfLayers() {
    return this.images.keySet().toArray(new String[0]);
  }
}
