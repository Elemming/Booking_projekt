import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class MyReservationsTab extends Tab implements ActionListener
{
    private JPanel orderPanel, topPanel, buttonPanel;
    private JButton finishOrder;
    private String customerName;

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
        
        contentPanel.add(buttonPanel);
        contentPanel.add(orderPanel);
        
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
        resPanel.setLayout(new GridLayout(1, 6));

        JLabel film = new JLabel("Moive");
        resPanel.add(film);
        JLabel theater = new JLabel("Theater");
        resPanel.add(theater);
        JLabel date = new JLabel("Date");
        resPanel.add(date);
        JLabel time = new JLabel("Time");
        resPanel.add(time);
        JLabel seat = new JLabel("Seat");
        resPanel.add(seat);

        JButton button = new JButton("Unreserve Button");
        button.addActionListener(this);
        resPanel.add(button);
    }
    
    //event Handeling
    
    public void actionPerformed(ActionEvent event)
    {
        
    }
}
