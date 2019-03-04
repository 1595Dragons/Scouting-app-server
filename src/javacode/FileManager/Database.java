package javacode.FileManager;

import javacode.Core.Debugger;
import javacode.Core.Match;
import javacode.Core.Match.Autonomous;
import javacode.Core.Match.Endgame;
import javacode.Core.Match.TeleOp;
import javacode.Core.MatchBase.DataType;
import javacode.ScoutingApp;
import javacode.UI.MainPanel;

import javax.json.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class Database {

	private String databaseFile = System.getProperty("user.dir") + "/scouting-data.db";

	public void updateDatabase(int teamNumber, String[][] data) {
		// Get the headers from the data, and their values
		StringBuilder headers = new StringBuilder(), values = new StringBuilder();
		for (String[] datum : data) {
			for (int value = 0; value < 2; value++) {
				if (value == 0) {
					headers.append(String.format("\", \"%s", datum[value]));
				} else {
					values.append(String.format(", %s", datum[value]));
				}
			}
		}

		String query = String.format("INSERT INTO data (\"Team number%s\") VALUES (%s%s)", headers.toString(), teamNumber, values.toString());

		// Execute the query
		try {
			this.executeSQL(query);
			MainPanel.log("Successfully written data for team " + teamNumber, false);
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
		Debugger.d(this.getClass(), "New database successfully created: " + database.createNewFile());

		// Once the database file has been created, setup the SQL table
		// Create the query to execute on the SQL database
		// Get table headers and types of storage from the config file
		StringBuilder query = new StringBuilder("CREATE TABLE data (\"Team number\" INT NOT NULL");

		Match data = ScoutingApp.config.matchData;
		for (Autonomous autonomous : data.autonomousData) {

			// Check if the data-type is a boolean group
			if (autonomous.datatype.equals(DataType.BooleanGroup)) {
				// Get the checkboxes from the value of the data-type
				JsonObject checkBoxes = autonomous.value.get(0).asJsonObject();

				//noinspection ToArrayCallWithZeroLengthArrayArgument
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					query.append(String.format(", \"%s\" INT NOT NULL", keys[index]));
				}
			} else {
				query.append(String.format(", \"%s\" ", autonomous.name));
				// If the data-type is text, make the type text, otherwise its an int
				query.append(autonomous.datatype == DataType.Text ? "TEXT NOT NULL" : "INT NOT NULL");
			}

		}

		for (TeleOp teleop : data.teleopData) {
			// Check if the data-type is a boolean group
			if (teleop.datatype.equals(DataType.BooleanGroup)) {
				JsonObject checkBoxes = teleop.value.get(0).asJsonObject();

				//noinspection ToArrayCallWithZeroLengthArrayArgument
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					query.append(String.format(", \"%s\" INT NOT NULL", keys[index]));
				}
			} else {
				query.append(String.format(", \"%s\" ", teleop.name));
				// If the data-type is text, make the type text, otherwise its an int
				query.append(teleop.datatype == DataType.Text ? "TEXT NOT NULL" : "INT NOT NULL");
			}
		}

		for (Endgame endgame : data.endgameData) {
			// Check if the data-type is a boolean group
			if (endgame.datatype.equals(DataType.BooleanGroup)) {
				JsonObject checkBoxes = endgame.value.get(0).asJsonObject();

				//noinspection ToArrayCallWithZeroLengthArrayArgument
				String[] keys = checkBoxes.keySet().toArray(new String[checkBoxes.size()]);
				for (int index = 0; index < checkBoxes.size(); index++) {
					query.append(String.format(", \"%s\" INT NOT NULL", keys[index]));
				}
			} else {
				query.append(String.format(", \"%s\" ", endgame.name));
				// If the data-type is text, make the type text, otherwise its an int
				query.append(endgame.datatype == DataType.Text ? "TEXT NOT NULL" : "INT NOT NULL");
			}
		}

		query.append(", Comments TEXT)");

		Debugger.d(this.getClass(), "Creation query: " + query);

		// Execute the query
		this.executeSQL(query.toString());

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
				}
			}
		} else {
			// Just set the return variable to whether or not the file exists
			exists = database.exists();
		}

		return exists;
	}

	public ResultSet executeSQL(String query) throws SQLException {
		Debugger.d(getClass(), "Executing query: " + query);

		// Create the return variable
		ResultSet result = null;

		// Get the database connection
		Connection databaseConnection;

		try {
			databaseConnection = this.getDatabaseConnection();
		} catch (ClassNotFoundException e) {
			MainPanel.logError(e);
			return null;
		}

		// Execute the following only if the database connection isn't null
		if (databaseConnection != null) {

			// Prepare the query to prevent SQL injection attacks
			java.sql.PreparedStatement SQLStatement = databaseConnection.prepareStatement(query);

			// If the prepared statement isn't null, go ahead and execute it
			if (SQLStatement != null) {

				if (query.toUpperCase().startsWith("SELECT")) {
					result = SQLStatement.executeQuery();
				} else {
					SQLStatement.execute();
				}
			}
		}
		// Return the result of the execution
		return result;
	}

	private void deprecateDatabase() {
		final File directory = new File(System.getProperty("user.dir"));

		ArrayList<File> fileArray = new ArrayList<>();

		File[] files = directory.listFiles();

		// If there are no files, just return now
		if (files == null) {
			return;
		}

		for (final File file : files) {
			if (file.isFile() && file.getName().endsWith(".db") && file.getName().startsWith("scouting-data")) {
				fileArray.add(file);
				Debugger.d(this.getClass(), "Found database: " + file.getName());
			}
		}

		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < fileArray.size(); i++) {
			int oldNumber = 0;
			try {
				oldNumber = Integer.parseInt(fileArray.get(i).getName().replaceAll("\\D+", ""));
			} catch (Exception ignored) {
			}
			Debugger.d(this.getClass(), "Changing database number from " + (oldNumber) + " to " + (oldNumber + 1));
			Debugger.d(this.getClass(), "File successfully renamed: " + fileArray.get(i).renameTo(new File(directory + "/scouting-data" + (oldNumber + 1) + ".db")));

		}

	}

	public boolean validateDatabase() {

		// Get database headers
		String getHeaderQuery = "SELECT * FROM data";

		ResultSet result;
		try {

			Connection databaseConnection;
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

			// Close the result when done
			result.close();

			// Get the correct headers
			ArrayList<String> correctHeaderNames = new ArrayList<>();
			correctHeaderNames.add("Team number");

			Match data = ScoutingApp.config.matchData;
			for (Autonomous autonomous : data.autonomousData) {
				if (autonomous.datatype.equals(DataType.BooleanGroup)) {
					JsonObject checkBoxes = autonomous.value.get(0).asJsonObject();
					Collection<String> keys = checkBoxes.keySet();
					correctHeaderNames.addAll(keys);
				} else {
					correctHeaderNames.add(autonomous.name);
				}
			}

			for (TeleOp teleop : data.teleopData) {
				if (teleop.datatype.equals(DataType.BooleanGroup)) {
					JsonObject checkBoxes = teleop.value.get(0).asJsonObject();
					Collection<String> keys = checkBoxes.keySet();
					correctHeaderNames.addAll(keys);
				} else {
					correctHeaderNames.add(teleop.name);
				}
			}

			for (Endgame endgame : data.endgameData) {
				if (endgame.datatype.equals(DataType.BooleanGroup)) {
					JsonObject checkBoxes = endgame.value.get(0).asJsonObject();
					Collection<String> keys = checkBoxes.keySet();
					correctHeaderNames.addAll(keys);
				} else {
					correctHeaderNames.add(endgame.name);
				}
			}
			correctHeaderNames.add("Comments");

			// Compare stuff
			Debugger.d(this.getClass(), String.format("Size of current headers: %s\nSize of correct headers: %s",
					currentHeaderNames.length, correctHeaderNames.size()));
			if (currentHeaderNames.length == correctHeaderNames.size()) {

				// Iterate through each header, and check if they are the same
				for (int i = 0; i < correctHeaderNames.size(); i++) {
					Debugger.d(this.getClass(), String.format("Comparing headers: %s | %s", currentHeaderNames[i],
							correctHeaderNames.get(i)));
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
		//noinspection SpellCheckingInspection
		Debugger.d(getClass(), "SQLite driver: " + Class.forName("org.sqlite.JDBC"));
		return java.sql.DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
	}

	public static String resultSetToString(ResultSet resultset) {
		StringBuilder result = new StringBuilder();

		// Get the result as a string
		// https://coderwall.com/p/609ppa/printing-the-result-of-resultset
		try {
			ResultSetMetaData meta = resultset.getMetaData();

			// Get the number of columns
			final int columns = meta.getColumnCount();

			// Get the headers
			for (int i = 1; i <= columns; i++) {
				result.append(String.format("%s\t|\t", meta.getColumnName(i)));
			}

			// Add a new line
			result.append("\n");

			// Run through each row
			while (resultset.next()) {
				// Iterator over all appropriate columns, SQL starts at 1 though... REEEEEEEEEEE
				for (int i = 1; i <= columns; i++) {
					result.append(resultset.getString(i));
					if (i != columns) {
						result.append("\t|\t");
					} else {
						result.append("\n");
					}
				}
			}

			resultset.close();

			return result.toString();

		} catch (SQLException e) {
			MainPanel.logError(e);
			return "";
		}
	}

	public void exportToCSV(File file) {
		StringBuilder out = new StringBuilder();

		// Get everything out of the database
		try {
			ResultSet result = this.executeSQL("SELECT * FROM data");

			ResultSetMetaData meta = result.getMetaData();

			// Get the number of columns
			final int columns = meta.getColumnCount();

			// Get the headers
			for (int i = 1; i <= columns; i++) {
				out.append(String.format("%s,", meta.getColumnName(i).replace(",", "，")));
			}

			// Add a new line
			out.append("\n");

			// Run through each row
			while (result.next()) {
				// Iterator over all appropriate columns, SQL starts at 1 though... REEEEEEEEEEE
				for (int i = 1; i <= columns; i++) {
					out.append(result.getString(i).replace(",", "，"));
					if (i != columns) {
						out.append(",");
					} else {
						out.append("\n");
					}
				}
			}
			result.close();
		} catch (SQLException e) {
			MainPanel.logError(e);
		}

		try {
			// Write the out string to a file
			FileWriter fw = new FileWriter(file);
			fw.write(out.toString());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			MainPanel.logError(e);
		}

		MainPanel.log("Successfully exported data", false);

	}

}
