import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ShowsTab extends Tab implements ChangeListener 
{
    private String[][] shows;
    private ShowBox clickedBox;
    private Panel showPanel;

    public ShowsTab(JFrame frame)
    {
        super(frame);
    }

    public void createTab(String[][] shows)
    {
        this.shows = shows;

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        showPanel = new Panel();
        contentPanel.add(showPanel);
        showPanel.setLayout(new GridLayout(1, 5));
        createTopBox();

        for(String[] show : shows)
        {
            makeShow(show);
        }

        contentPanel.validate();
    }

    private void makeShow(String[] show)
    {
        ShowBox box = new ShowBox(super.getContentPanel(), show);
        box.addChangeListener(this);
    }

    private void createTopBox()
    {
        JLabel film = new JLabel("Movie");
        showPanel.add(film);
        JLabel theater = new JLabel("Theater");
        showPanel.add(theater);
        JLabel date = new JLabel("Date");
        showPanel.add(date);
        JLabel time = new JLabel("Time");
        showPanel.add(time);
        JLabel button = new JLabel("Reserve Button");
        showPanel.add(button);
    }

    public int getShowID()
    {
        try
        {
            return clickedBox.getShowID();
        } catch(Exception e)
        {
            return 0;
        }
    }

    public void stateChanged(ChangeEvent event)
    {
        clickedBox = (ShowBox)event.getSource();
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
