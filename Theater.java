public class Theater
{
    private int row;
    private int seat;
    private Seat[][] theater; 
    
    public Theater(int row, int seat)
    {
        this.row = row;
        this.seat = seat;
        theater = new Seat[row][seat];
    }    
    
    public void showTheater()
    {
    }		
}