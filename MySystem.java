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
     */
    public void createTheater(int ShowID)
    throws SQLException
    {
        ResultSet rs = mydb.getShowfromID(ShowID);
        String theaterID = null;
        while(rs.next()){
            theaterID = rs.getString("TheaterID");
        }
        ResultSet theater = mydb.getTheater(theaterID);

        int rows = 0;
        int cols = 0;
        while(theater.next()){        

            rows = theater.getInt("R");
            cols = theater.getInt("Col");
        }
        new Theater(rows, cols);
    }

    /**
     * Takes a name and phone number, calls the createCustomer method, and then creates a new order with
     * the name and phone number.
     */
    public void createOrder(String name, int phone)
    throws SQLException
    {
        createCustomer(name, phone);
        order = new Order(name, phone);
    }

    /**
     * Creates a customer in the database if the customer does not already exist.
     */
  public void createCustomer(String name, int phone)
    throws SQLException
   {
        if (mydb.getCustomer(name, phone) == null)
        {
            mydb.insertCustomer(name, phone);
        }
    }

    public String[][] getAllShows()
    {
        String[][] allShows = mydb.getAllShows();
        return allShows;
    }

    public String[][] getRelevantShows()
    {
        String[][] relevantShows = mydb.getRelevantShows();
        return relevantShows;
    }

    public void addReservation(Reservation reservation)
    {
        order.addReservation(reservation);
    }

    public void removeOrder(String name, int phone)
    {

    }

    public void removeReservation(Reservation reservation)
    {

    }   
}

