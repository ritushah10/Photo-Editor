import org.junit.Test;

import view.ImageEditorView;
import view.SimpleEditorView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the view.SimpleEditorView class.
*/
public class SimpleImageViewTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    new SimpleEditorView(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderException() {
    FakeAppendable fa = new FakeAppendable();
    ImageEditorView view = new SimpleEditorView(fa);
    view.renderMessage("Cause an error");
  }

  @Test
  public void testRender() {
    Appendable ap = new StringBuilder();
    ImageEditorView view = new SimpleEditorView(ap);
    assertEquals("", ap.toString());
    view.renderMessage("Hello");
    assertEquals("Hello", ap.toString());
    view.renderMessage("\nNew Line");
    assertEquals("Hello\nNew Line", ap.toString());
  }
}
