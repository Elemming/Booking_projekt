/**
 * Creates a reservation for a particular seat in a particular showing.
 */
public class Reservation
{
    private int ShowID;
    private Seat seat;

    /**
     * Notably, the SHOWID is supposed to be a valid SHOWID from the database.
     * @param  SHOWID   a valid SHOWID from the database
     * @param  seat     A Seat in a Theater
     */
    Reservation(int SHOWID, Seat seat)
    {
        this.ShowID = ShowID;
        this.seat = seat;
        seat.reserveSeat();
    }
    
    public Seat getSeat()
    {
        return seat;
    }
    
    public int getShowID()
    {
        return ShowID;
    }

    /** 
     * Gets information about the showing the reservation is for through the database.
     * (Should be the info the fits with the particular SHOWID used in the constructor somehow.)
     */
    public String getShowingInfo()
    {
        return "MOVIE TITLE" + ": " + "DATE" + " at " + "TIME" + " in " + "THEATER X";
    }

    public void show()
    {

    }
}