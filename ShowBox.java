import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ShowBox extends JComponent implements ActionListener
{
    private Container contentPanel;
    private String[] show;
    private String showFilm, showDate, showTheater, showTime;

    public ShowBox(Container panel, String[] show)
    {
        contentPanel = panel;
        this.show = show;
        showFilm = show[1];
        showTheater = show[2];
        showDate = show[3];
        showTime = show[4];
        addShowBox();
    }

    public String getShowID()
    {
        return show[0];
    }

    private void addShowBox()
    {
        Panel showPanel = new Panel();
        contentPanel.add(showPanel);
        showPanel.setLayout(new GridLayout(1, show.length));
        JLabel film = new JLabel(showFilm);
        showPanel.add(film);
        JLabel theater = new JLabel(showTheater);
        showPanel.add(theater);
        JLabel date = new JLabel(showDate);
        showPanel.add(date);
        JLabel time = new JLabel(showTime);
        showPanel.add(time);
        Button button = new Button("Reseve");
        button.addActionListener(this);
        showPanel.add(button);
    }

    public void actionPerformed(ActionEvent event)
    {
        buttonPressed();
    }

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
