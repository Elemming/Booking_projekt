import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Makes a Frame with menu at top.
 * 
 * @author Emil 
 * @version 0.1
 */
public class View extends Frame
{
    private JFrame frame;
    private Container contentPanel;

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

        JMenuItem showsMenu = new JMenuItem("Shows");
        menubar.add(showsMenu); 
        JMenuItem ReservationMenu = new JMenuItem("Reserations");
        menubar.add(ReservationMenu); 
        JMenuItem changeMenu = new JMenuItem("Change Resevertioner");
        menubar.add(changeMenu); 

        //         JMenuItem openItem = new JMenuItem("Open");
        //         fileMenu.add(openItem); 
    }

    /**
     * makes the show menu tab.
     */
    private void makeShowsMenu()
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        makeShow("Star Wars", "2016-12-14 19:00");
        makeShow("Star Wars", "2016-12-14 19:00");
        makeShow("Star Wars", "2016-12-14 19:00");
        makeShow("Star Wars", "2016-12-14 19:00");
        makeShow("Star Wars", "2016-12-14 19:00");
    }
    
    private void makeShow(String showName, String showTime)
    {
        Panel showPanel = new Panel();
        contentPanel.add(showPanel);
        showPanel.setLayout(new GridLayout(1, 0));
        JLabel show = new JLabel("Title: " + showName + ".");
        showPanel.add(show);
        JLabel time = new JLabel("Time: " + showTime);
        showPanel.add(time);
    }

    /**
     * Returns if the vindow has been closed.
     */
    public boolean isClosed()
    {
        if(frame.isDisplayable())
            return false;
        else 
            return true;
    }
}
