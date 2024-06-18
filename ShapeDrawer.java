package OnTapSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ShapeDrawer extends JFrame {
    private Color currentColor = Color.RED;
    private String currentShape = "Square";
    private String currentStyle = "Fill";
    private boolean drawing = false; 

    public ShapeDrawer() {
        setTitle("Using Mouse Paint");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creating the top panel with buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 7));

        JButton clearButton = new JButton("Clear all");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clear();
            }
        });

        JButton redButton = new JButton("Red");
        redButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.RED;
            }
        });

        JButton blueButton = new JButton("Blue");
        blueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentColor = Color.BLUE;
            }
        });

        JRadioButton squareButton = new JRadioButton("Square", true);
        squareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Square";
            }
        });

        JRadioButton ovalButton = new JRadioButton("Oval");
        ovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentShape = "Oval";
            }
        });

        JRadioButton fillButton = new JRadioButton("Fill", true);
        fillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentStyle = "Fill";
            }
        });

        JRadioButton outlineButton = new JRadioButton("Out Line");
        outlineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentStyle = "Out Line";
            }
        });

        ButtonGroup shapeGroup = new ButtonGroup();
        shapeGroup.add(squareButton);
        shapeGroup.add(ovalButton);

        ButtonGroup styleGroup = new ButtonGroup();
        styleGroup.add(fillButton);
        styleGroup.add(outlineButton);

        topPanel.add(clearButton);
        topPanel.add(redButton);
        topPanel.add(blueButton);
        topPanel.add(squareButton);
        topPanel.add(ovalButton);
        topPanel.add(fillButton);
        topPanel.add(outlineButton);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(drawingPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private DrawingPanel drawingPanel = new DrawingPanel();

    private class DrawingPanel extends JPanel {
        private Image image;
        private Graphics2D g2;

        public DrawingPanel() {
            setBackground(new Color(255, 255, 255));

            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    drawing = true; 
                    drawShape(e.getX(), e.getY());
                }

                public void mouseReleased(MouseEvent e) {
                    drawing = false; 
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (drawing) {
                        drawShape(e.getX(), e.getY());
                    }
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image == null) {
                image = createImage(getSize().width, getSize().height);
                g2 = (Graphics2D) image.getGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }
            g.drawImage(image, 0, 0, null);
        }

        public void clear() {
            g2.setPaint(new Color(255, 255, 255));
            g2.fillRect(0, 0, getSize().width, getSize().height);
            g2.setPaint(Color.BLACK);
            repaint();
        }

        private void drawShape(int x, int y) {
            g2.setPaint(currentColor);
            int width = 10;
            int height = 10;
            if (currentShape.equals("Square")) {
                if (currentStyle.equals("Fill")) {
                    g2.fillRect(x - width / 2, y - height / 2, width, height);
                } else {
                    g2.drawRect(x - width / 2, y - height / 2, width, height);
                }
            } else if (currentShape.equals("Oval")) {
                if (currentStyle.equals("Fill")) {
                    g2.fillOval(x - width / 2, y - height / 2, width, height);
                } else {
                    g2.drawOval(x - width / 2, y - height / 2, width, height);
                }
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ShapeDrawer();
            }
        });
    }
}
