/**
 * Creates a seat which has a seatnumber (the same as Theater's seat), the row it is in, and knows
 * whether it is reserved.
 */
public class Seat
{
    private int seatnumber;
    private int row;
    private boolean reserved;
    
    public Seat(int seatnumber, int row, boolean reserved)
    {
        this.seatnumber = seatnumber;
        this.row = row;
        this.reserved = reserved;
        reserved = false;
    }
    
    public void reserveSeat()
    {
        reserved = true;
    }
       
}