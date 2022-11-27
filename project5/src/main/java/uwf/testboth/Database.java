package uwf.testboth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * 
 * Class: Database
 * 
 * Handles the database creation and server connection.
 * 
 * @author Tia Sharpe
 *
 */

public class Database {

	Statement statement;
	Connection connection;
	ResultSet result;

	/**
	 * 
	 * Method: Database()
	 * 
	 * Constructor for the database class.
	 * @throws Exception 
	 * 
	 */

	public Database() throws Exception {

		Driver();
		
		connection = null;

	}

	/**
	 * 
	 * Method: Database(String)
	 * 
	 * Parameterized constructor for the Database class.
	 * 
	 * @param tableName
	 * 
	 */

	public Database(String tableName) {

		connection = null;
		statement = null;

	}

	/**
	 * 
	 * Method: openDB()
	 * 
	 * Creates a connection to the database.
	 * 
	 * @return statement
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 * 
	 */

	public Statement openDB() throws ClassNotFoundException, IOException, SQLException {

		System.out.println("Opening database\n");

		SimpleDataSource.init("database.properties");

		connection = SimpleDataSource.getConnection();
		statement = connection.createStatement();

		System.out.println("Database connection successful");

		return statement;

	}

	/**
	 * 
	 * Method: closeDB()
	 * 
	 * Closes the connection to the database.
	 * 
	 * @throws SQLException
	 * 
	 */

	public void closeDB() throws SQLException {

		System.out.println("\nClosing database\n");

		connection.close();

	}

	/**
	 * 
	 * Method: dropTable()
	 * 
	 * Drops the database tables.
	 * 
	 * @throws SQLException
	 * 
	 */

	public void dropTable() throws SQLException {

		System.out.println("\n\tDropping tables");

		try {

			statement.execute("DROP TABLE Instruments");
			statement.execute("DROP TABLE Locations");
			statement.execute("DROP TABLE Inventory");

		}

		catch(Exception e) {

			System.out.println("\nTables failed to drop\n");

		}

	}

	/**
	 * 
	 * Method: createInstruments(Statements)
	 * 
	 * Creates Instruments table.
	 * 
	 * @param statement
	 * @return result
	 * @throws Exception
	 * 
	 */
	public ResultSet createInstruments() throws Exception {

		System.out.println("\n\tCreating Instruments Table");

		statement.execute("CREATE TABLE Instruments (instName CHAR(12),instNumber INTEGER,cost DOUBLE,descrip CHAR(20))");

		statement.execute("INSERT INTO Instruments VALUES ('guitar',1,100.0,'yamaha')");
		statement.execute("INSERT INTO Instruments VALUES ('guitar',2,500.0,'gibson')");
		statement.execute("INSERT INTO Instruments VALUES ('bass',3,250.0,'fender')");
		statement.execute("INSERT INTO Instruments VALUES ('keyboard',4,600.0,'roland')");
		statement.execute("INSERT INTO Instruments VALUES ('keyboard',5,500.0,'alesis')");
		statement.execute("INSERT INTO Instruments VALUES ('drums',6,1500.0,'ludwig')");
		statement.execute("INSERT INTO Instruments VALUES ('drums',7,400.0,'yamaha')");

		ResultSet result = statement.executeQuery("SELECT * FROM Instruments");

		showResults(result);
		
		return result;

	}

	/**
	 * 
	 * Method: createLocations(Statement)
	 * 
	 * Creates Locations table.
	 * 
	 * @param statement
	 * @return result
	 * @throws Exception
	 */

	public ResultSet createLocations() throws Exception {

		System.out.println("\n\tCreating Location Table");

		statement.execute("CREATE TABLE Locations (locName CHAR(12),locNumber INTEGER,address CHAR(50))");

		statement.execute("INSERT INTO Locations VALUES ('PNS',1,'Pensacola Florida')");
		statement.execute("INSERT INTO Locations VALUES ('CLT',2,'Charlotte North Carolina')");
		statement.execute("INSERT INTO Locations VALUES ('DFQ',3,'Dallas Fort Worth Texas')");

		ResultSet result = statement.executeQuery("SELECT * FROM Locations");

		showResults(result);
		
		return result;

	}

	/**
	 * 
	 * Method: createInventory(Statement)
	 * 
	 * Creates Inventory table.
	 * 
	 * @param statement
	 * @return result
	 * @throws Exception
	 * 
	 */

	public ResultSet createInventory() throws Exception {

		System.out.println("\n\tCreating Inventory Table");

		statement.execute("CREATE TABLE Inventory(iNumber INTEGER,lNumber INTEGER,quantity INTEGER)");

		statement.execute("INSERT INTO Inventory VALUES (1,1,15)");
		statement.execute("INSERT INTO Inventory VALUES (1,2,27)");
		statement.execute("INSERT INTO Inventory VALUES (1,3,20)");
		statement.execute("INSERT INTO Inventory VALUES (2,1,10)");
		statement.execute("INSERT INTO Inventory VALUES (2,2,10)");
		statement.execute("INSERT INTO Inventory VALUES (2,3,35)");
		statement.execute("INSERT INTO Inventory VALUES (3,1,45)");
		statement.execute("INSERT INTO Inventory VALUES (3,2,10)");
		statement.execute("INSERT INTO Inventory VALUES (3,3,17)");
		statement.execute("INSERT INTO Inventory VALUES (4,1,28)");
		statement.execute("INSERT INTO Inventory VALUES (4,2,10)");
		statement.execute("INSERT INTO Inventory VALUES (4,3,16)"); 
		statement.execute("INSERT INTO Inventory VALUES (5,1,28)");
		statement.execute("INSERT INTO Inventory VALUES (5,2,10)");
		statement.execute("INSERT INTO Inventory VALUES (5,3,1)");
		statement.execute("INSERT INTO Inventory VALUES (6,1,2)");
		statement.execute("INSERT INTO Inventory VALUES (6,2,10)"); 
		statement.execute("INSERT INTO Inventory VALUES (6,3,16)");
		statement.execute("INSERT INTO Inventory VALUES (7,1,16)");
		statement.execute("INSERT INTO Inventory VALUES (7,2,4)"); 
		statement.execute("INSERT INTO Inventory VALUES (7,3,12)");     

		ResultSet result = statement.executeQuery("SELECT * FROM Inventory");

		showResults(result);
		
		return result;

	}

	/**
	 * 
	 * Method: showResults(ResultSet)
	 * 
	 * Displays the results from the queries.
	 * 
	 * @param result
	 * @throws SQLException
	 * 
	 */

	public void showResults(ResultSet result) throws SQLException {

		ResultSetMetaData rsm = result.getMetaData();

		int columns = rsm.getColumnCount();

		while(result.next()) {

			for(int index = 1; index <= columns; index++) {

				System.out.print("\t\t" + result.getString(index) + " ");

			}

			System.out.println("");

		}

	}
	

	/**
	 * 
	 * Method:executeCommands(String)
	 * 
	 * executes the required queries.
	 * 
	 * @param string
	 * @return
	 * @throws SQLException
	 * 
	 */

	public ResultSet executeCommands(String command) throws SQLException {
		
		ResultSet result = statement.executeQuery(command);
		
		showResults(result);
		
		return result;

	}
	
	/**
	 * 
	 * Method: Driver()
	 * 
	 * Controls the program.
	 * @throws Exception 
	 * 
	 */

	public void Driver() throws Exception {

		// starts the database connection

		openDB();

		// drops the database tables

		dropTable();

		// creating tables

		createInstruments();
		createLocations();
		createInventory();
		
		// closes the database 

		//closeDB();

	}

	public static void main(String[] args) throws Exception {

		final int portNumber = 8888;

		Database db = new Database();

		ServerSocket server = new ServerSocket(portNumber);
		System.out.println("Server: Waiting for clients to connect");

		while(true) { 

			Socket s = server.accept();
			System.out.println("Server: Client connected");

			Proxy proxy = new Proxy(s, db);
			Thread t = new Thread(proxy);
			t.start();

		}

	}

}
