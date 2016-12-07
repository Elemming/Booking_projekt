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
     * Update: It probably also needs to
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
        String[][] allShows = mydb.getAllShows();
        return allShows;
    }

    public void addReservation(Reservation reservation)
    {
        order.addReservation(reservation);
    }

}
