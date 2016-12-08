import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShowsTab extends Tab
{
    private String[][] shows;
    private String[] show;
    
    public ShowsTab(Container panel)
    {
        super(panel);
    }
    
    public void createTab(String[][] shows)
    {
        this.shows = shows;
        
        super.getContentPanel().setLayout(new BoxLayout(super.getContentPanel(), BoxLayout.Y_AXIS));
        
        Panel showPanel = new Panel();
        super.getContentPanel().add(showPanel);
        showPanel.setLayout(new GridLayout(1, 5));
        JLabel film = new JLabel("Movie");
        showPanel.add(film);
        JLabel theater = new JLabel("Theater");
        showPanel.add(theater);
        JLabel date = new JLabel("Date");
        showPanel.add(date);
        JLabel time = new JLabel("Time");
        showPanel.add(time);
        JLabel button = new JLabel("Reseve Button");
        showPanel.add(button);
        
        for(String[] show : shows)
        {
            makeShow(show);
        }
        
        super.getContentPanel().validate();
    }
    
    private void makeShow(String[] show)
    {
        new ShowBox(super.getContentPanel(), show);
    }
}
