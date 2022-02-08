import org.junit.Test;

import java.io.StringReader;

import controller.ImageEditorController;
import controller.SimpleEditorController;
import model.ImageEditorModel;
import model.Pixel;
import model.SimpleEditorModel;
import view.ImageEditorView;
import view.SimpleEditorView;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the model.SimpleEditorModel.
 */
public class SimpleImageEditorTest {

  @Test
  public void testLoadSaveImage() {
    /*
     * Loads the test file Test.ppm into the system and names it "testing". Then saves it as
     * NewTest.ppm. Finally, uses the ImageUtil readPPM() method to confirm the image was properly
     * loaded and saved into the new file with the same data.
     */
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing" +
        "\nsave res/test/NewTest.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTest.ppm");

    assertEquals(array1[0][0].getRed(), array2[0][0].getRed());
    assertEquals(array1[0][0].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getRed(), array2[0][1].getRed());
    assertEquals(array1[0][1].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getRed(), array2[1][0].getRed());
    assertEquals(array1[1][0].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getRed(), array2[1][1].getRed());
    assertEquals(array1[1][1].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[1][1].getBlue());
  }

  @Test
  public void testBrighten() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nbrighten 10 testing testing" +
        "\nsave res/test/NewTestBright.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestBright.ppm");

    assertEquals(array1[0][0].getRed() + 10, array2[0][0].getRed());
    assertEquals(array1[0][0].getGreen() + 10, array2[0][0].getGreen());
    assertEquals(array1[0][0].getBlue() + 10, array2[0][0].getBlue());

    assertEquals(array1[0][1].getRed() + 10, array2[0][1].getRed());
    assertEquals(array1[0][1].getGreen() + 10, array2[0][1].getGreen());
    assertEquals(array1[0][1].getBlue() + 10, array2[0][1].getBlue());

    assertEquals(array1[1][0].getRed() + 10, array2[1][0].getRed());
    assertEquals(array1[1][0].getGreen() + 10, array2[1][0].getGreen());
    assertEquals(array1[1][0].getBlue() + 10, array2[1][0].getBlue());

    assertEquals(array1[1][1].getRed() + 10, array2[1][1].getRed());
    assertEquals(array1[1][1].getGreen() + 10, array2[1][1].getGreen());
    assertEquals(array1[1][1].getBlue() + 10, array2[1][1].getBlue());
  }

  @Test
  public void testFlipHorizontal() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nhorizontal-flip " +
        "testing testing\nsave res/test/NewTestFlipH.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestFlipH.ppm");

    assertEquals(array1[0][0].getRed(), array2[0][1].getRed());
    assertEquals(array1[0][0].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[0][1].getBlue());

    assertEquals(array1[0][1].getRed(), array2[0][0].getRed());
    assertEquals(array1[0][1].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[1][0].getRed(), array2[1][1].getRed());
    assertEquals(array1[1][0].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[1][1].getBlue());

    assertEquals(array1[1][1].getRed(), array2[1][0].getRed());
    assertEquals(array1[1][1].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[1][0].getBlue());
  }

  @Test
  public void testFlipVertical() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nvertical-flip " +
        "testing testing\nsave res/test/NewTestFlipV.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestFlipV.ppm");

    assertEquals(array1[0][0].getRed(), array2[1][0].getRed());
    assertEquals(array1[0][0].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[1][0].getBlue());

    assertEquals(array1[0][1].getRed(), array2[1][1].getRed());
    assertEquals(array1[0][1].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[1][1].getBlue());

    assertEquals(array1[1][0].getRed(), array2[0][0].getRed());
    assertEquals(array1[1][0].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[1][1].getRed(), array2[0][1].getRed());
    assertEquals(array1[1][1].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[0][1].getBlue());
  }

  @Test
  public void testRedComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nred-component " +
        "testing testing\nsave res/test/NewTestCompRed.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompRed.ppm");

    assertEquals(array1[0][0].getRed(), array2[0][0].getRed());
    assertEquals(array1[0][0].getRed(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getRed(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getRed(), array2[0][1].getRed());
    assertEquals(array1[0][1].getRed(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getRed(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getRed(), array2[1][0].getRed());
    assertEquals(array1[1][0].getRed(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getRed(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getRed(), array2[1][1].getRed());
    assertEquals(array1[1][1].getRed(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getRed(), array2[1][1].getBlue());
  }

  @Test
  public void testGreenComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\ngreen-component " +
        "testing testing\nsave res/test/NewTestCompGreen.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompGreen.ppm");

    assertEquals(array1[0][0].getGreen(), array2[0][0].getRed());
    assertEquals(array1[0][0].getGreen(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getGreen(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getGreen(), array2[0][1].getRed());
    assertEquals(array1[0][1].getGreen(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getGreen(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getGreen(), array2[1][0].getRed());
    assertEquals(array1[1][0].getGreen(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getGreen(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getGreen(), array2[1][1].getRed());
    assertEquals(array1[1][1].getGreen(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getGreen(), array2[1][1].getBlue());
  }

  @Test
  public void testBlueComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nblue-component " +
        "testing testing\nsave res/test/NewTestCompBlue.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompBlue.ppm");

    assertEquals(array1[0][0].getBlue(), array2[0][0].getRed());
    assertEquals(array1[0][0].getBlue(), array2[0][0].getGreen());
    assertEquals(array1[0][0].getBlue(), array2[0][0].getBlue());

    assertEquals(array1[0][1].getBlue(), array2[0][1].getRed());
    assertEquals(array1[0][1].getBlue(), array2[0][1].getGreen());
    assertEquals(array1[0][1].getBlue(), array2[0][1].getBlue());

    assertEquals(array1[1][0].getBlue(), array2[1][0].getRed());
    assertEquals(array1[1][0].getBlue(), array2[1][0].getGreen());
    assertEquals(array1[1][0].getBlue(), array2[1][0].getBlue());

    assertEquals(array1[1][1].getBlue(), array2[1][1].getRed());
    assertEquals(array1[1][1].getBlue(), array2[1][1].getGreen());
    assertEquals(array1[1][1].getBlue(), array2[1][1].getBlue());
  }

  @Test
  public void testValueComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nvalue-component " +
        "testing testing\nsave res/test/NewTestCompValue.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompValue.ppm");

    int max1 = Math.max(Math.max(array1[0][0].getRed(), array1[0][0].getGreen()),
        array1[0][0].getBlue());
    assertEquals(max1, array2[0][0].getRed());
    assertEquals(max1, array2[0][0].getGreen());
    assertEquals(max1, array2[0][0].getBlue());

    int max2 = Math.max(Math.max(array1[0][1].getRed(), array1[0][1].getGreen()),
        array1[0][1].getBlue());
    assertEquals(max2, array2[0][1].getRed());
    assertEquals(max2, array2[0][1].getGreen());
    assertEquals(max2, array2[0][1].getBlue());

    int max3 = Math.max(Math.max(array1[1][0].getRed(), array1[1][0].getGreen()),
        array1[1][0].getBlue());
    assertEquals(max3, array2[1][0].getRed());
    assertEquals(max3, array2[1][0].getGreen());
    assertEquals(max3, array2[1][0].getBlue());

    int max4 = Math.max(Math.max(array1[1][1].getRed(), array1[1][1].getGreen()),
        array1[1][1].getBlue());
    assertEquals(max4, array2[1][1].getRed());
    assertEquals(max4, array2[1][1].getGreen());
    assertEquals(max4, array2[1][1].getBlue());
  }

  @Test
  public void testIntensityComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nintensity-component " +
        "testing testing\nsave res/test/NewTestCompIntensity.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompIntensity.ppm");

    int avg1 = (int) Math.round((array1[0][0].getRed() + array1[0][0].getGreen()
        + array1[0][0].getBlue()) / 3.0);
    assertEquals(avg1, array2[0][0].getRed());
    assertEquals(avg1, array2[0][0].getGreen());
    assertEquals(avg1, array2[0][0].getBlue());

    int avg2 = (int) Math.round((array1[0][1].getRed() + array1[0][1].getGreen()
        + array1[0][1].getBlue()) / 3.0);
    assertEquals(avg2, array2[0][1].getRed());
    assertEquals(avg2, array2[0][1].getGreen());
    assertEquals(avg2, array2[0][1].getBlue());

    int avg3 = (int) Math.round((array1[1][0].getRed() + array1[1][0].getGreen()
        + array1[1][0].getBlue()) / 3.0);
    assertEquals(avg3, array2[1][0].getRed());
    assertEquals(avg3, array2[1][0].getGreen());
    assertEquals(avg3, array2[1][0].getBlue());

    int avg4 = (int) Math.round((array1[1][1].getRed() + array1[1][1].getGreen()
        + array1[1][1].getBlue()) / 3.0);
    assertEquals(avg4, array2[1][1].getRed());
    assertEquals(avg4, array2[1][1].getGreen());
    assertEquals(avg4, array2[1][1].getBlue());
  }

  @Test
  public void testLumaComponent() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nluma-component " +
        "testing testing\nsave res/test/NewTestCompLuma.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestCompLuma.ppm");

    int val1 = (int) Math.round((array1[0][0].getRed() * 0.2126)
        + (array1[0][0].getGreen() * 0.7152) + (array1[0][0].getBlue() * 0.0722));
    assertEquals(val1, array2[0][0].getRed());
    assertEquals(val1, array2[0][0].getGreen());
    assertEquals(val1, array2[0][0].getBlue());

    int val2 = (int) Math.round((array1[0][1].getRed() * 0.2126)
        + (array1[0][1].getGreen() * 0.7152) + (array1[0][1].getBlue() * 0.0722));
    assertEquals(val2, array2[0][1].getRed());
    assertEquals(val2, array2[0][1].getGreen());
    assertEquals(val2, array2[0][1].getBlue());

    int val3 = (int) Math.round((array1[1][0].getRed() * 0.2126)
        + (array1[1][0].getGreen() * 0.7152) + (array1[1][0].getBlue() * 0.0722));
    assertEquals(val3, array2[1][0].getRed());
    assertEquals(val3, array2[1][0].getGreen());
    assertEquals(val3, array2[1][0].getBlue());

    int val4 = (int) Math.round((array1[1][1].getRed() * 0.2126)
        + (array1[1][1].getGreen() * 0.7152) + (array1[1][1].getBlue() * 0.0722));
    assertEquals(val4, array2[1][1].getRed());
    assertEquals(val4, array2[1][1].getGreen());
    assertEquals(val4, array2[1][1].getBlue());
  }

  @Test
  public void testBlur() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nblur " +
        "testing testing\nsave res/test/NewTestBlur.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestBlur.ppm");

    int r1 = (int) Math.round((array1[0][0].getRed() * 0.25)
            + (array1[0][1].getRed() * 0.125) + (array1[1][1].getRed() * 0.0625)
            + (array1[1][0].getRed() * 0.125));
    int g1 = (int) Math.round((array1[0][0].getGreen() * 0.25)
            + (array1[0][1].getGreen() * 0.125) + (array1[1][1].getGreen() * 0.0625)
            + (array1[1][0].getGreen() * 0.125));
    int b1 = (int) Math.round((array1[0][0].getBlue() * 0.25)
            + (array1[0][1].getBlue() * 0.125) + (array1[1][1].getBlue() * 0.0625)
            + (array1[1][0].getBlue() * 0.125));
    assertEquals(r1, array2[0][0].getRed());
    assertEquals(g1, array2[0][0].getGreen());
    assertEquals(b1, array2[0][0].getBlue());

    int r2 = (int)(Math.round((r1 * 0.125)
            + (array1[0][1].getRed() * 0.25)
            + (array1[1][0].getRed() * 0.0625)
            + (array1[1][1].getRed() * 0.125)));
    int g2 = (int) Math.round((g1 * 0.125)
            + (array1[0][1].getGreen() * 0.25) + (array1[1][1].getGreen() * 0.125)
            + (array1[1][0].getGreen() * 0.0625));
    int b2 = (int) Math.round((b1 * 0.125)
            + (array1[0][1].getBlue() * 0.25) + (array1[1][1].getBlue() * 0.125)
            + (array1[1][0].getBlue() * 0.0625));
    assertEquals(r2, array2[0][1].getRed());
    assertEquals(g2, array2[0][1].getGreen());
    assertEquals(b2, array2[0][1].getBlue());

    int r3 = (int) Math.round((r1 * 0.125)
            + (r2 * 0.0625) + (array1[1][1].getRed() * 0.125)
            + (array1[1][0].getRed() * 0.25));
    int g3 = (int) Math.round((g1 * 0.125)
        + (g2 * 0.0625) + (array1[1][1].getGreen() * 0.125)
        + (array1[1][0].getGreen() * 0.25));
    int b3 = (int) Math.round((b1 * 0.125)
        + (b2 * 0.0625) + (array1[1][1].getBlue() * 0.125)
        + (array1[1][0].getBlue() * 0.25));
    assertEquals(r3, array2[1][0].getRed());
    assertEquals(g3, array2[1][0].getGreen());
    assertEquals(b3, array2[1][0].getBlue());

    int r4 = (int) Math.round((r1 * 0.0625)
        + (r2 * 0.125) + (array1[1][1].getRed() * 0.25)
        + (r3 * 0.125));
    int g4 = (int) Math.round((g1 * 0.0625)
        + (g2 * 0.125) + (array1[1][1].getGreen() * 0.25)
        + (g3 * 0.125));
    int b4 = (int) Math.round((b1 * 0.0625)
        + (b2 * 0.125) + (array1[1][1].getBlue() * 0.25)
        + (b3 * 0.125));
    assertEquals(r4, array2[1][1].getRed());
    assertEquals(g4, array2[1][1].getGreen());
    assertEquals(b4, array2[1][1].getBlue());
  }

  @Test
  public void testSharpen() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\nsharpen " +
        "testing testing\nsave res/test/NewTestSharpen.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestSharpen.ppm");

    int r1 = (int) Math.round((array1[0][0].getRed())
        + (array1[0][1].getRed() * 0.25) + (array1[1][1].getRed() * 0.25)
        + (array1[1][0].getRed() * 0.25));
    int g1 = (int) Math.round((array1[0][0].getGreen())
        + (array1[0][1].getGreen() * 0.25) + (array1[1][1].getGreen() * 0.25)
        + (array1[1][0].getGreen() * 0.25));
    int b1 = (int) Math.round((array1[0][0].getBlue())
        + (array1[0][1].getBlue() * 0.25) + (array1[1][1].getBlue() * 0.25)
        + (array1[1][0].getBlue() * 0.25));
    assertEquals(this.colorCap(r1), array2[0][0].getRed());
    assertEquals(this.colorCap(g1), array2[0][0].getGreen());
    assertEquals(this.colorCap(b1), array2[0][0].getBlue());

    int r2 = (int) Math.round((r1 * 0.25)
        + (array1[0][1].getRed()) + (array1[1][1].getRed() * 0.25)
        + (array1[1][0].getRed() * 0.25));
    int g2 = (int) Math.round((g1 * 0.25)
        + (array1[0][1].getGreen()) + (array1[1][1].getGreen() * 0.25)
        + (array1[1][0].getGreen() * 0.25));
    int b2 = (int) Math.round((b1 * 0.25)
        + (array1[0][1].getBlue()) + (array1[1][1].getBlue() * 0.25)
        + (array1[1][0].getBlue() * 0.25));
    assertEquals(this.colorCap(r2), array2[0][1].getRed());
    assertEquals(this.colorCap(g2), array2[0][1].getGreen());
    assertEquals(this.colorCap(b2), array2[0][1].getBlue());

    int r3 = (int) Math.round((r1 * 0.25)
        + (r2 * 0.25) + (array1[1][1].getRed() * 0.25)
        + (array1[1][0].getRed()));
    int g3 = (int) Math.round((g1 * 0.25)
        + (g2 * 0.25) + (array1[1][1].getGreen() * 0.25)
        + (array1[1][0].getGreen()));
    int b3 = (int) Math.round((b1 * 0.25)
        + (b2 * 0.25) + (array1[1][1].getBlue() * 0.25)
        + (array1[1][0].getBlue()));
    assertEquals(this.colorCap(r3), array2[1][0].getRed());
    assertEquals(this.colorCap(g3), array2[1][0].getGreen());
    assertEquals(this.colorCap(b3), array2[1][0].getBlue());

    int r4 = (int) Math.round((r1 * 0.25)
        + (r2 * 0.25) + (array1[1][1].getRed())
        + (r3 * 0.25));
    int g4 = (int) Math.round((g1 * 0.25)
        + (g2 * 0.25) + (array1[1][1].getGreen())
        + (g3 * 0.25));
    int b4 = (int) Math.round((b1 * 0.25)
        + (b2 * 0.25) + (array1[1][1].getBlue())
        + (b3 * 0.25));
    assertEquals(this.colorCap(r4), array2[1][1].getRed());
    assertEquals(this.colorCap(g4), array2[1][1].getGreen());
    assertEquals(this.colorCap(b4), array2[1][1].getBlue());
  }

  @Test
  public void testGreyscale() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable r = new StringReader("load res/test/Test.ppm testing\ngreyscale " +
        "testing testing\nsave res/test/NewTestGreyscale.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, r);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestGreyscale.ppm");

    int val1 = (int) Math.round((array1[0][0].getRed() * 0.2126)
            + (array1[0][0].getGreen() * 0.7152) + (array1[0][0].getBlue() * 0.0722));
    assertEquals(val1, array2[0][0].getRed());
    assertEquals(val1, array2[0][0].getGreen());
    assertEquals(val1, array2[0][0].getBlue());

    int val2 = (int) Math.round((array1[0][1].getRed() * 0.2126)
            + (array1[0][1].getGreen() * 0.7152) + (array1[0][1].getBlue() * 0.0722));
    assertEquals(val2, array2[0][1].getRed());
    assertEquals(val2, array2[0][1].getGreen());
    assertEquals(val2, array2[0][1].getBlue());

    int val3 = (int) Math.round((array1[1][0].getRed() * 0.2126)
            + (array1[1][0].getGreen() * 0.7152) + (array1[1][0].getBlue() * 0.0722));
    assertEquals(val3, array2[1][0].getRed());
    assertEquals(val3, array2[1][0].getGreen());
    assertEquals(val3, array2[1][0].getBlue());

    int val4 = (int) Math.round((array1[1][1].getRed() * 0.2126)
        + (array1[1][1].getGreen() * 0.7152) + (array1[1][1].getBlue() * 0.0722));
    assertEquals(val4, array2[1][1].getRed());
    assertEquals(val4, array2[1][1].getGreen());
    assertEquals(val4, array2[1][1].getBlue());
  }

  @Test
  public void testSepia() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView(new StringBuilder());
    Readable read = new StringReader("load res/test/Test.ppm testing\nsepia " +
        "testing testing\nsave res/test/NewTestSepia.ppm testing\nq");
    ImageEditorController cont = new SimpleEditorController(model, view, read);
    cont.start();

    Pixel[][] array1 = ImageUtil.readPPM("res/test/Test.ppm");
    Pixel[][] array2 = ImageUtil.readPPM("res/test/NewTestSepia.ppm");

    int r1 = (int) Math.round((array1[0][0].getRed() * 0.393)
        + (array1[0][0].getGreen() * 0.769) + (array1[0][0].getBlue() * 0.189));
    int g1 = (int) Math.round((array1[0][0].getRed() * 0.349)
        + (array1[0][0].getGreen() * 0.686) + (array1[0][0].getBlue() * 0.168));
    int b1 = (int) Math.round((array1[0][0].getRed() * 0.272)
        + (array1[0][0].getGreen() * 0.534) + (array1[0][0].getBlue() * 0.131));
    assertEquals(r1, array2[0][0].getRed());
    assertEquals(g1, array2[0][0].getGreen());
    assertEquals(b1, array2[0][0].getBlue());

    int r2 = (int) Math.round((array1[0][1].getRed() * 0.393)
        + (array1[0][1].getGreen() * 0.769) + (array1[0][1].getBlue() * 0.189));
    int g2 = (int) Math.round((array1[0][1].getRed() * 0.349)
        + (array1[0][1].getGreen() * 0.686) + (array1[0][1].getBlue() * 0.168));
    int b2 = (int) Math.round((array1[0][1].getRed() * 0.272)
        + (array1[0][1].getGreen() * 0.534) + (array1[0][1].getBlue() * 0.131));
    assertEquals(r2, array2[0][1].getRed());
    assertEquals(g2, array2[0][1].getGreen());
    assertEquals(b2, array2[0][1].getBlue());

    int r3 = (int) Math.round((array1[1][0].getRed() * 0.393)
        + (array1[1][0].getGreen() * 0.769) + (array1[1][0].getBlue() * 0.189));
    int g3 = (int) Math.round((array1[1][0].getRed() * 0.349)
        + (array1[1][0].getGreen() * 0.686) + (array1[1][0].getBlue() * 0.168));
    int b3 = (int) Math.round((array1[1][0].getRed() * 0.272)
        + (array1[1][0].getGreen() * 0.534) + (array1[1][0].getBlue() * 0.131));
    assertEquals(r3, array2[1][0].getRed());
    assertEquals(g3, array2[1][0].getGreen());
    assertEquals(b3, array2[1][0].getBlue());

    int g4 = (int) Math.round((array1[1][1].getRed() * 0.349)
        + (array1[1][1].getGreen() * 0.686) + (array1[1][1].getBlue() * 0.168));
    int b4 = (int) Math.round((array1[1][1].getRed() * 0.272)
        + (array1[1][1].getGreen() * 0.534) + (array1[1][1].getBlue() * 0.131));
    assertEquals(255, array2[1][1].getRed()); // Capped at 255
    assertEquals(g4, array2[1][1].getGreen());
    assertEquals(b4, array2[1][1].getBlue());
  }

  private int colorCap(int val) {
    if (val < 0) {
      return 0;
    }
    return Math.min(val, 255);
  }
}


