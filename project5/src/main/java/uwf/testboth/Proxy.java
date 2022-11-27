package uwf.testboth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 
 * Class: Proxy
 * 
 * Handles communication between the client and server.
 * 
 * @author Tia Sharpe
 *
 */

public class Proxy implements Runnable {

	// declaring variables

	private Socket s;
	private Scanner in;
	private PrintWriter out;
	private Database database;

	/**
	 * 
	 * Method: Proxy(Socket, Database)
	 * 
	 * Parameterized constructor.
	 * 
	 * @param socket
	 * @param db
	 * 
	 */

	public Proxy(Socket socket, Database db) {

		s = socket;
		database = db;

	}

	/**
	 * 
	 * Method: run()
	 * 
	 * Runs the program.
	 * 
	 */

	public void run() {

		try {

			in = new Scanner(s.getInputStream());
			out = new PrintWriter(s.getOutputStream());

			service();

		} 

		catch (IOException | SQLException e) {

			e.printStackTrace();

		}

	}

	/**
	 * 
	 * Method: service()
	 * 
	 * Gets the commands needed from the client.
	 * @throws SQLException 
	 * 
	 */

	public void service() throws SQLException {

		if(!in.hasNext()) {

			return;

		}

		String command = in.nextLine();

		commands(command);

	}

	/**
	 * 
	 * Method: commands(String)
	 * 
	 * 
	 * 
	 * @param command
	 * @throws SQLException 
	 * 
	 */

	public void commands(String command) throws SQLException {

		String[] split = command.split(" ");

		String instrument = split[0];
		String brand = split[1];
		int cost = Integer.parseInt(split[2]);
		String warehouse = split[3];
		/**
		if(instrument == "all") {
			
			command = "SELECT Instruments.* "
					+ "FROM Instruments "
					+ "JOIN Inventory "
						+ "ON Instruments.instNumber = Inventory.iNumber "
					+ "JOIN Locations "
						+ "ON Locations.locNumber = Inventory.lNumber"
					+ " WHERE Instruments.cost < " + cost
						+ " AND Instruments.instName = '" + instrument + "'"
						+ " AND Instruments.descrip = '" + brand + "'"
						+ " AND Locations.locName = '" + warehouse + "'";
			
		}
		
		else if(brand == "all") {
			
			command = "SELECT Instruments.* "
					+ "FROM Instruments "
					+ "JOIN Inventory "
						+ "ON Instruments.instNumber = Inventory.iNumber "
					+ "JOIN Locations "
						+ "ON Locations.locNumber = Inventory.lNumber"
					+ " WHERE Instruments.cost < " + cost
						+ " AND Instruments.instName = '" + instrument + "'"
						+ " AND Instruments.descrip = '" + brand + "'"
						+ " AND Locations.locName = '" + warehouse + "'";
			
		}
		
		else if(warehouse == "all") {
			
			command = "SELECT Instruments.* "
					+ "FROM Instruments "
					+ "JOIN Inventory "
						+ "ON Instruments.instNumber = Inventory.iNumber "
					+ "JOIN Locations "
						+ "ON Locations.locNumber = Inventory.lNumber"
					+ " WHERE Instruments.cost < " + cost
						+ " AND Instruments.instName = '" + instrument + "'"
						+ " AND Instruments.descrip = '" + brand + "'"
						+ " AND Locations.locName = '" + warehouse + "'";
			
		}
		
		else {
			*/
			
			command = "SELECT Instruments.instName,Instruments.descrip, Instruments.cost, Inventory.quantity, Locations.address "
				+ "FROM Instruments "
				+ "JOIN Inventory "
					+ "ON Instruments.instNumber = Inventory.iNumber "
				+ "JOIN Locations "
					+ "ON Locations.locNumber = Inventory.lNumber"
				+ " WHERE Instruments.cost < " + cost
					+ " AND Instruments.instName = '" + instrument + "'"
					+ " AND Instruments.descrip = '" + brand + "'"
					+ " AND Locations.locName = '" + warehouse + "'";
			
	//	}
		
		System.out.println();

		database.executeCommands(command);
		
		out.println(command);
		out.flush();

	}

}
