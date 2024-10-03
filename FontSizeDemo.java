import javax.swing.*;
import java.awt.*;

public class FontSizeDemo extends JFrame {

    public FontSizeDemo() {
        // Create a JLabel with text
        JLabel label = new JLabel("Welcome to Java JFrame");

        // Set a larger font size for the label
        label.setFont(new Font("Serif", Font.PLAIN, 24)); // Font name, style, size

        // Add the label to the frame
        add(label);

        // Set JFrame properties
        setTitle("Font Size Demo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FontSizeDemo();
    }
}

