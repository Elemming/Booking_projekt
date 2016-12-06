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
    private Container contentPane;

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

        contentPane = frame.getContentPane();

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

        JMenuItem showsMenu = new JMenuItem("Showings");
        menubar.add(showsMenu); 
        JMenuItem ReservationMenu = new JMenuItem("Reservations");
        menubar.add(ReservationMenu); 
        JMenuItem changeMenu = new JMenuItem("Change Reservations");
        menubar.add(changeMenu); 

        //         JMenuItem openItem = new JMenuItem("Open");
        //         fileMenu.add(openItem); 
    }

    /**
     * makes the show menu tab.
     */
    private void makeShowsMenu()
    {
        JLabel label = new JLabel("I am a label. I can display some text.");
        contentPane.add(label);
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
}
