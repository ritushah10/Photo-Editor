import org.junit.Test;

import controller.ImageEditorController;
import controller.gui.SimpleEditorGUIController;
import mocks.MockActionEvent;
import mocks.MockGUIController;
import mocks.MockGUIView;
import model.ImageEditorModel;
import model.SimpleEditorModel;
import view.gui.ImageEditorGUIView;
import view.gui.SimpleEditorGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the program's GUI component.
 */
public class GUITest {
  @Test
  public void testGUIController() {
    StringBuilder log = new StringBuilder();
    ImageEditorGUIView view = new MockGUIView(log);
    ImageEditorModel model = new SimpleEditorModel();
    ImageEditorController cont = new SimpleEditorGUIController(model, view);
    cont.start();
    assertEquals("RELEASED FRAME\nSHOWN\n", log.toString());
  }

  @Test
  public void testEvents() {
    StringBuilder log = new StringBuilder();
    MockGUIController cont = new MockGUIController(log);
    cont.actionPerformed(new MockActionEvent("ABC"));
    cont.actionPerformed(new MockActionEvent("1234"));
    assertEquals("EVENT: ABC\nEVENT: 1234\n", log.toString());
  }

  @Test
  public void testView() {
    StringBuilder log = new StringBuilder();
    ImageEditorGUIView view = new MockGUIView(log);
    view.renderMessage("MSG");
    view.show(e -> {
      // Do Nothing
    });
    view.releaseFrame();
    view.drawImage("NAME");
    assertEquals("RENDERED: MSG\nSHOWN\nRELEASED FRAME\nIMAGE DRAWN\n", log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    new SimpleEditorGUIView(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNull1() {
    new SimpleEditorGUIController(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNull2() {
    new SimpleEditorGUIController(new SimpleEditorModel(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNull3() {
    new SimpleEditorGUIController(null, new SimpleEditorGUIView(new SimpleEditorModel()));
  }
}
