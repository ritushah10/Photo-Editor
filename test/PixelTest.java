import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the model.Pixel class.
 */
public class PixelTest {
  @Test
  public void getRed() {
    Pixel pixel1 = new Pixel(0, 0, 0);
    pixel1.setRed(1);
    assertEquals(1, pixel1.getRed());
  }

  @Test
  public void getGreen() {
    Pixel pixel2 = new Pixel(0, 0, 0);
    pixel2.setRed(2);
    assertEquals(2, pixel2.getRed());
  }

  @Test
  public void getBlue() {
    Pixel pixel3 = new Pixel(0, 0, 0);
    pixel3.setRed(3);
    assertEquals(3, pixel3.getRed());
  }

  @Test
  public void setRed() {
    Pixel pixel1 = new Pixel(0, 2, 3);
    pixel1.setRed(1);
    assertEquals(1, pixel1.getRed());
  }

  @Test
  public void setGreen() {
    Pixel pixel2 = new Pixel(1, 0, 3);
    pixel2.setGreen(3);
    assertEquals(3, pixel2.getGreen());
  }

  @Test
  public void setBlue() {
    Pixel pixel3 = new Pixel(1, 2, 0);
    pixel3.setBlue(3);
    assertEquals(3, pixel3.getBlue());
  }

  @Test
  public void setRGB() {
    Pixel pixel = new Pixel(3, 4, 5);
    pixel.setRGB(3,4,5);
    assertEquals(3, pixel.getRed());
    assertEquals(4, pixel.getGreen());
    assertEquals(5, pixel.getBlue());
  }

  @Test
  public void testClone() {
    Pixel pixel = new Pixel(3, 4, 5);
    Pixel pixelClone = pixel.clone();
    assertEquals(pixel.getRed(), pixelClone.getRed());
    assertEquals(pixel.getGreen(), pixelClone.getGreen());
    assertEquals(pixel.getBlue(), pixelClone.getBlue());
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorException1() {
    new Pixel(-1, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorException2() {
    new Pixel(1, 1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorException3() {
    new Pixel(1, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorException4() {
    new Pixel(-1, 1, 1);
  }
}
