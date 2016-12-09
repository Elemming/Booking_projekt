import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ReservationTab extends Tab implements ActionListener, ChangeListener
{
    private String customerName;
    private int customerPhone;
    private Panel logInPanel;
    private JTextField nameField, phoneField;
    private JButton logInButton;

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
        namePanel.add(nameField);

        //creats phone Panel        
        JLabel phoneLabel = new JLabel("Phonenumber: ");
        phoneLabel.setMinimumSize(new Dimension(100, 20));
        phoneLabel.setPreferredSize(new Dimension(100, 20));
        phonePanel.add(phoneLabel);
        phoneField = new JFormattedTextField(new Integer("88888888"));
        phoneField.setColumns(8);
        phonePanel.add(phoneField);

        //create Log in Button
        logInButton = new JButton("Check Customer");
        logInButton.addActionListener(this);

        //adds Panels
        contentPanel.add(logInPanel);
        logInPanel.add(namePanel);
        logInPanel.add(phonePanel);
        logInPanel.add(logInButton);
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public int getCustomerPhone()
    {
        return customerPhone;
    }

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource().equals(logInButton))
        {
            try{

                if(nameField.getText() != "Name" || nameField.getText() != null)
                    customerName = nameField.getText();
                else
                    throw new Exception("no name");
                if(phoneField.getText() != "Name" || phoneField.getText() != null)
                    customerPhone = Integer.parseInt(phoneField.getText());
                else
                    throw new Exception("no name");
                buttonPressed();
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public void stateChanged(ChangeEvent event)
    {

    }

    public void addChangeListener(ChangeListener changeListener) 
    {
        listenerList.add(ChangeListener.class, changeListener);
    }

    /**
     * happens when the button is pressed
     * 
     * copied and altered from http://stackoverflow.com/questions/20153868/using-changelistener-to-fire-changes-in-java-swing
     * 12/8/2016
     */
    private void buttonPressed() 
    {
        ChangeListener[] changeListeners = listenerList.getListeners(ChangeListener.class);
        if (changeListeners != null && changeListeners.length > 0)
        {
            ChangeEvent evt = new ChangeEvent(this);
            for (ChangeListener changeListener : changeListeners)
            {
                changeListener.stateChanged(evt);
            }
        }
    }
}
