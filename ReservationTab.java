import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ReservationTab extends Tab
{
    public ReservationTab(Container panel)
    {
        super(panel);
    }

    public void createTab()
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }
}
