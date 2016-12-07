import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyReservationsTab extends Tab
{
    public MyReservationsTab(Container panel)
    {
        super(panel);
    }

    public void createTab()
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }
}
