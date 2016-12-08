//The only class that has direct access to the MySQL database. 
//Creates a connection for a set amount of time before closing the connection to the database.
//MySystem pulls information from the database through this class and redistributes accordingly.
//Singleton design was implemented as we only wish a single connection at any time.

import java.sql.*;
import java.time.*;

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
        }catch(SQLException e){
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
        }catch(SQLException e){
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

    public ResultSet getTheater(String theater){
        try{
            return selectQuery("R, Col FROM Theaters WHERE TheaterID = '" + theater + "'");
        }catch(SQLException e){
            e.printStackTrace();        
            System.out.println("owned");
            return null;
        }
    }

    public ResultSet getShowing(String theater, String film, String date, String time){
        try{
            return selectQuery("Seatrow, Seatcol FROM SeatReservation , Showings WHERE TheaterID = '" + theater + "' AND Film = '" + film + "' AND Dato = '" + date + "' AND Tid = '" + time + "'");
        }catch(SQLException e){
            e.printStackTrace();        
            System.out.println("owned");
            return null;
        }
    }

    public String[][] getAllShows(){
        try{
            String[][] allShows = null;
            ResultSet rs = selectQuery("* FROM Showings");
            allShows = createArrayofShows(rs);
            return allShows;
        }catch(SQLException e){
            e.printStackTrace();        
            System.out.println("owned");
            return null;
        }
    }

    public String[][] getRelevantShows(){
        try{
            String[][] RelevantShows = null;
            LocalDate date = LocalDate.now();
            date.toString();
            LocalTime time = LocalTime.now();
            ResultSet rs = selectQuery("* FROM Showings WHERE Dato >= CURDATE()");
            RelevantShows = createArrayofShows(rs);
            return RelevantShows;
        }catch(SQLException e){
            e.printStackTrace();        
            System.out.println("owned");
            return null;
        }

    }

    private String[][] createArrayofShows(ResultSet rs)
    throws SQLException
    {
        rs.last();
        String[][] Shows = new String[rs.getRow()][5];
        rs.beforeFirst(); 
        for(int i=0; rs.next(); i++)
        {
            Shows[i][0]= rs.getString("ShowID");
            Shows[i][1]= rs.getString("Film");
            Shows[i][2]= rs.getString("TheaterID");
            Shows[i][3]= rs.getString("Dato");
            Shows[i][4]= rs.getString("Tid");
        }
        return Shows;
    }

    public void test(){
        LocalDate date = LocalDate.now();
        System.out.println(date.toString());
    }
    
    public ResultSet getCustomer(String name, int phone)
    throws SQLException
    {
        return selectQuery("* FROM Customers WHERE Name = '" + name + "' AND Phone = '" + phone + "'");
    }
    
    public void insertSeatReservation(int showID, int seatRow, int seatCol)
    throws SQLException{
        try{
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO SeatReservation (ShowID, SeatRow, SeatCol) VALUES (" + showID + ", " + seatRow + ", " + seatCol + ")");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
        }      
    }
}