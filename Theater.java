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
        int x = 1;
        int y = 1;
        for (int rows = 0; rows < theater.length; rows++) {
            for (int cols = 0; cols < theater[rows].length; cols++) {   
                theater[rows][cols] = new Seat(x, y);
                x++;
            }
            y++;
            x = 1;
        }
    }    

    /**
     * Shows the rows and columns in the theater, representing seats as "[Seat]". 
     */
    public void showTheater()
    {
       for (int rows = 0; rows < theater.length; rows++) {
            for (int cols = 0; cols < theater[rows].length; cols++) {   
                System.out.print("[Seat]");
            } 
            System.out.println();
    }     
}
}
