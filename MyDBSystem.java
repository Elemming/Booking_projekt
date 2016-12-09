/**The only class that has direct access to the MySQL database. 
 *Creates a connection for a set amount of time before closing the connection to the database.
 *MySystem pulls information from the database through this class and redistributes accordingly.
 *Singleton design was implemented as we only wish a single connection at any time.
 **/

import java.sql.*;
import java.time.*;
import java.util.*;

public final class MyDBSystem{
    private static final String MYDB = "EBMCinema";
    private static final String USER = "EBMCinema";
    private static final String PASS = "TheaterEBM";
    private static Connection connection = null;
    private static Statement statement;
    private static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;
    private static MyDBSystem SingleDB = null;

    /**
     * Connection to database is made here
     */
    private MyDBSystem(){
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Checks if connection is closed, if not, closes connection.
     */
    public static void closeConnection(){
        try{
            if(!connection.isClosed()){        
                connection.close();
            }
            connection = null;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error closing connection.");
        }
    }

    /**
     * Singleton design where the single instance is stored and can be retrieved by this method.
     */
    public static MyDBSystem getInstance(){
        if (SingleDB == null) {
            SingleDB = new MyDBSystem();
        }
        return SingleDB;
    }

    //     /**
    //      * A method only acccesible by this class that is designated to run a SELECT query
    //      */
    //     private ResultSet selectQuery(String selectQuery, Hashtable mapper){
    //         try{            
    //             PreparedStatement statement = connection.prepareStatement("SELECT " + selectQuery);
    //             statement.setString(1, mapper.get("1"));
    //             ResultSet rs = statement.executeUpdate("SELECT " + selectQuery);
    //             return rs;
    //         }catch(SQLException e){
    //             e.printStackTrace();
    //             System.out.println("owned");     
    //             return null;
    //         }
    //     }

    private ResultSet selectQuery(String selectQuery){
        try{
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + selectQuery);
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
            return null;
        }
    }

    //     //public ResultSet getTheater(String theater){
    //         Hashtable<String, String> inputmapping = new Hashtable<String, String>(); 
    //         inputmapping.put("1", theater);
    //         return selectQuery("R, Col FROM Theaters WHERE TheaterID = ?", inputmapping);       
    //     }

    private void insertQuery(String insertQuery){
        try{
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " + insertQuery);
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
        }      
    }

    public void insertReservation(int CustomerID, int SeatID){
        String tablename = "Reservations";
        insertQuery("Reservations (CustomerID, SeatID) VALUES (" + CustomerID + ", " + SeatID + " )");
    }

    public void insertCustomer(String name, int phone){
        insertQuery("Customers (Name, Phone) VALUES ('" + name + "', " + phone + " )");
    }

    public void insertSeatReservation(int showID, int seatRow, int seatCol){
        insertQuery("SeatReservation (ShowID, SeatRow, SeatCol) VALUES (" + showID + ", " + seatRow + ", " + seatCol + ")");
    }

    public ResultSet getTheater(String theater){
        return selectQuery("R, Col FROM Theaters WHERE TheaterID = '" + theater + "'");
    }

    public ResultSet getShowing(String theater, String film, String date, String time){
        return selectQuery("Seatrow, Seatcol FROM SeatReservation , Showings WHERE TheaterID = '" + theater + "' AND Film = '" + film + "' AND Dato = '" + date + "' AND Tid = '" + time + "'");
    }

    public String[][] getAllShows(){

        String[][] allShows = null;
        ResultSet rs = selectQuery("* FROM Showings");
        allShows = createArrayofShows(rs);
        return allShows;
    }

    public String[][] getRelevantShows(){
        String[][] RelevantShows = null;
        LocalDate date = LocalDate.now();
        date.toString();
        LocalTime time = LocalTime.now();
        ResultSet rs = selectQuery("* FROM Showings WHERE Dato >= CURDATE()");
        RelevantShows = createArrayofShows(rs);
        return RelevantShows;
    }

    private int[][] create2DArrayofInt(ResultSet rs, String firstcol, String secondcol){
        try{
            //ResultSetMetaData rsmetadata = rs.getMetaData();
            //int numofcolumns = rsmetadata.getColumnCount();
            rs.last();
            int[][] array2D = new int[2][rs.getRow()];
            System.out.println(rs.getRow());
            rs.beforeFirst();
            for(int i = 0; rs.next(); i++)
            {
                array2D[0][i] = rs.getInt(firstcol);
                array2D[1][i] = rs.getInt(secondcol);
            }
            return array2D;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
            return null;
        }
    }

    private String[][] createArrayofShows(ResultSet rs){
        try{
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
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
            return null;
        }
    }

    public ResultSet getCustomer(String name, int phone){
        return selectQuery("CustomerID FROM Customers WHERE Name = '" + name + "' AND Phone = '" + phone + "'");
    }

    public ResultSet getShowfromID(int showID){
        return selectQuery("* FROM Showings WHERE ShowID = '" + showID + "'");
    }

    public int[][] getReservationsfromShow(int showID){          
        ResultSet rs = selectQuery("Seatrow, Seatcol FROM SeatReservation WHERE ShowID = '" + showID + "'");
        return create2DArrayofInt(rs, "Seatrow" , "Seatcol");
    }

    public void deleteCustomer(String name, int phone){
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customers WHERE Name = ? AND Phone = ?");
            statement.setString(1, name);
            statement.setInt(2, phone);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();        
            System.out.println("Customer was not removed from the database");            
        }
    }

    public void deleteReservation(int resID){
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Reservations WHERE ResID = ?");
            statement.setInt(1, resID);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Reservation was not deleted");
        }
    }

    public void deleteSeatReservation(int seatResID){
        try{
            PreparedStatement statement = connection.prepareStatement("DELETE FROM SeatReservation WHERE SeatID = ?");
            statement.setInt(1, seatResID);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Seat was not deleted");
        }
    }
}    