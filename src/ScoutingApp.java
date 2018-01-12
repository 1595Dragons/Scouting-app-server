import java.io.IOException;

import javax.bluetooth.LocalDevice;

public class ScoutingApp {

	public static void main(String args[]) throws IOException {
		
		display d  = new display();
		server blueToothServer = new server();
		d.makePanel();
		if (selectFile.fileMissing()) {
			d.feed.setText("File missing, creating new file");
			selectFile.makeFile();
			d.feed.setText("File created! Awaiting data...");
		} else {
			d.feed.setText("File found! Awaiting data...");
			d.MACAddress.setText("MAC Address: " + LocalDevice.getLocalDevice().getBluetoothAddress().replace("-", ":").toUpperCase());
		}
		
		blueToothServer.startServer();
		
	}
	
}
