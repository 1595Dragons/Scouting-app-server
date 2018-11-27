package javacode.Core;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javacode.UI.MainPanel;

public class Database {

	private String databaseFile = System.getProperty("user.dir") + "/scouting-data.db";

	//private DataCollection data;

	public void updateDatabase() {
		// TODO: Update the data in the database (Basically add new data, like a write
		// to)
	}

	public void createDatabase() throws IOException, SQLException {

		// Create an object for the physical database file on disk
		File database = new File(databaseFile);

		// Delete the current file (because we're recreating it)
		if (database.exists()) {
			database.delete();
		}

		// Create the new file.
		database.createNewFile();

		// Once the database file has been created, setup the SQL table
		Connection databaseConnection = null;
		try {
			databaseConnection = getDatabaseConnection();
		} catch (ClassNotFoundException | SQLException e) {
			MainPanel.logError(e);
			return;
		}
		
		// Check if the connection is not null
		if (databaseConnection != null) {
			
			Statement SQLStatement = databaseConnection.createStatement();
			
			// Create the query to execute on the SQL database
			String sqlQuery = "CREATE TABLE data (" + 
							  "teamNumber INT NOT NULL, "+
							  "hasAuto INT NOT NULL, "+
							  "autoSwitchCube INT NOT NULL, "+
							  "autoScaleCube INT NOT NULL, "+
							  "teleSwithCube INT NOT NULL,"+
							  "teleScaleCube INT NOT NULL,"+
							  "exchangeCube INT NOT NULL,"+
							  "canClimb INT NOT NULL,"+
							  "comments TEXT)";
			
			// Execute the query
			SQLStatement.execute(sqlQuery);
			SQLStatement.close();
			databaseConnection.close();
			
		} else {
			// Throw an error
			throw new SQLException("Database connection is null");
		}

	}

	public boolean databaseExists(boolean autoCreate) {

		// Create a variable to be returned
		boolean exists = false;

		// Create an object for the physical database file on disk
		File database = new File(databaseFile);

		// Determine if the file should be automatically created if missing
		if (autoCreate) {
			// Check if the file exists
			if (database.exists()) {
				exists = true;
			} else {
				// If it doesn't, make a new file
				try {
					createDatabase();
					exists = true;
				} catch (IOException | SQLException e) {
					// If there's an error, return false, and log the error
					MainPanel.logError(e);
					exists = false;
				}
			}
		} else {
			// Just set the return variable to whether or not the file exists
			exists = database.exists();
		}

		return exists;
	}

	public Object executeSQL(String query) {
		// TODO: Execute a SQL query on the database, return whatever is appropriate...
		return null;
	}

	private Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {

		// Load the SQLite library drivers
		Debugger.d(getClass(), "SQLite driver: " + Class.forName("org.sqlite.JDBC"));
		
		return DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
	}

}
