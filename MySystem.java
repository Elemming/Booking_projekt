import java.sql.*;

/**
 * The Great and Powerful Trix- er, System! Uses the Theater/Order/Reservation/Seat classes in a way
 * that fits with the theaters and showings at EBMCinema, drawing from MyDBSystem, and feeding View.
 */
public class MySystem
{
    private Order order;
    private MyDBSystem mydb;

    //Constructor
    MySystem()
    {
        mydb = MyDBSystem.getInstance();
    }

    /**
     * Creates a theater by taking a TheaterID from the database and uses the matching rows and cols
     * to create a fitting 2D array of Seats.
     * It then checks the seat reservations to that showing and make the appropiate seats reserved.
     */
    //   public void createTheater(SHOWID ShowID)
    //     {
    //         int rows = THEATERID_ROWS;
    //         int cols = THEATERID_COLS;
    //         new Theater(rows, cols);
    //     }

    public void createOrder(String name, int phone)
    {
        order = new Order(name, phone);
    }

    public String[][] getAllShows()
    {
        ResultSet rs = mydb.getAllShows();
        String[][] allShows = null;
        try{
            rs.last();
            allShows = new String[rs.getRow()][5];
            rs.beforeFirst(); 
            for(int i=0; rs.next(); i++)
            {
                allShows[i][0]= rs.getString("ShowID");
                allShows[i][1]= rs.getString("FIlm");
                allShows[i][2]= rs.getString("TheaterID");
                allShows[i][3]= rs.getString("Dato");
                allShows[i][4]= rs.getString("Tid");
            }
        }
        catch(Exception e){}

        return allShows;
    }

    public void addReservation(Reservation reservation)
    {
        order.addReservation(reservation);
    }

}
