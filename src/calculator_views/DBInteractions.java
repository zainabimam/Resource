package calculator_views;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * This class is responsible for interactions with database related to input
 * data, such as list of components and their attributes
 */

public class DBInteractions {
	/**
	 * Path to the location of database
	 */
	// private static String connectionString =
	// "jdbc:sqlite:C:\\Users\\emozmah\\workspace\\er-ct-tco\\DB\\TCO.sqlite";
	// "jdbc:sqlite:C:\\Users\\imam\\workspace\\ResourceUtilizationCalculator\\src\\calculator_resources\\Application_Category_Data.sqlite"
	private static String connectionString = "jdbc:sqlite:Application_Category_Data.sqlite";
	private static Connection connection;

	/**
	 * 
	 */
	public static Connection DbConnector() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(connectionString);
			//JOptionPane.showMessageDialog(null, "Connection succesful");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in connecting to database");
			return null;
		}
	}

	public DBInteractions() {

		connection = DbConnector();
	}

	/**
	 * Read a value from database Note: one key is not enough for reading DB,
	 * this should be completed after having the structure of DB
	 * 
	 * @param productNo:
	 *            product number of the entry to be read, if known
	 * @param queryKey:
	 *            the key that can be used for searching in DB to find the
	 *            correct entry
	 * @param tableName:
	 *            name of the table to search
	 */
	public static int ReadFromDB(String tableName, String queryKey, int ID) {
		try {
			int result;
			if (connection == null)
				connection = DbConnector();
			String query = "select " + queryKey + " from " + tableName + " where ID=" + ID;
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			result = rs.getInt(queryKey);
			pst.close();
			rs.close();
			return result;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in reading integer value from database");
			return 0;
		}
	}

	/**
	 * Read a value from database Note: one key is not enough for reading DB,
	 * this should be completed after having the structure of DB
	 * 
	 * @param productNo:
	 *            product number of the entry to be read, if known
	 * @param queryKey:
	 *            the key that can be used for searching in DB to find the
	 *            correct entry
	 * @param tableName:
	 *            name of the table to search
	 */
	public static String ReadStringFromDB(String tableName, String queryKey) {
		try {
			String result;
			if (connection == null)
				connection = DbConnector();
			String query = "select " + queryKey + "from " + tableName;
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			result = rs.getString(queryKey);

			pst.close();
			rs.close();
			return result;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in reading string value from database");
			return null;
		}
	}

	
	
	/**
	 * Read a value from database Note: one key is not enough for reading DB,
	 * this should be completed after having the structure of DB
	 * 
	 * @param productNo:
	 *            product number of the entry to be read, if known
	 * @param queryKey:
	 *            the key that can be used for searching in DB to find the
	 *            correct entry
	 * @param tableName:
	 *            name of the table to search
	 */
	public static double ReadDoubleFromDB(String tableName, String queryKey, int ID) {
		try {
			Double result;
			if (connection == null)
				connection = DbConnector();
			String query = "select " + queryKey + "from " + tableName + " where ID=" + ID;
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			result = rs.getDouble(queryKey);
			pst.close();
			rs.close();
			return result;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error in reading double value from database");
			return 0;
		}
	}

	/**
	 * Creating a new table in DB
	 * 
	 * @param tableName:
	 *            name of the new table
	 * @param Keys:
	 *            an array of strings to be used as the column titles of new
	 *            table
	 */
	public static void CreateTable(String tableName) {
		try {
			if (!connection.isClosed())
				connection.close();
			connection = DbConnector();

			Statement stmt = null;
			stmt = connection.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			String query = "CREATE TABLE " + tableName + "(RowNumber INTEGER not NULL, "
					+ " PRIMARY KEY ( RowNumber ))";
			stmt.executeUpdate(query);
			stmt.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not creat new table");
		}
	}

	public static void AddColumn(String tableName, String key, String type) {
		try {
			if (connection == null)
				connection = DbConnector();
			String query = "ALTER TABLE " + tableName + " ADD " + key + " " + type;
			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void AddRow(String tableName, String key, int value) {
		try {
			if (connection == null)
				connection = DbConnector();
			String query = "insert into " + tableName + " (" + key + ") values (" + value + ")";

			PreparedStatement pst = connection.prepareStatement(query);
			pst.executeUpdate();
			pst.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can not add row");
		}
	}

}
