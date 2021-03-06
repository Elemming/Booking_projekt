import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

/**
 * The order tab.
 * Crates a List of the prepared reservations and exsiting reservations if the are some.
 * 
 * @author Emil 
 * @version 1.0
 */
public class MyReservationsTab extends Tab implements ActionListener, ChangeListener
{
    private JPanel orderPanel,exOrderPanel, topPanel, topExPanel, buttonPanel;
    private JButton finishOrder;
    private String customerName;
    private String[] show;
    private int buttonChoice;
    private Reservation removeRes;
    private JScrollPane orderScroll, exOrderScroll;

    /**
     * Initializes the MyReservationsTab by calling the super constructer.
     * 
     * @param needs a JFrame for the super.
     */
    public MyReservationsTab(JFrame frame)
    {
        super(frame);
    }

    /**
     * Creates the standart tab with all the pending reservations to the given customer.
     * 
     * @param   An Arraylist of reservations
     *          the customers name
     */
    public void createTab(ArrayList<Reservation> order, String customerName)
    {
        this.customerName = customerName;
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

        orderScroll = new JScrollPane(orderPanel);
        orderScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        orderScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        orderScroll.setMinimumSize(new Dimension(500, 500));
        orderScroll.setPreferredSize(new Dimension(500, 500));
        contentPanel.add(buttonPanel);
        contentPanel.add(orderScroll);

        createTopBox();

        for(Reservation reservation : order)
        {
            createOrderBox(reservation);
        }

        contentPanel.validate();
    }
    
    /**
     * Calls the standart tab creations and adds a scroll panel with the exsisting order.
     * 
     * @param   An Arraylist of reservations
     *          the customers name
     *          An Arraylist of exsisting orders
     */
    public void createTab(ArrayList<Reservation> order, String customerName, ArrayList<Reservation> exOrder)
    {
        createTab(order, customerName);
        
        exOrderPanel = new JPanel();
        exOrderPanel.setLayout(new BoxLayout(exOrderPanel, BoxLayout.Y_AXIS));
        
        
        exOrderScroll = new JScrollPane(exOrderPanel);
        exOrderScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        exOrderScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contentPanel.add(exOrderScroll);
        
        JLabel name = new JLabel("Reservations of: " + customerName);
        exOrderPanel.add(name);
        
        topExPanel = new JPanel();
        topExPanel.setLayout(new GridLayout(1, 6));

        JLabel film = new JLabel("Moive");
        topExPanel.add(film);
        JLabel theater = new JLabel("Theater");
        topExPanel.add(theater);
        JLabel date = new JLabel("Date");
        topExPanel.add(date);
        JLabel time = new JLabel("Time");
        topExPanel.add(time);
        JLabel seat = new JLabel("Seat");
        topExPanel.add(seat);

        JLabel button = new JLabel("Unreserve Button");
        topExPanel.add(button);

        exOrderPanel.add(topExPanel);

        for(Reservation reservation : exOrder)
        {
            createExOrderBox(reservation);
        }
    }

    /**
     * makes the top of the tab.
     */
    private void createTopBox()
    {
        //Creats the overview and buttons

        JLabel name = new JLabel("Customer: " + customerName);
        buttonPanel.add(name);
        finishOrder = new JButton("Reserve");
        finishOrder.addActionListener(this); 
        buttonPanel.add(finishOrder);

        //top part of the reservations

        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 6));

        JLabel film = new JLabel("Moive");
        topPanel.add(film);
        JLabel theater = new JLabel("Theater");
        topPanel.add(theater);
        JLabel date = new JLabel("Date");
        topPanel.add(date);
        JLabel time = new JLabel("Time");
        topPanel.add(time);
        JLabel seat = new JLabel("Seat");
        topPanel.add(seat);

        JLabel button = new JLabel("Unreserve Button");
        topPanel.add(button);

        orderPanel.add(topPanel);
    }

    /**
     * makes the scroll part of the current reservation box
     */
    private void createOrderBox(Reservation reservation)
    {
        JPanel resPanel = new JPanel();
        resPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resPanel.setLayout(new GridLayout(1, 6));

        buttonChoice = 2;
        show = getShow();

        JLabel film = new JLabel(show[0]);
        resPanel.add(film);
        JLabel theater = new JLabel(show[1]);
        resPanel.add(theater);
        JLabel date = new JLabel(show[2]);
        resPanel.add(date);
        JLabel time = new JLabel(show[3]);
        resPanel.add(time);
        JLabel seat = new JLabel(reservation.getSeatPlacement());
        resPanel.add(seat);

        ObjectButton button = new ObjectButton();
        button.setName("Unreserve Button");
        button.setObject(reservation);
        button.addActionListener(this);
        resPanel.add(button);

        orderPanel.add(resPanel);
    }
    
    /**
     * creates the panel with the scroll panel of exsisting Reservations
     */
    private void createExOrderBox(Reservation reservation)
    {
        JPanel resPanel = new JPanel();
        resPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resPanel.setLayout(new GridLayout(1, 6));

        buttonChoice = 4;
        show = getShow();

        JLabel film = new JLabel(show[0]);
        resPanel.add(film);
        JLabel theater = new JLabel(show[1]);
        resPanel.add(theater);
        JLabel date = new JLabel(show[2]);
        resPanel.add(date);
        JLabel time = new JLabel(show[3]);
        resPanel.add(time);
        JLabel seat = new JLabel(reservation.getSeatPlacement());
        resPanel.add(seat);

        ObjectButton button = new ObjectButton();
        button.setName("Unreserve Button");
        button.setObject(reservation);
        button.addActionListener(this);
        resPanel.add(button);

        exOrderPanel.add(resPanel);
    }
    
    //small methods

    /**
     * gets the show info as a string array
     * 
     * @return  show info as string[]
     */
    private String[] getShow()
    {
        buttonPressed();
        return show;
    }

    /**
     * set the show array field to the given show array.
     */
    public void setShow(String[] show)
    {
        this.show = show;
    }
    
    /**
     * gets the Reservation that is about to be removed.
     * 
     * @return  Reservation taget for removel
     */
    public Reservation getRemoveRes()
    {
        return removeRes;
    }

    //event Handeling

    /**
     * makes buttons do stuff depending on their name
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource().equals(finishOrder))
        {
            buttonChoice = 1;
            buttonPressed();
        }
        if(event.getSource() instanceof ObjectButton)
        {
            ObjectButton button = (ObjectButton)event.getSource();
            removeRes = (Reservation)button.getObject();
            buttonChoice = 3;
            buttonPressed();
        }
    }

    /**
     * is just here to implement changelistener
     */
    public void stateChanged(ChangeEvent event)
    {

    }

    /**
     * does so other classes can detect changes in this class
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
