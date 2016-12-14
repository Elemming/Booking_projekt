import java.sql.*;
import java.time.*;
import java.util.*;

/**The only class that has direct access to the MySQL database. 
 *Creates a connection for a set amount of time before closing the connection to the database.
 *MySystem pulls information from the database through this class and redistributes accordingly.
 *Singleton design was implemented as we only wish a single connection at any time.
 *Prepared statements are used for the security it provides.
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
     * Connection to database is made here.
     */
    private MyDBSystem(){
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }catch(SQLException e){
            //             e.printStackTrace();
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
            //             e.printStackTrace();
            //             System.out.println("Error closing connection.");
        }
    }

    /**
     * Singleton design where the single instance is stored and can be retrieved by this method.
     * Reconnects if last connection timed out.
     */
    public static MyDBSystem getInstance(){
        try{
            if (SingleDB == null || !connection.isValid(0)) {
                SingleDB = new MyDBSystem();
            }
            return SingleDB;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * A method only acccesible by this class that is designated to be the main SELECT query.
     * Takes a specified query string and a HashMap that maps values as paramaters to the prepared statement.
     * 
     * @return A ResultSet with the desired data from the database.
     */
    private ResultSet selectQuery(String selectQuery, HashMap<Integer, String> mapper){
        try{            
            if(!connection.isValid(0) || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            String updateString = ("SELECT " + selectQuery);
            PreparedStatement statement = connection.prepareStatement(updateString);
            for(int i = 1; i <= mapper.size(); i++){
                statement.setString(i, mapper.get(i));
            }
            ResultSet rs = statement.executeQuery();
            return rs;
        } catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("owned");     
            return null;
        }finally{
            try{
                statement.close();
            }catch(Exception e){

            }
        }

    }

    /**
     * Main INSERT query method. All specific INSERT methods runs through this method.    
     */
    private void insertQuery(String insertQuery, HashMap<Integer, String> mapper){
        try{
            if(!connection.isValid(0) || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            String updateString = ("INSERT INTO " + insertQuery);
            PreparedStatement statement = connection.prepareStatement(updateString);
            for(int i = 1; i <= mapper.size(); i++){
                statement.setString(i, mapper.get(i));
            }
            statement.executeUpdate();
        }catch(Exception e){
//             e.printStackTrace();
            //             System.out.println("owned");
        }finally{
            try{
                statement.close();
            }catch(Exception e){

            }
        }
    }

    /**
     * Creates a new reservation record in the database with the given parameters.
     */
    public void insertReservation(int customerID, int seatID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(customerID));
            inputMap.put(2, String.valueOf(seatID));
            insertQuery("Reservationygata (CustomerID, SeatID) VALUES (?, ?)", inputMap);
        }catch(Exception e){
            //             e.printStackTrace();
        }
    }

    /**
     * Creates a new customer record in the database with the given parameters.
     */
    public void insertCustomer(String name, int phone){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, name);
            inputMap.put(2, String.valueOf(phone));
            insertQuery("Customersxcaballo (Name, Phone) VALUES (?,?)", inputMap);
        }catch(Exception e){
            //             e.printStackTrace();
        }
    }

    /**
     * Creates a new seat reservation record in the database with the given paramaters.
     */
    public void insertSeatReservation(int showID, int seatRow, int seatCol){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(showID));
            inputMap.put(2, String.valueOf(seatRow));
            inputMap.put(3, String.valueOf(seatCol));
            insertQuery("SeatResarroz (ShowID, SeatRow, SeatCol) VALUES (?, ?, ?)", inputMap);
        }catch(Exception e){
            //             e.printStackTrace();      
        }
    }

    /**
     * Retrieves the number of rows and columns of a given theater
     * 
     * @return An array of size 2 with number of rows and columns
     */
    public int[] getTheater(String theaterID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>(); 
            inputMap.put(1, theaterID);
            ResultSet rs = selectQuery("R, Col FROM Theaterquieros WHERE TheaterID = ?", inputMap); 
            int[] sizearray = new int[2];
            while(rs.next()){
                sizearray[0] = rs.getInt("R");
                sizearray[1] = rs.getInt("Col");
            }        
            return sizearray;
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("Theater could not be retrieved");
            return null;
        }
    }    

    /**
     * Retrieves the seatID of a 
     */
    public int getSeatID(int showID, int seatRow, int seatCol){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(showID));        
            inputMap.put(2, String.valueOf(seatRow));
            inputMap.put(3, String.valueOf(seatCol));
            ResultSet rs = selectQuery("SeatID FROM SeatResarroz WHERE ShowID = ? AND Seatrow = ? AND Seatcol = ?", inputMap);
            rs.first();
            return rs.getInt("SeatID");
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("SeatID not found/retrieved");
            return 0;
        }
    }

    public int[][] getShowing(String theater, String film, String date, String time){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, theater);
            inputMap.put(2, film);
            inputMap.put(3, date);
            inputMap.put(4, time);
            ResultSet rs = selectQuery("Seatrow, Seatcol FROM SeatResarroz , Showings WHERE TheaterID = ? AND Film = ? AND Dato = ? AND Tid = ?", inputMap);
            int[][] array2D = create2DArrayofInt(rs, "Seatrow", "Seatcol");
            return array2D;
        }catch(Exception e){
            //             e.printStackTrace();
            return null;
        }
    }

    public String[][] getAllShows(){
        try{
            String[][] allShows = null;
            ResultSet rs = selectQuery("* FROM Showingsbcebolla");
            allShows = createArrayofShows(rs);
            return allShows;
        }catch(Exception e){
            //             e.printStackTrace();
            return null;
        }
    }

    public String[][] getRelevantShows(){
        try{
            String[][] RelevantShows = null;
            LocalDate date = LocalDate.now();
            date.toString();
            LocalTime time = LocalTime.now();
            ResultSet rs = selectQuery("* FROM Showingsbcebolla WHERE Dato >= CURDATE()");
            RelevantShows = createArrayofShows(rs);
            return RelevantShows;
        }catch(Exception e){
            //             e.printStackTrace();
            return null;
        }
    }

    private int[][] create2DArrayofInt(ResultSet rs, String firstcol, String secondcol){
        try{
            //ResultSetMetaData rsmetadata = rs.getMetaData();
            //int numofcolumns = rsmetadata.getColumnCount();
            rs.last();
            int[][] array2D = new int[rs.getRow()][2];
            rs.beforeFirst();
            for(int i = 0; rs.next(); i++)
            {
                array2D[i][0] = rs.getInt(firstcol);
                array2D[i][1] = rs.getInt(secondcol);
            }
            return array2D;
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("owned");
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
            //             e.printStackTrace();
            //             System.out.println("owned");
            return null;
        }
    }

    public int getCustomer(String name, int phone){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, name);
            inputMap.put(2, String.valueOf(phone));
            ResultSet rs = selectQuery("CustomerID FROM Customersxcaballo WHERE Name = ? AND Phone = ?", inputMap);
            rs.first();
            return rs.getInt("CustomerID");
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("There was no CustomerID retrieved");
            return 0;
        }
    }

    private ResultSet selectQuery(String selectQuery){
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + selectQuery);
            return rs;
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("There was no ResultSet retrieved");
            return null; 
        }
    }

    public ResultSet getShowfromID(int showID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(showID));
            return selectQuery("* FROM Showingsbcebolla WHERE ShowID = ?", inputMap);
        }catch(Exception e){
            //             e.printStackTrace();
            return null;
        }
    }

    public int[][] getReservationsfromShow(int showID){   
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(showID));
            ResultSet rs = selectQuery("Seatrow, Seatcol FROM SeatResarroz WHERE ShowID = ? ", inputMap);
            return create2DArrayofInt(rs, "Seatrow" , "Seatcol");
        }catch(Exception e){
            //             e.printStackTrace();
            return null;
        }
    }

    public int getResID(int customerID, int seatID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(customerID));
            inputMap.put(2, String.valueOf(seatID));
            ResultSet rs = selectQuery("ResID FROM Reservationygata WHERE CustomerID = ? AND SeatID = ?", inputMap);
            rs.first();
            return rs.getInt("ResID");
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("ResID not found/retrieved");
            return 0;
        }        
    }

    public void deleteCustomer(String name, int phone){
        try{
            if(!connection.isValid(0) || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Customersxcaballo WHERE Name = ? AND Phone = ?");
            statement.setString(1, name);
            statement.setInt(2, phone);
            statement.executeUpdate();
        }catch(SQLException e){
            //             e.printStackTrace();        
            //             System.out.println("Customer was not removed from the database");            
        }finally{
            try{
                statement.close();
            }catch(Exception e){

            }
        }
    }

    public void deleteReservation(int resID){
        try{
            if(!connection.isValid(0) || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Reservationygata WHERE ResID = ?");
            statement.setInt(1, resID);
            statement.executeUpdate();
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("Reservation was not deleted");
        }finally{
            try{
                statement.close();
            }catch(Exception e){

            }
        }
    }

    public void deleteSeatReservation(int seatResID){
        try{
            if(!connection.isValid(0) || connection.isClosed()){
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            }
            PreparedStatement statement = connection.prepareStatement("DELETE FROM SeatResarroz WHERE SeatID = ?");
            statement.setInt(1, seatResID);
            statement.executeUpdate();
        }catch(SQLException e){
            //             e.printStackTrace();
            //             System.out.println("Seat was not deleted");
        }finally{
            try{
                statement.close();
            }catch(Exception e){

            }
        }
    }

    public int[] getSeat(int seatID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(seatID));
            ResultSet rs = selectQuery("Seatrow, Seatcol FROM SeatResarroz WHERE SeatID = ?", inputMap);
            int[] seatArray = new int[2];
            while(rs.next()){
                seatArray[0] = rs.getInt("Seatrow");
                seatArray[1] = rs.getInt("Seatcol");
            }        
            return seatArray;            
        }catch(Exception e){
            return null;
        }
    }

    public int[][] getCurrentReservations(int customerID){
        try{
            HashMap<Integer, String> inputMap = new HashMap<Integer, String>();
            inputMap.put(1, String.valueOf(customerID));
            ResultSet rs = selectQuery("ShowID, SeatResarroz.SeatID FROM SeatResarroz , Reservationygata WHERE SeatResarroz.SeatID = Reservationygata.SeatID AND CustomerID = ?", inputMap);
            int[][] array2D = create2DArrayofInt(rs, "ShowID", "SeatID");
            return array2D;
        }catch(Exception e){
            return null;
        }

    }  
}