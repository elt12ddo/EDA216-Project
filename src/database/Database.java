package database;

import java.sql.*;
import java.util.ArrayList;

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
		ArrayList<String> cookieList = new ArrayList<String>();
		try {
			PreparedStatement prepState = conn.prepareStatement("SELECT cookieType FROM Cookie");
			ResultSet set = prepState.executeQuery();
			while(set.next()){
				cookieList.add(set.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cookieList.toArray(new String[0]);//This is an hack, if I give it null it will crash, if I use toArray without a parameter it can not cast to a String[] and will crash. If it receives an array that is too small it creates a new array instead (why the method without parameter can not do this I do not know, although it could be that a generic class cannot create the correct type of array).
	}
	public boolean createPallet(String cookieType){ //Obviously used by a listener in ProductionPane to create new pallets
		try {
			conn.setAutoCommit(false);
			PreparedStatement createPalletStatement = conn.prepareStatement("INSERT INTO Pallet (cookieType) values(?)");
			createPalletStatement.setString(1,cookieType);
			createPalletStatement.executeUpdate();
			//Add DEFAULT getDate() to date in the database, could probably be done for the time also, which means that I will omit these here.
			//location & status should probably also have default values instead of setting them here. This leaves me with cookieType.
			
			PreparedStatement fetchRecipeStatement = conn.prepareStatement("SELECT ingredientName, amount FROM Recipe WHERE cookieType = ?");
			fetchRecipeStatement.setString(1, cookieType);
			ResultSet ingredientSet = fetchRecipeStatement.executeQuery();
			while(ingredientSet.next()){
				PreparedStatement setIngredientStatement = conn.prepareStatement("UPDATE Ingredient SET amountInStock = amountInStock - ? WHERE ingredientName = ?");
				setIngredientStatement.setInt(1, ingredientSet.getInt(2));
				setIngredientStatement.setString(2, ingredientSet.getString(1));
				setIngredientStatement.executeUpdate();
			}
			
			PreparedStatement checkStockStatement = conn.prepareStatement("SELECT ingredientName FROM Ingredient WHERE amountInStock < 0");
			if(checkStockStatement.executeQuery().next()){
				conn.rollback();
				return false;
			} else {
				conn.setAutoCommit(true);
				return true;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Add new pallet, should the database have add time and date of production or should it be created here?
		//Remove ingredients here, or should we use a separate method to affect the ingredients?
		return false;
	}
	public void deductIngredients(String cookieType){ //If used as a help method to createPallets change visibility to private.
		//Ask table Recipe for all tuples with cookieType as cookieType
		//Loop through the results and deduct amount for each ingredient in the Ingredients table (might need to fetch the amount of ingredients first though)
	}
	//Methods created for the PalletPane


	//Where the ProductionPane seem crystal clear how it should work, the PalletPane could use some more discussion.
}