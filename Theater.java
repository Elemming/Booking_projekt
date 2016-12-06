public class Theater
{
    private int row;
    private int col;
    private Seat[][] theater; 

    public Theater(int row, int col)
    {
        this.row = row;
        this.col = col;
        theater = new Seat[row][col];
    }    

    public void showTheater()
    {
    }		
}