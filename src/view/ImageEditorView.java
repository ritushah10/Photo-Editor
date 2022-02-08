package view;

/**
 * Represents the view for an image editor. Allows the editor to provide output to the user at the
 * provided data destination.
 */
public interface ImageEditorView {
  /**
   * Render a specific message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IllegalStateException if transmission of the board to the provided data
   *         destination fails
   */
  void renderMessage(String message) throws IllegalStateException;
}
