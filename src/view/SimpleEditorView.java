package view;

import java.io.IOException;

import view.ImageEditorView;

/**
 * Implementation of the editor view. Allows the program to provide output to the user using the
 * provided data destination (appendable).
 */
public class SimpleEditorView implements ImageEditorView {
  private final Appendable out;

  /**
   * Constructs a view.SimpleEditorView. Because no output was provided, defaults to System.out.
   * @throws IllegalArgumentException if the provided state is null.
   */
  public SimpleEditorView() throws IllegalArgumentException {
    this(System.out);
  }

  /**
   * Constructs a view.SimpleEditorView using the given output destination. Then instantiates
   * this value to the instance fields.
   * @param out the provided data destination to show output.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public SimpleEditorView(Appendable out) throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("State or out may not be null.");
    }

    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IllegalStateException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException(
          "The editor view could not properly append the message: " + message);
    }
  }
}
