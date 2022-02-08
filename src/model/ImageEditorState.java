package model;

/**
 * Represents the viewable state of the model. This is used to restrict access to operations
 * when viewing the model. Currently, there are no methods needing to be put into the state as
 * the output of the model will be shown externally when the image is opened.
 */
public interface ImageEditorState {
  String[] getListOfLayers();

  /**
   * Given a name of an image in memory, returns the image 2D array of Pixels representing that
   * specific image. If it is not found, throws an error.
   * @param name the name of the image that has been saved in memory.
   * @return the 2D array of Pixels representing the image.
   * @throws IllegalStateException if the image cannot be found by its name in memory.
   */
  Pixel[][] releaseImage(String name) throws IllegalStateException;
}