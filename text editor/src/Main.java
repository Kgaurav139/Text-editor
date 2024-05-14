import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Main extends JFrame {
    public JTextArea textArea;
    public JFileChooser fileChooser;
    public JComboBox<String> fontComboBox;
    public JComboBox<Integer> sizeComboBox;
    public JToggleButton boldToggleButton;
    public JToggleButton italicToggleButton;
    public JToggleButton underlineToggleButton;

    public Main() {
        setTitle("Styled Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(Color.BLUE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        fileChooser = new JFileChooser();

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                        textArea.read(reader, null);
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
                        writer.write(textArea.getText());
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        fontComboBox = new JComboBox<>(fontNames);
        fontComboBox.setSelectedItem("Arial"); //
        fontComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fontName = (String) fontComboBox.getSelectedItem();
                updateFont(fontName);
            }
        });
        controlPanel.add(new JLabel("Font:"));
        controlPanel.add(fontComboBox);

        Integer[] sizes = { 2, 4, 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 72 }; // Common font sizes
        sizeComboBox = new JComboBox<>(sizes);
        sizeComboBox.setSelectedItem(14);
        sizeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int fontSize = (int) sizeComboBox.getSelectedItem();
                updateFontSize(fontSize);
            }
        });
        controlPanel.add(new JLabel("Size:"));
        controlPanel.add(sizeComboBox);

        boldToggleButton = new JToggleButton("Bold");
        boldToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFontStyle();
            }
        });
        controlPanel.add(boldToggleButton);

        italicToggleButton = new JToggleButton("Italic");
        italicToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFontStyle();
            }
        });
        controlPanel.add(italicToggleButton);

        underlineToggleButton = new JToggleButton("Underline");
        underlineToggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFontStyle();
            }
        });
        controlPanel.add(underlineToggleButton);

        add(controlPanel, BorderLayout.NORTH);
    }

    private void updateFont(String fontName) {
        Font currentFont = textArea.getFont();
        textArea.setFont(new Font(fontName, currentFont.getStyle(), currentFont.getSize()));
    }

    private void updateFontSize(int fontSize) {
        Font currentFont = textArea.getFont();
        textArea.setFont(new Font(currentFont.getName(), currentFont.getStyle(), fontSize));
    }

    private void updateFontStyle() {
        int style = Font.PLAIN;
        if (boldToggleButton.isSelected()) {
            style |= Font.BOLD;
        }
        if (italicToggleButton.isSelected()) {
            style |= Font.ITALIC;
        }
        textArea.setFont(textArea.getFont().deriveFont(style));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main editor = new Main();
            editor.setVisible(true);
        });
    }
}
