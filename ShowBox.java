import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * a class that makes a panel with a show
 */
public class ShowBox extends JComponent implements ActionListener
{
    private JPanel showsPanel;
    private String[] show;
    private String showFilm, showDate, showTheater, showTime;

    /**
     * makes a panel in the given JPanel (sets up fields)
     * 
     * @param a JPanel to put the new panel
     *        a String array that desribes a show
     */
    public ShowBox(JPanel panel, String[] show)
    {
        showsPanel = panel;
        this.show = show;
        showFilm = show[1];
        showTheater = show[2];
        showDate = show[3];
        showTime = show[4];
        addShowBox();
    }

    /**
     * gets the panels show ID
     * 
     * @return show id
     */
    public int getShowID()
    {
        return Integer.parseInt(show[0]);
    }

    /**
     * makes a panel in the given JPanel
     */
    private void addShowBox()
    {
        JPanel showPanel = new JPanel();
        showPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        showPanel.setLayout(new GridLayout(1, show.length));
        showsPanel.add(showPanel);
        JLabel film = new JLabel(showFilm);
        showPanel.add(film);
        JLabel theater = new JLabel(showTheater);
        showPanel.add(theater);
        JLabel date = new JLabel(showDate);
        showPanel.add(date);
        JLabel time = new JLabel(showTime);
        showPanel.add(time);
        Button button = new Button("Reserve");
        button.addActionListener(this);
        showPanel.add(button);
    }

    /**
     * happens when its button is clicked
     */
    public void actionPerformed(ActionEvent event)
    {
        buttonPressed();
    }

    /**
     * makes it possible to detckt changes in the panel
     */
    public void addChangeListener(ChangeListener listener) 
    {
        listenerList.add(ChangeListener.class, listener);
    }

    /**
     * happens when the button is pressed
     * 
     * copied and altered from http://stackoverflow.com/questions/20153868/using-changelistener-to-fire-changes-in-java-swing
     * 12/8/2016
     */
    private void buttonPressed() 
    {
        ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);
        if (listeners != null && listeners.length > 0)
        {
            ChangeEvent evt = new ChangeEvent(this);
            for (ChangeListener listener : listeners)
            {
                listener.stateChanged(evt);
            }
        }
    }
}
