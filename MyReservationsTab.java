import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyReservationsTab extends Tab
{
    public MyReservationsTab(JFrame frame)
    {
        super(frame);
    }

    public void createTab()
    {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    }
}
