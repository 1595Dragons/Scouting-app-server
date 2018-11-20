package javacode;

import java.io.IOException;

import javacode.UI.MainPanel;
import javafx.application.Application;
import javafx.stage.Stage;

public class ScoutingApp extends Application {

	public static boolean debug;

	@Override
	public void start(Stage primaryWindow) {

		MainPanel mainPanel = new MainPanel();
		
		try {
			primaryWindow.setScene(mainPanel.loadMainPanel());
			primaryWindow.setTitle("1595 Scouting app");
			primaryWindow.setResizable(false);
			primaryWindow.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		mainPanel.log("Loaded successfully", false);

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
