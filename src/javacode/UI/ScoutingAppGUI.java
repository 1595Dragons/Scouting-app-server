package javacode.UI;

import javacode.Core.Debugger;
import javacode.FileManager.Database;
import javacode.ScoutingApp;

import java.io.IOException;
import java.sql.SQLException;

public class ScoutingAppGUI extends javafx.application.Application implements Runnable {

	public void start(javafx.stage.Stage primaryWindow) {

		primaryWindow.setScene(new MainPanel().loadMainPanel());
		primaryWindow.setTitle("1595 Scouting app");
		primaryWindow.show();

		// Check for valid config
		try {
			ScoutingApp.config.validateConfig();
		} catch (IOException e) {
			// If config validation fails, just return
			MainPanel.logError(e);
			return;
		}

		// Check if the database exists
		Database database = new Database();
		boolean databaseExists = database.databaseExists(true);
		Debugger.d(getClass(), "Database exists: " + databaseExists);

		// If no database exists, create one
		if (!databaseExists) {
			try {
				database.createDatabase();
			} catch (IOException | SQLException e) {
				MainPanel.logError(e);
				return;
			}
		}

		// Check if the database is valid
		if (!database.validateDatabase()) {
			MainPanel.log("Database is invalid, creating new database", true);
			try {
				database.createDatabase();
			} catch (IOException | SQLException e) {
				MainPanel.logError(e);
				return;
			}

		}
	}

	public void run() {
		launch(ScoutingAppGUI.class);
	}
}

