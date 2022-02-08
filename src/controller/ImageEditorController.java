package controller;

/**
 * The controller for the image editor program. Allows for usage of the program along with taking
 * in the inputs, conducting operations with the model, and providing outputs for the user.
 */
public interface ImageEditorController {
  /**
   * Starts the program and runs every operation needed to begin. Allows the controller to start
   * taking inputs to use the program.
   * @throws IllegalStateException if the Scanner has run out of inputs when it is expecting more.
   */
  void start() throws IllegalStateException;
}
