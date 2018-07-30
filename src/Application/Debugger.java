package Application;

import java.awt.Color;

public class Debugger {

	public static void d(Class<?> name, String message) {
		if (Application.release.ScoutingApp.debug || Application.debug.ScoutingAppTest.debug) {
			System.out.println(String.format("%s: %s", name.getName(), message));
		}
	}

	public static void log(String message, boolean error) {
		Application.release.MainPanel.ConsoleText.setText(message);
		if (error) {
			Application.release.MainPanel.ConsoleText.setForeground(Color.RED);
		} else {
			Application.release.MainPanel.ConsoleText.setForeground(Color.WHITE);
		}
	}

	public static void logTest(String message, boolean error) {
		Application.debug.MainPanel.ConsoleText.setText(message);
		if (error) {
			Application.debug.MainPanel.ConsoleText.setForeground(Color.RED);
		} else {
			Application.debug.MainPanel.ConsoleText.setForeground(Color.WHITE);
		}
	}
}
