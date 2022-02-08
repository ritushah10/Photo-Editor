package mocks;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import view.gui.ImageEditorGUIView;

/**
 * Represents a mock view for the program GUI in order to test inputs.
 */
public class MockGUIView implements ImageEditorGUIView {
  private final StringBuilder log;

  /**
   * Constructs a new MockGUIView mock model. Instantiates a new StringBuilder given
   * as a log in order to keep track of the inputs provided to the program, and confirm they are
   * correctly being used.
   * @param log the StringBuilder log provided in which to use as a log.
   */
  public MockGUIView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    log.append("RENDERED: ").append(message).append("\n");
  }

  @Override
  public void show(ActionListener a) {
    log.append("SHOWN").append("\n");
  }

  @Override
  public JFrame releaseFrame() {
    log.append("RELEASED FRAME").append("\n");
    return null;
  }

  @Override
  public void drawImage(String name) {
    log.append("IMAGE DRAWN").append("\n");
  }
}
