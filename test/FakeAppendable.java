import java.io.IOException;

/**
 * A class to represent an Appendable which always throws an IOException when called. This is used
 * in order to test the rendering of text to the output.
 */
public class FakeAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Cannot write");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Cannot write");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Cannot write");
  }
}