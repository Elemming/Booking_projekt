import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShowBox implements ActionListener
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
        showPanel.setLayout(new GridLayout(1, 5));
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
        getShowID();
    }
}
