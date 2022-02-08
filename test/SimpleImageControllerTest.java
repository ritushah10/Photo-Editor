import org.junit.Test;

import java.io.StringReader;

import controller.ImageEditorController;
import controller.SimpleEditorController;
import mocks.ConfirmInputs;
import model.ImageEditorModel;
import model.SimpleEditorModel;
import view.ImageEditorView;
import view.SimpleEditorView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the controller.SimpleEditorController class.
 */
public class SimpleImageControllerTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull1() {
    new SimpleEditorController(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull2() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView();
    new SimpleEditorController(model, view, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull3() {
    ImageEditorModel model = new SimpleEditorModel();
    Readable r = new StringReader("");
    new SimpleEditorController(model, null, r);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull4() {
    ImageEditorView view = new SimpleEditorView();
    Readable r = new StringReader("");
    new SimpleEditorController(null, view, r);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull5() {
    Readable r = new StringReader("");
    new SimpleEditorController(null, null, r);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull6() {
    ImageEditorModel model = new SimpleEditorModel();
    new SimpleEditorController(model, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull7() {
    ImageEditorModel model = new SimpleEditorModel();
    Readable r = new StringReader("");
    new SimpleEditorController(model, null, r);
  }

  @Test
  public void testInputs() {
    StringBuilder log = new StringBuilder();
    StringBuilder dontCareOutput = new StringBuilder();

    ConfirmInputs ci = new ConfirmInputs(log);
    ImageEditorView view = new SimpleEditorView(dontCareOutput);

    Readable in = new StringReader("load my-path1 name-1\n"
                                    + "horizontal-flip name-2 name-3\n"
                                    + "vertical-flip name-4 name-5\n"
                                    + "brighten 10 name-6 name-7\n"
                                    + "red-component name-9 name-10\n"
                                    + "green-component name-11 name-12\n"
                                    + "blue-component name-13 name-14\n"
                                    + "value-component name-15 name-16\n"
                                    + "intensity-component name-17 name-18\n"
                                    + "luma-component name-19 name-20\n"
                                    + "blur name-21 name-22\n"
                                    + "sharpen name-23 name-24\n"
                                    + "greyscale name-25 name-26\n"
                                    + "sepia name-27 name-28\nq");
    ImageEditorController controller = new SimpleEditorController(ci, view, in);
    controller.start();

    String expectedLog = "FLIP HORIZONTAL name-2 name-3\n"
                       + "FLIP VERTICAL name-4 name-5\n"
                       + "BRIGHTEN 10 name-6 name-7\n"
                       + "COMPONENT RED name-9 name-10\n"
                       + "COMPONENT GREEN name-11 name-12\n"
                       + "COMPONENT BLUE name-13 name-14\n"
                       + "COMPONENT VALUE name-15 name-16\n"
                       + "COMPONENT INTENSITY name-17 name-18\n"
                       + "COMPONENT LUMA name-19 name-20\n"
                       + "BLUR name-21 name-22\n"
                       + "SHARPEN name-23 name-24\n"
                       + "GREYSCALE name-25 name-26\n"
                       + "SEPIA name-27 name-28\n";

    assertEquals(expectedLog, log.toString());
  }

  @Test
  public void testWelcomeOutputs() {
    Appendable out = new StringBuilder();
    Readable r = new StringReader("q");
    ImageEditorView view = new SimpleEditorView(out);
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorController controller = new SimpleEditorController(model, view, r);
    controller.start();

    String[] outs = out.toString().split("\n");

    assertEquals("Welcome to the SimpleEditor program. Please use one of the following "
        + "commands: ", outs[0]);
    assertEquals("- load <path> <save-name> - Load an image", outs[1]);
    assertEquals("- save <path> <save-name> - Saves the image to the given path", outs[2]);
    assertEquals("- brighten <value> <image-name> <save-name> - Brightens an image to the "
        + "value (pos/neg) and saves to the new name", outs[3]);
    assertEquals("- vertical-flip <image-name> <save-name> - Flips the image vertically "
        + "and saves to the new name", outs[4]);
    assertEquals("- horizontal-flip <image-name> <save-name> - Flips the image horizontally"
        + " and saves to the new name", outs[5]);
    assertEquals("- value-component <image-name> <save-name> - Converts the image to show "
        + "the value component and saves to the new name", outs[6]);
    assertEquals("- intensity-component <image-name> <save-name> - Converts the image to "
        + "show the intensity component and saves to the new name", outs[7]);
    assertEquals("- luma-component <image-name> <save-name> - Converts the image to show "
        + "the luma component and saves to the new name", outs[8]);
    assertEquals("- red-component <image-name> <save-name> - Converts the image to show "
        + "the red component and saves to the new name", outs[9]);
    assertEquals("- green-component <image-name> <save-name> - Converts the image to show "
        + "the green component and saves to the new name", outs[10]);
    assertEquals("- blue-component <image-name> <save-name> - Converts the image to show "
        + "the blue component and saves to the new name", outs[11]);
    assertEquals("- blur <image-name> <save-name> - Blurs the image", outs[12]);
    assertEquals("- sharpen <image-name> <save-name> - Sharpens the image", outs[13]);
    assertEquals("- sepia <image-name> <save-name> - Color transforms the image to " +
        "sepia", outs[14]);
    assertEquals("- greyscale <image-name> <save-image> - Color transforms the image to " +
        "greyscale", outs[15]);
    assertEquals("- menu - Shows this screen again.", outs[16]);
    assertEquals("Type \"q\" if you would like to quit the program.", outs[17]);
    assertEquals("Enter a command: Goodbye.", outs[18]);
  }

  @Test
  public void testOutputLoad() {
    Appendable out = new StringBuilder();
    Readable r = new StringReader("load res/test/Test.ppm small\nload res/test/NoHere.ppm " +
        "NotHere\nq");
    ImageEditorView view = new SimpleEditorView(out);
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorController controller = new SimpleEditorController(model, view, r);
    controller.start();

    String[] outs = out.toString().split("\n");

    assertEquals("Enter a command: Loading...Successfully loaded image with name "
        + "\"small\".", outs[18]);
    assertEquals("Enter a command: Loading...The was an error loading the provided file "
        + "path. Not found or empty.", outs[19]);
  }

  @Test
  public void testOutputsOther() {
    Appendable out = new StringBuilder();
    Readable r = new StringReader("brighten 10 name1 name2\nvertical-flip name1 name2"
        + "\nhorizontal-flip name1 name2\nred-component name1 name2\nblur name1 name2\n" +
        "sharpen name1 name2\ngreyscale name1 name2\nsepia name1 name2\nq");
    ImageEditorView view = new SimpleEditorView(out);
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorController controller = new SimpleEditorController(model, view, r);
    controller.start();

    String[] outs = out.toString().split("\n");

    assertEquals("Enter a command: Brightening...ERROR: The provided image name was not "
        + "found.", outs[18]);
    assertEquals("Enter a command: Rendering flip VERTICAL...ERROR: The provided image name "
        + "was not found.", outs[19]);
    assertEquals("Enter a command: Rendering flip HORIZONTAL...ERROR: The provided image name "
        + "was not found.", outs[20]);
    assertEquals("Enter a command: Rendering component RED...ERROR: The provided image "
        + "name was not found.", outs[21]);
    assertEquals("Enter a command: Blurring image...ERROR: The provided image "
        + "name was not found.", outs[22]);
    assertEquals("Enter a command: Sharpening image...ERROR: The provided image "
        + "name was not found.", outs[23]);
    assertEquals("Enter a command: Transforming image to greyscale...ERROR: The provided image "
        + "name was not found.", outs[24]);
    assertEquals("Enter a command: Transforming image to sepia...ERROR: The provided image "
        + "name was not found.", outs[25]);
  }
}