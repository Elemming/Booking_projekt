/**
 * Creates a Seat which has a seatnumber, a rownumber, and which knows whether it is reserved.
 */
public class Seat
{
    private int seatnumber;
    private int rownumber;
    private boolean reserved;
    
    /**
     * Creates a Seat.
     */
    public Seat(int seatnumber, int rownumber)
    {
        this.seatnumber = seatnumber;
        this.rownumber = rownumber;
        reserved = false;
    }
    
    /**
     * Gets the Seat's seatnumber.
     */
    public int getSeatnumber()
    {
        return seatnumber;
    }
    
    /**
     * Gets the Seat's rownumber.
     */
    public int getRownumber()
    {
        return rownumber;
    }
    
    /**
     * Tells if the Seat is reserved.
     */
    public boolean isReserved()
    {
        return reserved;
    }
    
    /**
     * Makes the Seat indicate that it is reserved.
     */
    public void reserveSeat()
    {
        reserved = true;
    }
    
    /**
     * Makes the Seat indicate that it is no longer reserved.
     */
    public void unreserveSeat()
    {
        if (reserved = true)
        {
            reserved = false;
        }
    }
       
}