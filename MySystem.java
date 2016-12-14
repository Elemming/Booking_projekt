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
    private Theater theater;
    private Reservation reservation;

    //Constructor
    MySystem()
    {
        mydb = MyDBSystem.getInstance();
    }

    /**
     * Creates a theater by taking a TheaterID from the database and uses the matching rows and cols
     * to create a fitting 2D array of Seats. Then it fills in the reservations.
     */
    public void createTheater(int ShowID)
    {
        int rows = 0;
        int cols = 0;
        try 
        {
            ResultSet rs = mydb.getShowfromID(ShowID);
            String theaterID = null;
            while(rs.next()){
                theaterID = rs.getString("TheaterID");
            }
            int[] theater = mydb.getTheater(theaterID);
            rows = theater[0];
            cols = theater[1];
        }
        catch(Exception e) 
        {
        }
        theater = new Theater(rows, cols);
        int[][] reservations = mydb.getReservationsfromShow(ShowID);
        try {
            for (int i = 0; i < reservations.length; i++)
            {
                theater.getTheater()[reservations[i][0]-1][reservations[i][1]-1].reserveSeat();
            }
        }

        catch (Exception e) {
        }
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
    public void addReservation(int ShowID, int SeatRow, int SeatCol)
    {
        Seat seat = new Seat(SeatCol, SeatRow);
        Reservation reservation = new Reservation(ShowID, seat); 
        order.addReservation(reservation);
    }

    /**
     * Adds the reservations in an order to the SeatReservation table and Reservation table.
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

    /**
     * Removes an entire order.
     */  
    public void removeOrder()
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
            int SeatID = mydb.getSeatID(ShowID, SeatRow, SeatCol);
            int ResID = mydb.getResID(CustomerID, SeatID);
            mydb.deleteSeatReservation(SeatID);
            mydb.deleteReservation(ResID);
        }

    }

    /**
     * Removes a chosen reservation from the order and the database (both Reservation and SeatReservation).    
     */
    public void removeReservation(Reservation reservation)
    {   
        String name = order.getName();
        int phone = order.getPhone();
        int CustomerID = mydb.getCustomer(name, phone);
        order.getOrder().remove(reservation);
        int ShowID = reservation.getShowID();
        Seat seat = reservation.getSeat();
        int SeatCol = seat.getSeatnumber();
        int SeatRow = seat.getRownumber();
        int SeatID = mydb.getSeatID(ShowID, SeatRow, SeatCol);
        int ResID = mydb.getResID(CustomerID, SeatID);
        mydb.deleteSeatReservation(SeatID);
        mydb.deleteReservation(ResID);
        seat.unreserveSeat();  
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
        if (mydb.getCustomer(name, phone) == 0)
        {
            createCustomer(name, phone);
            getCustomerID(name, phone);
        }
        {
        }
        return mydb.getCustomer(name, phone);
    }

    /**
     * Calls a Theater's getTheater() method.
     */
    public Seat[][] getTheater()
    {
        return theater.getTheater();
    }

    /**
     * Calls the database's closeConnection method.
     */
    public void closeConnection()
    {
        mydb.closeConnection();
    }

    /**
     * Returns an Order.
     */ 
    public Order getOrder()
    {
        return order;
    }

    /**
     * Returns an Order's getOrder() method.
     */
    public ArrayList<Reservation> getOrderlist()
    {
        return order.getOrder();
    }	

    /**
     * Returns info about a showing in a String array, ShowID being the needed input.
     */
    public String[] getShowingInfo (int ShowID)
    {
        String[] ShowInfo =  new String[4]; 
        mydb.getShowfromID(ShowID);
        ResultSet rs = mydb.getShowfromID(ShowID);
        try {
            while(rs.next()){
                ShowInfo[0] = rs.getString("Film");
                 ShowInfo[1] = rs.getString("TheaterID");
                   ShowInfo[2] = rs.getString("Dato");
                    ShowInfo[3] = rs.getString("Tid");
            }
        }
        catch (Exception e) {

        }
        return ShowInfo;
    }
    
    /**
     * Prints the showing info from a ShowID.
     */
    public void printShowInfo(int ShowID)
    {
       String[] ShowInfo = getShowingInfo(ShowID);
        for (int i = 0; i < ShowInfo.length; i++)
        {
            System.out.println(ShowInfo[i]);
        }
        
    }
}

