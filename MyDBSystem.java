// COMPILE:
// javac -cp mysql.jar MySQL.java 

// RUN:
// java -cp mysql.jar:. MySQL

import java.sql.*;

public class MyDBSystem{

    static final String MYDB = "EBMCinema";
    static final String USER = "EBMCinema";
    static final String PASS = "TheaterEBM";

    // JDBC driver name and database URL
    static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;

    public MyDBSystem() {
        makeConnection("mailinglist");
    }

    public void makeConnection(String command){
        Connection connection = null;
        Statement statement = null;

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver()); // STEP 2: Register JDBC driver
            connection = DriverManager.getConnection(DB_URL, USER, PASS); // STEP 3: Open a connection
            statement = connection.createStatement(); // STEP 4: Execute a query

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
            connection.close();
        } catch(Exception e) { // handle errors:
            e.printStackTrace();
        }

    }

}
