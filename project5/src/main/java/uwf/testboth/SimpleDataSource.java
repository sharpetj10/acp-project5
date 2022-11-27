package uwf.testboth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * Method: SimpleDataSource
 * 
 * Gets the database connection.
 * 
 */

public class SimpleDataSource
{
   private static String url;
   private static String username;
   private static String password;

   /**
    * 
    * Method: init(String)
    * 
    * Initializes the data source.
    * 
    *  @param fileName
    *  
   */
   
   public static void init(String fileName) throws IOException, ClassNotFoundException {  
     
	  Properties props = new Properties();
      FileInputStream in = new FileInputStream(fileName);
      props.load(in);
      
      String driver = props.getProperty("jdbc.driver");
      url = props.getProperty("jdbc.url");
      username = props.getProperty("jdbc.username");
      
      if (username == null) {
    	  
    	  username = "";
    	  password = props.getProperty("jdbc.password");
    	  
      }
      
      if (password == null) {
    	  
    	  password = "";
    	  
      }
      
      if (driver != null) {
    	  
    	  Class.forName(driver);
    	  
      }
         
   }

   /**
    * 
    * Method: getConnection()
    * 
    * Gets a connection to the database.
    * 
    * @return database connection
    */
   
   public static Connection getConnection() throws SQLException {
	   
      return DriverManager.getConnection(url, username, password);
      
   }
   
}


