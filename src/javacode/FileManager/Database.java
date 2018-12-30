package javacode.FileManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javacode.ScoutingApp;
import javacode.Core.Debugger;
import javacode.Core.Match;
import javacode.Core.Match.Autonomous;
import javacode.Core.Match.Endgame;
import javacode.Core.Match.TeleOp;
import javacode.Core.MatchBase.DataType;
import javacode.UI.MainPanel;

public class Database {

	private String databaseFile = System.getProperty("user.dir") + "/scouting-data.db";

	public void updateDatabase(int teamNumber, boolean hasAuto, int autoSwitchCube, int autoScaleCube,
			int teleSwitchCube, int teleScaleCube, int exchangeCube, boolean canClimb, String comments) {
		// Create insert query based on entered arguments... because there aren't enough
		// arguments to this function...
		// FIXME
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

		// Deprecate any current databases (Bump up the numbers by 1)
		if (database.exists()) {
			this.deprecateDatabase();
		}

		// Create the new file.
		database.createNewFile();

		// Once the database file has been created, setup the SQL table
		// Create the query to execute on the SQL database
		// Get table headers and types of storage from the config file
		String query = "CREATE TABLE data (\"Team number\" INT NOT NULL";

		Match data = ScoutingApp.config.matchData;
		for (Autonomous autonomous : data.autonomousData) {
			query += ", \"" + autonomous.name + "\" ";

			// If the datatype is text, make the type text, otherwise its an int
			query += autonomous.datatype == DataType.Text ? "TEXT NOT NULL" : "INT NOT NULL";
		}

		for (TeleOp teleop : data.teleopData) {
			query += ", \"" + teleop.name + "\" ";

			// If the datatype is text, make the type text, otherwise its an int
			query += teleop.datatype == DataType.Text ? "TEXT NOT NULL" : "INT NOT NULL";
		}

		for (Endgame endgame : data.endgameData) {
			query += ", \"" + endgame.name + "\" ";

			// If the datatype is text, make the type text, otherwise its an int
			query += endgame.datatype == DataType.Text ? "TEXT NOT NULL" : "INT NOT NULL";
		}

		query += ", Comments TEXT)";

		Debugger.d(this.getClass(), "Creation query: " + query);

		// Execute the query
		this.executeSQL(query);

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
					this.createDatabase();
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
			databaseConnection = this.getDatabaseConnection();
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

				if (query.toUpperCase().startsWith("SELECT")) {
					ResultSet resultset = SQLStatement.executeQuery();

					result = resultSetToString(resultset);
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

	private void deprecateDatabase() {
		final File directory = new File(System.getProperty("user.dir"));

		ArrayList<File> fileArray = new ArrayList<File>();

		for (final File file : directory.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".db") && file.getName().startsWith("scouting-data")) {
				fileArray.add(file);
				Debugger.d(this.getClass(), "Found database: " + file.getName());
			}
		}

		for (int i = 0; i < fileArray.size(); i++) {
			int oldNumber = 0;
			try {
				oldNumber = Integer.parseInt(fileArray.get(i).getName().replaceAll("\\D+", ""));
			} catch (Exception e) {
				oldNumber = 0;
			}
			Debugger.d(this.getClass(), "Changing database number from " + (oldNumber) + " to " + (oldNumber + 1));
			fileArray.get(i).renameTo(new File(directory + "/scouting-data" + (oldNumber + 1) + ".db"));
		}

	}

	public boolean validateDatabase() {
		
		// Get database headers
		String getHeaderQuery = "SELECT * FROM data";
		
		ResultSet result = null;
		try {
			
			Connection databaseConnection = null;
			try {
				databaseConnection = this.getDatabaseConnection();
			} catch (ClassNotFoundException e) {
				MainPanel.logError(e);
				return false;
			}
			
			result = databaseConnection.prepareStatement(getHeaderQuery).executeQuery();
			
			final int numberOfHeaders = result.getMetaData().getColumnCount();
			String[] currentHeaderNames = new String[numberOfHeaders];
			
			// Get current headers
			for (int i = 1; i <= numberOfHeaders; i++) {
				currentHeaderNames[i - 1] = result.getMetaData().getColumnName(i);
				Debugger.d(this.getClass(), "Current header name: " + currentHeaderNames[i - 1]);
			}
			
			// Get the correct headers
			ArrayList<String> correctHeaderNames = new ArrayList<String>();
			correctHeaderNames.add("Team number");
			
			Match data = ScoutingApp.config.matchData;
			for (Autonomous autonomous : data.autonomousData) {
				correctHeaderNames.add(autonomous.name);
			}

			for (TeleOp teleop : data.teleopData) {
				correctHeaderNames.add(teleop.name);
			}

			for (Endgame endgame : data.endgameData) {
				correctHeaderNames.add(endgame.name);
			}
			correctHeaderNames.add("Comments");

			// Compare stuff
			Debugger.d(this.getClass(), String.format("Size of current headers: %s\nSize of correct headers: %s", currentHeaderNames.length, correctHeaderNames.size()));
			if (currentHeaderNames.length == correctHeaderNames.size()) {
				
				// Iterate through each header, and check if they are the same
				for (int i = 0; i < correctHeaderNames.size(); i++) {
					Debugger.d(this.getClass(), String.format("Comparing headers: %s | %s", currentHeaderNames[i], correctHeaderNames.get(i)));
					if (!currentHeaderNames[i].equals(correctHeaderNames.get(i))) {
						return false;
					}
				}
				
				return true;
				
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			MainPanel.logError(e);
			return false;
		}
	}

	private Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {

		// Load the SQLite library drivers
		Debugger.d(getClass(), "SQLite driver: " + Class.forName("org.sqlite.JDBC"));

		return DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
	}

	public static String resultSetToString(ResultSet resultset) throws SQLException {
		String result = "";

		// Get the result as a string
		// https://coderwall.com/p/609ppa/printing-the-result-of-resultset
		ResultSetMetaData meta = resultset.getMetaData();

		// Get the number of columns
		final int columns = meta.getColumnCount();

		// Get the headers
		for (int i = 1; i <= columns; i++) {
			result += meta.getColumnName(i) + "\t|\t";
		}

		// Add a new line
		result += "\n";

		// Run through each row
		while (resultset.next()) {
			// Iterator over all appropriate columns, SQL starts at 1 though... REEEEEEEEEEE
			for (int i = 1; i <= columns; i++) {
				result += resultset.getString(i);
				if (i != columns) {
					result += "\t|\t";
				} else {
					result += "\n";
				}
			}
		}

		return result;
	}

}
