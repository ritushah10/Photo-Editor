package mocks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a mock GUI controller to test the controller for the simple editor program.
 */
public class MockGUIController implements ActionListener {
  private final StringBuilder log;

  /**
   * Constructs a new MockGUIController mock model. Instantiates a new StringBuilder given
   * as a log in order to keep track of the inputs provided to the program, and confirm they are
   * correctly being used.
   * @param log the StringBuilder log provided in which to use as a log.
   */
  public MockGUIController(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    log.append("EVENT: ").append(e.getActionCommand()).append("\n");
  }
}
