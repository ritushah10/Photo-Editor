package model.histogram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JPanel;

/**
 * This class allows the program to draw the histogram to the screen. Extended from JPanel,
 * this class draws the components and intensity for the image on the screen and connects the
 * points.
 */
public class DrawHistogram extends JPanel {
  private final Histogram hm;

  /**
   * Constructs a new histogram drawing class in order to draw the histogram to the screen.
   * @param hm the Histogram model to be used with this class in order to obtain information
   *           about the histogram.
   */
  public DrawHistogram(Histogram hm) {
    super();
    if (hm == null) {
      throw new IllegalArgumentException("Histogram must not be null");
    }

    this.hm = hm;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g1 = (Graphics2D) g;

    g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Red
    int xRed = 0;
    g.setColor(Color.RED);
    List<Integer> red = this.hm.getRedBar();
    int lastX = 0;
    int lastY = 0;
    for (Integer yRed : red) {
      g.fillRect(xRed, 500 - yRed, 2, 2);
      g.drawLine(lastX, lastY, xRed, 500 - yRed);
      lastX = xRed;
      lastY = 500 - yRed;
      xRed++;
    }

    // Green
    int xGreen = 0;
    lastX = 0;
    lastY = 0;
    g.setColor(Color.GREEN);
    List<Integer> green = this.hm.getGreenBar();
    for (Integer yGreen : green) {
      g.fillRect(xGreen, 500 - yGreen, 2, 2);
      g.drawLine(lastX, lastY, xGreen, 500 - yGreen);
      lastX = xGreen;
      lastY = 500 - yGreen;
      xGreen++;
    }

    // Blue
    int xBlue = 0;
    lastX = 0;
    lastY = 0;
    g.setColor(Color.blue);
    List<Integer> blue = this.hm.getBlueBar();
    for (Integer yBlue : blue) {
      g.fillRect(xBlue, 500 - yBlue, 2, 2);
      g.drawLine(lastX, lastY, xBlue, 500 - yBlue);
      lastX = xBlue;
      lastY = 500 - yBlue;
      xBlue++;
    }

    // Intensity
    int xIntensity = 0;
    lastX = 0;
    lastY = 0;
    g.setColor(Color.black);
    List<Integer> intensity = this.hm.getIntensityBar();
    System.out.println(intensity);
    for (Integer yIntensity : intensity) {
      g.fillRect(xIntensity, 500 - yIntensity, 2, 2);
      g.drawLine(lastX, lastY, xIntensity, 500 - yIntensity);
      lastX = xIntensity;
      lastY = 500 - yIntensity;
      xIntensity++;
    }
  }
}


