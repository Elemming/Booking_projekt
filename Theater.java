public class Theater
{
    private int row;
    private int seat;
    private Reservation[][] theater;
    
    public Theater(int row, int seat)
    {
        this.row = row;
        this.seat = seat;
        theater = new Reservation[row][seat];
    }    
}