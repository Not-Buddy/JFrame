import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormDesignJFrame extends JFrame 
{
  public FormDesignJFrame() 
  {
    setLayout(new FlowLayout());
    
    // Create and add components
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameTextField = new JTextField(15);
    JLabel ageLabel = new JLabel("Age:");
    JTextField ageTextField = new JTextField(5);
    JButton submitButton = new JButton("Submit");

    add(nameLabel);
    add(nameTextField);
    add(ageLabel);
    add(ageTextField);
    add(submitButton);
    
    // Action listener for the submit button
    submitButton.addActionListener(new ActionListener() 
    {
      @Override
      public void actionPerformed(ActionEvent e) 
      {
        String name = nameTextField.getText();
        String age = ageTextField.getText();
        JOptionPane.showMessageDialog(null, "Name: " + name + "\nAge: " + age);
      }
    });

    // Set JFrame properties
    setTitle("Form Design Frame");
    setSize(300, 150);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) 
  {
    new FormDesignJFrame();
  }
}

