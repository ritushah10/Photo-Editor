package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.Component;
import model.Flip;
import model.ImageEditorModel;
import model.Pixel;
import view.ImageEditorView;

/**
 * Implementation of the controller for a SimpleEditor. Allows the program to be run, the intake
 * of commands, and facilitation of the output for the user. Runs the program.
 */
public class SimpleEditorController implements ImageEditorController {
  protected final ImageEditorModel model;
  private final ImageEditorView view;
  private final Scanner scan;

  /**
   * Constructs a SimpleEditor controller using the provided parameters: the model, the view for
   * the model, and the input as a Readable. Then instantiates these instance fields along with the
   * creation of a new scanner.
   * @param model the model for the Simple Editor.
   * @param view the view providing output for the Simple Editor.
   * @param in the means of input, as a Readable, for the program.
   * @throws IllegalArgumentException if any of the provided parameters are null.
   */
  public SimpleEditorController(ImageEditorModel model, ImageEditorView view, Readable in)
      throws IllegalArgumentException {
    if (model == null || view == null || in == null) {
      throw new IllegalArgumentException("Arguments must not be null.");
    }

    this.model = model;
    this.view = view;
    this.scan = new Scanner(in); // Instantiates a Scanner with the Readable input provided
  }

  public SimpleEditorController(ImageEditorModel model, ImageEditorView view) {
    this(model, view, new StringReader(""));
  }

  @Override
  public void start() throws IllegalStateException {
    this.welcomeMessage();

    while (true) {
      this.view.renderMessage("Enter a command: ");

      String input = "";
      try {
        input = this.scan.nextLine();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Not enough inputs.");
      }

      if (input.equals("q")) {
        this.view.renderMessage("Goodbye.");
        break;
      }

      String[] args = input.split(" ");

      switch (args[0]) {
        case "load":
          if (this.argsError(args, 2)) {
            this.loadHelp(args[1], args[2]);
          }
          break;
        case "save":
          if (this.argsError(args, 2)) {
            this.saveHelp(args[1], args[2]);
          }
          break;
        case "brighten":
          if (this.argsError(args, 3)) {
            this.brighten(args[1], args[2], args[3]);
          }
          break;
        case "vertical-flip":
          if (this.argsError(args, 2)) {
            this.flip(Flip.VERTICAL, args[1], args[2]);
          }
          break;
        case "horizontal-flip":
          if (this.argsError(args, 2)) {
            this.flip(Flip.HORIZONTAL, args[1], args[2]);
          }
          break;
        case "value-component":
          if (this.argsError(args, 2)) {
            this.component(Component.VALUE, args[1], args[2]);
          }
          break;
        case "intensity-component":
          if (this.argsError(args, 2)) {
            this.component(Component.INTENSITY, args[1], args[2]);
          }
          break;
        case "luma-component":
          if (this.argsError(args, 2)) {
            this.component(Component.LUMA, args[1], args[2]);
          }
          break;
        case "red-component":
          if (this.argsError(args, 2)) {
            this.component(Component.RED, args[1], args[2]);
          }
          break;
        case "green-component":
          if (this.argsError(args, 2)) {
            this.component(Component.GREEN, args[1], args[2]);
          }
          break;
        case "blue-component":
          if (this.argsError(args, 2)) {
            this.component(Component.BLUE, args[1], args[2]);
          }
          break;
        case "blur":
          if (this.argsError(args, 2)) {
            this.blur(args[1], args[2]);
          }
          break;
        case "sharpen":
          if (this.argsError(args, 2)) {
            this.sharpen(args[1], args[2]);
          }
          break;
        case "greyscale":
          if (this.argsError(args, 2)) {
            this.greyscale(args[1], args[2]);
          }
          break;
        case "sepia":
          if (this.argsError(args, 2)) {
            this.sepia(args[1], args[2]);
          }
          break;
        case "menu":
          this.welcomeMessage();
          break;
        default:
          this.view.renderMessage("Invalid command...please try again.\n");
      }
    }
  }

  /**
   * Renders the welcome message to the view.
   */
  private void welcomeMessage() {
    this.view.renderMessage("Welcome to the SimpleEditor program. "
        + "Please use one of the following commands: \n");
    this.view.renderMessage("- load <path> <save-name> - Load an image\n");
    this.view.renderMessage("- save <path> <save-name> - Saves the image to the given path\n");
    this.view.renderMessage("- brighten <value> <image-name> <save-name> - Brightens an image to "
        + "the value (pos/neg) and saves to the new name\n");
    this.view.renderMessage("- vertical-flip <image-name> <save-name> - Flips the image vertically"
        + " and saves to the new name\n");
    this.view.renderMessage("- horizontal-flip <image-name> <save-name> - Flips the image "
        + "horizontally and saves to the new name\n");
    this.view.renderMessage("- value-component <image-name> <save-name> - Converts the image to "
        + "show the value component and saves to the new name\n");
    this.view.renderMessage("- intensity-component <image-name> <save-name> - Converts the image "
        + "to show the intensity component and saves to the new name\n");
    this.view.renderMessage("- luma-component <image-name> <save-name> - Converts the image to "
        + "show the luma component and saves to the new name\n");
    this.view.renderMessage("- red-component <image-name> <save-name> - Converts the image to "
        + "show the red component and saves to the new name\n");
    this.view.renderMessage("- green-component <image-name> <save-name> - Converts the image to "
        + "show the green component and saves to the new name\n");
    this.view.renderMessage("- blue-component <image-name> <save-name> - Converts the image to "
        + "show the blue component and saves to the new name\n");
    this.view.renderMessage("- blur <image-name> <save-name> - Blurs the image\n");
    this.view.renderMessage("- sharpen <image-name> <save-name> - Sharpens the image\n");
    this.view.renderMessage("- sepia <image-name> <save-name> - Color transforms the " +
        "image to sepia\n");
    this.view.renderMessage("- greyscale <image-name> <save-image> - Color transforms the " +
        "image to greyscale\n");
    this.view.renderMessage("- menu - Shows this screen again.\n");
    this.view.renderMessage("Type \"q\" if you would like to quit the program.\n");
  }

  /**
   * Handles errors when an incorrect amount of arguments has been provided. Will then output an
   * error message. Adds one to the amt parameter to account for the command itself.
   * @param args the arguments taken in from the scanner.
   * @param amt the amount of arguments that is expected.
   * @return whether the number of arguments is correct.
   */
  private boolean argsError(String[] args, int amt) {
    if (args.length == amt + 1) {
      return true;
    }
    this.view.renderMessage("You have provided an incorrect amount of arguments. "
        + "Please try again.\n");
    return false;
  }

  /**
   * Handles the loading of images for the controller. Adds the subsequent messages and then calls
   * the model to handle the task internally.
   * @param path the path of the file to load.
   * @param name the name to save the image as in memory.
   */
  protected void loadHelp(String path, String name) {
    try {
      this.load(path, name);
      this.view.renderMessage("Loading image with name \"" + name + "\".\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("The was an error loading the provided file path. Not found or "
          + "empty.\n");
    }
  }

  /**
   * Checks to see if the file name ends with ppm or not and loads the image file accordingly
   * whether it is a PPM file or another type.
   * @param path the path at which the file is loaded from.
   * @param name the name the file is being stored as in memory.
   * @throws IllegalArgumentException if the path or name is null.
   * @throws IllegalStateException if the program cannot read the file provided.
   */
  private void load(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }

    if (path.endsWith(".ppm")) {
      this.loadPPM(path, name);
    } else {
      this.loadNotPPM(path, name);
    }
  }

  /**
   * Loads an image of the type PPM differently than the other file types due to how it is
   * read into the system. Then calls the accept method in the model which saves this specifc
   * image in memory.
   * @param path the path of the image to load.
   * @param name the to save the image in memory as.
   * @throws IllegalArgumentException if either of the parameters are null.
   * @throws IllegalStateException if the file is not found.
   */
  private void loadPPM(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File is not found");
    }

    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());
    String token;

    try {
      token = sc.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("File is empty");
    }

    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    Pixel[][] loadedImage = new Pixel[height][width];

    int maxValue = sc.nextInt(); // Not used, but needs to be scanned

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel p = new Pixel(r, g, b);
        loadedImage[i][j] = p;
      }
    }
    this.model.acceptNewImage(loadedImage, name);
  }

  /**
   * Loads all other supported file types than PPM through a BufferedImage, and inputs the array
   * of Pixels into the HashMap with the given name.
   * @param path the path of the file to be read.
   * @param name the name to store the array in memory with.
   * @throws IllegalArgumentException if either of the parameters are null.
   * @throws IllegalStateException if the program cannot properly read the file provided.
   */
  private void loadNotPPM(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }
    BufferedImage img;
    try {
      img = ImageIO.read(new File(path));
    } catch (IOException e) {
      throw new IllegalStateException("Program could not read JPG");
    }

    Pixel[][] loadedImage = new Pixel[img.getHeight()][img.getWidth()];
    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int color = img.getRGB(j, i);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        Pixel p = new Pixel(r, g, b);
        loadedImage[i][j] = p;
      }
    }
    this.model.acceptNewImage(loadedImage, name);
  }

  /**
   * Handles the saving of images for the controller. Adds the subsequent messages and then calls
   * the model to handle the task internally.
   * @param path the path of where to save.
   * @param name the name of the image in memory.
   */
  protected void saveHelp(String path, String name) {
    try {
      this.save(path, name);
      this.view.renderMessage("Successfully saved image to path \"" + path + "\".\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: Couldn't save the provided file. Please ensure the name and "
          + "path are correct and try again.\n");
    }
  }

  /**
   * Saves the image file if it is a ppm file or another type of file.
   * @param path the path where the file is being saved.
   * @param name the name of the image stored in memory to save.
   * @throws IllegalArgumentException if the path or name is null.
   * @throws IllegalStateException if the program could not save the file properly.
   */
  private void save(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }
    if (path.endsWith(".ppm")) {
      this.savePPM(path,name);
    } else {
      this.saveNotPPM(path,name);
    }
  }

  /**
   * Handles the saving of images that are of type PPM.
   * @param path the name of the path to save the file to.
   * @param name the image name to save from memory.
   * @throws IllegalArgumentException if either of the parameters are null.
   * @throws IllegalStateException if the name is not found, or if there is an error saving.
   */
  private void savePPM(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(path));
      Pixel[][] image = this.model.releaseImage(name);

      writer.write("P3" + System.lineSeparator());
      writer.write("# Created by SimpleImage program. OOD Assignment 4"
          + System.lineSeparator());
      writer.write(image[0].length + " " + image.length + System.lineSeparator());
      writer.write("255" + System.lineSeparator());

      for (int i = 0; i < image.length; i++) {
        for (int j = 0; j < image[i].length; j++) {
          writer.write(image[i][j].getRed() + System.lineSeparator());
          writer.write(image[i][j].getGreen() + System.lineSeparator());
          writer.write(image[i][j].getBlue() + System.lineSeparator());
        }
      }

      writer.close();

    } catch (IOException e) {
      throw new IllegalStateException("Editor could not properly save file.");
    }
  }

  /**
   * Handles the saving of images that are not of type PPM.
   * @param path the name of the path to save the file to.
   * @param name the image name to save from memory.
   * @throws IllegalArgumentException if either of the parameters are null.
   * @throws IllegalStateException if the name is not found, or if there is an error saving.
   */
  private void saveNotPPM(String path, String name)
      throws IllegalArgumentException, IllegalStateException {
    if (path == null || name == null) {
      throw new IllegalArgumentException("Path and name must both not be null.");
    }
    try {
      Pixel[][] image = this.model.releaseImage(name);
      BufferedImage img = new BufferedImage(image[0].length, image.length, 1);

      for (int i = 0; i < image.length; i++) {
        for (int j = 0; j < image[i].length; j++) {
          Pixel p = image[i][j];
          int color = (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
          img.setRGB(j, i, color);
        }
      }

      String ext = path.substring(path.length() - 3);
      if (!(ext.equals("jpg") || ext.equals("png") || ext.equals("bmp"))) {
        throw new IllegalStateException("File extension not recognized");
      }

      ImageIO.write(img, ext, new File(path));
    } catch (IOException e) {
      throw new IllegalStateException("Couldn't properly save file");
    }
  }

  /**
   * Handles the brightening of images for the controller. Adds the subsequent messages and then
   * calls the model to handle the task internally.
   * @param val the value to brighten by, as a String from the Scanner.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory
   */
  protected void brighten(String val, String beforeImage, String afterImage) {
    int value = 0;
    try {
      value = Integer.parseInt(val);
    } catch (NumberFormatException e) {
      this.view.renderMessage("You did not enter a valid integer for the first argument.\n");
      return;
    }

    try {
      this.model.brighten(value, beforeImage, afterImage);
      this.view.renderMessage("Brightening image by " + val + ".\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the flipping of an image for the controller. Adds the subsequent messages and then
   * calls the model to handle the task internally.
   * @param f the type of flip that will be performed on the image
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  protected void flip(Flip f, String beforeImage, String afterImage) {
    try {
      this.model.flip(f, beforeImage, afterImage);
      this.view.renderMessage("Flipping the image. \n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the rendering of components for images for the controller. Adds the subsequent
   * messages and then calls the model to handle the task internally.
   * @param c The component that is being rendered.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  protected void component(Component c, String beforeImage, String afterImage) {
    try {
      this.model.component(c, beforeImage, afterImage);
      this.view.renderMessage("Rendering the component " + c + " for the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the blurring of images for the controller. Adds the subsequent
   * messages and then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  protected void blur(String beforeImage, String afterImage) {
    try {
      this.model.blur(beforeImage, afterImage);
      this.view.renderMessage("Blurring the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles the sharpening of images for the controller. Adds the subsequent
   * messages and then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  protected void sharpen(String beforeImage, String afterImage) {
    try {
      this.model.sharpen(beforeImage, afterImage);
      this.view.renderMessage("Sharpening the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles greyscale of the images for the controller. Adds the subsequent messages and
   * then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  protected void greyscale(String beforeImage, String afterImage) {
    try {
      this.model.greyscale(beforeImage, afterImage);
      this.view.renderMessage("Transforming the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }

  /**
   * Handles sepia of the images for the controller. Adds the subsequent messages and
   * then calls the model to handle the task internally.
   * @param beforeImage the name of the image in memory to conduct the operation on.
   * @param afterImage the name to save the resulting image in memory.
   */
  protected void sepia(String beforeImage, String afterImage) {
    try {
      this.model.sepia(beforeImage, afterImage);
      this.view.renderMessage("Transforming the image.\n");
    } catch (IllegalStateException e) {
      this.view.renderMessage("ERROR: The provided image name was not found.\n");
    }
  }
}
