package view.gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.ImageEditorState;

/**
 * Represents the view for the GUI component of the program. Allows the program to show the
 * initialized GUI to the user for integration.
 */
public class SimpleEditorGUIView implements ImageEditorGUIView {
  private final EditorView ev;
  private final ImageEditorState state;

  /**
   * Constructs a view for the GUI using the given state of the model, which is read only.
   * @param state the current state of the model.
   */
  public SimpleEditorGUIView(ImageEditorState state) {
    if (state == null) {
      throw new IllegalArgumentException("State may not be null");
    }
    this.ev = new EditorView(state);
    this.state = state;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this.ev, message, "Simple Image Editor",
        JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void show(ActionListener a) {
    this.ev.setUpListeners(a);
    this.ev.setVisible(true);
  }

  @Override
  public JFrame releaseFrame() {
    return ev;
  }

  @Override
  public void drawImage(String name) {
    this.ev.drawImage(this.state.releaseImage(name));
  }
}
