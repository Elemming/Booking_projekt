/**
 * Creates a reservation for a particular seat in a particular showing.
 */
public class Reservation
{
    private int ShowID;
    private Seat seat;

    /**
     * Notably, the ShowID is supposed to be a valid ShowID from the database.
     * @param  ShowID   a valid ShowID from the database
     * @param  seat     A Seat in a Theater
     */
    Reservation(int ShowID, Seat seat)
    {
        this.ShowID = ShowID;
        this.seat = seat;
        seat.reserveSeat();
    }
    
    /**
     * Gets the Reservation's Seat.
     */
    public Seat getSeat()
    {
        return seat;
    }
    
    public String getSeatPlacement()
    {
        return "Row: " + seat.getRownumber() + "\n" + "Number: " + seat.getSeatnumber();
    }
    
    /**
     * Gets the Reservation's ShowID.
     */
    public int getShowID()
    {
        return ShowID;
    }

    /** 
     * Gets information about the showing the reservation is for through the database.
     * ´Needed?
     */
    public String getShowingInfo()
    {
        return "MOVIE TITLE" + ": " + "DATE" + " at " + "TIME" + " in " + "THEATER X";
    }

    /**
     * Needed?
     */
    public void show()
    {

    }
}