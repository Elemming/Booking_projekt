import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;

public class ReservationTab extends Tab implements ActionListener, ChangeListener
{
    private String customerName;
    private int customerPhone, customerID, row, col, buttonChoice;
    private JPanel logInPanel, theaterPanel;
    private JTextField nameField, phoneField;
    private JButton logInButton, seatButton, addButton, checkOutButton;
    private NumberFormat nf;
    private Seat[][] theater;

    public ReservationTab(JFrame frame)
    {
        super(frame);
        customerID = 0;
        createTab();
    }

    public void createTab(Seat[][] theater)
    {
        if(customerID == 0)
            createLogInTab();
        else 
        {
            this.theater = theater;
            createTheaterTab(theater);
        }

        contentPanel.validate();
    }

    public void createLogInTab()
    {
        //Ceates Panels        
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        logInPanel = new JPanel();
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

        //NumberFormat set up
        nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(8);
        nf.setMinimumIntegerDigits(8);
        //         nf.setDecimalSeparatorAlwaysShown(false);

        //creats phone Panel        
        JLabel phoneLabel = new JLabel("Phonenumber: ");
        phoneLabel.setMinimumSize(new Dimension(100, 20));
        phoneLabel.setPreferredSize(new Dimension(100, 20));
        phonePanel.add(phoneLabel);
        phoneField = new JFormattedTextField(nf);
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

    public void createTheaterTab(Seat[][] theater)
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        createTheater(theater, contentPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        addButton = new JButton("Add Reservesion");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        checkOutButton = new JButton("Check out");
        checkOutButton.addActionListener(this);
        buttonPanel.add(checkOutButton);

        contentPanel.add(buttonPanel);
    }

    public void createTheater(Seat[][] theater, Container panel)
    {
        row = -1;

        theaterPanel = new JPanel();
        theaterPanel.setLayout(new GridLayout(theater.length, getMaxLength(theater)));
        for( int i = 0; i < theater.length; i++)
        {
            int n = 0;
            for( Seat seat : theater[i])
            {
                JButton seatButton = new JButton(i + ", " + n);
                seatButton.setBackground(Color.GREEN);
                seatButton.setForeground(Color.GREEN);
                if(seat.isReserved())
                {
                    seatButton.setBackground(Color.RED);
                    seatButton.setForeground(Color.RED);
                }
                seatButton.addActionListener(this);
                theaterPanel.add(seatButton);
                n++;
            }
        }
        panel.add(theaterPanel);
    }

    private void pickSeat()
    throws Exception
    {
        if(row != -1)
        {
            theaterPanel.getComponent(row*getMaxLength(theater) + col).setBackground(Color.RED);
            theaterPanel.getComponent(row*getMaxLength(theater) + col).setForeground(Color.RED);
        }
        else
            throw new Exception("You have not picked a seat");
    }

    //return methods

    public void setCustomerID(int ID)
    {
        customerID = ID;
    }

    public int getCustomerID()
    {
        return customerID;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public int getCustomerPhone()
    {
        return customerPhone;
    }

    public int getSeatRow()
    {
        return row;
    }

    public int getSeatCol()
    {
        return col;
    }

    private int getMaxLength(Seat[][] theater)
    {
        int max = 0;
        if(theater == null)
            return 0;

        for(int i = 0; i < theater.length; i++)
        {
            if (theater[i].length > max)
                max = theater[i].length;
        }
        return max;
    }

    public int getRow(String placement)
    {
        String[] rowCol = placement.split(",");
        return Integer.parseInt(rowCol[0].trim());
    }

    private int getCol(String placement)
    {
        String[] rowCol = placement.split(",");
        return Integer.parseInt(rowCol[1].trim());
    }

    //Event stuff

    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource().equals(logInButton))
        {
            try{
                String name;
                int phone;
                if(!(nameField.getText().endsWith("Name") )|| nameField.getText() != null || nameField.getText() != "")
                    name = nameField.getText();
                else
                    throw new Exception("no name");
                if(phoneField.getText() != null || !(phoneField.getText().startsWith("0")) || phoneField.getText() != "")
                {
                    int number = nf.parse(phoneField.getText()).intValue();
                    phone = number;
                }
                else
                    throw new Exception("no phone");
                if(name != null && phone > 0)
                {
                    customerName = name;
                    customerPhone = phone;
                    buttonChoice = 1;
                    buttonPressed();
                }
            }
            catch(Exception e)
            {
                JLabel errorLabel = new JLabel( "error: " + e.getMessage());
                if(logInPanel.getComponentCount() < 4)
                {
                    logInPanel.add(errorLabel);
                    logInPanel.validate();
                }
            }
        }
        else if(event.getSource().equals(addButton))
        {
            try
            {
                pickSeat();
                buttonChoice = 2;
                buttonPressed();
            } catch(Exception e)
            {
                JLabel mistakLabe = new JLabel(e.getMessage());
                contentPanel.add(mistakLabe);
            }
        }
        else if(event.getSource().equals(checkOutButton))
        {
            try
            {
                pickSeat();
                buttonChoice = 3;
                buttonPressed();
            } catch(Exception e)
            {
                JLabel mistakLabe = new JLabel(e.getMessage());
                contentPanel.add(mistakLabe);
            }
        }
        else if(event.getSource() instanceof JButton)
        {
            this.seatButton = (JButton)event.getSource();
            if(this.seatButton.getBackground() != Color.RED)
            {
                if(!(row == -1))
                {
                    if(theaterPanel.getComponent(row*getMaxLength(theater) + col).getBackground() != Color.RED)
                    {
                        theaterPanel.getComponent(row*getMaxLength(theater) + col).setBackground(new Color(0, 255, 0));
                        theaterPanel.getComponent(row*getMaxLength(theater) + col).setForeground(new Color(0, 255, 0));
                    }
                }
                if(this.seatButton.getBackground() != Color.RED)
                {
                    this.seatButton.setBackground(Color.MAGENTA);
                    this.seatButton.setForeground(Color.MAGENTA);
                    contentPanel.validate();
                    row = getRow(this.seatButton.getText());
                    col = getCol(this.seatButton.getText());
                }
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

    public int getButtonChoice()
    {
        return buttonChoice;
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
