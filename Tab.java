import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Templat of a tab that can open and change thinks in the gui.
 * 
 * @author Emil 
 * @version 1.0
 */
public abstract class Tab extends JComponent
{
    protected JFrame frame;
    protected Container contentPanel;
    
    /**
     * standart constructer that takes a JFrame and creates the variables frame and contentPanel
     * 
     * @param needs a JFrame
     */
    public Tab(JFrame frame)
    {
        this.frame = frame;
        contentPanel = frame.getContentPane();
    }
    
    /**
     * makes sure that all tabs can create a Tab, but get overwrite by most sub classes.
     */
    public void createTab()
    {
        
    }
}
