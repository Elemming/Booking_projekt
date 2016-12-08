import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ShowsTab extends Tab implements ChangeListener 
{
    private String[][] shows;
    
    public ShowsTab(Container panel)
    {
        super(panel);
    }
    
    public void createTab(String[][] shows)
    {
        this.shows = shows;
        
        super.getContentPanel().setLayout(new BoxLayout(super.getContentPanel(), BoxLayout.Y_AXIS));
        
        createTopBox();
        
        for(String[] show : shows)
        {
            makeShow(show);
        }
        
        super.getContentPanel().validate();
    }
    
    private void makeShow(String[] show)
    {
        ShowBox box = new ShowBox(super.getContentPanel(), show);
        box.addChangeListener(this);
    }
    
    private void createTopBox()
    {
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
    }
    
    public void stateChanged(ChangeEvent event)
    {
        buttonPressed();
    }

    public void addChangeListener(ChangeListener changeListener) 
    {
        listenerList.add(ChangeListener.class, changeListener);
    }

    /**
     * happens when the button is pressed
     * 
     * copied and altered from http://stackoverflow.com/questions/20153868/using-changelistener-to-fire-changes-in-java-swing
     * 12/8/2016
     */
    private void buttonPressed() 
    {
        ChangeListener[] changeListeners = listenerList.getListeners(ChangeListener.class);
        if (changeListeners != null && changeListeners.length > 0)
        {
            ChangeEvent evt = new ChangeEvent(this);
            for (ChangeListener changeListener : changeListeners)
            {
                changeListener.stateChanged(evt);
            }
        }
    }
}
