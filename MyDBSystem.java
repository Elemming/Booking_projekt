// COMPILE:
// javac -cp mysql.jar MySQL.java 

// RUN:
// java -cp mysql.jar:. MySQL

import java.sql.*;

public final class MyDBSystem{
    private static final String MYDB = "EBMCinema";
    private static final String USER = "EBMCinema";
    private static final String PASS = "TheaterEBM";
    private static Connection connection = null;
    private static Statement statement;
    // JDBC driver name and database URL
    private static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;
    private static MyDBSystem SingleDB = null;

    private MyDBSystem(){
       try{
        makeConnection();
       }
       catch(SQLException e){
           e.printStackTrace();
       }
    }

    public static void makeConnection() 
    throws SQLException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void makeConnection(String command) 
    throws SQLException{
        try {
            makeConnection();
            if(command.equals("mailinglist"))
            {
                String sql = "SELECT * FROM mailing_list"; // implicit semi-colon!
                ResultSet rs = statement.executeQuery(sql);
                //STEP 5: Extract data from result set
                while (rs.next()) { //Retrieve by column name
                    // int id  = rs.getInt("id");
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    //Display values
                    System.out.println("Name: '" + name + "', Email: '" + email + "'");
                }
                rs.close();
            }
            closeConnection();
        } catch(Exception e) { // handle errors:
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

    /*public void selectQuery(String select_query)
    throws SQLException{
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT " + select_query);
            while (rs.next()) { //Retrieve by column name
                // int id  = rs.getInt("id");
                String email = rs.getString("email");
                String name = rs.getString("name");
                //Display values
                System.out.println("Name: '" + name + "', Email: '" + email + "'");
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("owned");
        }
    }*/

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