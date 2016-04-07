package database;

import java.sql.*;

/**
 * Database is a class that specifies the interface to the movie database. Uses
 * JDBC and the MySQL Connector/J driver.
 */
public class Database {
	/**
	 * The database connection.
	 */
	private Connection conn;
	
	/**
	 * Create the database interface object. Connection to the database is
	 * performed later.
	 */
	public Database() {
		conn = null;
	}

	/**
	 * Open a connection to the database, using the specified user name and
	 * password.
	 * 
	 * @param userName
	 *            The user name.
	 * @param password
	 *            The user's password.
	 * @return true if the connection succeeded, false if the supplied user name
	 *         and password were not recognized. Returns false also if the JDBC
	 *         driver isn't found.
	 */
	public boolean openConnection(String userName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://puccini.cs.lth.se/" + userName, userName,
					password);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close the connection to the database.
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}
		conn = null;
	}

	/**
	 * Check if the connection to the database has been established
	 * 
	 * @return true if the connection has been established
	 */
	public boolean isConnected() {
		return conn != null;
	}

	/* --- insert own code here --- */
	
	// Methods created for the ProductionPane
	public String[] getCookieTypes(){ //Used to get the different cookie types available for the production of new pallets
		String[] strings = {"Aaron", "Bbron","Ccron","Ddron","Eeron"};
		return strings;
	}
	public void createPallet(String cookieType){ //Obviously used by a listener in ProductionPane to create new pallets
		System.out.println("Create pallet with " + cookieType + " cookies.");
		//Add new pallet, should the database have add time and date of production or should it be created here?
		//Remove ingredients here, or should we use a separate method to affect the ingredients?
	}
	public void deductIngredients(String cookieType){ //If used as a help method to createPallets change visibility to private.
		//Ask table Recipe for all tuples with cookieType as cookieType
		//Loop through the results and deduct amount for each ingredient in the Ingredients table (might need to fetch the amount of ingredients first though)
	}
	//Methods created for the PalletPane

	//Where the ProductionPane seem crystal clear how it should work, the PalletPane could use some more discussion.
}