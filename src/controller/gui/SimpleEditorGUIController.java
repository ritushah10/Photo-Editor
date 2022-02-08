package controller.gui;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.ImageEditorController;
import controller.SimpleEditorController;
import model.Component;
import model.Flip;
import model.ImageEditorModel;
import view.gui.ImageEditorGUIView;

/**
 * Handles all the inputs and outputs for the program with the GUI, and allows the user to control
 * the model using the GUI elements.
 */
public class SimpleEditorGUIController extends SimpleEditorController
    implements ImageEditorController, ActionListener {
  private final ImageEditorGUIView view;
  private final JFrame frame;
  private String currentImage;
  private String currentPath;

  /**
   * Creates a GUI controller to run the program using the provided parameters of model and view.
   * Will allow the controller to control the model and view to make the program work.
   * @param model the editor model in order to edit images.
   * @param view the view in order to show output to the user.
   */
  public SimpleEditorGUIController(ImageEditorModel model, ImageEditorGUIView view) {
    super(model, view);

    this.view = view;
    this.frame = view.releaseFrame();
    this.currentImage = null;
    this.currentPath = "C:\\";
  }

  @Override
  public void start() throws IllegalStateException {
    this.view.show(this);
  }

  /**
   * This static inner class represents a FilenameFilter implementation that ensures the file
   * types when saving and loading are one of the following: png, jpg, jpeg, ppm, or bmp.
   */
  private static class EditorFilter implements FilenameFilter {
    @Override
    public boolean accept(File dir, String name) {
      return name.endsWith(".png")
          || name.endsWith(".jpg")
          || name.endsWith(".jpeg")
          || name.endsWith(".ppm")
          || name.endsWith(".bmp");
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Load":
        String newName = this.showPopUp("Enter a Name for this Layer");
        if (newName == null) {
          break;
        }
        FileDialog fd = new FileDialog(this.frame, "Select an Image", FileDialog.LOAD);
        fd.setDirectory(this.currentPath);
        fd.setFilenameFilter(new EditorFilter());
        fd.setMultipleMode(false);
        fd.setVisible(true);

        String path = fd.getDirectory() + fd.getFile();
        if (fd.getFile() != null) {
          super.loadHelp(path, newName);
          this.currentPath = path;
          this.currentImage = newName;
          this.view.drawImage(this.currentImage);
        }
        break;
      case "Save":
        if (this.checkLayersWithError()) {
          break;
        }
        FileDialog save = new FileDialog(this.frame, "Select an Image", FileDialog.SAVE);
        save.setDirectory(this.currentPath);
        save.setFilenameFilter(new EditorFilter());
        save.setVisible(true);

        String savePath = save.getDirectory() + save.getFile();
        if (!(savePath.endsWith(".png") || savePath.endsWith(".jpg") || savePath.endsWith(".ppm")
            || savePath.endsWith(".jpeg") || savePath.endsWith(".bmp"))) {
          savePath = savePath + ".png";
        }
        if (save.getFile() != null) {
          super.saveHelp(savePath, this.currentImage);
          this.currentPath = savePath;
        }
        break;
      case "Change Layer":
        if (this.checkLayersWithError()) {
          break;
        }
        String selected = this.showSelection("Choose a Layer", "Layer Selection",
            this.model.getListOfLayers());
        if (selected == null) {
          break;
        }
        if (this.currentImage.equals(selected)) {
          this.showError("You are already on this layer");
          return;
        }
        this.currentImage = selected;
        this.view.drawImage(this.currentImage);
        break;
      case "Brighten":
        if (this.checkLayersWithError()) {
          break;
        }
        this.brighten();
        this.view.drawImage(this.currentImage);
        break;
      case "Vertical Flip":
        if (this.checkLayersWithError()) {
          break;
        }
        this.vFlip();
        this.view.drawImage(this.currentImage);
        break;
      case "Horizontal Flip":
        if (this.checkLayersWithError()) {
          break;
        }
        this.hFlip();
        this.view.drawImage(this.currentImage);
        break;
      case "Component":
        if (this.checkLayersWithError()) {
          break;
        }
        this.component();
        this.view.drawImage(this.currentImage);
        break;
      case "Filter":
        if (this.checkLayersWithError()) {
          break;
        }
        this.filter();
        this.view.drawImage(this.currentImage);
        break;
      default:
        throw new IllegalStateException("Unknown action performed");
    }
  }

  private boolean checkLayersWithError() {
    if (this.currentImage == null) {
      this.showError("No Layers Loaded");
      return true;
    }
    return false;
  }

  private void brighten() {
    String layerName = this.showPopUp("Enter Layer Name for Change");
    if (layerName == null) {
      return;
    }

    super.brighten(this.showPopUp("Enter Amount to Brighten"), currentImage, layerName);
    this.currentImage = layerName;
  }

  private void vFlip() {
    String layerName = this.showPopUp("Enter Layer Name for Change");
    if (layerName == null) {
      return;
    }

    super.flip(Flip.VERTICAL, this.currentImage, layerName);
    this.currentImage = layerName;
  }

  private void hFlip() {
    String layerName = this.showPopUp("Enter Layer Name for Change");
    if (layerName == null) {
      return;
    }

    super.flip(Flip.HORIZONTAL, this.currentImage, layerName);
    this.currentImage = layerName;
  }

  private void component() {
    String selection = this.showSelection("Choose a Component Type", "Component Selection",
        "Value", "Intensity", "Luma", "Red", "Green", "Blue");
    if (selection == null) {
      return;
    }

    String layerName = this.showPopUp("Enter Layer Name for Change");
    if (layerName == null) {
      return;
    }

    super.component(this.getComponentType(selection), this.currentImage, layerName);
    this.currentImage = layerName;
  }

  private Component getComponentType(String name) {
    switch (name) {
      case "Value":
        return Component.VALUE;
      case "Intensity":
        return Component.INTENSITY;
      case "Luma":
        return Component.LUMA;
      case "Red":
        return Component.RED;
      case "Green":
        return Component.GREEN;
      case "Blue":
        return Component.BLUE;
      default:
        // No action
    }
    return Component.VALUE;
  }

  private void filter() {
    String selection = this.showSelection("Choose a Filter Type", "Filter Selection",
        "Blur", "Sharpen", "Greyscale", "Sepia");
    if (selection == null) {
      return;
    }

    String layerName = this.showPopUp("Enter Layer Name for Change");
    if (layerName == null) {
      return;
    }

    switch (selection) {
      case "Blur":
        super.blur(this.currentImage, layerName);
        break;
      case "Sharpen":
        super.sharpen(this.currentImage, layerName);
        break;
      case "Greyscale":
        super.greyscale(this.currentImage, layerName);
        break;
      case "Sepia":
        super.sepia(this.currentImage, layerName);
        break;
      default:
        // No action
    }
    this.currentImage = layerName;
  }

  private String showPopUp(String str) {
    return JOptionPane.showInputDialog(this.frame, str);
  }

  private void showError(String error) {
    JOptionPane.showMessageDialog(null, error, "ERROR",
        JOptionPane.ERROR_MESSAGE);
  }

  private String showSelection(String message, String title, String... list) {
    if (list.length == 0) {
      return null;
    }
    Object selectionObj = JOptionPane.showInputDialog(null, message,
        title, JOptionPane.PLAIN_MESSAGE, null, list, "");
    if (selectionObj == null) {
      return null;
    }
    return selectionObj.toString();
  }
}
