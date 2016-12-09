import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ReservationTab extends Tab implements ActionListener
{
    private String customerName, customerPhone;
    private Panel logInPanel;
    private JTextField nameField, phoneField;

    public ReservationTab(Container panel)
    {
        super(panel);
        createTab();
    }

    public void createTab()
    {
        createLogInTab();

        contentPanel.validate();
    }

    public void createLogInTab()
    {
        //Ceates Panels        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        logInPanel = new Panel();
        logInPanel.setLayout(new BoxLayout(logInPanel, BoxLayout.Y_AXIS));
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.setMaximumSize(new Dimension(500, 30));
        namePanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.X_AXIS));
        phonePanel.setMaximumSize(new Dimension(500, 30));
        phonePanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        //creats name Panel
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setMinimumSize(new Dimension(100, 20));
        nameLabel.setPreferredSize(new Dimension(100, 20));
        namePanel.add(nameLabel);
        nameField = new JFormattedTextField("Name");
        nameField.addActionListener(this);
        namePanel.add(nameField);

        //creats phone Panel        
        JLabel phoneLabel = new JLabel("Phonenumber: ");
        phoneLabel.setMinimumSize(new Dimension(100, 20));
        phoneLabel.setPreferredSize(new Dimension(100, 20));
        phonePanel.add(phoneLabel);
        phoneField = new JFormattedTextField("Phone");
        phoneField.addActionListener(this);
        phonePanel.add(phoneField);
        
        //create Log in Button
        JButton logInButton = new JButton("Check Customer");
        logInButton.addActionListener(this);

        //adds Panels
        contentPanel.add(logInPanel);
        logInPanel.add(namePanel);
        logInPanel.add(phonePanel);
        logInPanel.add(logInButton);
    }

    public void actionPerformed(ActionEvent event)
    {
        
    }

    public Dimension getMaximumSize()
    {
        Dimension size = getPreferredSize();
        return size;
    }
}
