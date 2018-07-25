
package Application.debug;

import Application.Debugger;

public class ScoutingAppTest {

	public static boolean debug = false;

	public static void main(String args[]) {
		debug = Boolean.parseBoolean(args[0]);
		Debugger.d(ScoutingAppTest.class, "Creating the main info window");
		InfoTest infoWindow = new InfoTest();
		Debugger.d(ScoutingAppTest.class, "Showing the main info window");
		infoWindow.showWindow();
		Debugger.d(ScoutingAppTest.class, "Does file exist?");
		if (!ScoutingFileTest.FileExists()) {
			Debugger.d(ScoutingAppTest.class, "Creating file");
			ScoutingFileTest.makeFile();
		}
		Debugger.d(ScoutingAppTest.class, "Updating file info");
		ScoutingFileTest.setFileText();
		Debugger.d(ScoutingAppTest.class, "Enabling goto button");
		ScoutingFileTest.setupButton();
		
	}

}
