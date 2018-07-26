package Application;

import java.awt.Color;

import Application.debug.InfoTest;
import Application.debug.ScoutingAppTest;
import Application.release.ScoutingApp;

public class Debugger {

	public static void d(Class<?> name, String message) {
		if (ScoutingApp.debug || ScoutingAppTest.debug) {
			System.out.println(String.format("%s: %s", name.getName(), message));
		}
	}
	public static void log(String message, boolean error) {
		// TODO: Finish for official one
	}
	
	public static void logTest(String message, boolean error) {
		InfoTest.ConsoleText.setText(message);
		if (error) {
			InfoTest.ConsoleText.setForeground(Color.RED);
		} else {
			InfoTest.ConsoleText.setForeground(Color.WHITE);
		}
	}
}
