import java.sql.*;
import java.util.ArrayList;

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
    {
        createCustomer(name, phone);
        order = new Order(name, phone);
    }

    /**
     * Creates a customer in the database if the customer does not already exist.
     */
    public void createCustomer(String name, int phone)
    {
        if (mydb.getCustomer(name, phone) == 0)
        {
            mydb.insertCustomer(name, phone);
        }
    }

    /**
     * Gets ALL the shows!
     */
    public String[][] getAllShows()
    {
        String[][] allShows = mydb.getAllShows();
        return allShows;
    }

    /**
     * Gets ALL the shows? 
     * (No, only the relevant ones.)
     */
    public String[][] getRelevantShows()
    {
        String[][] relevantShows = mydb.getRelevantShows();
        return relevantShows;
    }

    /**
     * Adds a reservation to the order.
     */
    public void addReservation(Reservation reservation)
    {
        order.addReservation(reservation);
    }

    /**
     * Adds the reservations in an order to the SeatReservation table and adds the whole order to Reservation.
     */
    public void finishOrder()
    {
        String name = order.getName();
        int phone = order.getPhone();
        int CustomerID = mydb.getCustomer(name, phone);
        for (int i = 0; i < order.getOrder().size(); i++) 
        {
            Reservation reservation = order.getOrder().get(i);
            Seat seat = reservation.getSeat();
            int ShowID = reservation.getShowID();
            int SeatCol = seat.getSeatnumber();
            int SeatRow = seat.getRownumber();
            mydb.insertSeatReservation(ShowID, SeatRow, SeatCol);
            int SeatID = mydb.getSeatID(ShowID, SeatRow, SeatCol);
            mydb.insertReservation(CustomerID, SeatID);
        }
    }

    public void removeOrder(String name, int phone)
    {

    }

    public void removeReservation(Reservation reservation)
    {

    } 

    /**
     * Unadds/deselects an reservation already added to the order.
     */
    public void unaddReservation(Reservation reservation)
    {
        order.unaddReservation(reservation);
    }   

    /**
     * Returns the CustomerID that matches the given name and phone number. 
     */
    public int getCustomerID(String name, int phone)
    {
        createCustomer(name, phone);
        if (mydb.getCustomer(name, phone) == 0)
        {
            return 0;
        }
        //         return mydb.getCustomer(name, phone);
        return 1;
    }

    /**
     * Needed?
     */
    public int getShowID()
    {
        return 300;
    }
}

