import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * The show tab.
 * Crates a List of the futur shows.
 * 
 * @author Emil 
 * @version 1.0
 */
public class ShowsTab extends Tab implements ChangeListener 
{
    private String[][] shows;
    private ShowBox clickedBox;
    private JPanel showPanel, showsPanel;
    private JScrollPane showsScrollPanel;

    /**
     * Initializes the ShowsTab by calling the super constructer.
     * 
     * @param needs a JFrame for the super.
     */
    public ShowsTab(JFrame frame)
    {
        super(frame);
    }

    /**
     * Creats a tab with the given shows in a table with a scroll bar.
     * 
     * @param an Array of String Arrays that follow
     *        0: show ID
     *        1: Film
     *        2: Theater
     *        3: Date
     *        4: Time
     */
    public void createTab(String[][] shows)
    {
        this.shows = shows;

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        showPanel = new JPanel();
        contentPanel.add(showPanel);
        showPanel.setLayout(new GridLayout(1, 5));
        createTopBox();

        
        showsPanel = new JPanel();
        showsPanel.setLayout(new BoxLayout(showsPanel, BoxLayout.Y_AXIS));
        showsScrollPanel = new JScrollPane(showsPanel);
        showsScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        showsScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        for(String[] show : shows)
        {
            makeShow(show);
        }
        contentPanel.add(showsScrollPanel);

        contentPanel.validate();
    }

    /**
     * makes a single show and adds it to the showPanel
     * 
     * @param a String Array that follow
     *        0: show ID
     *        1: Film
     *        2: Theater
     *        3: Date
     *        4: Time
     */
    private void makeShow(String[] show)
    {
        ShowBox box = new ShowBox(showsPanel, show);
        box.addChangeListener(this);
    }

    /**
     * creats the top panel in the tab.
     */
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

    /**
     * returns the show ID of the show from the cilckedBox.
     * 
     * @return show ID
     */
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

    /**
     * what happens when there is a change in a ShowBox
     */
    public void stateChanged(ChangeEvent event)
    {
        clickedBox = (ShowBox)event.getSource();
        buttonPressed();
    }

    /**
     * makes it possible to detect changes in this class
     */
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
