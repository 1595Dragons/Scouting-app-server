package Application.release;

import java.io.IOException;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import Application.Debugger;
import Application.Updater;

public class ScoutingApp {

	public static boolean debug = false;

	public static StreamConnection currentConnection;

	public static StreamConnectionNotifier streamConnNotifier;

	public static void main(String args[]) {
		try {
			debug = Boolean.parseBoolean(args[0]);
		} catch (ArrayIndexOutOfBoundsException noArgs) {
			debug = false;
		}
		Debugger.d(ScoutingApp.class, "Checking for updates");
		if (Updater.updateAvalible()) {
			Updater.showUpdatePrompt();
		}
		Debugger.d(ScoutingApp.class, "Creating the main info window");
		MainPanel infoWindow = new MainPanel();
		Debugger.d(ScoutingApp.class, "Showing the main info window");
		infoWindow.showWindow();
		Debugger.d(ScoutingApp.class, "Does file exist?");
		if (!ScoutingFile.FileExists()) {
			Debugger.d(ScoutingApp.class, "Creating file");
			Debugger.log("File missing. Creating new file", false);
			ScoutingFile.makeFile();
		}
		Debugger.d(ScoutingApp.class, "Updating file info");
		ScoutingFile.setFileText();
		Debugger.d(ScoutingApp.class, "Enabling goto button");
		ScoutingFile.setupButton();
		Debugger.d(ScoutingApp.class, "Resetting devices");
		DeviceManagement.reset();
		Debugger.d(ScoutingApp.class, "Is bluetooth enabled?");
		if (!Bluetooth.isEnabled()) {
			Debugger.log("Bluetooth is not enabled on this device.", true);
		} else {

			Bluetooth.changeMACDisplay();

			// Create a UUID for SPP, and then create the URL
			UUID uuid = new UUID("1101", true);
			String connectionString = "btspp://localhost:" + uuid + ";name=SpudSPPServer";
			Debugger.d(ScoutingApp.class, "Conenction URL: " + connectionString);

			// open server URL using the created URL
			streamConnNotifier = null;
			try {
				streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
			} catch (IOException e1) {
				Debugger.log(String.format("Error, cannot open URL: %s", e1.getMessage()), true);
				Debugger.d(ScoutingApp.class, "Error: " + e1.getMessage());
				e1.printStackTrace();
			}

			Debugger.log("Ready to recieve data!", false);
			new Thread(new EnterTeamNumberPrompt()).start();
			while (true) {
				// Check if receiving a connection
				// On connection, open it, and pass it to a newly created thread
				try {
					currentConnection = streamConnNotifier.acceptAndOpen();
					if (currentConnection != null) {
						DeviceManagement.deviceConnected(
								RemoteDevice.getRemoteDevice(currentConnection).getFriendlyName(false));
						new Thread(new SspServer()).start();
					}
				} catch (IOException e) {
					Debugger.log(String.format("Error creating a new thread: %s", e.getMessage()), true);
					Debugger.d(ScoutingApp.class, "Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}

}
