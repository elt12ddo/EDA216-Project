package database;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

/**
 * Database is a class that specifies the interface to the cookie database. Uses
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
	
	//Common Methods
	public String[] getCookieTypes(){
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
		return cookieList.toArray(new String[0]);
	}
	//Methods created for the ProductionPane
	public boolean createPallet(String cookieType){
		try {
			conn.setAutoCommit(false);
			PreparedStatement createPalletStatement = conn.prepareStatement("INSERT INTO Pallet (date, time, cookieType) values(?,?,?)");
			createPalletStatement.setDate(1, new Date(System.currentTimeMillis()));
			createPalletStatement.setTime(2, new Time(System.currentTimeMillis()));
			createPalletStatement.setString(3,cookieType);
			createPalletStatement.executeUpdate();
			
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
		return false;
	}
	//Methods created for the PalletPane
	public DefaultListModel<String> fetchPallets(String date1, String date2, String cookieType){
		DefaultListModel<String> palletList = new DefaultListModel<String>();
		try {
			PreparedStatement fetchPallets;
			if(cookieType.compareTo("All") == 0){
				fetchPallets = conn.prepareStatement("SELECT palletId FROM Pallet WHERE date >= ? AND date <= ?");
			} else {
				fetchPallets = conn.prepareStatement("SELECT palletId FROM Pallet WHERE date >= ? AND date <= ? AND cookieType = ?");
				fetchPallets.setString(3, cookieType);
			}
			fetchPallets.setString(1, date1);
			fetchPallets.setString(2, date2);
			ResultSet palletSet = fetchPallets.executeQuery();
			
			while(palletSet.next()){
				palletList.addElement(palletSet.getString(1));
			}
			return palletList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletList;
	}
	public String[] getPalletData(String palletId){
		String[] palletData = new String[7];
		try {
			PreparedStatement statement = conn.prepareStatement("SELECT date, time, orderId, location, dateDelivered, cookieType, status FROM Pallet WHERE palletId = ?");
			statement.setString(1, palletId);			
			ResultSet palletSet = statement.executeQuery();
			
			palletSet.next();
			for(int k = 1; k <= 7; k++){
				palletData[k-1] = palletSet.getString(k);
			}
			
			setLocationData(palletData);
			if(palletData[6].compareTo("0") == 0){
				palletData[6] = "Not blocked";
			} else {
				palletData[6] = "Blocked";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return palletData;
	}
	private void setLocationData(String[] palletData) {
		if(palletData[3].compareTo("0") == 0){
			palletData[3] = "In storage";
		} else if(palletData[3].compareTo("1") == 0){
			palletData[3] = "In transit";
		} else if(palletData[3].compareTo("2") == 0){
			palletData[3] = "Delivered";
		}
	}
	public boolean setBlocked(String palletId, boolean block){
		try {
			conn.setAutoCommit(false);
			PreparedStatement statement = conn.prepareStatement("UPDATE Pallet SET status = ? WHERE palletId = ?");
			statement.setBoolean(1, block);
			statement.setString(2, palletId);
			statement.executeUpdate();
			
			PreparedStatement checkBlock = conn.prepareStatement("SELECT status FROM Pallet WHERE palletId = ?");
			checkBlock.setString(1, palletId);
			ResultSet result = checkBlock.executeQuery();
			result.next();
			if(result.getBoolean(1) != block){
				conn.rollback();
				return false;
			}
			conn.setAutoCommit(true);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}