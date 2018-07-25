package Application;

import Application.debug.ScoutingAppTest;
import Application.release.ScoutingApp;

public class Debugger {

	public static void d(Class<?> name, String message) {
		if (ScoutingApp.debug || ScoutingAppTest.debug) {
			System.out.println(String.format("%s: %s", name.getName(), message));
		}
	}
}
