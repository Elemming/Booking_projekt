
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
        mySystem = new MySystem();
        view = new View(mySystem);
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
            if(view.isClosed())
                done = true;
        }
        mySystem.closeConnection();
    }
}
