//The only class that has direct access to the MySQL database. 
//Creates a connection for a set amount of time before closing the connection to the database.
//MySystem pulls information from the database through this class and redistributes accordingly.
//Singleton design was implemented as we only wish a single connection at any time.

import java.sql.*;

public final class MyDBSystem{
    private static final String MYDB = "EBMCinema";
    private static final String USER = "EBMCinema";
    private static final String PASS = "TheaterEBM";
    private static Connection connection = null;
    private static Statement statement;
    private static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;
    private static MyDBSystem SingleDB = null;
    
    //
    private MyDBSystem(){
       try{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    }

    public static void closeConnection()
    throws SQLException 
    {
        if(!connection.isClosed()){        
            connection.close();
        }
        connection = null;
    }

    public static MyDBSystem getInstance(){
        if (SingleDB == null) {
            SingleDB = new MyDBSystem();
        }
        return SingleDB;
    }

    private ResultSet selectQuery(String select_query)
    throws SQLException{
        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + select_query);
            return rs;
        }
        catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");     
            return null;
        }
    }

    public void insertReservation(int CustomerID, int SeatID)
    throws SQLException{
        try{
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Reservations (CustomerID, SeatID) VALUES (" + CustomerID + ", " + SeatID + " )");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
        }      
    }
    
    public void insertCustomer(String name, int phone){
        try{
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Customers (Name, Phone) VALUES ('" + name + "', " + phone + " )");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
        }   
    }
    
    
}