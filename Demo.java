import javax.swing.*;
import java.awt.*;

public class Demo extends JFrame {

    // Constructor for the frame
    public Demo() {
        // Set background color of content pane
        getContentPane().setBackground(Color.blue);

        // Set the frame properties
        setTitle("Demo Frame");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Override paint method to draw the string
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Call the superclass paint method
        g.setColor(Color.white); // Set text color
        g.drawString("Welcome To Java JFrame", 500, 500); // Draw the string
    }

    // Main method to run the application
    public static void main(String[] args) {
        new Demo(); // Create and show the frame
    }
}

