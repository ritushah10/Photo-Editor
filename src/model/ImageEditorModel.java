package model;

/**
 * Represents an image editor. Allows for multiple different types of operations to be done on
 * images within the editor. Can store multiple image representations in memory as they are loaded
 * in.
 */
public interface ImageEditorModel extends ImageEditorState {
  /**
   * Accepts a loaded image from the given path. Obtains the internal information about the file,
   * such as the pixels, and stores this in memory as the given name.
   * @param image the 2D array of Pixels for the newly loaded image to accept into the model.
   * @param name the name the file is being stored as in memory.
   * @throws IllegalArgumentException if either of the parameters are null.
   */
  void acceptNewImage(Pixel[][] image, String name)
      throws IllegalArgumentException;

  /**
   * Uses the given component type to convert the given image to greyscale. Then, stores the
   * resulting image in memory with the new name.
   * @param type the component type, enum, which can be used to convert the image to greyscale.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void component(Component type, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Conducts either a vertical or horizontal flip on the given image in memory,
   * and save the resulting image to the new name in memory.
   * @param type the flip type, enum, which can either perform a vertical or horizontal flip
   * @param fromImageName the image to conduct this operation on, stored in memory
   * @param toImageName the new image name to save the resulting image to in memory
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void flip(Flip type, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Brightens the image by the given value to the image stored in memory, and then saves the
   * resulting image to the name in memory. If the value is negative, the image will be darkened.
   * @param value the value by which to brighten the image, either positive or negative.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   * @throws IllegalArgumentException if any of the parameters are null.
   * @throws IllegalStateException if the given image name cannot be found in memory.
   */
  void brighten(int value, String fromImageName, String toImageName)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Blurs the image and saves as the new name.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   */
  void blur(String fromImageName, String toImageName);

  /**
   * Sharpens the image and saves as the new name.
   * @param fromImageName the image to conduct this operation on, stored in memory.
   * @param toImageName the new image name to save the resulting image to in memory.
   */
  void sharpen(String fromImageName, String toImageName);

  void greyscale(String fromImageName, String toImageName);

  void sepia(String fromImageName, String toImageName);
}
