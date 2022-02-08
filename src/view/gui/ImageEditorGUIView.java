package view.gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import view.ImageEditorView;

/**
 * Represents the GUI view for the program, and allows the operations of the program to be
 * conducted, such as showing the image and drawing it.
 */
public interface ImageEditorGUIView extends ImageEditorView {
  /**
   * Shows the view of the program by making the frame visible after all the components have
   * been added.
   * @param a the Action Listener for the buttons in order to control the program.
   */
  void show(ActionListener a);

  /**
   * Releases the frame from the view to other parts of the program in order to be used to edit
   * the view of the program.
   * @return the current JFrame that is being operated upon.
   */
  JFrame releaseFrame();

  /**
   * Draws the given image to the screen. Given a String name for the image to be drawn, the
   * method will show the image on the screen.
   * @param name the name of the image stored in memory to show.
   */
  void drawImage(String name);
}
