package javacode;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScoutingApp extends Application {

	public static boolean debug;

	@Override
	public void start(Stage mainPanel) throws Exception {

		Parent root = null;

		// Get the path of the main panel's fxml file
		URL path = getClass().getResource("fxml/MainPanel.fxml");
		Debugger.d(getClass(), "Path: " + path.toString());

		try {
			root = FXMLLoader.load(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (root != null) {
			Scene scene = new Scene(root);
			mainPanel.setScene(scene);
			mainPanel.setTitle("1595 Scouting app");
			mainPanel.show();
		} else {
			throw new Exception("Cannot load main panel FXML");
		}

	}

	public static void main(String[] args) {

		// Check if the debugger is enabled
		try {
			for (String arg : args) {
				if (Boolean.parseBoolean(arg)) {
					debug = true;
					break;
				} else {
					debug = false;
				}
			}
		} catch (Exception e) {
			debug = false;
		}

		Debugger.d(ScoutingApp.class, "Debugging enabled: " + debug);

		launch(args);
	}
}
