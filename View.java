import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Write a description of class View here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class View extends Frame
{
    private JFrame frame;
    private Container contentPane;

    public View()
    {
        makeFrame();
    }

    private void makeFrame()
    {
        frame = new JFrame("ImageViewer");
        contentPane = frame.getContentPane();

        makeMenu();
        
        makeShowsMenu();
        
        frame.pack();
        frame.setVisible(true);
    }

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
    
    private void makeShowsMenu()
    {
        JLabel label = new JLabel("I am a label. I can display some text.");
        contentPane.add(label);
    }
}
