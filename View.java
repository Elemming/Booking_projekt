import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Makes a Frame with menu at top.
 * 
 * @author Emil 
 * @version 0.1
 */
public class View extends Frame implements ActionListener
{
    private JFrame frame;
    private Container contentPanel;
    private ShowsTab showsTab; 

    /**
     * A new View.
     */
    public View()
    {
        makeFrame();
    }

    /**
     * Makes the GUI. 
     */
    private void makeFrame()
    {
        frame = new JFrame("ImageViewer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(820,640);

        contentPanel = frame.getContentPane();

        makeMenu();

        showsTab = new ShowsTab(contentPanel);
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
        
        JMenuItem myReservationsMenu = new JMenuItem("Change Reservations");
        myReservationsMenu.addActionListener(this);
        menubar.add(myReservationsMenu); 

        //         JMenuItem openItem = new JMenuItem("Open");
        //         fileMenu.add(openItem); 
    }

    /**
     * makes the show menu tab.
     */
    private void makeShowsMenu()
    {
        
        contentPanel.removeAll();
        showsTab.createTab();
        
//         contentPanel.removeAll();
//         contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//         
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         makeShow("Star Wars", "2016-12-14 19:00", "A");
//         
//         contentPanel.validate();
    }

//     /**
//      * makes the shows in the show tab.
//      */
//     private void makeShow(String showName, String showTime, String showPlace)
//     {
//         Panel showPanel = new Panel();
//         contentPanel.add(showPanel);
//         showPanel.setLayout(new GridLayout(1, 0));
//         JLabel show = new JLabel("Title: " + showName + ".");
//         showPanel.add(show);
//         JLabel time = new JLabel("Time: " + showTime);
//         showPanel.add(time);
//         JLabel place = new JLabel("Theater: " + showPlace);
//         showPanel.add(place);
//     }

    /**
     * makes the reservation menu tab.
     */
    private void makeReservationMenu()
    {
        contentPanel.removeAll();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }

    /**
     * makes the myreservation menu tab.
     */
    private void makeMyReservationsMenu()
    {
        contentPanel.removeAll();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
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

    public void actionPerformed(ActionEvent event)
    {
        if(event.getActionCommand().equals("Showings"))
            makeShowsMenu();
        if(event.getActionCommand().equals("Reservations"))
            makeReservationMenu();
        if(event.getActionCommand().equals("Change Reservations"))
            makeMyReservationsMenu();
    }
}
