import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShowsTab extends Tab
{
    public ShowsTab(Container panel)
    {
        super(panel);
    }
    
    public void createTab(String[][] shows)
    {
        super.getContentPanel().setLayout(new BoxLayout(super.getContentPanel(), BoxLayout.Y_AXIS));
        
        for(String[] show : shows)
        {
            makeShow(show[1], show[2], show[3], show[4]);
        }
        
        super.getContentPanel().validate();
    }
    
    private void makeShow(String showFilm, String showTheater, String showDate, String showTime)
    {
        Panel showPanel = new Panel();
        super.getContentPanel().add(showPanel);
        showPanel.setLayout(new GridLayout(1, 4));
        JLabel film = new JLabel("Title: " + showFilm + ".");
        showPanel.add(film);
        JLabel date = new JLabel("Date: " + showDate + ".");
        showPanel.add(date);
        JLabel theater = new JLabel("Theater: " + showTheater);
        showPanel.add(theater);
        JLabel time = new JLabel("Time: " + showTime);
        showPanel.add(time);
    }
}
