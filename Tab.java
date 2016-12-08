import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class Tab extends JComponent
{
    public Container contentPanel;
    
    public Tab(Container panel)
    {
        contentPanel = panel;
    }
    
    public void createTab()
    {
        
    }
    
    public Container getContentPanel()
    {
        return contentPanel;
    }
}
