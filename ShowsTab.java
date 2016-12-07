import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShowsTab extends Tab
{
    public ShowsTab(Container panel)
    {
        super(panel);
    }
    
    public void createTab()
    {
        super.getContentPanel().setLayout(new BoxLayout(super.getContentPanel(), BoxLayout.Y_AXIS));
        
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        makeShow("Star Wars", "2016-12-14 19:00", "A");
        
        super.getContentPanel().validate();
    }
    
    private void makeShow(String showName, String showTime, String showPlace)
    {
        Panel showPanel = new Panel();
        super.getContentPanel().add(showPanel);
        showPanel.setLayout(new GridLayout(1, 0));
        JLabel show = new JLabel("Title: " + showName + ".");
        showPanel.add(show);
        JLabel time = new JLabel("Time: " + showTime);
        showPanel.add(time);
        JLabel place = new JLabel("Theater: " + showPlace);
        showPanel.add(place);
    }
}
