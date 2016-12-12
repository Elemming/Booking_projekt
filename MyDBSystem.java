import java.sql.*;
import java.time.*;
import java.util.*;

/**The only class that has direct access to the MySQL database. 
 *Creates a connection for a set amount of time before closing the connection to the database.
 *MySystem pulls information from the database through this class and redistributes accordingly.
 *Singleton design was implemented as we only wish a single connection at any time.
 **/

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

    /**
     * A method only acccesible by this class that is designated to be the main SELECT query.
     */
    private ResultSet selectQuery(String selectQuery, HashMap<Integer, String> map){
        try{            
            String updateString = ("SELECT " + selectQuery);
            System.out.println(updateString);
            PreparedStatement statement = connection.prepareStatement(updateString);
            for(int i = 1; i <= map.size(); i++){
                statement.setString(i, map.get(i));
            }
            ResultSet rs = statement.executeQuery();
            return rs;
        } catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");     
            return null;
        }
    }

    /**
     *  
     */
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
        String Mapname = "Reservations";
        insertQuery("Reservations (CustomerID, SeatID) VALUES (" + CustomerID + ", " + SeatID + " )");
    }

    public void insertCustomer(String name, int phone){
        insertQuery("Customers (Name, Phone) VALUES ('" + name + "', " + phone + " )");
    }

    public void insertSeatReservation(int showID, int seatRow, int seatCol){
        insertQuery("SeatReservation (ShowID, SeatRow, SeatCol) VALUES (" + showID + ", " + seatRow + ", " + seatCol + ")");
    }

    public int[] getTheater(String theaterID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>(); 
            inputMap.put(1, theaterID);
            ResultSet rs = selectQuery("R, Col FROM Theaters WHERE TheaterID = ?", inputMap); 
            int[] sizearray = new int[2];
            while(rs.next()){
                sizearray[0] = rs.getInt("R");
                sizearray[1] = rs.getInt("Col");
            }        
            return sizearray;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Theater could not be retrieved");
            return null;
        }
    }    

    public int getSeatID(int showID, int seatRow, int seatCol){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(showID));        
            inputMap.put(2, String.valueOf(seatRow));
            inputMap.put(3, String.valueOf(seatCol));
            ResultSet rs = selectQuery("SeatID FROM SeatReservation WHERE ShowID = ? AND Seatrow = ? AND Seatcol = ?", inputMap);
            rs.first();
            return rs.getInt("SeatID");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("SeatID not found/retrieved");
            return 0;
        }
    }

    public int[][] getShowing(String theater, String film, String date, String time){
        HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
        inputMap.put(1, theater);
        inputMap.put(2, film);
        inputMap.put(3, date);
        inputMap.put(4, time);
        ResultSet rs = selectQuery("Seatrow, Seatcol FROM SeatReservation , Showings WHERE TheaterID = ? AND Film = ? AND Dato = ? AND Tid = ?", inputMap);
        int[][] array2D = create2DArrayofInt(rs, "Seatrow", "Seatcol");
        return array2D;
    }

    public String[][] getAllShows(){
        String[][] allShows = null;
        ResultSet rs = selectQuery("* FROM Showings", null);
        allShows = createArrayofShows(rs);
        return allShows;
    }

    public String[][] getRelevantShows(){
        String[][] RelevantShows = null;
        LocalDate date = LocalDate.now();
        date.toString();
        LocalTime time = LocalTime.now();
        ResultSet rs = selectQuery("* FROM Showings WHERE Dato >= CURDATE()", null);
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

    public int getCustomer(String name, int phone){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, name);
            inputMap.put(2, String.valueOf(phone));
            ResultSet rs = selectQuery("CustomerID FROM Customers WHERE Name = ? AND Phone = ?", inputMap);
            rs.first();
            return rs.getInt("CustomerID");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("There was no CustomerID retrieved");
            return 0;
        }
    }

    private ResultSet selectQuery(String selectQuery){
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + selectQuery);
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("There was no ResultSet retrieved");
            return null; 
        }
    }

    public ResultSet getShowfromID(int showID){
        return selectQuery("* FROM Showings WHERE ShowID = '" + showID + "'");
    }

    public int[][] getReservationsfromShow(int showID){         
        HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
        inputMap.put(1, String.valueOf(showID));
        ResultSet rs = selectQuery("Seatrow, Seatcol FROM SeatReservation WHERE ShowID = ? ", inputMap);
        return create2DArrayofInt(rs, "Seatrow" , "Seatcol");
    }

    public int getResID(int customerID, int seatID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(customerID));
            inputMap.put(2, String.valueOf(seatID));
            ResultSet rs = selectQuery("ResID FROM Reservations WHERE CustomerID = ? AND SeatID = ?", inputMap);
            rs.first();
            return rs.getInt("ResID");
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("ResID not found/retrieved");
            return 0;
        }        
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