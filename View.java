import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Makes a Frame with menu at top.
 * 
 * @author Emil 
 * @version 0.1
 */
public class View extends Frame implements ActionListener, ChangeListener
{
    private MySystem mySystem;
    private JFrame frame;
    private Container contentPanel;
    private ShowsTab showsTab; 
    private ReservationTab reservationTab;
    private MyReservationsTab myReservationsTab;
    private int customerID;

    /**
     * A new View.
     */
    public View(MySystem mySystem)
    {
        this.mySystem = mySystem;
        makeFrame();
    }

    /**
     * Makes the GUI. 
     */
    private void makeFrame()
    {
        frame = new JFrame("Booking System");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(820,640);

        contentPanel = frame.getContentPane();

        makeMenu();

        showsTab = new ShowsTab(frame);
        reservationTab = new ReservationTab(frame);
        myReservationsTab = new MyReservationsTab(frame);
        makeShowsMenu();

        frame.setVisible(true);
    }

    /**
     * Makes the menu bar.
     */
    private void makeMenu()
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar); 

        JMenuItem showsMenu = new JMenuItem("Showings");
        showsMenu.addActionListener(this);
        menubar.add(showsMenu); 

        JMenuItem reservationMenu = new JMenuItem("Reservations");
        reservationMenu.addActionListener(this);
        menubar.add(reservationMenu); 

        JMenuItem myReservationsMenu = new JMenuItem("Order");
        myReservationsMenu.addActionListener(this);
        menubar.add(myReservationsMenu); 
    }

    /**
     * makes the show menu tab.
     */
    private void makeShowsMenu()
    {
        contentPanel.removeAll();
        showsTab.createTab(mySystem.getRelevantShows());
        showsTab.addChangeListener(this);
    }

    /**
     * makes the reservation menu tab.
     */
    private void makeReservationMenu()
    {
        contentPanel.removeAll();
        if(reservationTab.getCustomerID()!=0)
        {
            if(showsTab.getShowID() != 0)
            {
                reservationTab.createTab(mySystem.getTheater());
            }
            else
                makeShowsMenu();
        }
        else
            reservationTab.createTab(new Seat[0][0]);
        reservationTab.addChangeListener(this);
    }

    /**
     * makes the myreservation menu tab.
     */
    private void makeMyReservationsMenu()
    {
        contentPanel.removeAll();
        myReservationsTab.createTab(mySystem.getOrderlist());
    }

    /**
     * Returns if the window has been closed.
     */
    public boolean isClosed()
    {
        if(frame.isDisplayable())
            return false;
        else 
            return true;
    }

    /**
     * Makes the buttons work.
     */
    public void actionPerformed(ActionEvent event)
    {
        if(event.getActionCommand().equals("Showings"))
            makeShowsMenu();
        if(event.getActionCommand().equals("Reservations"))
            makeReservationMenu();
        if(event.getActionCommand().equals("Order"))
        {
            if(customerID >  0)
                makeMyReservationsMenu();
            else
                makeReservationMenu();
        }
    }

    public void stateChanged(ChangeEvent event)
    {
        if(event.getSource() instanceof ShowsTab)
        {
            mySystem.createTheater(showsTab.getShowID());
            makeReservationMenu();
        }
        if(event.getSource() instanceof ReservationTab)
        {
            switch (reservationTab.getButtonChoice())
            {
                case 1: 
                contentPanel.removeAll();
                customerID = mySystem.getCustomerID(reservationTab.getCustomerName(), reservationTab.getCustomerPhone());
                reservationTab.setCustomerID(customerID);
                reservationTab.createTab();
                break;

                case 2: 
                if(mySystem.getOrder() == null)
                    mySystem.createOrder(reservationTab.getCustomerName(), reservationTab.getCustomerPhone());
                mySystem.addReservation(showsTab.getShowID(), reservationTab.getSeatRow(), reservationTab.getSeatCol());
                break;

                case 3:
                if(mySystem.getOrder() == null)
                    mySystem.createOrder(reservationTab.getCustomerName(), reservationTab.getCustomerPhone());
                mySystem.addReservation(showsTab.getShowID(), reservationTab.getSeatRow(), reservationTab.getSeatCol());
                makeMyReservationsMenu();
                break;
            }
            contentPanel.validate();
        }
    }
}
