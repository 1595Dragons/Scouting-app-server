package javacode.Core;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javacode.UI.MainPanel;

public class Database {

	private String databaseFile = System.getProperty("user.dir") + "/scouting-data.db";

	public void updateDatabase(int teamNumber, boolean hasAuto, int autoSwitchCube, int autoScaleCube,
			int teleSwitchCube, int teleScaleCube, int exchangeCube, boolean canClimb, String comments) {
		// Create insert query based on entered arguments... because there aren't enough
		// arguments to this function...
		String query = "INSERT INTO data (teamNumber, hasAuto, autoSwitchCube, autoScaleCube, teleSwitchCube, teleScaleCube, exchangeCube, canClimb, comments) "
				+ "VALUES ("
				+ String.format("%s, %s, %s, %s, %s, %s, %s, %s, '%s');", teamNumber, hasAuto ? 1 : 0, autoSwitchCube,
						autoScaleCube, teleSwitchCube, teleScaleCube, exchangeCube, canClimb ? 1 : 0, comments);

		// Execute the query
		try {
			executeSQL(query);
			new MainPanel().log("Successfully written data for team " + teamNumber, false);
		} catch (SQLException e) {
			MainPanel.logError(e);
		}

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
		// Create the query to execute on the SQL database
		String sqlQuery = "CREATE TABLE data (" + "teamNumber INT NOT NULL, " + "hasAuto INT NOT NULL, "
				+ "autoSwitchCube INT NOT NULL, " + "autoScaleCube INT NOT NULL, " + "teleSwitchCube INT NOT NULL,"
				+ "teleScaleCube INT NOT NULL," + "exchangeCube INT NOT NULL," + "canClimb INT NOT NULL,"
				+ "comments TEXT)";

		// Execute the query
		executeSQL(sqlQuery);

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

	public String executeSQL(String query) throws SQLException {
		Debugger.d(getClass(), "Executing query: " + query);

		// Create the return variable
		String result = "";

		// Get the database connection
		Connection databaseConnection = null;

		try {
			databaseConnection = getDatabaseConnection();
		} catch (ClassNotFoundException e) {
			MainPanel.logError(e);
			return null;
		}

		// Execute the following only if the database connection isn't null
		if (databaseConnection != null) {

			// Prepare the query to prevent SQL injection attacks
			PreparedStatement SQLStatement = databaseConnection.prepareStatement(query);

			// If the prepared statement isn't null, go ahead and execute it
			if (SQLStatement != null) {

				if (query.startsWith("SELECT")) {
					ResultSet resultset = SQLStatement.executeQuery();
					
					// Get the result as a string
					// https://coderwall.com/p/609ppa/printing-the-result-of-resultset
					ResultSetMetaData metadata = resultset.getMetaData();
					
					int columnsNumber = metadata.getColumnCount();
					while (resultset.next()) {
						for (int i = 1; i <= columnsNumber; i++) {
							if (i > 1)
								System.out.print(",  ");
							String columnValue = resultset.getString(i);
							result += (columnValue + " " + metadata.getColumnName(i) + "\n");
						}
						result += ("\n");
					}
					
				} else {
					SQLStatement.execute();
				}

				// Close the statement at the end, to free up resources
				SQLStatement.close();

			}
			// Close the database connection
			databaseConnection.close();
		}
		// Return the result of the execution
		Debugger.d(getClass(), "Result of execution: " + result);
		return result;
	}

	private Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {

		// Load the SQLite library drivers
		Debugger.d(getClass(), "SQLite driver: " + Class.forName("org.sqlite.JDBC"));

		return DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
	}

}
