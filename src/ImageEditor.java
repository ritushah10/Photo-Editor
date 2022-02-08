import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import controller.ImageEditorController;
import controller.SimpleEditorController;
import controller.gui.SimpleEditorGUIController;
import model.ImageEditorModel;
import model.SimpleEditorModel;
import view.ImageEditorView;
import view.SimpleEditorView;
import view.gui.ImageEditorGUIView;
import view.gui.SimpleEditorGUIView;

/**
 * Contains the main method for the program and starts using the model, view, and controller
 * depending on certain command line arguments provided (or not provided) at runtime.
 */
public class ImageEditor {
  /**
   * The main method for the program: originating method. The starting point for interactions
   * between the user and program.
   * @param args the command line arguments provided.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      runAsGUI();
      return;
    }

    for (int i = 0; i < args.length; i++) {
      if (args[i].equalsIgnoreCase("-text")) {
        runInteractive();
        break;
      }
      if (args[i].equalsIgnoreCase("-file")) {
        if (i + 1 < args.length) {
          runScriptFile(args[i + 1]);
        }
      }
    }
    runAsGUI();
  }

  /**
   * Runs the program as a GUI view using the created model and view classes for the GUI.
   */
  private static void runAsGUI() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorGUIView view = new SimpleEditorGUIView(model);
    ImageEditorController cont = new SimpleEditorGUIController(model, view);
    cont.start();
  }

  /**
   * Runs the interactive version of the program by setting the input to the controller as an
   * InputStreamReader with System.in. This allows the user to type in commands through the system
   * input.
   */
  private static void runInteractive() {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView();
    ImageEditorController cont = new SimpleEditorController(model, view,
        new InputStreamReader(System.in));
    cont.start();
  }

  /**
   * Runs the script file version of the program by setting the input to the controller as a
   * StringReader using the text from the provided text file.
   * @param fileName the file name containing the script.
   */
  private static void runScriptFile(String fileName) {
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorView view = new SimpleEditorView();
    ImageEditorController cont;

    Scanner sc;

    /*
     * Attempts to initialize a Scanner using a FileInputStream with the given file name. If there
     * is an error with the file name provided, the program will automatically revert to the
     * interactive version of the program.
     */
    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (IOException e) {
      view.renderMessage("**********\n");
      view.renderMessage("File \"" + fileName + "\" not found. Reverting to interactive input.\n");
      view.renderMessage("**********\n\n");
      runInteractive();
      return;
    }

    StringBuilder sb = new StringBuilder();

    // Reads the data from the provided script file and appends to the StringBuilder
    while (sc.hasNext()) {
      sb.append(sc.nextLine()).append("\n");
    }

    /*
     * If the file did not already contain a quit command at the end, adds it in automatically
     * so the controller does not encounter errors with the Scanner not having enough inputs.
     */
    if (!sb.substring(sb.length() - 1).equals("q")) {
      sb.append("q");
    }

    // Starts the program
    cont = new SimpleEditorController(model, view, new StringReader(sb.toString()));
    cont.start();
  }
}
