package javacode;


import java.awt.Color;

public class Debugger {

	public static void d(Class<?> name, String message) {
		if (javacode.ScoutingApp.debug) {
			System.out.println(String.format("%s: %s", name.getName(), message));
		}
	}

	public static void log(String message, boolean error) {
		javacode.MainPanel.ConsoleText.setText(message);
		if (error) {
			javacode.MainPanel.ConsoleText.setForeground(Color.RED);
		} else {
			javacode.MainPanel.ConsoleText.setForeground(Color.WHITE);
		}
	}

	public static void logTest(String message, boolean error) {
		javacode.MainPanel.ConsoleText.setText(message);
		if (error) {
			javacode.MainPanel.ConsoleText.setForeground(Color.RED);
		} else {
			javacode.MainPanel.ConsoleText.setForeground(Color.WHITE);
		}
	}
}
