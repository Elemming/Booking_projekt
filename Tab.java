import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Tab extends JComponent
{
    protected JFrame frame;
    protected Container contentPanel;
    
    public Tab(JFrame frame)
    {
        this.frame = frame;
        contentPanel = frame.getContentPane();
    }
    
    public void createTab()
    {
        
    }
    
    public Container getContentPanel()
    {
        return contentPanel;
    }
}
