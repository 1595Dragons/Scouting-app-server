package Application;

import Application.debug.ScoutingAppTest;
import Application.release.ScoutingApp;

public class Debugger {

	public static void d(String message) {
		if (ScoutingApp.debug || ScoutingAppTest.debug) {
			System.out.print(message);
		}
	}
}
