package model.histogram;

import java.util.ArrayList;

import model.Pixel;
import model.SimpleEditorModel;

/**
 * Represents a model.histogram.HistogramModel.
 */
public class HistogramModel extends SimpleEditorModel implements Histogram {
  private final ArrayList<Integer> redFrequency;
  private final ArrayList<Integer> greenFrequency;
  private final ArrayList<Integer> blueFrequency;
  private final ArrayList<Integer> intensityFrequency;
  private Pixel[][] image;

  /**
   * Constructs a histogram model and instantiates the fields above. Allows the program to store
   * component values for red, green, blue, and intensity.
   */
  public HistogramModel() {
    this.redFrequency = new ArrayList<>();
    this.greenFrequency = new ArrayList<>();
    this.blueFrequency = new ArrayList<>();
    this.intensityFrequency = new ArrayList<>();
    this.image = null;

    for (int i = 0; i < 256; i++) {
      redFrequency.add(0);
      greenFrequency.add(0);
      blueFrequency.add(0);
      intensityFrequency.add(0);
    }
  }

  @Override
  public void setImage(Pixel[][] image) {
    this.image = image;
    this.fillFrequencies();
  }

  @Override
  public void fillFrequencies() {
    if (image == null) {
      return;
    }
    for (int i = 0; i < this.image.length; i++) {
      for (int j = 0; j < this.image[i].length; j++) {
        Pixel p = this.image[i][j];
        double avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3.0;
        int newAvg = (int) Math.round(avg);
        this.redFrequency.set(p.getRed(), this.redFrequency.get(p.getRed()) + 1);
        this.greenFrequency.set(p.getGreen(), this.greenFrequency.get(p.getGreen()) + 1);
        this.blueFrequency.set(p.getBlue(), this.blueFrequency.get(p.getBlue()) + 1);
        this.intensityFrequency.set(newAvg, this.intensityFrequency.get(newAvg) + 1);
      }
    }
  }

  public ArrayList<Integer> getRedBar() {
    return this.redFrequency;
  }

  public ArrayList<Integer> getGreenBar() {
    return this.greenFrequency;
  }

  public ArrayList<Integer> getBlueBar() {
    return this.blueFrequency;
  }

  public ArrayList<Integer> getIntensityBar() {
    return this.intensityFrequency;
  }
}

