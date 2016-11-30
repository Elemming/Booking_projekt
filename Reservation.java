/**
 * Creates a reservation for a particular seat in a particular row in a particular showing.
 */
public class Reservation
{
    private String SHOWID;
    private int row;
    private int seat;

    /**
     * Notably, the SHOWID is supposed to be a valid SHOWID from the database.
     */
    Reservation(String SHOWID, int row, int seat)
    {
        this.SHOWID = SHOWID;
        this.row = row;
        this.seat = seat;
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