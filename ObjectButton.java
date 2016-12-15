import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * a button that can hold an object.
 */
public class ObjectButton extends JButton
{
    private Object object;
    
    /**
     * gives the button an object.
     * 
     * @param the object the button shold hold
     */
    public void setObject(Object object)
    {
        this.object = object;
    }
    
    /**
     * gets the object
     * 
     * @return object
     */
    public Object getObject()
    {
        return object;
    }
}