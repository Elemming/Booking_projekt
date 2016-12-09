import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ReservationTab extends Tab
{
    private String customerName, customerPhone;
    
    public ReservationTab(Container panel)
    {
        super(panel);
    }

    public void createTab()
    {
        createLogInTab();
        
    }
    
    public void createLogInTab()
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }
}
