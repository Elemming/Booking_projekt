
/**
 * takes input form the user and controls the system and view.
 * 
 * @author Emil 
 * @version 0.1
 */
public class Controller
{
    private View view;
    private MySystem mySystem;
    private boolean done;

    /**
     * Constructor for objects of class Controller
     */
    public Controller()
    {
        view = new View();
        mySystem = new MySystem();
        run();
    }

    /**
     * Makes the system run
     */
    public void run()
    {
        done = false;
        while(!done)
        {
            
        }
    }
}
