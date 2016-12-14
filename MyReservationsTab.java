import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class MyReservationsTab extends Tab implements ActionListener, ChangeListener
{
    private JPanel orderPanel, topPanel, buttonPanel;
    private JButton finishOrder;
    private String customerName;
    private String[] show;
    private int buttonChoice;
    private Reservation removeRes;
    private JScrollPane orderScroll;

    public MyReservationsTab(JFrame frame)
    {
        super(frame);
    }

    public void createTab(ArrayList<Reservation> order, String customerName)
    {
        this.customerName = customerName;
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));

        orderScroll = new JScrollPane(orderPanel);
        contentPanel.add(buttonPanel);
        contentPanel.add(orderScroll);

        createTopBox();

        for(Reservation reservation : order)
        {
            createOrderBox(reservation);
        }

        contentPanel.validate();
    }

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

    private void createOrderBox(Reservation reservation)
    {
        JPanel resPanel = new JPanel();
        resPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        resPanel.setLayout(new GridLayout(1, 6));

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
    
    //small methods

    private String[] getShow()
    {
        buttonChoice = 2;
        buttonPressed();
        return show;
    }

    public void setShow(String[] show)
    {
        this.show = show;
    }
    
    public Reservation getRemoveRes()
    {
        return removeRes;
    }

    //event Handeling

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
