/**
 * Creates a reservation for a particular seat in a particular row in a particular showing.
 */
public class Reservation
{
    private String SHOWID;
    private Seat seat;

    /**
     * Notably, the SHOWID is supposed to be a valid SHOWID from the database.
     */
    Reservation(String SHOWID, Seat seat)
    {
        this.SHOWID = SHOWID;
        this.seat = seat;
        seat.reserveSeat();
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