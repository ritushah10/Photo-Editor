package mocks;

import java.awt.event.ActionEvent;

/**
 * Represents a mock action event for the GUI.
 */
public class MockActionEvent extends ActionEvent  {
  /**
   * Constructs a mock action event for the GUI in order to test incoming values.
   * @param name the name of the action event.
   */
  public MockActionEvent(String name) {
    super("", ActionEvent.ACTION_FIRST, name);
  }
}
