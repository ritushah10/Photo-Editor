package model;

/**
 * Represents a specific pixel within an image using the RGB coloring system along with row and
 * column number locations.
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a new model.Pixel object using the given parameters and checks to ensure none of the
   * provided parameters are negative.
   * @param red the red value of the RGB combination for the pixel's color.
   * @param green the green value of the RGB combination for the pixel's color.
   * @param blue the blue value of the RGB combination for the pixel's color.
   * @throws IllegalArgumentException if any of the provided parameters are negative.
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("Arguments for pixel may not be negative.");
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns the red value of the RGB combination for this pixel's color.
   * @return the red value as an integer.
   */
  public int getRed() {
    return this.red;
  }

  /**
   * Returns the green value of the RGB combination for this pixel's color.
   * @return the green value as an integer.
   */
  public int getGreen() {
    return this.green;
  }

  /**
   * Returns the blue value of the RGB combination for this pixel's color.
   * @return the blue value as an integer.
   */
  public int getBlue() {
    return this.blue;
  }

  /**
   * Sets the red value of the RGB combination for the pixel's color.
   * @param red is an integer
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   * Sets the green value of the RGB combination for the pixel's color.
   * @param green is an integer
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * Sets the blue value of the RGB combination for the pixel's color.
   * @param blue is an integer
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  /**
   * Sets the red, blue, and green values of the RGB comination for the pixel's color.
   * @param r represents the red value of a pixel color as an integer.
   * @param g represents the green value of a pixel color as an integer
   * @param b represents the blue value of a pixel color as an integer
   */
  public void setRGB(int r, int g, int b) {
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  /**
   * Creates a copy of a pixel with the red, green, and blue values.
   * @return a copied model.Pixel object
   */
  @Override
  public Pixel clone() {
    return new Pixel(this.getRed(), this.getGreen(), this.getBlue());
  }
}
