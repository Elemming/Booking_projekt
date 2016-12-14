import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ObjektButton extends JButton
{
    private Object object;
    
    public void setObject(Object object)
    {
        this.object = object;
    }
    
    public Object getObject()
    {
        return object;
    }
}