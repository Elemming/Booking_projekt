/**
 * Creates a list of reservations (an order) for a particular name and phone number.
 */
import java.util.ArrayList;
public class Order 
{
    private ArrayList<Reservation> order;
    private String name;
    private int phone;

    public Order(String name, int phone)
    {
        this.name = name;
        this.phone = phone; 
        order = new ArrayList<Reservation>();
    }

    /** 
     * Adds a new reservation to the order.
     */
    public void addReservation(Reservation reservation)
    {
        order.add(reservation);
    }
    
    /**
     * Removes a reservation from the order.
     */
    public void unaddReservation(Reservation reservation)
    {
        order.remove(reservation);
    }

    /**
     * Returns the order.
     */
    public ArrayList getOrder()
    {
        return order;
    }

}