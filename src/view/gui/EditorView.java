package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import model.ImageEditorState;
import model.Pixel;
import model.histogram.DrawHistogram;
import model.histogram.Histogram;
import model.histogram.HistogramModel;

/**
 * Represents the main frame for the GUI component of the program. Sets up all the different
 * components that make up the program.
 */
public class EditorView extends JFrame {
  private final JPanel leftPanel;
  private final JPanel rightPanel;
  private final JPanel container;
  private final JMenuBar menuBar;

  private final JMenu file;
  private final JMenuItem load;
  private final JMenuItem save;
  private final JMenuItem changeLayer;

  private final JMenu edit;
  private final JMenuItem brighten;
  private final JMenuItem vFlip;
  private final JMenuItem hFlip;
  private final JMenuItem component;
  private final JMenuItem filter;

  private final JProgressBar bar;
  private final JPanel barCont;
  private JScrollPane lastUsed;
  private final Histogram hm;

  /**
   * Constructs a new EditorView in order to allow the program to set up the GUI.
   * @param state the state of the image editor, read only, so changes may not be made to the
   *              model.
   */
  protected EditorView(ImageEditorState state) {
    super();
    if (state == null) {
      throw new IllegalArgumentException("State may not be null");
    }
    this.hm = new HistogramModel();
    this.leftPanel = new JPanel();
    this.rightPanel = new DrawHistogram(hm);
    this.container = new JPanel();

    this.menuBar = new JMenuBar();
    this.file = new JMenu("File");
    this.load = new JMenuItem("Load");
    this.save = new JMenuItem("Save");
    this.changeLayer = new JMenuItem("Change Layer");

    this.edit = new JMenu("Edit");
    this.brighten = new JMenuItem("Brighten");
    this.vFlip = new JMenuItem("Vertical Flip");
    this.hFlip = new JMenuItem("Horizontal Flip");
    this.component = new JMenuItem("Component");
    this.filter = new JMenuItem("Filter");
    this.bar = new JProgressBar();
    this.barCont = new JPanel();

    this.initialize();
  }

  private void initialize() {
    JPanel finalPanel = new JPanel();
    finalPanel.setLayout(new GridLayout(2, 1));
    this.container.setLayout(new GridLayout(1, 2));
    this.leftPanel.setLayout(new BorderLayout());
    this.rightPanel.setLayout(new FlowLayout());
    leftPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.BLACK), "Image View"));
    this.container.add(this.leftPanel);
    this.container.add(this.rightPanel);
    this.barCont.add(this.bar);
    finalPanel.add(this.container);
    finalPanel.add(this.barCont);
    this.add(this.container);

    this.setResizable(true);
    this.setTitle("Simple Image Editor GUI");
    this.pack();
    this.setSize(1000, 500);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBackground(Color.darkGray);
    this.setUpMenu();
  }

  private void setUpMenu() {
    this.menuBar.add(this.file);
    this.menuBar.add(this.edit);
    this.file.add(this.load);
    this.file.add(this.save);
    this.file.add(this.changeLayer);
    this.edit.add(this.brighten);
    this.edit.add(this.vFlip);
    this.edit.add(this.hFlip);
    this.edit.add(this.component);
    this.edit.add(this.filter);

    this.setJMenuBar(this.menuBar);
  }

  protected void setUpListeners(ActionListener a) {
    this.load.addActionListener(a);
    this.save.addActionListener(a);
    this.changeLayer.addActionListener(a);
    this.brighten.addActionListener(a);
    this.vFlip.addActionListener(a);
    this.hFlip.addActionListener(a);
    this.component.addActionListener(a);
    this.filter.addActionListener(a);
  }

  protected void drawImage(Pixel[][] image) {
    this.hm.setImage(image);
    this.rightPanel.invalidate();
    this.rightPanel.repaint();
    BufferedImage img = new BufferedImage(image[0].length, image.length, 1);

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        Pixel p = image[i][j];
        int color = (p.getRed() << 16) | (p.getGreen() << 8) | p.getBlue();
        img.setRGB(j, i, color);
      }
    }

    JLabel picLabel = new JLabel(new ImageIcon(img));
    JScrollPane scrollPane = new JScrollPane(picLabel);
    if (this.lastUsed != null) {
      this.leftPanel.remove(this.lastUsed);
    }
    this.leftPanel.add(scrollPane);
    this.lastUsed = scrollPane;
    scrollPane.setPreferredSize(new Dimension(500,500));
    this.pack();
  }
}
