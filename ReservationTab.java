import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.*;

/**
 * The Resevation tab.
 * Creates a log in tab or a theater tab for reservtion.
 * 
 * @author Emil 
 * @version 1.0
 */
public class ReservationTab extends Tab implements ActionListener, ChangeListener
{
    private String customerName;
    private int customerPhone, customerID, row, col, buttonChoice;
    private JPanel logInPanel, theaterPanel;
    private JTextField nameField, phoneField;
    private JButton logInButton, seatButton, addButton, checkOutButton;
    private NumberFormat nf;
    private Seat[][] theater;

    /**
     * Initializes the Resevation tab by calling the super constructer.
     * 
     * @param needs a JFrame for the super.
     */
    public ReservationTab(JFrame frame)
    {
        super(frame);
        customerID = 0;
        createTab();
    }

    /**
     * decides if the system needs a user or it can go strait to the theater tab and then creats the needed tab.
     * 
     * @param   needs a double Array of Seats (if not yet eksisting can do with a new double array of empty seats)
     *          a show name (if not eksisting can do with a empty String)
     */
    public void createTab(Seat[][] theater, String showName)
    {
        if(customerID == 0)
            createLogInTab();
        else 
        {
            this.theater = theater;
            createTheaterTab(theater, showName);
        }

        contentPanel.validate();
    }

    /**
     * Creats the log in paneland adds it to the main panel.
     */
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

    /**
     * creates the theater to the given show.
     * 
     * @param   double array of seats
     *          name of the show
     */
    public void createTheaterTab(Seat[][] theater, String showName)
    {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        infoPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel showTitle = new JLabel(showName);
        infoPanel.add(showTitle);

        contentPanel.add(infoPanel);

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

    /**
     * creates the theater panel to the theater tab.
     */
    private void createTheater(Seat[][] theater, Container panel)
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

    /**
     * picks the seat then clicked
     */
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

    /**
     * sets custemer ID to given ID
     * 
     * @param   int customer ID
     */
    public void setCustomerID(int ID)
    {
        customerID = ID;
    }

    /**
     * returns the customers ID
     * 
     * @return  int customer ID
     */
    public int getCustomerID()
    {
        return customerID;
    }

    /**
     * returns the customers name
     * 
     * @return  String customer name
     */
    public String getCustomerName()
    {
        return customerName;
    }

    /**
     * returns the customers phone number an eigth digit
     * 
     * @return  int customer phone number
     */
    public int getCustomerPhone()
    {
        return customerPhone;
    }

    /**
     * returns the seats row
     * 
     * @return  int row
     */
    public int getSeatRow()
    {
        return row;
    }

    /**
     * returns the seats colum
     * 
     * @return  int colum
     */
    public int getSeatCol()
    {
        return col;
    }

    /**
     * get the longest seat row in the given theater
     * 
     * @param   double array of seats
     * @return  int length of the longest row
     */
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

    /**
     * get the first int # from a string like: #, #
     * 
     * @param   String in the form: #, #
     * @return  int seat row
     */
    public int getRow(String placement)
    {
        String[] rowCol = placement.split(",");
        return Integer.parseInt(rowCol[0].trim());
    }

    /**
     * get the last int # from a string like: #, #
     * 
     * @param   String in the form: #, #
     * @return  int seat colum
     */
    private int getCol(String placement)
    {
        String[] rowCol = placement.split(",");
        return Integer.parseInt(rowCol[1].trim());
    }

    /**
     * logs the Current customer out
     */
    public void logOut()
    {
        customerName = null;
        customerPhone = 0;
        customerID = 0;
    }

    //Event stuff

    /**
     * makes stuff hapen then butons pressed.
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource().equals(logInButton))
        {
            try{
                String name;
                int phone;
                if(!((nameField.getText().endsWith("Name") )|| nameField.getText() == null || nameField.getText() == ""))
                    name = nameField.getText();
                else
                    throw new Exception("no name");
                if(!(phoneField.getText() == null || phoneField.getText().startsWith("0") || phoneField.getText() == ""))
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

    /**
     * imported for the changelistener
     */
    public void stateChanged(ChangeEvent event)
    {

    }

    /**
     * so other classes can detckt changes in this object.
     */
    public void addChangeListener(ChangeListener changeListener) 
    {
        listenerList.add(ChangeListener.class, changeListener);
    }

    /**
     * returns the chosen integer that will decide what buttons do in other classes
     * 
     * @return int reprsenting the button of choice
     */
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
